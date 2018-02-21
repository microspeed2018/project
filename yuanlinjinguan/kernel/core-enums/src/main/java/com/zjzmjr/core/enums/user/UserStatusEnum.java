package com.zjzmjr.core.enums.user;

/**
 * 用户的状态
 * 
 * @author oms
 * @version $Id: UserStatusEnum.java, v 0.1 2016-6-13 下午4:36:58 oms Exp $
 */
public enum UserStatusEnum {
    
    FROZEN(Integer.valueOf(0), "冻结"), 
    NORMAL(Integer.valueOf(1), "正常"),
    LOGOFF(Integer.valueOf(2), "注销"),
    TO_ACTIVATE(Integer.valueOf(3), "待激活");

    private Integer value;

    private String message;

    private UserStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static UserStatusEnum getByValue(Integer value) {
        if (value != null) {
            for (UserStatusEnum enu : values()) {
                if (enu.value.equals(value)) {
                    return enu;
                }
            }
        }
        return null;
    }

}
