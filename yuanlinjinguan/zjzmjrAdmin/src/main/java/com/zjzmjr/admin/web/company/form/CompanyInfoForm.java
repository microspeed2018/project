package com.zjzmjr.admin.web.company.form;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 
 * 
 * @author oms
 * @version $Id: CompanyInfoForm.java, v 0.1 2017-8-8 下午8:06:39 oms Exp $
 */
public class CompanyInfoForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = 2905165030298672552L;

    private Integer id;

    private String companyName;

    private String legalPerson;

    /**
     * 注册资本（万元）
     */
    private String capitalFrom;

    /**
     * 注册资本（万元）
     */
    private String capitalTo;

    /**
     * 成立日期
     */
    private String durationFrom;

    /**
     * 成立日期
     */
    private String durationTo;

    private String address;

    private Integer status;

    private String createTimeStart;

    private String createTimeEnd;
    
    /**
     * 公司属性
     */
    private Integer companyDistinguish;

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCapitalFrom() {
        return capitalFrom;
    }

    public void setCapitalFrom(String capitalFrom) {
        this.capitalFrom = capitalFrom;
    }

    public String getCapitalTo() {
        return capitalTo;
    }

    public void setCapitalTo(String capitalTo) {
        this.capitalTo = capitalTo;
    }

    public String getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(String durationFrom) {
        this.durationFrom = durationFrom;
    }

    public String getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(String durationTo) {
        this.durationTo = durationTo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    
    public Integer getCompanyDistinguish() {
        return companyDistinguish;
    }

    
    public void setCompanyDistinguish(Integer companyDistinguish) {
        this.companyDistinguish = companyDistinguish;
    }

    @Override
    public String toString() {
        return "CompanyInfoForm [id=" + id + ", companyName=" + companyName + ", legalPerson=" + legalPerson + ", capitalFrom=" + capitalFrom + ", capitalTo=" + capitalTo + ", durationFrom=" + durationFrom + ", durationTo=" + durationTo + ", address=" + address + ", status=" + status + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", companyDistinguish=" + companyDistinguish + "]";
    }

}
