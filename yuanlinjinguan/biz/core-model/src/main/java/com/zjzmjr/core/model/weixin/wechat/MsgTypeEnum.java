package com.zjzmjr.core.model.weixin.wechat;

public enum MsgTypeEnum {
	
	TEXT_MSG("文本消息","text"),
	//群发图文消息
	MPNEWS_MSG("图文消息","mpnews"),
	IMAGE_MSG("图片消息","image"),
	VOICE_MSG("语音消息","voice"),
	VIDEO_MSG("视频消息","video"),
	//客服图文消息
	NEWS_MSG("图文消息","news"),
	MUSIC_MSG("音乐消息","music"),
	WXCARD_MSG("卡券消息","wxcard"),
	THUMB_MSG("缩略图","thumb");
	private String msgCnName;
	private String msgEnName;
	public String getMsgCnName() {
		return msgCnName;
	}
	public void setMsgCnName(String msgCnName) {
		this.msgCnName = msgCnName;
	}
	public String getMsgEnName() {
		return msgEnName;
	}
	public void setMsgEnName(String msgEnName) {
		this.msgEnName = msgEnName;
	}
	private MsgTypeEnum(String msgCnName, String msgEnName) {
		this.msgCnName = msgCnName;
		this.msgEnName = msgEnName;
	}
}
