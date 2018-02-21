package com.zjzmjr.finance.web.company.form;

import java.util.List;

import com.zjzmjr.core.common.biz.BaseForm;
import com.zjzmjr.core.model.company.CompanyAssociatedContact;

/**
 * 关联公司信息form
 * 
 * @author oms
 * @version $Id: CompanyAssociatedInfoForm.java, v 0.1 2017-8-31 下午4:59:56 oms Exp $
 */
public class CompanyAssociatedInfoForm extends BaseForm{

    /**  */
    private static final long serialVersionUID = 688019657118959299L;

    private List<CompanyAssociatedContact> companyAssociatedContact;
    
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

    private String createTimeStart;

    private String createTimeEnd;
    
    private String departmentId;
    
    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    
    public List<CompanyAssociatedContact> getCompanyAssociatedContact() {
        return companyAssociatedContact;
    }

    
    public void setCompanyAssociatedContact(List<CompanyAssociatedContact> companyAssociatedContact) {
        this.companyAssociatedContact = companyAssociatedContact;
    }

    
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
        this.companyName = companyName;
    }

    
    public String getLegalPerson() {
        return legalPerson;
    }

    
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    
    public String getLegalMobile() {
        return legalMobile;
    }

    
    public void setLegalMobile(String legalMobile) {
        this.legalMobile = legalMobile;
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
        this.address = address;
    }

    
    public String getIntroduction() {
        return introduction;
    }

    
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    
    public String getBankName() {
        return bankName;
    }

    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    
    public String getAccountNo() {
        return accountNo;
    }

    
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    
    public String getLicenseNumber() {
        return licenseNumber;
    }

    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    
    public String getMobile() {
        return mobile;
    }

    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    
    public String getFaxPhone() {
        return faxPhone;
    }

    
    public void setFaxPhone(String faxPhone) {
        this.faxPhone = faxPhone;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }


    
    public String getCreateTimeStart() {
        return createTimeStart;
    }


    
    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }


    
    public String getCreateTimeEnd() {
        return createTimeEnd;
    }


    
    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    
    public String getDepartmentId() {
        return departmentId;
    }


    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }


    @Override
    public String toString() {
        return "CompanyAssociatedInfoForm [companyAssociatedContact=" + companyAssociatedContact + ", id=" + id + ", companyId=" + companyId + ", companyDistinguish=" + companyDistinguish + ", companyName=" + companyName + ", legalPerson=" + legalPerson + ", legalMobile=" + legalMobile + ", cityId=" + cityId + ", address=" + address + ", introduction=" + introduction + ", bankName=" + bankName + ", accountNo=" + accountNo + ", licenseNumber=" + licenseNumber + ", mobile=" + mobile
                + ", faxPhone=" + faxPhone + ", status=" + status + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", departmentId=" + departmentId + "]";
    }

}
