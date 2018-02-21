package com.zjzmjr.finance.web.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.cache.redis.JedisPull;
import com.zjzmjr.core.common.biz.weixin.WeixinMessageDigestUtil;
import com.zjzmjr.core.model.user.LoginRecord;
import com.zjzmjr.core.model.user.LoginRecordQuery;
import com.zjzmjr.core.model.weixin.wechat.WeixinAddress;
import com.zjzmjr.finance.web.user.form.LoginRecordForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 网站入口的微信分享
 * 
 * @author oms
 * @version $Id: ShareEntranceUtil.java, v 0.1 2016-6-27 上午11:08:58 oms Exp $
 */
@Controller
public class ShareEntranceUtil extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ShareEntranceUtil.class);

    private final String HOME_URL = "url";

    private static final String appId = PropertyUtils.getPropertyFromFile("commBiz", "WX_APPID");
    
    /**
     * 
     * 
     * @param model
     */
    @RequestMapping(value = "/home/entrance.htm", method = RequestMethod.POST)
    public void home(ModelMap model, HttpServletResponse resp, HttpServletRequest req) {
        try {
            String jsapi_ticket = getTicket();

            // // 注意 URL 一定要动态获取，不能 hardcode
            // String url = "http://example.com";
            // String share2FirendUrl =
            // PropertyUtils.getInstance().getProperty(HOME_URL);
            // String share2FirendUrl = req.getScheme() + "://" +
            // req.getServerName() + req.getRequestURI();
            String share2FirendUrl = req.getParameter(HOME_URL);
            Map<String, String> ret = sign(jsapi_ticket, share2FirendUrl);
            for (Map.Entry<String, String> entry : ret.entrySet()) {
                model.put(entry.getKey(), entry.getValue());
            }
            putSuccess(model, "");
        } catch (Exception ex) {
            logger.error("登录出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    // @RequestMapping(value = "/home/getAddressinate.htm", method =
    // RequestMethod.POST)
    // public void getAddressinate(HttpServletResponse resp, HttpServletRequest
    // req){
    // Map<String, Object> model = new HashMap<String, Object>();
    // try{
    // String lat = req.getParameter("lat");
    // String lon = req.getParameter("lon");
    // String url =
    // String.format("http://ditu.google.cn/maps/geo?output=json&key=abcdef&q=%s,%s",lat,
    // lon);
    // String url =
    // String.format("http://ditu.google.cn/maps/geo?q=%s,%s&key=ABQIAAAAE7SAS10g-ATpf14mvmoY3RQnYy5xmakd5i22O2aWTK8_BJDSThTClyozg_yWXE5JwJ2I5FHxvu1BFw&sensor=true&output=json",lat,
    // lon);
    // JSONObject jsonObject = httpRequest(url, "GET", null);
    // model.put("data", jsonObject);
    // putSuccess(model, "");
    // }catch(Exception ex){
    // logger.error("获取地址出错：", ex);
    // putError(model, ex.getMessage());
    // }
    // responseAsJson(model, resp);
    // }

    /**
     * 反向地址解析
     * 
     * @param latLng
     *            经纬度，格式形如：lat,lng
     * @return 地址
     */
    @RequestMapping(value = "/home/getAddressinate.htm", method = RequestMethod.POST)
    public void getAddressByLatLng(HttpServletResponse resp, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        String address = "", business = "", sematicDescription = "";
        double lat = Double.parseDouble(req.getParameter("lat"));
        double lon = Double.parseDouble(req.getParameter("lon"));
        double[] zuobiao = WeixinMessageDigestUtil.gcj2bd(lat, lon);
        String latLng = zuobiao[0] + "," + zuobiao[1];
        BufferedReader in = null;
        try {
            /* status  constant      返回结果状态值， 成功返回0，其他值请查看附录。
             * location    lat       纬度坐标
             * lng                   经度坐标
             * formatted_address     结构化地址信息
             * business              所在商圈信息，如 "人民大学,中关村,苏州街"
             * addressComponent 
             * country               国家
             * province              省名
             * city                  城市名
             * district              区县名
             * street                街道名
             * street_number         街道门牌号
             * adcode                行政区划代码
             * country_code          国家代码
             * direction             和当前坐标点的方向，当有门牌号的时候返回数据
             * distance              和当前坐标点的距离，当有门牌号的时候返回数据
             * pois（周边poi数组） addr  地址信息
             * cp                    数据来源
             * direction             和当前坐标点的方向
             * distance              离坐标点距离
             * name                  poi名称
             * poiType               poi类型，如’ 办公大厦,商务大厦’
             * point                 poi坐标{x,y}
             * tel                   电话
             * uid                   poi唯一标识
             * zip                   邮编
             * sematic_description 
             * constant              当前位置结合POI的语义化结果描述。*/
            URL url = new URL("http://api.map.baidu.com/geocoder/v2/?ak=57kgfuf8NGcPOIcrWhK2zgxxuRlioMjz&callback=renderReverse&location=" + latLng + "&output=json&pois=1");
//            URL url = new URL("http://api.map.baidu.com/geocoder?location="+lat+","+ lon +"&coord_type=gcj02&output=html&src=zjzmjr");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.connect();
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));// 注意，这里要声明字符编码是UTF-8，否则会乱码
            String line;
            String data = "";
            while ((line = in.readLine()) != null) {
                data += line;
                // String[] retList = line.split(",");
                // saddress = retList[3];
                // String[] result = saddress.split(":");
                // address = result[1];
                // address = address.substring(1, address.length() - 1);
            }
            in.close();
            JSONObject jsonObject = JSONObject.fromObject(data.substring(29, data.length() - 1));
            if (jsonObject.getString("status").equals("0")) {
                address = JSONObject.fromObject(jsonObject.getString("result")).getString("formatted_address");
                business = JSONObject.fromObject(jsonObject.getString("result")).getString("business");
                sematicDescription = JSONObject.fromObject(jsonObject.getString("result")).getString("sematic_description");
                WeixinAddress weixin = new WeixinAddress();
                weixin.setAddress(address);
                weixin.setBusiness(business);
                weixin.setSematicDescription(sematicDescription);
                model.put("data", weixin);
                putSuccess(model, "");
            } else {
                putError(model, "获取位置失败！");
            }

            // System.out.println("address=" + address);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        responseAsJson(model, resp);
    }

    private String getTicket() {
        String ticket = null;
        String access_token = JedisPull.getObject("access_token", String.class);
        if(Util.isNull(access_token)){
            access_token = WeixinMessageDigestUtil.getAccessToken(); // ACCESS_TOKEN; //有效期为7200秒
            JedisPull.setObject("access_token", access_token, 3600);
        }
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        ticket = getHttpsRequest(url, "ticket");
        return ticket;
    }

//    private String getAccessToken() {
//        String APPID = "wxfde12c4b9a78c06a";
//        String APPSECRET = "76288bd882081a43a5478e48f2163adb";
//        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
//        return getHttpsRequest(url, "access_token");
//    }

    private String getHttpsRequest(String url, String accessKey) {
        String responseStr = StringUtils.EMPTY;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            // JSONObject demoJson = new JSONObject(message);
            // ticket = demoJson.getString("ticket");
            responseStr = JSONObject.fromObject(message).getString(accessKey);
//            System.out.println(responseStr);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseStr;
    }

    public Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        // 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
//        System.out.println(string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appId", appId);

        return ret;
    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static void main(String[] args) {
        ShareEntranceUtil util = new ShareEntranceUtil();
        String jsapi_ticket = util.getTicket();

        // 注意 URL 一定要动态获取，不能 hardcode
        String url = "http://example.com";
        Map<String, String> ret = util.sign(jsapi_ticket, url);
        for (Map.Entry<String, String> entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

//    /**
//     * 
//     * 
//     * @param resp
//     * @param form
//     */
//    @RequestMapping(value = "/insertLoginRecord.htm", method = RequestMethod.POST)
//    public void insertLoginRecord(HttpServletResponse resp, @Valid LoginRecordForm form) {
//        Map<String, Object> model = new HashMap<String, Object>();
//        try {
//            LoginRecord query = new LoginRecord();
//            query.setCreateTime(new Date());
//            query.setLastAccessDate(form.getLastAccessDate());
//            query.setLatitude(form.getLatitude());
//            Calendar c = Calendar.getInstance();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            query.setLastAccessDate(sdf.format(c.getTime()));
//            query.setUserId(SpringSecurityUtil.getIntPrincipal());
//            query.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
//            ResultEntry<Integer> result = userFacade.insertLoginRecord(query);
//            if (result.isSuccess()) {
//                model.put("data", result.getT());
//                putSuccess(model, "");
//            } else {
//                putError(model, result.getErrorCode(), result.getErrorMsg());
//            }
//        } catch (Exception ex) {
//            logger.error("用户登录记录新增出错：", ex);
//            putError(model, ex.getMessage());
//        }
//        responseAsJson(model, resp);
//    }
    
//    /**
//     * 查询用户登录记录
//     * 
//     * @param resp
//     * @param form
//     */
//    @RequestMapping(value = "/getLoginRecord.htm", method = RequestMethod.POST)
//    public void getLoginRecord(HttpServletResponse resp, @Valid LoginRecordForm form) {
//        Map<String, Object> model = new HashMap<String, Object>();
//        try {
//            LoginRecordQuery query = convertForm(form);
//            ResultPage<LoginRecord> result = userFacade.getLoginRecord(query);
//            if (result.isSuccess()) {
//                model.put("data", result.getList());
//                putSuccess(model, "");
//            } else {
//                putError(model, result.getErrorCode(), result.getErrorMsg());
//            }
//        } catch (Exception ex) {
//            logger.error("用户登录记录查询一览出错：", ex);
//            putError(model, ex.getMessage());
//        }
//        responseAsJson(model, resp);
//    }
    
//    /**
//     * 
//     * 
//     * @param resp
//     * @param form
//     */
//    @RequestMapping(value = "/updateLoginRecordById.htm", method = RequestMethod.POST)
//    public void updateLoginRecordById(HttpServletResponse resp, @Valid LoginRecordForm form) {
//        Map<String, Object> model = new HashMap<String, Object>();
//        try {
//            LoginRecord query = new LoginRecord();
//            query.setUpdateTime(new Date());
//            query.setLastAccessDate(form.getLastAccessDate());
//            query.setLatitude(form.getLatitude());
//            Calendar c = Calendar.getInstance();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            query.setLastAccessDate(sdf.format(c.getTime()));
//            query.setUserId(SpringSecurityUtil.getIntPrincipal());
//            query.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
//            ResultEntry<Integer> result = userFacade.updateLoginRecordById(query);
//            if (result.isSuccess()) {
//                model.put("data", result.getT());
//                putSuccess(model, "");
//            } else {
//                putError(model, result.getErrorCode(), result.getErrorMsg());
//            }
//        } catch (Exception ex) {
//            logger.error("用户登录记录更新出错：", ex);
//            putError(model, ex.getMessage());
//        }
//        responseAsJson(model, resp);
//    }
    
    /**
     * 生成用户登录记录
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/home/insertOrUpdate.htm", method = RequestMethod.POST)
    public void insertOrUpdate(HttpServletResponse resp, @Valid LoginRecordForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        LoginRecord data = new LoginRecord();
        try {
            LoginRecordQuery query = convertForm(form);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String lastAccessDate = sdf.format(c.getTime());
//            ResultPage<LoginRecord> list = userFacade.getLoginRecord(query);
//            // list 有记录 且上次登录时间不是今天则新增记录
//            if (list.isSuccess() && !list.getList().isEmpty()) {
//                if (!lastAccessDate.equals(list.getList().get(0).getLastAccessDate())) {
//                    data.setCreateTime(new Date());
//                    data.setLatitude(query.getLatitude());
//                    data.setLongitude(query.getLongitude());
//                    data.setLastAccessDate(lastAccessDate);
//                    data.setUserId(SpringSecurityUtil.getIntPrincipal());
//                    data.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
//                    result = userFacade.insertLoginRecord(data);
//                }
//            } else {
//                data.setCreateTime(new Date());
//                data.setLatitude(query.getLatitude());
//                data.setLongitude(query.getLongitude());
//                data.setLastAccessDate(lastAccessDate);
//                data.setUserId(SpringSecurityUtil.getIntPrincipal());
//                data.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
//                result = userFacade.insertLoginRecord(data);
//            }
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("用户登录记录新增出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 用户登录记录form转换
     * 
     * @param form
     * @return
     */
    public LoginRecordQuery convertForm(LoginRecordForm form) {
        LoginRecordQuery rec = new LoginRecordQuery();
        rec.setUserId(SpringSecurityUtil.getIntPrincipal());
        rec.setLatitude(formatCommaBigDecimal(form.getLatitude(),3));
        rec.setLongitude(formatCommaBigDecimal(form.getLongitude(),3));
        return rec;
    }

    // 保留3位小数
    private BigDecimal formatCommaBigDecimal(Object obj, int wei) {
        String val = String.valueOf(obj);
        if (val == null)
            return new BigDecimal("0.00");
        val = val.replaceAll(",", "");
        BigDecimal decimal = new BigDecimal(val);
        decimal = decimal.setScale(wei, RoundingMode.HALF_UP);
        return decimal;
    }
}
