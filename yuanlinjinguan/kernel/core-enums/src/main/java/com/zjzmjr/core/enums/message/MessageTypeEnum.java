package com.zjzmjr.core.enums.message;

/**
 * 
 * 
 * @author oms
 * @version $Id: MessageTypeEnum.java, v 0.1 2017-9-13 下午8:36:19 oms Exp $
 */
public enum MessageTypeEnum {
    REMIND(1,"【提醒】"),

    NOTICE(2,"【公告】");
    
    private Integer value;

    private String message;
     
     private MessageTypeEnum(Integer value, String message){
         this.value = value;
         this.message = message;
     }
     public Integer getValue() {
         return value;
     }

     public String getMessage() {
         return message;
     }
     public static MessageTypeEnum getByValue(Integer value) {
         if (value != null) {
             for (MessageTypeEnum enu : values()) {
                 if (enu.value.equals(value)) {
                     return enu;
                 }
             }
         }
         return null;
     }
}
