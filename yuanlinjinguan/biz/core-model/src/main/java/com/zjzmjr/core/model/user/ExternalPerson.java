package com.zjzmjr.core.model.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 外部人员表
 * 
 * @author oms
 * @version $Id: ExternalPerson.java, v 0.1 2017-8-15 下午3:43:43 oms Exp $
 */
public class ExternalPerson implements Serializable {

    /**  */
    private static final long serialVersionUID = -126084066484774306L;

    private Integer id;

    private Integer userId;

    private String name;

    private Integer personnelNature;

    private String company;

    private String job;

    private String mobile;

    private String email;

    private Integer relatedPerson;

    private String memo;

    private Integer status;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;
    
    /**
     * 员工编号
     */
    private Integer employeeNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPersonnelNature() {
        return personnelNature;
    }

    public void setPersonnelNature(Integer personnelNature) {
        this.personnelNature = personnelNature;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getRelatedPerson() {
        return relatedPerson;
    }

    public void setRelatedPerson(Integer relatedPerson) {
        this.relatedPerson = relatedPerson;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    
    public Integer getEmployeeNo() {
        return employeeNo;
    }

    
    public void setEmployeeNo(Integer employeeNo) {
        this.employeeNo = employeeNo;
    }

    @Override
    public String toString() {
        return "ExternalPerson [id=" + id + ", userId=" + userId + ", name=" + name + ", personnelNature=" + personnelNature + ", company=" + company + ", job=" + job + ", mobile=" + mobile + ", email=" + email + ", relatedPerson=" + relatedPerson + ", memo=" + memo + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", employeeNo=" + employeeNo + "]";
    }

}