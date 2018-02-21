package com.zjzmjr.core.model.company;

import java.io.Serializable;
import java.util.Date;

/**
 * 职位权限信息表
 * 
 * @author oms
 * @version $Id: JobAuthority.java, v 0.1 2017-8-29 上午11:15:42 oms Exp $
 */
public class JobAuthority implements Serializable {

    /**  */
    private static final long serialVersionUID = 8113896585132973547L;

    private Integer id;

    private Integer companyId;

    private Integer jobId;

    private Integer authorityId;

    /** 部门id */
    private Integer departmentId;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    public JobAuthority(Integer id, Integer companyId, Integer jobId, Integer authorityId, Date createTime, Integer createUserId, Date updateTime, Integer updateUserId) {
        super();
        this.id = id;
        this.companyId = companyId;
        this.jobId = jobId;
        this.authorityId = authorityId;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.updateTime = updateTime;
        this.updateUserId = updateUserId;
    }

    public JobAuthority(Integer companyId, Integer jobId, Integer authorityId, Date createTime, Integer createUserId) {
        super();
        this.companyId = companyId;
        this.jobId = jobId;
        this.authorityId = authorityId;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.updateTime = createTime;
        this.updateUserId = createUserId;
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

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
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
        return "JobAuthority [id=" + id + ", companyId=" + companyId + ", jobId=" + jobId + ", authorityId=" + authorityId + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}