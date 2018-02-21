package com.zjzmjr.admin.web.message.form;

import com.zjzmjr.core.common.biz.BaseForm;


public class SendMessageForm extends BaseForm{

    /**  */
    private static final long serialVersionUID = 2471950510045547419L;

    private Integer id;
    
    private String title;
    
    private String context;
    
    private String createTime;
    
    private String lastTime;
    
    private Integer status;
    
    private Integer type;
    
    private String merchantName;
    @Override
    public String resolveFiled(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
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
    
    
    public String getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    
    public String getLastTime() {
        return lastTime;
    }

    
    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
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
    
    public String getMerchantName() {
        return merchantName;
    }
    
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "SendMessageForm [id=" + id + ", title=" + title + ", context=" + context + ", createTime=" + createTime + ", lastTime=" + lastTime + ", status=" + status + ", type=" + type + ", merchantName=" + merchantName + "]";
    }

    

   

    
}
