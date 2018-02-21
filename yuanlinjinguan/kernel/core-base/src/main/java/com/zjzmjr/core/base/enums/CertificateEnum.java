package com.zjzmjr.core.base.enums;

/**
 * 证件类型
 * 
 * @author oms
 * @version $Id: CertificateEnum.java, v 0.1 2016-6-29 上午11:16:49 oms Exp $
 */
public enum CertificateEnum {

    IDCard("01", "身份证"),
    PASSPORT("03", "护照"),
    GANGAOHVPS("04", "港澳回乡证"),
    MTP("05", "台胞证"),
    HOUSEHOLD("07", "户口簿"),
    SAILORCARD("27", "军官证"),
    ORGANIZATION("51", "组织机构代码"),
    ENTERPRISE_LICENSE("55", "企业法人营业执照"),
    OTHER_CARD("99", "其它证件");
    
    private String code;

    private String name;

    private CertificateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static CertificateEnum getByValue(String code) {
        if (code != null) {
            for (CertificateEnum enu : values()) {
                if (enu.code.equals(code)) {
                    return enu;
                }
            }
        }
        return null;
    }
}
