package com.zjzmjr.core.model.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目分包表
 * 
 * @author oms
 * @version $Id: ContractSubpackage.java, v 0.1 2017-9-3 下午4:45:57 oms Exp $
 */
public class ContractSubpackage implements Serializable {

    /**  */
    private static final long serialVersionUID = -3046257325095246874L;

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    private Integer subpackageLeader;

    private BigDecimal subpackageCapital;

    private String subpackageContent;

    private String paymentMemo;

    private String subpackageMemo;

    private Integer status;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    /**
     * 分包负责人姓名
     */
    private String subpackageLeaderName;
    
    /**
     * 项目进度step
     */
    private Integer step;
    
    /**
     * 临时数据id
     */
    private Integer temporaryId;
    
    private Integer subTemporaryId;
    
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

    public Integer getSubpackageLeader() {
        return subpackageLeader;
    }

    public void setSubpackageLeader(Integer subpackageLeader) {
        this.subpackageLeader = subpackageLeader;
    }

    public BigDecimal getSubpackageCapital() {
        return subpackageCapital;
    }

    public void setSubpackageCapital(BigDecimal subpackageCapital) {
        this.subpackageCapital = subpackageCapital;
    }

    public String getSubpackageContent() {
        return subpackageContent;
    }

    public void setSubpackageContent(String subpackageContent) {
        this.subpackageContent = subpackageContent == null ? null : subpackageContent.trim();
    }

    public String getPaymentMemo() {
        return paymentMemo;
    }

    public void setPaymentMemo(String paymentMemo) {
        this.paymentMemo = paymentMemo == null ? null : paymentMemo.trim();
    }

    public String getSubpackageMemo() {
        return subpackageMemo;
    }

    public void setSubpackageMemo(String subpackageMemo) {
        this.subpackageMemo = subpackageMemo == null ? null : subpackageMemo.trim();
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

    
    public String getSubpackageLeaderName() {
        return subpackageLeaderName;
    }

    
    public void setSubpackageLeaderName(String subpackageLeaderName) {
        this.subpackageLeaderName = subpackageLeaderName;
    }

    
    public Integer getStep() {
        return step;
    }

    
    public void setStep(Integer step) {
        this.step = step;
    }

    
    public Integer getTemporaryId() {
        return temporaryId;
    }

    
    public void setTemporaryId(Integer temporaryId) {
        this.temporaryId = temporaryId;
    }
    
    public Integer getSubTemporaryId() {
        return subTemporaryId;
    }

    
    public void setSubTemporaryId(Integer subTemporaryId) {
        this.subTemporaryId = subTemporaryId;
    }

    @Override
    public String toString() {
        return "ContractSubpackage [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", subpackageLeader=" + subpackageLeader + ", subpackageCapital=" + subpackageCapital + ", subpackageContent=" + subpackageContent + ", paymentMemo=" + paymentMemo + ", subpackageMemo=" + subpackageMemo + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version
                + ", subpackageLeaderName=" + subpackageLeaderName + ", step=" + step + ", temporaryId=" + temporaryId + ", subTemporaryId=" + subTemporaryId + "]";
    }

}