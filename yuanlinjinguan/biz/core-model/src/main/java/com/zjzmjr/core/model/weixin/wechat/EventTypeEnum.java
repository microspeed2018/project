package com.zjzmjr.core.model.weixin.wechat;

/**
 * 接收事件消息类型
 * @author Administrator
 *
 */
public enum EventTypeEnum {
	
	SUBSCRIBE("subscribe","订阅事件");
	
	private String  eventKey;
	
	private String eventDesp;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getEventDesp() {
		return eventDesp;
	}

	public void setEventDesp(String eventDesp) {
		this.eventDesp = eventDesp;
	}

	private EventTypeEnum(String eventKey, String eventDesp) {
		this.eventKey = eventKey;
		this.eventDesp = eventDesp;
	}
	
}
