package com.zjzmjr.core.model.message;

import java.io.Serializable;
import java.util.List;


public class GetuiMessage implements Serializable{

    /**  */
    private static final long serialVersionUID = -3010163306167678669L;

    //appClientId
    private String cid;
    
    //通知栏标题
    private String title;
    
    //通知栏内容
    private String text;
    
    //通知栏图标
    private String logo;
    
    //通知栏网络图标
    private String logoUrl;
    
    //设置打开的网址地址
    private String url;
    
    //appClientId集合
    private List<String> cidList;

    
    public String getCid() {
        return cid;
    }

    
    public void setCid(String cid) {
        this.cid = cid;
    }

    
    public String getTitle() {
        return title;
    }

    
    public void setTitle(String title) {
        this.title = title;
    }

    
    public String getText() {
        return text;
    }

    
    public void setText(String text) {
        this.text = text;
    }

    
    public String getLogo() {
        return logo;
    }

    
    public void setLogo(String logo) {
        this.logo = logo;
    }

    
    public String getLogoUrl() {
        return logoUrl;
    }

    
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    
    public String getUrl() {
        return url;
    }

    
    public void setUrl(String url) {
        this.url = url;
    }

    
    public List<String> getCidList() {
        return cidList;
    }


    
    public void setCidList(List<String> cidList) {
        this.cidList = cidList;
    }


    @Override
    public String toString() {
        return "GetuiMessage [cid=" + cid + ", title=" + title + ", text=" + text + ", logo=" + logo + ", logoUrl=" + logoUrl + ", url=" + url + ", cidList=" + cidList + "]";
    }  
    
}