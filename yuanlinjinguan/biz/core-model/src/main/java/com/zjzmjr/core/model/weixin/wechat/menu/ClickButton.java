package com.zjzmjr.core.model.weixin.wechat.menu;

public class ClickButton extends Button{
	
	private static final long serialVersionUID = -8678613433237152531L;
	private String key;
	
	public ClickButton(){
		super.setType("click");
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "ClickButton [key=" + key + "]";
	}
	
}
