package com.zjzmjr.core.enums.admin;


/**
 * 员工在职离职状态
 * 
 * @author Administrator
 * @version $Id: AdminJobStatusEnum.java, v 0.1 2017-12-18 上午10:45:46 Administrator Exp $
 */
public enum AdminJobStatusEnum {

    NONDUTY(111, "离职"),

    WORKING(110, "在职"),
    
    PREBATIONARY(112,"试用"),
    
    PRACTICE(113,"实习"),
    
    RETIRE(114,"退休"),
    
    REHELLORING(115,"返聘");

    private Integer value;

    private String message;

    private AdminJobStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static AdminJobStatusEnum getByValue(Integer value) {
        if (value != null) {
            for (AdminJobStatusEnum enu : values()) {
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
