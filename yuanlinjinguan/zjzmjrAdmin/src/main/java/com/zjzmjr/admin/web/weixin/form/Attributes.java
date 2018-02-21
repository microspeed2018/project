package com.zjzmjr.admin.web.weixin.form;

import java.io.Serializable;

public class Attributes implements Serializable{
	
	private static final long serialVersionUID = 4547692448119154371L;
	
	private String type;
	
	private Integer order;
	
	private String key;
	
	private String url;
	
	private String mediaId;
	
	private Integer mid;
	
	private String content;
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}
	
}
