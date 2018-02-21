package com.zjzmjr.admin.web.message.form;

import java.util.List;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 后台管理消息模块form
 * 
 * @author lenovo
 * @version $Id: MessageForm.java, v 0.1 2016-7-13 下午3:47:05 lenovo Exp $
 */
public class MessageForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = 6992617448566862431L;

    private String userCode;

    private String title;

    private String context;

    private String messageDate;

    private String messageDateEnd;

    private Integer status;

    private Integer type;

    private Integer companyId;

    private String sendUserName;

    private String userName;
    
    private String address;

    private List<Integer> userIdLst;
    
    @Override
    public String resolveFiled(String arg0) {

        return null;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessageDateEnd() {
        return messageDateEnd;
    }

    public void setMessageDateEnd(String messageDateEnd) {
        this.messageDateEnd = messageDateEnd;
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<Integer> getUserIdLst() {
        return userIdLst;
    }
    
    public void setUserIdLst(List<Integer> userIdLst) {
        this.userIdLst = userIdLst;
    }

    @Override
    public String toString() {
        return "MessageForm [userCode=" + userCode + ", title=" + title + ", context=" + context + ", messageDate=" + messageDate + ", messageDateEnd=" + messageDateEnd + ", status=" + status + ", type=" + type + ", companyId=" + companyId + ", sendUserName=" + sendUserName + ", userName=" + userName + ", address=" + address + ", userIdLst=" + userIdLst + "]";
    }

}
