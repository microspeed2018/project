/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.model.sms;

import java.util.Date;
import java.util.List;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 
 * @author Administrator
 * @version $Id: NotifyQuery.java, v 0.1 2015-10-31 下午4:33:29 Administrator Exp
 *          $
 */
public class NotifyQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = 6155489683954831622L;

    private Integer userId;

    /**
     * 用户登陆名
     */
    private String userName;

    private Integer type;

    private Integer status;

    private Integer priority;

    private String destination;

    private String content;

    private String cause;

    /**
     * 发送时间
     */
    private Date sendTime;

    private List<String> teList;
    
    private Date createTime;
    
    private Date lastTime;

    /**
     * 发送时间String类型
     */
    private String sendDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public List<String> getTeList() {
        return teList;
    }

    public void setTeList(List<String> teList) {
        this.teList = teList;
    }

    
    public String getSendDate() {
        return sendDate;
    }

    
    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
    
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getLastTime() {
        return lastTime;
    }
    
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "NotifyQuery [userId=" + userId + ", userName=" + userName + ", type=" + type + ", status=" + status + ", priority=" + priority + ", destination=" + destination + ", content=" + content + ", cause=" + cause + ", sendTime=" + sendTime + ", teList=" + teList + ", createTime=" + createTime + ", lastTime=" + lastTime + ", sendDate=" + sendDate + "]";
    }
  

}
