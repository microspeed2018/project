package com.zjzmjr.core.enums.user;

/**
 * 用户类型
 * 
 * @author oms
 * @version $Id: UserTypeEnum.java, v 0.1 2016-6-14 上午10:56:28 oms Exp $
 */
public enum UserTypeEnum {

    NORMAL(1, "普通用户"),
    
    SALESMAN(2, "业务员"),
    
    WORKMAN(3,"内勤"),
    
    AGRICULTURE_SALESMAN(4,"农保协保员"),
    
    CAR_LOAN_MANAGER(5,"车贷经理"),
    
    INSURANCE_SALESMAN(6,"保险业务员"),
    
    INSURANCE_MANAGER(7,"保险审核人员")
    ;
    
    private Integer value;

    private String message;

    private UserTypeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static UserTypeEnum getByValue(Integer value) {
        if (value != null) {
            for (UserTypeEnum enu : values()) {
                if (enu.value.equals(value)) {
                    return enu;
                }
            }
        }
        return null;
    }

}
