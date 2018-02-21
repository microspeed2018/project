/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.enums.admin;

/**
 * 部门类型
 * 
 * @author js
 * @version $Id: DepartmentEnum.java, v 0.1 2015年10月28日 下午5:42:01 js Exp $
 */
public enum DepartmentEnum {

    /**
     * 总经理室
     */
    MANAGER_ROOM(1, "总经理室"),

    /**
     * 总助
     */
    MANAGER_ASSIST(2, "总助"),

    /**
     * 市场部
     */
    FINANCE(3, "财务部"),

    /**
     * 风控部
     */
    HUMAN_ADMIN(4, "人事行政部"),

    /**
     * 行政部
     */
    ASSET_ENSURE(5, "资产保全部"),

    /**
     * 财务部
     */
    RISK_MANAGER(6, "风险管理部"),

    /**
     * 内控部
     */
    BUSINESS_EXPAND(7, "业务部"),

    /**
     * 瑞丰银行征信用
     */
    RUIFENG_BANK(8, "市场部"),
    
    /**
     * 其他
     */
    OTHER(99, "其他");


    private Integer value;

    private String message;

    private DepartmentEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static DepartmentEnum getByValue(Integer value) {
        if (value != null) {
            for (DepartmentEnum enu : values()) {
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
