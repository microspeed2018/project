package com.zjzmjr.core.model.company;

import java.math.BigDecimal;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 关联公司信息查询
 * 
 * @author oms
 * @version $Id: CompanyAssociatedQuery.java, v 0.1 2017-8-11 下午4:54:03 oms Exp
 *          $
 */
public class CompanyAssociatedQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = 8437843735495000881L;
    
    private Integer id;
    
    private Integer companyId;

    private String companyName;

    private String legalPerson;

    private String address;
    
    /**
     * 公司属性
     */
    private Integer companyDistinguish;

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

    private Integer status;

    private String createTimeStart;

    private String createTimeEnd;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getCompanyDistinguish() {
        return companyDistinguish;
    }

    
    public void setCompanyDistinguish(Integer companyDistinguish) {
        this.companyDistinguish = companyDistinguish;
    }

    
    public Integer getCompanyId() {
        return companyId;
    }

    
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CompanyAssociatedQuery [id=" + id + ", companyId=" + companyId + ", companyName=" + companyName + ", legalPerson=" + legalPerson + ", address=" + address + ", companyDistinguish=" + companyDistinguish + ", capitalFrom=" + capitalFrom + ", capitalTo=" + capitalTo + ", durationFrom=" + durationFrom + ", durationTo=" + durationTo + ", status=" + status + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + "]";
    }

}
