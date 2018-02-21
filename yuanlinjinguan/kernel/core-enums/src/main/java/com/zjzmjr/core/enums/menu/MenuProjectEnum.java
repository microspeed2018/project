package com.zjzmjr.core.enums.menu;


/**
 * 菜单使用系统枚举类
 * 
 * @author chenning
 * @version $Id: MenuProjectEnum.java, v 0.1 2016-11-7 上午10:47:29 chenning Exp $
 */
public enum MenuProjectEnum {
    
    DECIDER(1,"决胜系统"),
    ADMIN(2,"后台管理系统"),
    CREDIT(3, "征信系统");
    
    private Integer value;

    private String message;

    private MenuProjectEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static MenuProjectEnum getByValue(Integer value) {
        if (value != null) {
            for (MenuProjectEnum enu : values()) {
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
