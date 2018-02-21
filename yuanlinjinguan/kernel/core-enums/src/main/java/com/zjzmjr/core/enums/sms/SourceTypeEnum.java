/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.enums.sms;

/**
 * 
 * @author Administrator
 * @version $Id: SourceTypeEnum.java, v 0.1 2015-11-10 上午11:46:07 Administrator Exp $
 */
public enum SourceTypeEnum {


    /**
     * 未定义
     */
    UNKNOW(0,"未定义"),
    /**
     * 利息支付
     */
    INTEREST_PAY(1,"利息支付"),
    /**
     * 续约申请
     */
    RENEWAL_APPLY(2,"续约申请"),
    
    /**
     * 佣金支付
     */
    UNION_PAY(3,"佣金支付"),;
    
    private Integer value;
    private String message;
    
    private SourceTypeEnum(Integer value,String message){
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public static SourceTypeEnum getByValue(Integer value){
        if(value!=null){
            for(SourceTypeEnum enu:values()){
                if(enu.value.equals(value)){
                    return enu;
                }
            }
        }
        return null;
    }
    

}
