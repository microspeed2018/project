package com.zjzmjr.core.model.audit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 出纳确认表
 * 
 * @author lenovo
 * @version $Id: CashierConfirmation.java, v 0.1 2017-9-1 下午4:13:28 lenovo Exp $
 */
public class CashierConfirmation implements Serializable{

    /**  */
    private static final long serialVersionUID = 3218068806856099680L;

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    private Integer type;

    private Integer paymentMode;

    private BigDecimal amount;

    private Integer status;

    private String memo;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    /**
     * 付款方式名称
     */
    private String cashierPaymentModeName;
    
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

    
    public Integer getProjectId() {
        return projectId;
    }

    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    
    public Integer getType() {
        return type;
    }

    
    public void setType(Integer type) {
        this.type = type;
    }

    
    public Integer getPaymentMode() {
        return paymentMode;
    }

    
    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    
    public BigDecimal getAmount() {
        return amount;
    }

    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    
    public String getMemo() {
        return memo;
    }

    
    public void setMemo(String memo) {
        this.memo = memo;
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

    
    public String getCashierPaymentModeName() {
        return cashierPaymentModeName;
    }


    
    public void setCashierPaymentModeName(String cashierPaymentModeName) {
        this.cashierPaymentModeName = cashierPaymentModeName;
    }


    @Override
    public String toString() {
        return "CashierConfirmation [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", type=" + type + ", paymentMode=" + paymentMode + ", amount=" + amount + ", status=" + status + ", memo=" + memo + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", cashierPaymentModeName=" + cashierPaymentModeName + "]";
    }
 
}
