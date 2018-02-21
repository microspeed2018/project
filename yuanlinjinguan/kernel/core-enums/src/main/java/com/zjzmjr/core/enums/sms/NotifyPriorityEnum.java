/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.enums.sms;


/**
 * 
 * @author Administrator
 * @version $Id: NotifyPriorityEnum.java, v 0.1 2015-11-11 上午9:47:51
 *          Administrator Exp $
 */
public enum NotifyPriorityEnum {

    /**
     * 最大优先级-0
     */
    MAX_PRIORITY(0, "最大优先级"),
    /**
     * 一般优先级-5
     */
    NORMAL_PRIORITY(5, "一般优先级"),
    /**
     * 最小优先级
     */
    MIN_PRIORITY(10, "最小优先级"),

    PRIORITY_0(0, "优先级-0"), PRIORITY_1(1, "优先级-1"), PRIORITY_2(2, "优先级-2"), PRIORITY_3(3, "优先级-3"), PRIORITY_4(4, "优先级-4"), PRIORITY_5(5, "优先级-5"), PRIORITY_6(6, "优先级-6"), PRIORITY_7(7, "优先级-7"), PRIORITY_8(8, "优先级-8"), PRIORITY_9(9, "优先级-9"), PRIORITY_10(10, "优先级-10");

    private Integer value;

    private String message;

    private NotifyPriorityEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static NotifyPriorityEnum getByValue(Integer value) {
        if (value != null) {
            for (NotifyPriorityEnum enu : values()) {
                if (enu.value.equals(value)) {
                    return enu;
                }
            }
        }
        return null;
    }

    public boolean equals(NotifyPriorityEnum priority) {
        if (priority != null) {
            return this.value.equals(priority.getValue());
        }
        return false;
    }

    public String toString() {
        return value + "|" + message;
    }

}
