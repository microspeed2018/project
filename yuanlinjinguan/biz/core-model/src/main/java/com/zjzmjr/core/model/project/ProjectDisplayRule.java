package com.zjzmjr.core.model.project;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目一览展示条件表
 * 
 * @author oms
 * @version $Id: ProjectDisplayRule.java, v 0.1 2017-9-4 下午4:52:23 oms Exp $
 */
public class ProjectDisplayRule implements Serializable {

    /**  */
    private static final long serialVersionUID = 6906570036587755597L;

    private Integer id;

    private Integer companyId;

    private Integer userId;

    private Integer timeOrder;

    private Integer projectLeader;

    private Integer managePerson;

    /**
     * 项目阶段,可以多个，逗号分隔
     */
    private String projectStep;

    private Integer status;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(Integer timeOrder) {
        this.timeOrder = timeOrder;
    }

    public Integer getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(Integer projectLeader) {
        this.projectLeader = projectLeader;
    }

    public Integer getManagePerson() {
        return managePerson;
    }

    public void setManagePerson(Integer managePerson) {
        this.managePerson = managePerson;
    }

    public String getProjectStep() {
        return projectStep;
    }

    public void setProjectStep(String projectStep) {
        this.projectStep = projectStep;
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

    @Override
    public String toString() {
        return "ProjectDisplayRule [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", timeOrder=" + timeOrder + ", projectLeader=" + projectLeader + ", managePerson=" + managePerson + ", projectStep=" + projectStep + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}