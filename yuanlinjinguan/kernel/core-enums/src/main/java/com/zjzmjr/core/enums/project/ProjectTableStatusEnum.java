package com.zjzmjr.core.enums.project;

/**
 * 系统中表的共同状态的定义枚举
 * 
 * @author oms
 * @version $Id: ProjectTableStatusEnum.java, v 0.1 2017-8-31 下午7:49:45 oms Exp $
 */
public enum ProjectTableStatusEnum {

    NOT_VERIFY(0, "未审核"),

    VERIFIED(1, "审核通过"),

    VERIFIED_FAIL(2, "审核不通过");

    private Integer value;

    private String message;

    private ProjectTableStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static ProjectTableStatusEnum getByValue(Integer value) {
        if (value != null) {
            for (ProjectTableStatusEnum enu : values()) {
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
