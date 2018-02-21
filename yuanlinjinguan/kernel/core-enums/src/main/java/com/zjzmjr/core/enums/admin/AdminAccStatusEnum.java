/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.enums.admin;

/**
 * ADMIN用户状态枚举
 * 
 * @author js
 * @version $Id: AdminAccStatusEnum.java, v 0.1 2015年10月28日 下午5:43:17 js Exp $
 */
public enum AdminAccStatusEnum {
    /**
     * 正常
     */
    NORMAL(0, "正常"), /**
                      * 冻结
                      */
    FROZEN(1, "冻结"), /**
                      * 注销
                      */
    LOGOFF(2, "注销");

    private Integer value;

    private String message;

    private AdminAccStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static AdminAccStatusEnum getByValue(Integer value) {
        if (value != null) {
            for (AdminAccStatusEnum enu : values()) {
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
