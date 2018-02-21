package com.zjzmjr.core.model.weixin;

import java.io.Serializable;
import java.util.List;

public class MenuForm implements Serializable{

	private static final long serialVersionUID = 8797831042804503465L;
	//父菜单id
	private Integer pid;
	private Integer id;
	private String name;
	private String type;
	private Integer order;
	private String key;
	private String url;
	private String mediaId;
	private List<MenuForm> mid;
	private String content;
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public List<MenuForm> getMid() {
		return mid;
	}

	public void setMid(List<MenuForm> mid) {
		this.mid = mid;
	}
	
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	public Integer getPid() {
		return pid;
	}

	@Override
	public String toString() {
		return "MenuForm [pid=" + pid + ", id=" + id + ", name=" + name
				+ ", type=" + type + ", order=" + order + ", key=" + key
				+ ", url=" + url + ", mediaId=" + mediaId + ", mid=" + mid
				+ "]";
	}

}
