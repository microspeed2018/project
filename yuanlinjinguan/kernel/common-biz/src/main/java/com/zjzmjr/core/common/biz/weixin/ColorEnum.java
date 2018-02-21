package com.zjzmjr.core.common.biz.weixin;

/**
 * 模版消息颜色类型枚举类
 * @author Administrator
 *
 */
public enum ColorEnum {

	BLUE_COLOR("#173177", "蓝色");

	private String hexColor;
	private String desp;

	public String getHexColor() {
		return hexColor;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	private ColorEnum(String hexColor, String desp) {
		this.hexColor = hexColor;
		this.desp = desp;
	}
}
