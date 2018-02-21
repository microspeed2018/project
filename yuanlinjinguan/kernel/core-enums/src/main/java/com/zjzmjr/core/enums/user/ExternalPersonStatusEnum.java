package com.zjzmjr.core.enums.user;



public enum ExternalPersonStatusEnum {
    
    DIMISSION(0, "离职"),

    ON_THE_JOB(1, "在职"),
    
    INTO_POST(2, "已入职");

    private Integer value;

    private String message;

    private ExternalPersonStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static ExternalPersonStatusEnum getByValue(Integer value) {
        if (value != null) {
            for (ExternalPersonStatusEnum enu : values()) {
                if (enu.value.equals(value)) {
                    return enu;
                }
            }
        }
        return null;
    }

    public String toString() {
        return this.value + "|" + this.message;
    }
}
