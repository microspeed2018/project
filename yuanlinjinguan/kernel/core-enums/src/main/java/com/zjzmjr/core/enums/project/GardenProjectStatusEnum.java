package com.zjzmjr.core.enums.project;

/**
 * 项目状态枚举类型
 * 
 * @author oms
 * @version $Id: GardenProjectStatusEnum.java, v 0.1 2017-8-16 下午5:40:57 oms Exp
 *          $
 */
public enum GardenProjectStatusEnum {

    NOT_VERIFY(0, "未归档"),

    SUSPEND(1, "中止"),

    END(2, "终止"),
    
    TEMPORARY(3, "临时");

    private Integer value;

    private String message;

    private GardenProjectStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static GardenProjectStatusEnum getByValue(Integer value) {
        if (value != null) {
            for (GardenProjectStatusEnum enu : values()) {
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
