package com.zjzmjr.core.model.audit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 业务审核model
 * 
 * @author lenovo
 * @version $Id: ManageAudit.java, v 0.1 2017-8-31 下午7:26:09 lenovo Exp $
 */
public class ManageAudit implements Serializable{

    /**  */
    private static final long serialVersionUID = -5359994580125046329L;

    private Integer id;

    private Integer companyId;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 联系人
     */
    private Integer applicant;

    /**
     * 审核类型
     */
    private Integer type;

    /**
     * 付款方式
     */
    private Integer paymentMode;

    /**
     * 审核id
     */
    private BigDecimal amount;

    /**
     * 状态
     */
    private Integer status;

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

    
    public Integer getProjectId() {
        return projectId;
    }

    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    
    public Integer getApplicant() {
        return applicant;
    }

    
    public void setApplicant(Integer applicant) {
        this.applicant = applicant;
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


    @Override
    public String toString() {
        return "ManageAudit [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", applicant=" + applicant + ", type=" + type + ", paymentMode=" + paymentMode + ", amount=" + amount + ", status=" + status + ", memo=" + memo + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }
    
    
}
