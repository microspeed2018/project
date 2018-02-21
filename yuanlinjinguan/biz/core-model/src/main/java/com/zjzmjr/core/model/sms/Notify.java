package com.zjzmjr.core.model.sms;

/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */

import java.io.Serializable;
import java.util.Date;

/**
 * 消息通知 __fs_notify
 * 
 * @author elliott
 * @version $Id: Notify.java, v 1.0 2014-2-18 下午1:07:04 elliott Exp $
 */
public class Notify implements Serializable {

    private static final long serialVersionUID = -7588731971091814663L;

    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;
    
    private Integer receiveUserId;

    private String userName;
    
    private String receiveName;

    /**
     * 通知类型0语言 1.邮件 2.短信 
     */
    private Integer type;

    /**
     * 发送目的地
     */
    private String destination;

    /**
     * 通知标题 注：短信没有标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知原因
     */
    private String cause;

    /**
     * 发送失败次数
     */
    private Integer failCount;

    /**
     * 优先级 越小越高麻烦
     */
    private Integer priority;

    /**
     * 发送状态
     */
    private Integer status;

    /**
     * 计划发送时间
     */
    private Date scheduleTime;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 事件源头类型
     */
    private Integer sourceType;

    /**
     * 事件源头ID
     */
    private Integer sourceId;

    /**
     * 手机号码
     */
    private String mobile;

    private Integer version;

    public Notify() {
    }

    public Notify(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Integer receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getReceiveName() {
        return receiveName;
    }
    
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCreateTime() {
        if(null == createTime){
            createTime = new Date();
        }
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cause == null) ? 0 : cause.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((destination == null) ? 0 : destination.hashCode());
        result = prime * result + ((failCount == null) ? 0 : failCount.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
        result = prime * result + ((priority == null) ? 0 : priority.hashCode());
        result = prime * result + ((receiveUserId == null) ? 0 : receiveUserId.hashCode());
        result = prime * result + ((scheduleTime == null) ? 0 : scheduleTime.hashCode());
        result = prime * result + ((sendTime == null) ? 0 : sendTime.hashCode());
        result = prime * result + ((sourceId == null) ? 0 : sourceId.hashCode());
        result = prime * result + ((sourceType == null) ? 0 : sourceType.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Notify other = (Notify) obj;
        if (cause == null) {
            if (other.cause != null)
                return false;
        } else if (!cause.equals(other.cause))
            return false;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (createTime == null) {
            if (other.createTime != null)
                return false;
        } else if (!createTime.equals(other.createTime))
            return false;
        if (destination == null) {
            if (other.destination != null)
                return false;
        } else if (!destination.equals(other.destination))
            return false;
        if (failCount == null) {
            if (other.failCount != null)
                return false;
        } else if (!failCount.equals(other.failCount))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (mobile == null) {
            if (other.mobile != null)
                return false;
        } else if (!mobile.equals(other.mobile))
            return false;
        if (priority == null) {
            if (other.priority != null)
                return false;
        } else if (!priority.equals(other.priority))
            return false;
        if (receiveUserId == null) {
            if (other.receiveUserId != null)
                return false;
        } else if (!receiveUserId.equals(other.receiveUserId))
            return false;
        if (scheduleTime == null) {
            if (other.scheduleTime != null)
                return false;
        } else if (!scheduleTime.equals(other.scheduleTime))
            return false;
        if (sendTime == null) {
            if (other.sendTime != null)
                return false;
        } else if (!sendTime.equals(other.sendTime))
            return false;
        if (sourceId == null) {
            if (other.sourceId != null)
                return false;
        } else if (!sourceId.equals(other.sourceId))
            return false;
        if (sourceType == null) {
            if (other.sourceType != null)
                return false;
        } else if (!sourceType.equals(other.sourceType))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Notify [id=" + id + ", userId=" + userId + ", receiveUserId=" + receiveUserId + ", userName=" + userName + ", receiveName=" + receiveName + ", type=" + type + ", destination=" + destination + ", title=" + title + ", content=" + content + ", cause=" + cause + ", failCount=" + failCount + ", priority=" + priority + ", status=" + status + ", scheduleTime=" + scheduleTime + ", sendTime=" + sendTime + ", createTime=" + createTime + ", sourceType=" + sourceType + ", sourceId="
                + sourceId + ", mobile=" + mobile + ", version=" + version + "]";
    }

}
