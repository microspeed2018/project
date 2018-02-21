package com.zjzmjr.core.enums.message;



public enum MessageStatusEnum {
   READ(1,"已读"),

   UNREAD(0,"未读");
   
   private Integer value;

   private String message;
    
    private MessageStatusEnum(Integer value, String message){
        this.value = value;
        this.message = message;
    }
    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
    public static MessageStatusEnum getByValue(Integer value) {
        if (value != null) {
            for (MessageStatusEnum enu : values()) {
                if (enu.value.equals(value)) {
                    return enu;
                }
            }
        }
        return null;
    }
}
