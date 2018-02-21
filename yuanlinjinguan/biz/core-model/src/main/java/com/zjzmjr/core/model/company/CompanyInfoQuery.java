package com.zjzmjr.core.model.company;

import java.math.BigDecimal;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: CompanyInfoQuery.java, v 0.1 2017-8-8 下午6:29:30 oms Exp $
 */
public class CompanyInfoQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = -166909423873691573L;

    private Integer id;

    private String companyName;

    private String legalPerson;

    /**
     * 注册资本（万元）
     */
    private BigDecimal capitalFrom;

    /**
     * 注册资本（万元）
     */
    private BigDecimal capitalTo;

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

    public BigDecimal getCapitalFrom() {
        return capitalFrom;
    }

    public void setCapitalFrom(BigDecimal capitalFrom) {
        this.capitalFrom = capitalFrom;
    }

    public BigDecimal getCapitalTo() {
        return capitalTo;
    }

    public void setCapitalTo(BigDecimal capitalTo) {
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

    @Override
    public String toString() {
        return "CompanyInfoQuery [id=" + id + ", companyName=" + companyName + ", legalPerson=" + legalPerson + ", capitalFrom=" + capitalFrom + ", capitalTo=" + capitalTo + ", durationFrom=" + durationFrom + ", durationTo=" + durationTo + ", address=" + address + ", status=" + status + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + "]";
    }

}
