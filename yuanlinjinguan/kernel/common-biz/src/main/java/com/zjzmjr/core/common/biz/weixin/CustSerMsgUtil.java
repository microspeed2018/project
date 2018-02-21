package com.zjzmjr.core.common.biz.weixin;




import com.zjzmjr.core.model.weixin.wechat.MsgTypeEnum;
import com.zjzmjr.core.model.weixin.wechat.cust.Image;
import com.zjzmjr.core.model.weixin.wechat.cust.ImageMsg;
import com.zjzmjr.core.model.weixin.wechat.cust.Text;
import com.zjzmjr.core.model.weixin.wechat.cust.TextMsg;



/**
 * 微信客服消息工具类
 * @author yangys
 *
 */
public final class CustSerMsgUtil {
	
	private CustSerMsgUtil(){
		
	}
	
	/**
	 * 发送客服消息接口地址
	 */
	private static final String SEND_CUST_SER_MSG_URL="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	
	/**
	 * 发送客服消息：图片/文字
	 */
	private static <T> ResultParseUtil sendMsg(T msg,String access_token){
		String url=SEND_CUST_SER_MSG_URL.replace("ACCESS_TOKEN", access_token).replace("\"", "");
		String data=JSONProcesserUtil.parseObj(msg);
		String result=HttpsRequestUtil.request(url, "POST", data);
		ResultParseUtil parseUtil=JSONProcesserUtil.parseStr(result, ResultParseUtil.class);
		return parseUtil.parseResult();
	}
	
	/**
	 * 发送文本消息
	 */
	public static ResultParseUtil sendTextMsg(String openid,String msg) {
		TextMsg textMsg=new TextMsg();
		textMsg.setTouser(openid);
		Text text=new Text(msg);
		textMsg.setText(text);
		return sendMsg(textMsg, WeixinMessageDigestUtil.getAccessToken());
	}
	
	/**
	 * 发送图片消息
	 */
	public static ResultParseUtil sendImageMsg(String openid,String filePath) {
		String access_token=WeixinMessageDigestUtil.getAccessToken();
		MultiMediaUpload mediaUpload=new MultiMediaUpload();
		String mediaId=mediaUpload.createSourceMaterial(filePath, access_token,MsgTypeEnum.IMAGE_MSG.getMsgEnName());
		ImageMsg msg=new ImageMsg();
		msg.setTouser(openid);
		Image image=new Image(mediaId.replace("\"", ""));
		msg.setImage(image);
		return sendMsg(msg, access_token);
	}
	
}
