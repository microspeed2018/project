package com.zjzmjr.core.enums.project;

/**
 * 分包数据状态枚举
 * 
 * @author lenovo
 * @version $Id: ProjectSubpackageStatusEnum.java, v 0.1 2017-10-19 下午1:22:13 lenovo Exp $
 */
public enum ProjectSubpackageStatusEnum {

    NOT_VERIFY(0, "未审核"),

    VERIFIED(1, "审核通过"),

    VERIFIED_FAIL(2, "审核不通过"),
    
    INSERT(3, "新增"),
    
    UPDATE(4, "修改"),
    
    DELETE(5, "删除");

    private Integer value;

    private String message;

    private ProjectSubpackageStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static ProjectSubpackageStatusEnum getByValue(Integer value) {
        if (value != null) {
            for (ProjectSubpackageStatusEnum enu : values()) {
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
