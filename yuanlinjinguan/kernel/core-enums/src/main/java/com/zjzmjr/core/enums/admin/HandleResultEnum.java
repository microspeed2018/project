package com.zjzmjr.core.enums.admin;


public enum HandleResultEnum {
    
    HANDLING(0,"处理中"),
    SUCCESS(1,"成功"),
    FAIL(2,"失败");
    
    private Integer value;

    private String message;

    private HandleResultEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static HandleResultEnum getByValue(Integer value) {
        if (value != null) {
            for (HandleResultEnum enu : values()) {
                if (enu.value.equals(value)) {
                    return enu;
                }
            }
        }
        return null;
    }

    public String toString() {
        return value + "|" + message;
    }

}
