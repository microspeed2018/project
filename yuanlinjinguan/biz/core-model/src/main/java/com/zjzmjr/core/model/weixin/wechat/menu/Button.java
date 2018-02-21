package com.zjzmjr.core.model.weixin.wechat.menu;

import java.io.Serializable;
import java.util.List;

public class Button implements Serializable{
	
	private static final long serialVersionUID = -8517948927432743299L;
	private String type;
	private String name;
	private List<Button> sub_button;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Button> getSub_button() {
		return sub_button;
	}
	public void setSub_button(List<Button> sub_button) {
		this.sub_button = sub_button;
	}
	@Override
	public String toString() {
		return "Button [type=" + type + ", name=" + name + ", sub_button="
				+ sub_button + "]";
	}
	
}
