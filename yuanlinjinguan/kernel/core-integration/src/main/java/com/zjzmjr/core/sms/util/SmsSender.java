package com.zjzmjr.core.sms.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContextException;

import com.zjzmjr.core.enums.sms.NotifyTypeEnum;

/**
 * 短信发送
 * 
 * @author oms
 * @version $Id: HttpSender.java, v 0.1 2016-5-18 上午10:36:02 oms Exp $
 */
public class SmsSender{
    
    private final String SMS_SEND_METHOD = "send";
    
    private final String VOICE_SEND_METHOD = "HttpBatchSendSM";

    private String smsUrl;

    private String smsUsername;

    private String smsPassword;

    private String voiceUrl;

    private String voiceUsername;

    private String voicePassword;


    /**
     * 
     * @param url 应用地址，类似于http://ip:port/msg/
     * @param account 账号
     * @param pswd  密码
     * @param mobile  手机号码，多个号码使用","分割
     * @param msg  短信内容
     * @param needstatus 是否需要状态报告，需要true，不需要false
     * @return 返回值定义参见HTTP协议文档
     * @throws Exception
     */
    public String batchSMSSend(String url, String sendMethod, String un, String pw, String phone, String msg, 
            String rd, String ex) throws Exception {
        HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        GetMethod method = new GetMethod();
        try {
            URI base = new URI(url, false);
            method.setURI(new URI(base, "send", false));
            method.setQueryString(new NameValuePair[] { 
                    new NameValuePair("un", un), 
                    new NameValuePair("pw", pw), 
                    new NameValuePair("phone", phone), 
                    new NameValuePair("rd", rd), 
                    new NameValuePair("msg", msg), 
                    new NameValuePair("ex", ex), });
            int result = client.executeMethod(method);
            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
                throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } finally {
            method.releaseConnection();
        }
    }
/*
    public String batchSMSSend(String url, String sendMethod, String account, String pswd, String mobile, String msg,
            boolean needstatus, String product, String extno) throws Exception {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod();
        try {
            int needReport = 1;
            if (needstatus) {
                needReport = 1;
            } else if (!needstatus) {
                needReport = 0;
            }
            URI base = new URI(url, false);
            method.setURI(new URI(base, sendMethod, false));
            method.setQueryString(new NameValuePair[] { 
                    new NameValuePair("un", account),
                    new NameValuePair("pw", pswd), 
                    new NameValuePair("phone", mobile),
                    new NameValuePair("rd", String.valueOf(needReport)), 
                    new NameValuePair("msg", msg),
                    //new NameValuePair("product", product),
                    new NameValuePair("ex", extno), 
                });
            int result = client.executeMethod(method);
            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
                throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } finally {
            method.releaseConnection();
        }

    }
*/
    /**
     * 
     * @param url 应用地址，类似于http://ip:port/msg/
     * @param account 账号
     * @param pswd 密码
     * @param mobile 手机号码，多个号码使用","分割
     * @param msg 短信内容
     * @param needstatus 是否需要状态报告，需要true，不需要false
     * @return 返回值定义参见HTTP协议文档
     * @throws Exception
     */
    public String batchVoiceSend(String url, String sendMethod, String account, String pswd, String mobile, String msg,
            boolean needstatus, String product, String extno) throws Exception {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod();
        try {
            URI base = new URI(url, false);
            method.setURI(new URI(base, sendMethod, false));
            method.setQueryString(new NameValuePair[] { 
                    new NameValuePair("account", account),
                    new NameValuePair("pswd", pswd), 
                    new NameValuePair("mobile", mobile),
                    new NameValuePair("needstatus", String.valueOf(needstatus)), 
                    new NameValuePair("msg", msg),
                    //new NameValuePair("product", product),
                    new NameValuePair("extno", extno), 
                });
            int result = client.executeMethod(method);
            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
                throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } finally {
            method.releaseConnection();
        }

    }

    public String getSmsUrl() {
        return smsUrl;
    }

    public void setSmsUrl(String smsUrl) {
        this.smsUrl = smsUrl;
    }

    public String getSmsUsername() {
        return smsUsername;
    }

    public void setSmsUsername(String smsUsername) {
        this.smsUsername = smsUsername;
    }

    public String getSmsPassword() {
        return smsPassword;
    }

    public void setSmsPassword(String smsPassword) {
        this.smsPassword = smsPassword;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getVoiceUsername() {
        return voiceUsername;
    }

    public void setVoiceUsername(String voiceUsername) {
        this.voiceUsername = voiceUsername;
    }

    public String getVoicePassword() {
        return voicePassword;
    }

    public void setVoicePassword(String voicePassword) {
        this.voicePassword = voicePassword;
    }

}

