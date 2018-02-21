package com.zjzmjr.core.sms.util;

public class HttpSenderTest {
    
	public static void main(String[] args) {
		String url = "http://sms.253.com/msg/";// 应用地址
		String account = "N3915117";// 账号
		String pswd = "PqDyLX45iOf7fa";// 密码
		String mobile = "15925685649";// 手机号码，多个号码使用","分割
		String msg = "【中茗金融】您好，您的验证码是123456。";// 短信内容
//		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String extno = null;// 扩展码

		try {
		    SmsSender sender = new SmsSender();
			String returnString = sender.batchSMSSend(url, "send", account, pswd, mobile, msg, "1", extno);
			System.out.println(returnString);
			// TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			// TODO 处理异常
			e.printStackTrace();
		}
	}
}
