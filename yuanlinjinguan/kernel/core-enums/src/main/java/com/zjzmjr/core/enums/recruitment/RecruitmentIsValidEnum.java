package com.zjzmjr.core.enums.recruitment;



public enum RecruitmentIsValidEnum {

    INVALID(0, "无效"),

    EFFECTIVE(1, "有效"),

    CHECK_PENDING(2, "审批中");

    private Integer value;

    private String message;

    private RecruitmentIsValidEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static RecruitmentIsValidEnum getByValue(Integer value) {
        if (value != null) {
            for (RecruitmentIsValidEnum enu : values()) {
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
