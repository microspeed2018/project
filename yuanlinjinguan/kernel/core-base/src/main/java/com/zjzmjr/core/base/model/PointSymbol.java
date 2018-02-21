package com.zjzmjr.core.base.model;

import java.io.Serializable;

/**
 * 短信模型
 * 
 * @author oms
 * @version $Id: PointSymbol.java, v 0.1 2017-11-17 上午10:08:48 oms Exp $
 */
public class PointSymbol implements Serializable {

    /**  */
    private static final long serialVersionUID = -5032128816538360785L;

    /**
     * 短信模板号
     */
    private String templateCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;
    
    private String receiveUserId;

    /**
     * 金额
     */
    private String money;

    /**
     * 验证码
     */
    private String securityCode;

    /**
     * 备注
     */
    private String reason;

    /**
     * 扩展字段
     */
    private String extendCode;

    /**
     * 时间
     */
    private String date;

    private String auditType;

    private String job;

    private String type;

    private String leader;

    private String file;

    private String sealType;

    private String file1;

    private String file2;

    private String confirmType;
    
    private String code;
    
    private String month;
    
    private String some;
    
    private String time;
    
    private String place;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExtendCode() {
        return extendCode;
    }

    public void setExtendCode(String extendCode) {
        this.extendCode = extendCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getSealType() {
        return sealType;
    }

    public void setSealType(String sealType) {
        this.sealType = sealType;
    }

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    public String getFile2() {
        return file2;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    public String getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(String confirmType) {
        this.confirmType = confirmType;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMonth() {
        return month;
    }
    
    public void setMonth(String month) {
        this.month = month;
    }
    
    public String getReceiveUserId() {
        return receiveUserId;
    }
    
    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
    
    public String getSome() {
        return some;
    }

    
    public void setSome(String some) {
        this.some = some;
    }

    
    public String getTime() {
        return time;
    }

    
    public void setTime(String time) {
        this.time = time;
    }

    
    public String getPlace() {
        return place;
    }

    
    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "PointSymbol [templateCode=" + templateCode + ", name=" + name + ", mobile=" + mobile + ", receiveUserId=" + receiveUserId + ", money=" + money + ", securityCode=" + securityCode + ", reason=" + reason + ", extendCode=" + extendCode + ", date=" + date + ", auditType=" + auditType + ", job=" + job + ", type=" + type + ", leader=" + leader + ", file=" + file + ", sealType=" + sealType + ", file1=" + file1 + ", file2=" + file2 + ", confirmType=" + confirmType + ", code=" + code
                + ", month=" + month + ", some=" + some + ", time=" + time + ", place=" + place + "]";
    }

}
