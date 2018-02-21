package com.zjzmjr.core.model.weixin.wechat.menu;

import java.io.Serializable;
import java.util.List;



public class Menu implements Serializable{
	
	private static final long serialVersionUID = 390992203334066252L;
	private List<Button> button;

	public List<Button> getButton() {
		return button;
	}
	public void setButton(List<Button> button) {
		this.button = button;
	}
	
	@Override
	public String toString() {
		return "Menu [button=" + button + "]";
	}
	
}
