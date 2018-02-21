/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.enums.sms;

/**
 * 
 * @author Administrator
 * @version $Id: NotifyTypeEnum.java, v 0.1 2015-11-10 下午1:31:40 Administrator Exp $
 */
public enum NotifyTypeEnum {
    /**
     * 邮件
     */
    EMAIL(1,"邮件"),
    /**
     * 短信
     */
    SMS(2,"短信"),
    /**
     * 语音短信
     */
    VOICE(3, "语音");
    private Integer value;
    private String message;
    
    private NotifyTypeEnum(Integer value,String message){
        this.value = value;
        this.message = message;
    }
    
    public static NotifyTypeEnum getByValue(Integer value){
        if(value!=null){
            for(NotifyTypeEnum enu:values()){
                if(enu.value.equals(value)){
                    return enu;
                }
            }
        }
        return null;
    }
    
    
    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public String toString(){
        return value+"|"+message;
    }
}
