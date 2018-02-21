package com.zjzmjr.core.model.weixin.wechat.cust;

public class Text {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Text(String content) {
		super();
		this.content = content;
	}

	public Text() {
		super();
	}

	@Override
	public String toString() {
		return "Text [content=" + content + "]";
	}
	
}
