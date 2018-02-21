/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.core.enums.menu;

/**
 * 权限类型
 * 
 * @author js
 * @version $Id: AuthTypeEnum.java, v 1.0 2014-1-17 下午1:41:30 elliott Exp $
 */
public enum AuthTypeEnum {
    
    /** 基本信息管理 */
    INFO_ROLE(5, "基本信息管理"),
    /** 业务操作 */
    BUSINESS_ROLE(10, "业务操作"),
    /** 报表统计 */
    REPORT_ROLE(15, "报表统计"),
    /** 平台管理 */
    PLAT_ROLE(20, "平台管理"),
    /** 系统管理 */
    SYSTEM_ROLE(25, "系统管理")
    ;

    private Integer value;

    private String message;

    private AuthTypeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static AuthTypeEnum getByValue(Integer value) {
        if (value != null) {
            for (AuthTypeEnum enu : values()) {
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
