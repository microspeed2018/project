package com.zjzmjr.core.model.weixin.wechat.cust;

public class Msg {
	
	/**
	 * 客服消息的openid
	 */
	private String touser;
	/**
	 * 消息类型
	 */
	private String msgtype;
	/**
	 * 可选
	 */
	private String title;
	/**
	 * 可选
	 */
	private String description;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Msg [touser=" + touser + ", msgtype=" + msgtype + ", title="
				+ title + ", description=" + description + "]";
	}
	
}
