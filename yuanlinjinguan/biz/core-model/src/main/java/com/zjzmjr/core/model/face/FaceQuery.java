package com.zjzmjr.core.model.face;

import com.zjzmjr.core.base.page.BasePageQuery;


public class FaceQuery extends BasePageQuery{

    private String name;
    
    private String idCard;
    
    private String bizNo;

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getIdCard() {
        return idCard;
    }

    
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    
    public String getBizNo() {
        return bizNo;
    }

    
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }


    @Override
    public String toString() {
        return "FaceQuery [name=" + name + ", idCard=" + idCard + ", bizNo=" + bizNo + "]";
    }
    
    
}
