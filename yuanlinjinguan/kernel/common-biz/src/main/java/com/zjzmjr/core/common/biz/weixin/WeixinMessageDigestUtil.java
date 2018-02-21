package com.zjzmjr.core.common.biz.weixin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.cache.redis.JedisPull;
import com.zjzmjr.core.model.weixin.wechat.Token;
import com.zjzmjr.core.model.weixin.wechat.WeixinUserInfo;

public class WeixinMessageDigestUtil {

    private static final Logger log = LoggerFactory.getLogger(WeixinMessageDigestUtil.class);

    private static final WeixinMessageDigestUtil _instance = new WeixinMessageDigestUtil();

    private static String token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    
    private static String access_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private static String showqrcode = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
    
    //action_name二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久
    //action_info二维码详细信息 场景值ID，scene_id 临时二维码时为32位非0整型，永久二维码时最大值为100000
    private static String output = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": SCENEID}}}";
    
//    private static String output = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": SCENEID}}}";
    
    private static String APPID = PropertyUtils.getPropertyFromFile("commBiz", "WX_APPID");
    
    private static String APPSECRET = PropertyUtils.getPropertyFromFile("commBiz", "WX_APPSECRET");
    
    // private static String REDIRECT_URI = "http://service.cbylsj.com";

    //private static String REDIRECT_URI = "http://service.cbylsj.com/my-user.html";// 测试

    private static String SCOPE = "snsapi_userinfo";

    private static MessageDigest alga;
    
    private static double pi = 3.14159265358979324;
    private static double a = 6378245.0;
    private static double ee = 0.00669342162296594323;
    public final static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    private WeixinMessageDigestUtil() {
        try {
            alga = MessageDigest.getInstance("SHA-1");
        } catch (Exception e) {
            throw new InternalError("init MessageDigest error:" + e.getMessage());
        }
    }

    public static WeixinMessageDigestUtil getInstance() {
        return _instance;
    }

    public static String byte2hex(byte[] b) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < b.length; i++) {
            tmp = (Integer.toHexString(b[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static String encipher(String strSrc) {
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        alga.update(bt);
        strDes = byte2hex(alga.digest()); // to HexString
        return strDes;
    }

    /**
     * 获取接口访问凭证
     * 
     * @param appid
     *            凭证
     * @param appsecret
     *            密钥
     * @return
     */
    public static Token getToken(String code) {
        Token token = null;
        String requestUrl = token_url.replace("APPID", APPID).replace("SECRET", APPSECRET).replaceAll("CODE", code);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                if(jsonObject.containsKey("errcode")){
                    log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                }else{
                    token = new Token();
                    token.setAccessToken(jsonObject.getString("access_token"));
                    token.setExpiresIn(jsonObject.getInt("expires_in"));
                    token.setOpenid(jsonObject.getString("openid"));
                }
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }
    
    public static String getTicket() {
        String ticket = null;
        String access_token = JedisPull.getObject("access_token", String.class);
        if(Util.isNull(access_token)){
            access_token = getAccessToken(); // ACCESS_TOKEN; //有效期为7200秒
            JedisPull.setObject("access_token", access_token, 3600);
        }
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + access_token;
        //scene_id 场景ID
        output = output.replace("SCENEID", "123");
        String result = httpRequest(url,"POST",output);
        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println(jsonObject);
        ticket = jsonObject.getString("ticket");
        return ticket;
    }
        
    // 通过ticket换取二维码URL
    public static String getQrCode() {
        String ticket = getTicket();
//        System.out.println(ticket);
        showqrcode = showqrcode.replace("TICKET", urlEnodeUTF8(ticket));
        return showqrcode;
    }
    
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
        //创建一个Buffer字符串 
        byte[] buffer = new byte[1024]; 
        //每次读取的字符串长度，如果为-1，代表全部读取完毕 
        int len = 0; 
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len); } 
        //关闭输入流 
        inStream.close(); 
        //把outStream里的数据写入内存 
        return outStream.toByteArray(); 
        } 
    
    public static String httpRequest(String reqUrl, String method, String outputStr){
        BufferedReader in = null;
        URL url;
        String data = "";
        try {
            url = new URL(reqUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod(method);
            if (null != outputStr) {
                OutputStream outputStream = httpConn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            httpConn.connect();
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));// 注意，这里要声明字符编码是UTF-8，否则会乱码
            String line;
            while ((line = in.readLine()) != null) {
                data += line;
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public static String getAccessToken() {
        String accessToken = "";
        String requestUrl = access_token.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                accessToken = jsonObject.getString("access_token");
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("获取accessToken失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    public static ResultEntry<WeixinUserInfo> getUserInfo(String accessToken, String openId) {
        ResultEntry<WeixinUserInfo> result = new ResultEntry<WeixinUserInfo>();
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
                // 用户的标识
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                // weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                // 用户关注时间
                // weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 数据保存在结果集
                result.setT(weixinUserInfo);
            } catch (Exception e) {
                // if (0 == weixinUserInfo.getSubscribe()) {
                // log.error("用户{}已取消关注", weixinUserInfo.getOpenId());
                // } else {
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                // }
            }
        }
        return result;
    }

    /**
     * 发送https请求
     * 
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return jsonObject;
    }

    public static String getCodeRequest(String home) {

        String GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=1#wechat_redirect";
        
        String result = null;

        GetCodeRequest = GetCodeRequest.replace("APPID", urlEnodeUTF8(APPID));

        GetCodeRequest = GetCodeRequest.replace("REDIRECT_URI", urlEnodeUTF8(home));

        GetCodeRequest = GetCodeRequest.replace("SCOPE", SCOPE);

        result = GetCodeRequest;

        return result;

    }

    public static String urlEnodeUTF8(String str) {

        String result = str;

        try {

            result = URLEncoder.encode(str, "UTF-8");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return result;

    }

    
    public static void main(String[] args) {
//    	  System.out.println(getAccessToken());
//        String signature = "b7982f21e7f18f640149be5784df8d377877ebf9";
//        String timestamp = "1365760417";
//        String nonce = "1365691777";
//
//        String[] ArrTmp = { "token", timestamp, nonce };
//        Arrays.sort(ArrTmp);
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < ArrTmp.length; i++) {
//            sb.append(ArrTmp[i]);
//        }
//        String pwd = encipher(sb.toString());
//
//        if (signature.equals(pwd)) {
//            System.out.println("token 验证成功~!");
//        } else {
//            System.out.println("token 验证失败~!");
//        System.out.println(getCodeRequest());
//        }
    }

    /**
     * WGS-84：是国际标准，GPS坐标（Google Earth使用、或者GPS模块）
     * GCJ-02：中国坐标偏移标准，Google Map、高德、腾讯使用
     * BD-09：百度坐标偏移标准，Baidu Map使用
     * 
     * @param lat
     * @param lon
     * @return
     */
    public static double[] wgs2bd(double lat, double lon) {
           double[] wgs2gcj = wgs2gcj(lat, lon);
           double[] gcj2bd = gcj2bd(wgs2gcj[0], wgs2gcj[1]);
           return gcj2bd;
    }

    //GCJ坐标转换为百度坐标
    public static double[] gcj2bd(double lat, double lon) {
           double x = lon, y = lat;
           double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
           double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
           double bd_lon = z * Math.cos(theta) + 0.0065;
           double bd_lat = z * Math.sin(theta) + 0.006;
           return new double[] { bd_lat, bd_lon };
    }

    public static double[] bd2gcj(double lat, double lon) {
           double x = lon - 0.0065, y = lat - 0.006;
           double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
           double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
           double gg_lon = z * Math.cos(theta);
           double gg_lat = z * Math.sin(theta);
           return new double[] { gg_lat, gg_lon };
    }
    //WGS坐标转换为GCJ坐标
    public static double[] wgs2gcj(double lat, double lon) {
           double dLat = transformLat(lon - 105.0, lat - 35.0);
           double dLon = transformLon(lon - 105.0, lat - 35.0);
           double radLat = lat / 180.0 * pi;
           double magic = Math.sin(radLat);
           magic = 1 - ee * magic * magic;
           double sqrtMagic = Math.sqrt(magic);
           dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
           dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
           double mgLat = lat + dLat;
           double mgLon = lon + dLon;
           double[] loc = { mgLat, mgLon };
           return loc;
    }

    private static double transformLat(double lat, double lon) {
           double ret = -100.0 + 2.0 * lat + 3.0 * lon + 0.2 * lon * lon + 0.1 * lat * lon + 0.2 * Math.sqrt(Math.abs(lat));
           ret += (20.0 * Math.sin(6.0 * lat * pi) + 20.0 * Math.sin(2.0 * lat * pi)) * 2.0 / 3.0;
           ret += (20.0 * Math.sin(lon * pi) + 40.0 * Math.sin(lon / 3.0 * pi)) * 2.0 / 3.0;
           ret += (160.0 * Math.sin(lon / 12.0 * pi) + 320 * Math.sin(lon * pi  / 30.0)) * 2.0 / 3.0;
           return ret;
    }

    private static double transformLon(double lat, double lon) {
           double ret = 300.0 + lat + 2.0 * lon + 0.1 * lat * lat + 0.1 * lat * lon + 0.1 * Math.sqrt(Math.abs(lat));
           ret += (20.0 * Math.sin(6.0 * lat * pi) + 20.0 * Math.sin(2.0 * lat * pi)) * 2.0 / 3.0;
           ret += (20.0 * Math.sin(lat * pi) + 40.0 * Math.sin(lat / 3.0 * pi)) * 2.0 / 3.0;
           ret += (150.0 * Math.sin(lat / 12.0 * pi) + 300.0 * Math.sin(lat / 30.0 * pi)) * 2.0 / 3.0;
           return ret;
    }
    
}
