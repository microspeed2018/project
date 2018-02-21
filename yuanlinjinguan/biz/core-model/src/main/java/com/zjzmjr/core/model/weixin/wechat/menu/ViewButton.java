package com.zjzmjr.core.model.weixin.wechat.menu;

public class ViewButton extends Button{

	private static final long serialVersionUID = -2934153900817787127L;
	private String url;
	public ViewButton(){
		super.setType("view");
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ViewButton [url=" + url + "]";
	}
	
}
