package com.zjzmjr.core.model.company;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 企业信息
 * 
 * @author oms
 * @version $Id: CompanyInfo.java, v 0.1 2017-8-8 下午6:13:45 oms Exp $
 */
public class CompanyInfo implements Serializable {

    /**  */
    private static final long serialVersionUID = 4467551047428953159L;

    private Integer id;

    private String licenseNumber;

    private String companyName;

    private String legalPerson;

    /**
     * 注册资本（万元）
     */
    private BigDecimal registeredCapital;

    /**
     * 成立日期
     */
    private String duration;

    private String introduction;

    /**
     * 主要业务范围
     */
    private String businessScope;

    private String qualityGrade;

    private String accountName;

    private String bankName;

    private String accountNo;

    private String iosQuality;

    private String mobile;

    private String faxPhone;

    private Integer staffCount;

    /**
     * 所在城市地区编号
     */
    private Integer cityId;

    private String address;

    private String zipCode;

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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber == null ? null : licenseNumber.trim();
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

    public BigDecimal getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope == null ? null : businessScope.trim();
    }

    public String getQualityGrade() {
        return qualityGrade;
    }

    public void setQualityGrade(String qualityGrade) {
        this.qualityGrade = qualityGrade == null ? null : qualityGrade.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
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

    public String getIosQuality() {
        return iosQuality;
    }

    public void setIosQuality(String iosQuality) {
        this.iosQuality = iosQuality == null ? null : iosQuality.trim();
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

    public Integer getStaffCount() {
        return staffCount;
    }

    public void setStaffCount(Integer staffCount) {
        this.staffCount = staffCount;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
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
        return "CompanyInfo [id=" + id + ", licenseNumber=" + licenseNumber + ", companyName=" + companyName + ", legalPerson=" + legalPerson + ", registeredCapital=" + registeredCapital + ", duration=" + duration + ", introduction=" + introduction + ", businessScope=" + businessScope + ", qualityGrade=" + qualityGrade + ", accountName=" + accountName + ", bankName=" + bankName + ", accountNo=" + accountNo + ", iosQuality=" + iosQuality + ", mobile=" + mobile + ", faxPhone=" + faxPhone
                + ", staffCount=" + staffCount + ", cityId=" + cityId + ", address=" + address + ", zipCode=" + zipCode + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}