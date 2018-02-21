package com.zjzmjr.core.common.biz.weixin;



import java.util.HashMap;
import java.util.Map;

import com.zjzmjr.core.model.weixin.wechat.template.TemplateData;
import com.zjzmjr.core.model.weixin.wechat.template.TemplateForm;

/**
 * 模版消息工具类
 * @author Administrator
 *
 */
public final class TemplateMsgUtil {
	
	private TemplateMsgUtil(){}
	
	/**
	 * 发送模版消息地址
	 */
	private static final String SEND_MSG_URL="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	/**
	 * 发送模版消息，可定制
	 * @param templateId 模版ID
	 * @param openid 用户唯一标识
	 * @param data 发送的模版数据
	 * @param url 点击模版消息跳转的url
	 * @return 结果解析对象
	 */
	public static ResultParseUtil sendTemplateMsg(String templateId,String openid,TemplateData data,String url){
		TemplateForm form=new TemplateForm();
		form.setTemplate_id(templateId);
		form.setTouser(openid);
		form.setUrl(url);
		form.setData(data);
		String access_token=WeixinMessageDigestUtil.getAccessToken();
		String requestUrl=SEND_MSG_URL.replace("ACCESS_TOKEN", access_token).replace("\"", "");
		String resultStr=HttpsRequestUtil.request(requestUrl, "POST", JSONProcesserUtil.parseObj(form));
		ResultParseUtil parseUtil = JSONProcesserUtil.parseStr(resultStr, ResultParseUtil.class);
		return parseUtil.parseResult();
	}
	
	/**
	 * 默认发送模版消息设置
	 * @param templateId 模版ID
	 * @param openid 用户唯一标识
	 * @param username 用户名
	 * @param msg	发送的消息内容
	 * @param color 消息的颜色
	 * @param url 点击模版消息跳转的url
	 * @return 结果解析对象
	 */
	public static ResultParseUtil sendTemplateMsg(String templateId,String openid,String username,String msg,String color,String url){
		TemplateData data=new TemplateData();
		Map<String, String> paramMapFirst=new HashMap<String, String>();
		Map<String, String> paramMapKeyWord1=new HashMap<String, String>();
		Map<String, String> paramMapKeyWord2=new HashMap<String, String>();
		Map<String, String> paramMapRemark=new HashMap<String, String>();
		paramMapFirst.put("value", "您好，用户【"+username+"】的征信关注");
		paramMapFirst.put("color", color);
		data.setFirst(paramMapFirst);
		paramMapKeyWord1.put("value", "汽车担保贷款");
		paramMapKeyWord1.put("color", color);
		data.setKeyword1(paramMapKeyWord1);
		paramMapKeyWord2.put("value", msg);
		paramMapKeyWord2.put("color", color);
		data.setKeyword2(paramMapKeyWord2);
		paramMapRemark.put("value", "请尽快处理。");
		paramMapRemark.put("color", color);
		data.setRemark(paramMapRemark);
		ResultParseUtil parseUtil=TemplateMsgUtil.sendTemplateMsg(templateId,openid, data, url);
		return parseUtil;
	}
}
