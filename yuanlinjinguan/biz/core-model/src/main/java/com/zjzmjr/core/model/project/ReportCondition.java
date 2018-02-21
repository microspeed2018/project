package com.zjzmjr.core.model.project;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 统计报表条件表
 * 
 * @author oms
 * @version $Id: ReportCondition.java, v 0.1 2017-9-18 下午8:48:12 oms Exp $
 */
public class ReportCondition implements Serializable {

    /**  */
    private static final long serialVersionUID = 862011175374183258L;

    private Integer id;

    private Integer companyId;

    private Integer userId;

    private String timeStart;

    private String timeEnd;

    private String projectLeader;

    /**
     * 负责人集合
     */
    private List<String> projectLeaderLst;

    private String managePerson;

    private List<String> managePersonLst;

    private String projectStep;

    private List<String> projectStepLst;

    private Integer status;

    private List<String> statusLst;

    private String dispField;

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

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart == null ? null : timeStart.trim();
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd == null ? null : timeEnd.trim();
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader == null ? null : projectLeader.trim();
    }

    public String getManagePerson() {
        return managePerson;
    }

    public void setManagePerson(String managePerson) {
        this.managePerson = managePerson == null ? null : managePerson.trim();
    }

    public String getProjectStep() {
        return projectStep;
    }

    public void setProjectStep(String projectStep) {
        this.projectStep = projectStep == null ? null : projectStep.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDispField() {
        return dispField;
    }

    public void setDispField(String dispField) {
        this.dispField = dispField == null ? null : dispField.trim();
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

    public List<String> getProjectLeaderLst() {
        return projectLeaderLst;
    }

    public void setProjectLeaderLst(List<String> projectLeaderLst) {
        this.projectLeaderLst = projectLeaderLst;
    }

    public List<String> getManagePersonLst() {
        return managePersonLst;
    }

    public void setManagePersonLst(List<String> managePersonLst) {
        this.managePersonLst = managePersonLst;
    }

    public List<String> getProjectStepLst() {
        return projectStepLst;
    }

    public void setProjectStepLst(List<String> projectStepLst) {
        this.projectStepLst = projectStepLst;
    }

    public List<String> getStatusLst() {
        return statusLst;
    }

    public void setStatusLst(List<String> statusLst) {
        this.statusLst = statusLst;
    }

    @Override
    public String toString() {
        return "ReportCondition [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + ", projectLeader=" + projectLeader + ", projectLeaderLst=" + projectLeaderLst + ", managePerson=" + managePerson + ", managePersonLst=" + managePersonLst + ", projectStep=" + projectStep + ", projectStepLst=" + projectStepLst + ", status=" + status + ", statusLst=" + statusLst + ", dispField=" + dispField + ", createTime=" + createTime
                + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}