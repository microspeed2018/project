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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.core.base.util.Util;

public class OCR {

	private static final Logger logger = LoggerFactory.getLogger(FaceDetect.class);

	private static final String DEF_CHATSET = "UTF-8";

	private static final int DEF_CONN_TIMEOUT = 30000;

	private static final int DEF_READ_TIMEOUT = 30000;

	private static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	private String appKey = "cf5PgxmkkHZ8ABY4sravrwx4jEv_Ndvg";

	private String appSecret = "7rNi4TdXjy4-h0j0CLjqVAtNZ12KG4Jq";

	/**
	 * 身份证识别接口
	 * 
	 * @param imgUrl
	 * @return 
	 * @return
	 */
	public Map<String, String> ocrIdCard(String imgUrl){
	    logger.debug("ocrIdCard 开始执行,入参:{}", imgUrl);
		String result = null;
		String url = "https://api-cn.faceplusplus.com/cardpp/v1/ocridcard";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		Map<String,String> cardMap = new HashMap<String,String>();
		params.put("api_key", appKey);// 应用APPKEY(应用详细页查询)
		params.put("api_secret", appSecret);
		params.put("image_url", imgUrl);
		try {
			result = net(url, params, "POST");
			JSONObject object = JSONObject.fromObject(result);
			if (Util.isNull(object.get("error_message"))) {
				JSONArray objects = object.getJSONArray("cards");
                if (!objects.isEmpty() || objects.size()>0) {
                    JSONObject obj = (JSONObject) objects.get(0);
                    if (obj.getString("side").equals("front")) {
                        cardMap.put("name", obj.getString("name"));
                        cardMap.put("identityNo", obj.getString("id_card_number"));
                        cardMap.put("gender", obj.getString("gender"));
                        cardMap.put("type", obj.getString("type"));
                        cardMap.put("side", obj.getString("side"));
                        cardMap.put("address", obj.getString("address"));

                    } else {
                        cardMap.put("side", obj.getString("side"));
                        cardMap.put("issuedBy", obj.getString("issued_by"));
                        cardMap.put("validDate", obj.getString("valid_date"));
                    }
                    cardMap.put("requestId",object.getString("request_id"));
                }
			} else {
				logger.info("身份证识别错误:{},{}", object.get("error_message"));
			}
		} catch (Exception e) {
			logger.error("身份证识别错误:", e);
		}
		logger.debug("ocrIdCard 执行结束  出参:{}", cardMap);
		return cardMap;
	}
//
//	public static void main(String[] args) {
//		OCR fd = new OCR();
//		String imgUrl = "http://test.upload.cbylsj.com/upload/web/20170315/zmjrnull11489583402331.jpg";
//		Map<String,String> cardMap = fd.ocrIdCard(imgUrl);
//		System.out.println(cardMap);
//	}

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
	public String net(String strUrl, Map<String, Object> params, String method)
			throws Exception {
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
					DataOutputStream out = new DataOutputStream(
							conn.getOutputStream());
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
	    logger.debug("net 开始执行,入参:{}", data);
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=")
						.append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
						.append("&");
			} catch (UnsupportedEncodingException e) {
				logger.error("将map型转为请求参数型:", e);
			}
		}
		logger.debug("urlencode 执行结束  出参:{}", sb);
		return sb.toString();

	}
}
