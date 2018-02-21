/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.enums.sms;

/**
 * 
 * @author Administrator
 * @version $Id: NotifyStatusEnum.java, v 0.1 2015-11-10 下午1:30:25 Administrator
 *          Exp $
 */
public enum NotifyStatusEnum {
    /**
     * 已取消
     */
    CANCEL(0, "已取消"),
    /**
     * 待发送
     */
    WAIT(1, "待发送"),
    /**
     * 发送中
     */
    SENDING(2, "发送中"),
    /**
     * 已发送
     */
    COMPLETE(3, "已发送"),
    /**
     * 发送失败
     */
    FAIL(4, "发送失败");

    private Integer value;

    private String message;

    private NotifyStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static NotifyStatusEnum getByValue(Integer value) {
        if (value != null) {
            for (NotifyStatusEnum enu : values()) {
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
