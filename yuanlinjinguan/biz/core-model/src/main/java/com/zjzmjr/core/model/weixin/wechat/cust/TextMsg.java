package com.zjzmjr.core.model.weixin.wechat.cust;

import com.zjzmjr.core.model.weixin.wechat.MsgTypeEnum;



public class TextMsg extends Msg {

	private Text text;

	public TextMsg() {
		super.setMsgtype(MsgTypeEnum.TEXT_MSG.getMsgEnName());
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public TextMsg(Text text) {
		super();
		this.text = text;
	}

	@Override
	public String toString() {
		return "TextMsg [text=" + text + "]";
	}
	
}
