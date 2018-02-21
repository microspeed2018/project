package com.zjzmjr.core.model.company;

import java.io.Serializable;
import java.util.Date;

/**
 * 关联公司表
 * 
 * @author oms
 * @version $Id: CompanyAssociated.java, v 0.1 2017-8-11 下午4:36:47 oms Exp $
 */
public class CompanyAssociated implements Serializable {

    /**  */
    private static final long serialVersionUID = -2284598441171082909L;

    private Integer id;

    private Integer companyId;

    private Integer companyDistinguish;

    private String companyName;

    private String legalPerson;

    private String legalMobile;

    private Integer cityId;

    private String address;

    private String introduction;

    private String bankName;

    private String accountNo;

    private String licenseNumber;

    private String mobile;

    private String faxPhone;   

    private Integer status;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;  

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyDistinguish() {
        return companyDistinguish;
    }

    public void setCompanyDistinguish(Integer companyDistinguish) {
        this.companyDistinguish = companyDistinguish;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getLegalMobile() {
        return legalMobile;
    }

    public void setLegalMobile(String legalMobile) {
        this.legalMobile = legalMobile == null ? null : legalMobile.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber == null ? null : licenseNumber.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getFaxPhone() {
        return faxPhone;
    }

    public void setFaxPhone(String faxPhone) {
        this.faxPhone = faxPhone == null ? null : faxPhone.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "CompanyAssociated [id=" + id + ", companyId=" + companyId + ", companyDistinguish=" + companyDistinguish + ", companyName=" + companyName + ", legalPerson=" + legalPerson + ", legalMobile=" + legalMobile + ", cityId=" + cityId + ", address=" + address + ", introduction=" + introduction + ", bankName=" + bankName + ", accountNo=" + accountNo + ", licenseNumber=" + licenseNumber + ", mobile=" + mobile + ", faxPhone=" + faxPhone + ", status=" + status + ", createTime=" + createTime
                + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}