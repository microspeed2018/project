package com.zjzmjr.core.model.audit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 法务审核表
 * 
 * @author lenovo
 * @version $Id: Law.java, v 0.1 2017-9-4 下午5:02:37 lenovo Exp $
 */
public class LawAudit implements Serializable{

    /**  */
    private static final long serialVersionUID = -7919913196894888529L;

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    private Integer applicant;

    private Integer type;

    private BigDecimal amount;

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
        return "Law [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", applicant=" + applicant + ", type=" + type + ", status=" + status + ", memo=" + memo + ", createUserId=" + createUserId + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }
    
    
}
