package com.zjzmjr.core.model.weixin.wechat.menu;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 接收上传视频的消息类
 * 
 * @author oms
 * @version $Id: VideoMsgPush.java, v 0.1 2017-3-29 下午3:26:13 oms Exp $
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
@XmlType(propOrder = { "ToUserName", "FromUserName", "CreateTime", "MsgType", "MediaId", "ThumbMediaId", "MsgId" })
public class VideoMsgPush implements Serializable {

    /**  */
    private static final long serialVersionUID = 7210880645231068497L;

    private String ToUserName;

    private String FromUserName;

    private Long CreateTime;

    private String MsgType;

    private String MediaId;

    private String ThumbMediaId;

    private String MsgId;

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

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    @Override
    public String toString() {
        return "VideoMsgPush [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType + ", MediaId=" + MediaId + ", ThumbMediaId=" + ThumbMediaId + ", MsgId=" + MsgId + "]";
    }

}
