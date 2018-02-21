package com.zjzmjr.core.model.weixin.wechat.menu;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * 
 * @author oms
 * @version $Id: ImageMsgPush.java, v 0.1 2017-3-22 下午7:42:10 oms Exp $
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
@XmlType(propOrder = { "ToUserName", "FromUserName", "CreateTime", "MsgType", "PicUrl", "MediaId", "MsgId" })
public class ImageMsgPush implements Serializable {

    /**  */
    private static final long serialVersionUID = -2500701247226169109L;

    private String ToUserName;

    private String FromUserName;

    private Long CreateTime;

    private String MsgType;

    private String PicUrl;

    private String MediaId;

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

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    @Override
    public String toString() {
        return "ImageMsgPush [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType + ", PicUrl=" + PicUrl + ", MediaId=" + MediaId + ", MsgId=" + MsgId + "]";
    }

}
