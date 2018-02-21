package com.zjzmjr.core.model.message;

import java.util.Date;

import com.zjzmjr.core.base.page.BasePageQuery;

public class MessageInfoQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = 2195897727121136732L;

    private String title;

    private String context;

    private Integer status;

    private Date createTime;

    private String userCode;

    private Date lastTime;

    private Integer type;

    private Integer companyId;

    private String sendUserName;

    private String userName;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        return "MessageInfoQuery [title=" + title + ", context=" + context + ", status=" + status + ", createTime=" + createTime + ", userCode=" + userCode + ", lastTime=" + lastTime + ", type=" + type + ", companyId=" + companyId + ", sendUserName=" + sendUserName + ", userName=" + userName + "]";
    }

}
