package com.zjzmjr.core.common.biz.weixin;


import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjzmjr.core.model.weixin.wechat.user.BaseInfoForm;

/**
 * 获取所有用户的openid列表
 * @author Administrator
 *
 */
@Service
public class GetUserListUtil {
	
	private static final String GET_USET_LIST_URL="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	
	public static BaseInfoForm list(){
		String access_token=WeixinMessageDigestUtil.getAccessToken();
		String url=GET_USET_LIST_URL.replace("ACCESS_TOKEN", access_token).replace("\"", "").replace("NEXT_OPENID", "");
		String result=HttpsRequestUtil.request(url, "GET", "");
		ObjectMapper mapper=new ObjectMapper();
		BaseInfoForm form=null;
		try {
			form=mapper.readValue(result, BaseInfoForm.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

}
