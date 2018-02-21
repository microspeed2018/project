package com.zjzmjr.core.model.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目合同表
 * 
 * @author oms
 * @version $Id: ProjectContract.java, v 0.1 2017-8-23 下午9:58:38 oms Exp $
 */
public class ProjectContract implements Serializable {

    /**  */
    private static final long serialVersionUID = 579858419578205289L;

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    private String contractNo;

    private BigDecimal contractCapital;

    private String signDate;

    private String contractMemo;

    private BigDecimal ratio;

    private String ratioMemo;

    private Integer status;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;
    
    /**
     * 临时合同id
     */
    private Integer temporaryContractId;
    
    /**
     * 当前操作步骤
     */
    private Integer step;
    
    /**
     * 合同状态传值参数
     */
    private Integer contractStatus;

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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public BigDecimal getContractCapital() {
        return contractCapital;
    }

    public void setContractCapital(BigDecimal contractCapital) {
        this.contractCapital = contractCapital;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate == null ? null : signDate.trim();
    }

    public String getContractMemo() {
        return contractMemo;
    }

    public void setContractMemo(String contractMemo) {
        this.contractMemo = contractMemo == null ? null : contractMemo.trim();
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public String getRatioMemo() {
        return ratioMemo;
    }

    public void setRatioMemo(String ratioMemo) {
        this.ratioMemo = ratioMemo == null ? null : ratioMemo.trim();
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
    
    public Integer getTemporaryContractId() {
        return temporaryContractId;
    }

    
    public void setTemporaryContractId(Integer temporaryContractId) {
        this.temporaryContractId = temporaryContractId;
    }
    
    public Integer getStep() {
        return step;
    }

    
    public void setStep(Integer step) {
        this.step = step;
    }

    
    public Integer getContractStatus() {
        return contractStatus;
    }

    
    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    @Override
    public String toString() {
        return "ProjectContract [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", contractNo=" + contractNo + ", contractCapital=" + contractCapital + ", signDate=" + signDate + ", contractMemo=" + contractMemo + ", ratio=" + ratio + ", ratioMemo=" + ratioMemo + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", temporaryContractId="
                + temporaryContractId + ", step=" + step + ", contractStatus=" + contractStatus + "]";
    }

}