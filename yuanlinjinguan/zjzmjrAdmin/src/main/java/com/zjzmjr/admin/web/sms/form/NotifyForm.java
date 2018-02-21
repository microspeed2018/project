/**
 * zjzmjr.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.admin.web.sms.form;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 
 * @author Administrator
 * @version $Id: NotifyForm.java, v 0.1 2015-11-9 下午5:11:42 Administrator Exp $
 */
public class NotifyForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = 9012375615764486650L;

    private String userName;

    private Integer type;

    private Integer status;

    private Integer priority;

    private String destination;

    private String content;

    private String sendTime;

    private String cause;

    private String messageDate;

    private String messageDateEnd;

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageDateEnd() {
        return messageDateEnd;
    }

    public void setMessageDateEnd(String messageDateEnd) {
        this.messageDateEnd = messageDateEnd;
    }

    @Override
    public String toString() {
        return "NotifyForm [userName=" + userName + ", type=" + type + ", status=" + status + ", priority=" + priority + ", destination=" + destination + ", content=" + content + ", sendTime=" + sendTime + ", cause=" + cause + ", messageDate=" + messageDate + ", messageDateEnd=" + messageDateEnd + "]";
    }

}
