package com.zjzmjr.core.enums.project;

/**
 * 项目方案表状态的枚举类
 * 
 * @author oms
 * @version $Id: ProjectSchemaStatusEnum.java, v 0.1 2017-8-17 下午1:31:40 oms Exp
 *          $
 */
public enum ProjectSchemaStatusEnum {

    WAITING_APPLY(0, "待申请"),

    APPLIED(1, "已申请"),

    ACCEPTED(2, "采纳"),

    REFUSED_APPLY(9, "不申请");

    private Integer value;

    private String message;

    private ProjectSchemaStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static ProjectSchemaStatusEnum getByValue(Integer value) {
        if (value != null) {
            for (ProjectSchemaStatusEnum enu : values()) {
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
