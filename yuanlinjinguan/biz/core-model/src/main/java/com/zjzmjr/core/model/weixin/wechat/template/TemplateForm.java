package com.zjzmjr.core.model.weixin.wechat.template;

import java.io.Serializable;

/**
 * 模版消息form
 * @author Administrator
 *
 */
public class TemplateForm implements Serializable{
	
	private static final long serialVersionUID = 6321191830521832216L;
	
	//是
	private String touser;
	
	//是
	private String template_id;
	
	//否
	private String url;
	
	//是
	private TemplateData data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TemplateData getData() {
		return data;
	}

	public void setData(TemplateData data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
