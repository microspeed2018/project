package com.zjzmjr.core.common.biz.weixin;

/**
 * 微信响应消息枚举
 * @author Administrator
 *
 */
public enum ResultEnum {

	SYSTEM_BUSY(-1, "系统繁忙"), 
	REQUEST_SUCCESS(0, "请求成功"),
	REQUEST_FAIL(1, "请求失败");

	private Integer errcode;

	private String errmsg;

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	private ResultEnum(Integer errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

}
