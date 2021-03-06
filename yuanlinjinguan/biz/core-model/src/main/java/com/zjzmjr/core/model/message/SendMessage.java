package com.zjzmjr.core.model.message;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户消息发送表model
 * 
 * @author Administrator
 * @version $Id: MsendMessage.java, v 0.1 2016-7-18 下午1:48:14 Administrator Exp $
 */
public class SendMessage implements Serializable {

    private static final long serialVersionUID = -8208949435112297376L;

    private Integer id;

    /**
     * 商户编号
     */
    private Integer merchantId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String context;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息状态
     */
    private Integer status;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "MsendMessage [id=" + id + ", merchantId=" + merchantId + ", title=" + title + ", context=" + context + ", type=" + type + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}
