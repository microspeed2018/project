package com.zjzmjr.core.enums.file;


/**
 * 置顶文件选项枚举类
 * 
 * @author Administrator
 * @version $Id: SharedFileEnum.java, v 0.1 2017-12-18 上午10:21:02 Administrator Exp $
 */
public enum SharedFileOptionEnum {

    NO(0, "否"),

    YES(1, "是");

    private Integer value;

    private String message;

    private SharedFileOptionEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static SharedFileOptionEnum getByValue(Integer value) {
        if (value != null) {
            for (SharedFileOptionEnum enu : values()) {
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
