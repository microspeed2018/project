package com.zjzmjr.core.sms.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

//import com.lakala.aps.commons.StringUtil;

/**
 * HTTP请求连接帮助管理工具
 * 
 * @author oms
 * @version $Id: HttpHelper.java, v 0.1 2017-5-22 上午11:19:05 oms Exp $
 */
public class HttpHelper {

    private static final Logger logger = Logger.getLogger(HttpHelper.class);

    public static final String GET = "GET";

    public static final String POST = "POST";

    private final static String CHARSET = "UTF-8";
    
    private final static String TIMEOUT = "5500000";
    
    public static String getNvPairs(List<String[]> list, String charSet) {
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String[] nvPairStr = (String[]) list.get(i);
            try {
                if (i > 0) {
                    stringBuffer.append("&");
                }
                stringBuffer.append(URLEncoder.encode(nvPairStr[0], charSet)).append("=").append(URLEncoder.encode(nvPairStr[1], charSet));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return stringBuffer.toString();
    }

    public static String getNvPairs(Map<String, String> map, String charSet) {
        if (map == null || map.size() == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        Set<String> key = map.keySet();
        int i = 0;
        for (Iterator<String> it = key.iterator(); it.hasNext();) {
            String s = (String) it.next();
            try {
                if (i > 0) {
                    stringBuffer.append("&");
                }
                i++;
                stringBuffer.append(URLEncoder.encode(s, charSet)).append("=").append(URLEncoder.encode(map.get(s), charSet));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
        /*
         * for (int i = 0; i < map; i++) { String[] nvPairStr = (String[])
         * list.get(i); try { if (i > 0) { stringBuffer.append("&"); }
         * stringBuffer.append(URLEncoder.encode(nvPairStr[0], charSet))
         * .append("=").append( URLEncoder.encode(nvPairStr[1], charSet)); }
         * catch (UnsupportedEncodingException e) { e.printStackTrace(); return
         * null; } }
         */
        return stringBuffer.toString();
    }

    /**
     * 获取http请求
     * 
     * @param urlStr
     * @param method
     * @param charSet
     * @param postStr
     * @param timeOut
     * @return
     */
    public static String doHttp(String urlStr, String method, String charSet, String postStr, String timeOut) {
        if (method == null || (!GET.equalsIgnoreCase(method) && !POST.equalsIgnoreCase(method))) {
            logger.error("无效http方法[" + method + "]");
            return null;
        }
        logger.info("urlStr=" + urlStr + ",method=" + method + ",charSet=" + charSet + ",postStr=" + postStr + ",timeOut=" + timeOut);

        HttpURLConnection httpURLConnection = (HttpURLConnection) getURLConnection(urlStr, method, postStr);
        setHttpHearderValue(httpURLConnection, null, timeOut);
        return getHttpPostResponseValue(httpURLConnection, method, postStr, charSet);
    }

    public static void main(String[] args) {
        HashMap<String, String> regMap = new HashMap<String, String>();
        regMap.put("loginname", "cs");
        regMap.put("groupid", "3220241");
        regMap.put("pagesize", "10");
        regMap.put("pageindex", "1");
        regMap.put("isonline", "0");
        regMap.put("orderbypalte", "0");
        regMap.put("orderbyrecvtime", "0");

        Map<String, String> heardMap = new HashMap<String, String>();
        heardMap.put("e", "cs");
        heardMap.put("t", "ABCDEFGHIJQLMNOPQRSTUVWXYZ123456");
        heardMap.put("d", String.valueOf(System.currentTimeMillis()));
        SortedMap<String, String> paraMap = new TreeMap<String, String>();
        paraMap.put("loginname", "cs");
        paraMap.putAll(heardMap);
        String signStr = ConetonSignHelper.sign(paraMap, "123456" + "123456ABCDEFGHIJQLMNOPQRSTUVWXYZ");
        heardMap.put("n", signStr);
        
        signStr = doHttp("https://gps200.coneton.com:7077/zyt/GetVehicleByGroupID", HttpHelper.GET, 
                ConetonSignHelper.createLinkString(regMap), heardMap);
        System.out.println(signStr);
    }
    
    /**
     * 获取http请求
     * 
     * @param urlStr
     * @param method
     * @param postStr
     * @param heardMap
     * @return
     */
    public static String doHttp(String urlStr, String method, String postStr, Map<String, String> heardMap){
        if (method == null || (!GET.equalsIgnoreCase(method) && !POST.equalsIgnoreCase(method))) {
            logger.error("无效http方法[" + method + "]");
            return null;
        }
        logger.info("urlStr=" + urlStr + ",method=" + method + ",charSet=" + CHARSET + ",postStr=" + postStr + ",timeOut=" + TIMEOUT);

        HttpURLConnection httpURLConnection = (HttpURLConnection) getURLConnection(urlStr, method, postStr);
        setHttpHearderValue(httpURLConnection, heardMap, TIMEOUT);
        return getHttpPostResponseValue(httpURLConnection, method, postStr, CHARSET);
    }

    /**
     * 提交http请求并且获取http返回结果值
     * 
     * @param httpURLConnection
     * @param method
     * @param postStr
     * @param charSet
     * @return
     */
    private static String getHttpPostResponseValue(HttpURLConnection httpURLConnection, String method, String postStr, String charSet) {
        String urlStr = httpURLConnection.getURL().toString();
        try {
            httpURLConnection.setRequestMethod(method.toUpperCase());
        } catch (ProtocolException e) {
            logger.error("设置HTTP通讯方式异常", e);
            return null;
        }
        if (POST.equalsIgnoreCase(method)) {
            httpURLConnection.setDoOutput(true);
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), charSet));
            } catch (UnsupportedEncodingException e) {
                logger.error("提交POST请求参数异常", e);
                return null;
            } catch (IOException e) {
                logger.error("提交POST请求参数IO异常", e);
                return null;
            }
            printWriter.write(postStr);
            printWriter.flush();
        }
        InputStream inputStream = null;
        try {
            if(GET.equalsIgnoreCase(method) && !httpURLConnection.getDoInput()){
                httpURLConnection.setDoInput(true);
            }
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException e) {
            logger.error("打开接收数据流异常", e);
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int data = 0;
        try {
            int statusCode = httpURLConnection.getResponseCode();
            if (statusCode < HttpURLConnection.HTTP_OK || statusCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
                logger.error("失败返回码[" + statusCode + "]");
                return null;
            }
            while ((data = inputStream.read()) != -1) {
                byteArrayOutputStream.write(data);
            }
        } catch (IOException e) {
            logger.error("接收HTTP响应数据IO异常", e);
            return null;
        }
        String serverCharset = null;
        String contentEncoding = httpURLConnection.getContentEncoding();
        String contentType = httpURLConnection.getContentType();
        serverCharset = contentEncoding;
        if (serverCharset == null) {
            int pos = contentType.indexOf("; charset=");
            if (-1 != pos) {
                serverCharset = contentType.substring(pos + 10);
            }
        }
        if (serverCharset == null) {
            serverCharset = charSet;
        }
        byte[] returnBytes = byteArrayOutputStream.toByteArray();
        String returnStr = null;
        try {
            returnStr = new String(returnBytes, serverCharset);
        } catch (UnsupportedEncodingException e) {
            logger.error("字符集转换异常", e);
            return null;
        }
        logger.info("urlStr=" + urlStr + ",returnStr=" + returnStr);
        return returnStr;
    }

    /**
     * 设置http请求的hearder部值
     * 
     * @param httpURLConnection
     * @param heardMap
     * @param timeOut
     */
    private static void setHttpHearderValue(HttpURLConnection httpURLConnection, Map<String, String> heardMap, String timeOut) {
        httpURLConnection.setConnectTimeout(Integer.parseInt(timeOut));
        httpURLConnection.setReadTimeout(Integer.parseInt(timeOut));
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Content-Type", "charset=utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json;charset=utf-8");
        if (heardMap != null && !heardMap.isEmpty()) {
            for (Map.Entry<String, String> entry : heardMap.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 获取http连接
     * 
     * @param urlStr
     * @param method
     * @param postStr
     * @return
     */
    private static URLConnection getURLConnection(String urlStr, String method, String postStr) {
        URL url = null;
        try {
            if ("GET".equals(method)) {
                if (StringUtils.isEmpty(postStr)) {
                    url = new URL(urlStr);
                } else {
                    url = new URL(urlStr + "?" + postStr);
                }
            } else
                url = new URL(urlStr);
        } catch (MalformedURLException e) {
            logger.error("URL地址不正确", e);
            return null;
        }
        if ("https".equalsIgnoreCase(urlStr.substring(0, 5))) {
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("TLS");
                X509TrustManager xtmArray[] = { new HttpX509TrustManager() };
                sslContext.init(null, xtmArray, new SecureRandom());
            } catch (GeneralSecurityException gse) {
                gse.printStackTrace();
            }
            if (sslContext != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            }
            HttpsURLConnection.setDefaultHostnameVerifier(new HttpHostnameVerifier());
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            logger.error("打开HTTP网络连接异常", e);
            return null;
        }

        return httpURLConnection;
    }

    /**
     * 获取response
     * 
     * @param urlStr
     * @param method
     * @param charSet
     * @param postStr
     * @param timeOut
     * @return
     */
    public static HttpResponse doHttpGetResponse(String urlStr, String method, String charSet, String postStr, String timeOut) {
        if (method == null || (!GET.equalsIgnoreCase(method) && !POST.equalsIgnoreCase(method))) {
            logger.error("无效http方法[" + method + "]");
            return null;
        }
        // logger.info("HTTP访问：urlStr=" + urlStr + ",method=" + method
        // + ",charSet=" + charSet + ",postStr=" + postStr + ",timeOut="
        // + timeOut);
        String myUrlStr = "";
        if (GET.equalsIgnoreCase(method)) {
            if (-1 == urlStr.indexOf("?")) {
                myUrlStr = urlStr + "?" + postStr;
            } else {
                myUrlStr = urlStr + "&" + postStr;
            }
        } else {
            myUrlStr = urlStr;
        }
        URL url = null;
        try {
            url = new URL(myUrlStr);
        } catch (MalformedURLException e) {
            logger.error("访问" + urlStr + "异常", e);
            return null;
        }
        if ("https".equalsIgnoreCase(urlStr.substring(0, 5))) {
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("TLS");
                X509TrustManager xtmArray[] = { new HttpX509TrustManager() };
                sslContext.init(null, xtmArray, new SecureRandom());
            } catch (GeneralSecurityException gse) {
                gse.printStackTrace();
            }
            if (sslContext != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            }
            HttpsURLConnection.setDefaultHostnameVerifier(new HttpHostnameVerifier());
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            logger.error("打开网络连接异常", e);
            return null;
        }
        System.setProperty("sun.net.client.defaultConnectTimeout", timeOut);
        System.setProperty("sun.net.client.defaultReadTimeout", timeOut);
        try {
            httpURLConnection.setRequestMethod(method.toUpperCase());
        } catch (ProtocolException e) {
            logger.error("HTTP请求方式不支持,METHOD=" + method, e);
            return null;
        }
        if (POST.equalsIgnoreCase(method)) {
            httpURLConnection.setDoOutput(true);
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), charSet));
            } catch (UnsupportedEncodingException e) {
                logger.error("POST方式打开输出流异常", e);
                return null;
            } catch (IOException e) {
                logger.error("POST方式打开输出流异常", e);
                return null;
            }
            printWriter.write(postStr);
            printWriter.flush();
        }
        InputStream inputStream = null;
        try {
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException e) {
            logger.error("打开输入流异常", e);
            return null;
        }

        int statusCode = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            statusCode = httpURLConnection.getResponseCode();
            int data = 0;
            while ((data = inputStream.read()) != -1) {
                byteArrayOutputStream.write(data);
            }
        } catch (IOException e) {
            logger.error("读取HTTP响应数据异常", e);
            return null;
        }
        byte[] returnBytes = byteArrayOutputStream.toByteArray();
        String returnStr = null;
        try {
            returnStr = new String(returnBytes, charSet);
        } catch (UnsupportedEncodingException e) {
            logger.error("二进制数据转换成字符串异常", e);
            return null;
        }

        // 返回
        HttpResponse httpRes = new HttpResponse();
        httpRes.setResponseCode(statusCode);
        httpRes.setBody(returnStr);
        logger.info("HTTP访问：urlStr=" + urlStr + ",method=" + method + ",charSet=" + charSet + ",postStr=" + postStr + ",timeOut=" + timeOut);
        return httpRes;
    }

}
