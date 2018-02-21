package com.zjzmjr.core.model.message;

import com.zjzmjr.core.model.admin.Admin;

public class MessageInfo extends Message {

    /**  */
    private static final long serialVersionUID = -6008479200778478580L;

    /**
     * 用户信息
     */
    private Admin admin;
    
    private String sendUserName;

    private SendMessage sendMessage;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
    public String getSendUserName() {
        return sendUserName;
    }
    
    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public String toString() {
        return "MessageInfo [admin=" + admin + ", sendUserName=" + sendUserName + ", sendMessage=" + sendMessage + "]";
    }

}
