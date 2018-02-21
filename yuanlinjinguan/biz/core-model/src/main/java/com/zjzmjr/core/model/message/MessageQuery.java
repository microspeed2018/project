package com.zjzmjr.core.model.message;

import java.util.Date;

import com.zjzmjr.core.base.page.BasePageQuery;

public class MessageQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = -8128613793837676493L;

    /**
     * 消息id
     */
    private Integer id;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String context;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 消息类型
     */
    private Integer type;

    private Date createTime;

    private String userCode;

    private Date lastTime;

    private Integer companyId;

    private String sendUserName;

    private String userName;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "MessageQuery [id=" + id + ", userId=" + userId + ", title=" + title + ", context=" + context + ", status=" + status + ", type=" + type + ", createTime=" + createTime + ", userCode=" + userCode + ", lastTime=" + lastTime + ", companyId=" + companyId + ", sendUserName=" + sendUserName + ", userName=" + userName + "]";
    }

}
