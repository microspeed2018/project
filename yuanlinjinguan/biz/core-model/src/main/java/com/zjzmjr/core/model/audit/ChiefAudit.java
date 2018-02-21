package com.zjzmjr.core.model.audit;

import java.io.Serializable;
import java.util.Date;

/**
 * 总工审核model
 * 
 * @author lenovo
 * @version $Id: ChiefAudit.java, v 0.1 2017-8-31 下午8:35:10 lenovo Exp $
 */
public class ChiefAudit implements Serializable{

    /**  */
    private static final long serialVersionUID = -2512314106975087956L;

    private Integer id;

    /**
     * 公司编号
     */
    private Integer companyId;

    /**
     * 项目编号
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
     * 审核状态
     */
    private Integer status;

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
        return "ChiefAudit [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", applicant=" + applicant + ", type=" + type + ", status=" + status + ", memo=" + memo + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }
       

}
