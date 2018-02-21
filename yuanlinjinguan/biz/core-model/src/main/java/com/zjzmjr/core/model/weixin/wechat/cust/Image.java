package com.zjzmjr.core.model.weixin.wechat.cust;

public class Image {

	private String media_id;

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public Image() {
		super();
	}

	public Image(String media_id) {
		super();
		this.media_id = media_id;
	}

	@Override
	public String toString() {
		return "Image [media_id=" + media_id + "]";
	}
	
}
