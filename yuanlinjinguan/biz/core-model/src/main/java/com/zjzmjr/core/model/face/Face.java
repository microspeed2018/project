package com.zjzmjr.core.model.face;

import java.io.Serializable;


public class Face implements Serializable{

    /**  */
    private static final long serialVersionUID = 8211424851551266939L;

    private String token;
    
    private String bizId;
    
    private String webUrl;
    
    public String getToken() {
        return token;
    }

    
    public void setToken(String token) {
        this.token = token;
    }

    
    public String getBizId() {
        return bizId;
    }

    
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    
    public String getWebUrl() {
        return webUrl;
    }


    
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }


    @Override
    public String toString() {
        return "Face [token=" + token + ", bizId=" + bizId + ", webUrl=" + webUrl + "]";
    }

}
