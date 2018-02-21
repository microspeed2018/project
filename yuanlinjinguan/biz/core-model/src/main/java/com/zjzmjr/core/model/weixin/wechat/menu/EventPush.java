package com.zjzmjr.core.model.weixin.wechat.menu;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 接收事件推送
 * @author Administrator
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
@XmlType(propOrder={
	"ToUserName",
	"FromUserName",
	"CreateTime",
	"MsgType",
	"Event",
	"EventKey",
	"Ticket"
})
public class EventPush implements Serializable{

	private static final long serialVersionUID = -1883445943103812552L;
	/**
	 * 开发者微信号
	 */
	private String ToUserName;
	/**
	 * 发送方帐号（一个OpenID）
	 */
	private String FromUserName;
	/**
	 * 消息创建时间 （整型）
	 */
	private Long CreateTime;
	/**
	 * 消息类型，event
	 */
	private String MsgType;
	/**
	 * 事件类型，VIEW|事件类型，CLICK
	 */
	private String Event;
	/**
	 * 事件KEY值，与自定义菜单接口中KEY值对应|事件KEY值，设置的跳转URL
	 */
	private String EventKey;
	
	/**
	 * 二维码的ticket，可用来换取二维码图片
	 */
	private String Ticket;
	
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	
	public String getTicket() {
		return Ticket;
	}
	
	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

    @Override
    public String toString() {
        return "EventPush [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType + ", Event=" + Event + ", EventKey=" + EventKey + ", Ticket=" + Ticket + "]";
    }

}
