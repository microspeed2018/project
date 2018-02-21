package com.zjzmjr.core.enums.interview;


/**
 * 面试结果枚举
 * 
 * @author lenovo
 * @version $Id: InterviewResultEnum.java, v 0.1 2018-1-2 下午6:25:16 lenovo Exp $
 */
public enum InterviewResultEnum { 

    PASS(1, "通过"),

    ENTER(2, "录取"),
    
    NOT_PASS(3, "不通过"),
    
    NOT_ATTEND(9,"未参加");

    private Integer value;

    private String message;

    private InterviewResultEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static InterviewResultEnum getByValue(Integer value) {
        if (value != null) {
            for (InterviewResultEnum enu : values()) {
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
