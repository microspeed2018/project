package com.zjzmjr.core.model.weixin.wechat.template;

import java.io.Serializable;
import java.util.Map;

public class TemplateData implements Serializable {

	private static final long serialVersionUID = 242186708936036811L;

	// 消息头部
	private Map<String, String> first;
	// 产品名称
	private Map<String, String> keyword1;
	// 征信结果
	private Map<String, String> keyword2;
	// 消息尾部
	private Map<String, String> remark;

	public Map<String, String> getFirst() {
		return first;
	}

	public void setFirst(Map<String, String> first) {
		this.first = first;
	}

	public Map<String, String> getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(Map<String, String> keyword1) {
		this.keyword1 = keyword1;
	}

	public Map<String, String> getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(Map<String, String> keyword2) {
		this.keyword2 = keyword2;
	}

	public Map<String, String> getRemark() {
		return remark;
	}

	public void setRemark(Map<String, String> remark) {
		this.remark = remark;
	}

}
