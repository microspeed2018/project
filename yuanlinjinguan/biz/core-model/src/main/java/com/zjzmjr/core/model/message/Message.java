package com.zjzmjr.core.model.message;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户消息表t_message
 * 
 * @author lenovo
 * @version $Id: Message.java, v 0.1 2016-7-12 上午10:33:01 lenovo Exp $
 */
public class Message implements Serializable {

    /**  */
    private static final long serialVersionUID = -5953701410933836950L;

    /**
     * 消息id
     */
    private Integer id;

    private Integer companyId;

    /**
     * 用户编号
     */
    private Integer userId;
    
    /**
     * 个推clientId
     */
    private String clientId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息内容
     */
    private String context;

    /**
     * 请求地址
     */
    private String address;

    /**
     * 状态
     */
    private Integer status;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    /**
     * 消息发布日期
     */
    private String messageDate;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
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

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public String getClientId() {
        return clientId;
    }

    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    
    public String getUserName() {
        return userName;
    }

    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", clientId=" + clientId + ", userName=" + userName + ", title=" + title + ", type=" + type + ", context=" + context + ", address=" + address + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", messageDate=" + messageDate + "]";
    }

}
