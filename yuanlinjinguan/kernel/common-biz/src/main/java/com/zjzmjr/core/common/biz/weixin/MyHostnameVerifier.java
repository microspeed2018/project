package com.zjzmjr.core.common.biz.weixin;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class MyHostnameVerifier implements HostnameVerifier{

	@Override
	public boolean verify(String arg0, SSLSession arg1) {
		return true;
	}

}
