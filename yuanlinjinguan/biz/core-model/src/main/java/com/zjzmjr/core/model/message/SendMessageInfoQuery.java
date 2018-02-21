package com.zjzmjr.core.model.message;

import java.util.Date;

import com.zjzmjr.core.base.page.BasePageQuery;



public class SendMessageInfoQuery extends BasePageQuery{

    /**  */
    private static final long serialVersionUID = -2146453780593514828L;

    private Integer id;

    /**
     * 商户编号
     */
    private Integer merchantId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String context;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息状态
     */
    private Integer status;
    
    private String  merchantName;

    private Date createTime;
    
    private Date lastTime;
    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getMerchantId() {
        return merchantId;
    }

    
    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
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

    
    public String getMerchantName() {
        return merchantName;
    }

    
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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
        return "SendMessageInfoQuery [id=" + id + ", merchantId=" + merchantId + ", title=" + title + ", context=" + context + ", type=" + type + ", status=" + status + ", merchantName=" + merchantName + ", createTime=" + createTime + ", lastTime=" + lastTime + "]";
    }


    
    
}
