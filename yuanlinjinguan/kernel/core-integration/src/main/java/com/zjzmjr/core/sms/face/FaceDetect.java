
package com.zjzmjr.core.sms.face;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.face.Face;
import com.zjzmjr.core.model.face.FaceQuery;
import com.zjzmjr.core.model.face.FaceResult;


public class FaceDetect {

    private static final Logger logger = LoggerFactory.getLogger(FaceDetect.class);
    
    private static final String DEF_CHATSET = "UTF-8";

    private static final int DEF_CONN_TIMEOUT = 30000;

    private static final int DEF_READ_TIMEOUT = 30000;
    
    private static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    
    private String appKey = "buRTFS2bVOSvLw57UabcgF4QWglNgnrd";

    private String appSecret = "fEhCih38UYO3XsF3qc5qhJKKBM_p0iSk";
    
    /**
     * 获取实名认证token
     * 
     * @return
     */
    public Face getToken(FaceQuery query){
        logger.debug("getToken 开始执行,入参:{}", query);
        String result = null;
        Face face = new Face();
        String url = "https://api.megvii.com/faceid/lite/get_token";// 请求接口地址
        Map<String, Object> params = new HashMap<String, Object>();// 请求参数
        params.put("api_key", appKey);// 应用APPKEY(应用详细页查询)
        params.put("api_secret", appSecret);
        params.put("return_url","http://service.service.cbylsj.com/tpl/bind/bind-IDcard.html");
        params.put("notify_url","http://service.service.cbylsj.com");
        params.put("biz_no", query.getBizNo());
        params.put("comparison_type", "1");
        params.put("idcard_mode", "0");
        params.put("idcard_name", query.getName());
        params.put("idcard_number", query.getIdCard());
        try {
            result = net(url, params, "POST");
            JSONObject object = JSONObject.fromObject(result);
            if(Util.isNull(object.get("error_message"))){
               face.setToken(object.getString("token"));
               face.setBizId(object.getString("biz_id"));
            }else{
                logger.info("获取实名认证token错误:{},{}", object.get("error_message"));
            }
        } catch (Exception e) {
            logger.error("获取实名认证token错误:", e);
        }
        logger.debug("getToken 执行结束  出参:{}", face);
        return face;
    }
    
    /**
     * 获取实名认证结果
     * 
     * @param bizId
     * @return
     */
    public FaceResult getResult(String bizId){
        logger.debug("getResult 开始执行,入参:{}", bizId);
        String result = null;
        JSONObject obj = null;
        FaceResult faceResult = new FaceResult();
        String url = "https://api.megvii.com/faceid/lite/get_result";// 请求接口地址
        Map<String, Object> params = new HashMap<String, Object>();// 请求参数
        params.put("api_key", appKey);// 应用APPKEY(应用详细页查询)
        params.put("api_secret", appSecret);
        params.put("biz_id", bizId);
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(Util.isNull(object.get("error_message"))){
              faceResult.setStatus(object.getString("status"));
              obj = object.getJSONObject("liveness_result");
              if(Util.isNotNull(obj)){
                  faceResult.setLivenessResult(obj.getString("result"));
               }
              obj = object.getJSONObject("verify_result");
              if(Util.isNotNull(obj)){
                  JSONObject objects = obj.getJSONObject("result_faceid");
                  faceResult.setFaceidConfidence(objects.getString("confidence"));
                  faceResult.setVerifyErrorMessage(obj.getString("error_message"));
                  objects = obj.getJSONObject("id_exceptions");
                  faceResult.setIsAttacked(objects.getString("id_attacked"));
                }            
            }else{
                logger.info("获取实名认证结果错误:{},{}", object.get("error_message"));
            }
        } catch (Exception e) {
            logger.error("获取实名认证结果错误:", e);
        }
        logger.debug("getResult 执行结束  出参:{}", faceResult);
        return faceResult;
    }

    /**
     * 
     * @param strUrl
     *            请求地址
     * @param params
     *            请求参数
     * @param method
     *            请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public String net(String strUrl, Map<String, Object> params, String method) throws Exception {
        logger.debug("net 开始执行,入参:{},{},{}", strUrl, params, method);
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    logger.error("接口请求出错:", e);
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            logger.error("接口请求出错:", e);
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        logger.debug("net 执行结束  出参:{}", rs);
        return rs;
    }

    /**
     * 将map型转为请求参数型
     * 
     * @param data
     * @return
     */
    public String urlencode(Map<String, Object> data) {
        logger.debug("urlencode 开始执行,入参:{}", data);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                logger.error("将map型转为请求参数型:", e);
            }
        }
        logger.debug("urlencode 执行结束  出参:{}", sb);
        return sb.toString();
        
    }
}
