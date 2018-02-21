package com.zjzmjr.core.model.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工薪酬表z_staff_wages
 * 
 * @author Administrator
 * @version $Id: StaffWages.java, v 0.1 2017-12-12 上午10:33:10 Administrator Exp
 *          $
 */
public class StaffWages implements Serializable {

    private static final long serialVersionUID = -7375099932155599245L;

    private Integer id;

    /**
     * 所属公司
     */
    private Integer companyId;

    /**
     * 员工编号
     */
    private Integer staffInfoId;

    /**
     * 薪酬年份
     */
    private Integer wagesYear;

    /**
     * 薪酬月份
     */
    private Integer wagesMonth;

    /**
     * 基本工资
     */
    private BigDecimal basePay;

    /**
     * 岗位工资
     */
    private BigDecimal postPay;

    /**
     * 绩效工资
     */
    private BigDecimal meritPay;

    /**
     * 奖金提成
     */
    private BigDecimal bonusPay;

    /**
     * 职称津贴
     */
    private BigDecimal professionalPay;

    /**
     * 司龄津贴
     */
    private BigDecimal silingPay;

    /**
     * 其他
     */
    private BigDecimal otherPay;

    /**
     * 应发小计
     */
    private BigDecimal paySubtotal;

    /**
     * 缺勤扣款
     */
    private BigDecimal absenceDeduct;

    /**
     * 社保
     */
    private BigDecimal socialFund;

    /**
     * 公积金
     */
    private BigDecimal housingProvident;

    /**
     * 其他
     */
    private BigDecimal otherDeduct;

    /**
     * 应扣小计
     */
    private BigDecimal deductSubtotal;

    /**
     * 应税工资
     */
    private BigDecimal taxableWages;

    /**
     * 代扣个税
     */
    private BigDecimal individualTax;

    /**
     * 通讯补贴
     */
    private BigDecimal communicationSubsidy;

    /**
     * 实发工资
     */
    private BigDecimal realWages;

    /**
     * 备注
     */
    private String memo;

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

    public Integer getStaffInfoId() {
        return staffInfoId;
    }

    public void setStaffInfoId(Integer staffInfoId) {
        this.staffInfoId = staffInfoId;
    }

    public Integer getWagesYear() {
        return wagesYear;
    }

    @Override
    public String toString() {
        return "StaffWages [id=" + id + ", companyId=" + companyId + ", staffInfoId=" + staffInfoId + ", wagesYear=" + wagesYear + ", wagesMonth=" + wagesMonth + ", basePay=" + basePay + ", postPay=" + postPay + ", meritPay=" + meritPay + ", bonusPay=" + bonusPay + ", professionalPay=" + professionalPay + ", silingPay=" + silingPay + ", otherPay=" + otherPay + ", paySubtotal=" + paySubtotal + ", absenceDeduct=" + absenceDeduct + ", socialFund=" + socialFund + ", housingProvident="
                + housingProvident + ", otherDeduct=" + otherDeduct + ", deductSubtotal=" + deductSubtotal + ", taxableWages=" + taxableWages + ", individualTax=" + individualTax + ", communicationSubsidy=" + communicationSubsidy + ", realWages=" + realWages + ", memo=" + memo + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

    public void setWagesYear(Integer wagesYear) {
        this.wagesYear = wagesYear;
    }

    public Integer getWagesMonth() {
        return wagesMonth;
    }

    public void setWagesMonth(Integer wagesMonth) {
        this.wagesMonth = wagesMonth;
    }

    public BigDecimal getBasePay() {
        return basePay;
    }

    public void setBasePay(BigDecimal basePay) {
        this.basePay = basePay;
    }

    public BigDecimal getPostPay() {
        return postPay;
    }

    public void setPostPay(BigDecimal postPay) {
        this.postPay = postPay;
    }

    public BigDecimal getMeritPay() {
        return meritPay;
    }

    public void setMeritPay(BigDecimal meritPay) {
        this.meritPay = meritPay;
    }

    public BigDecimal getBonusPay() {
        return bonusPay;
    }

    public void setBonusPay(BigDecimal bonusPay) {
        this.bonusPay = bonusPay;
    }

    public BigDecimal getProfessionalPay() {
        return professionalPay;
    }

    public void setProfessionalPay(BigDecimal professionalPay) {
        this.professionalPay = professionalPay;
    }

    public BigDecimal getSilingPay() {
        return silingPay;
    }

    public void setSilingPay(BigDecimal silingPay) {
        this.silingPay = silingPay;
    }

    public BigDecimal getOtherPay() {
        return otherPay;
    }

    public void setOtherPay(BigDecimal otherPay) {
        this.otherPay = otherPay;
    }

    public BigDecimal getPaySubtotal() {
        return paySubtotal;
    }

    public void setPaySubtotal(BigDecimal paySubtotal) {
        this.paySubtotal = paySubtotal;
    }

    public BigDecimal getAbsenceDeduct() {
        return absenceDeduct;
    }

    public void setAbsenceDeduct(BigDecimal absenceDeduct) {
        this.absenceDeduct = absenceDeduct;
    }

    public BigDecimal getSocialFund() {
        return socialFund;
    }

    public void setSocialFund(BigDecimal socialFund) {
        this.socialFund = socialFund;
    }

    public BigDecimal getHousingProvident() {
        return housingProvident;
    }

    public void setHousingProvident(BigDecimal housingProvident) {
        this.housingProvident = housingProvident;
    }

    public BigDecimal getOtherDeduct() {
        return otherDeduct;
    }

    public void setOtherDeduct(BigDecimal otherDeduct) {
        this.otherDeduct = otherDeduct;
    }

    public BigDecimal getDeductSubtotal() {
        return deductSubtotal;
    }

    public void setDeductSubtotal(BigDecimal deductSubtotal) {
        this.deductSubtotal = deductSubtotal;
    }

    public BigDecimal getTaxableWages() {
        return taxableWages;
    }

    public void setTaxableWages(BigDecimal taxableWages) {
        this.taxableWages = taxableWages;
    }

    public BigDecimal getIndividualTax() {
        return individualTax;
    }

    public void setIndividualTax(BigDecimal individualTax) {
        this.individualTax = individualTax;
    }

    public BigDecimal getCommunicationSubsidy() {
        return communicationSubsidy;
    }

    public void setCommunicationSubsidy(BigDecimal communicationSubsidy) {
        this.communicationSubsidy = communicationSubsidy;
    }

    public BigDecimal getRealWages() {
        return realWages;
    }

    public void setRealWages(BigDecimal realWages) {
        this.realWages = realWages;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
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
}