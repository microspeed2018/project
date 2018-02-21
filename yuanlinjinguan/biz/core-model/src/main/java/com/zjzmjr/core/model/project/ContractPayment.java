package com.zjzmjr.core.model.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同付款表
 * 
 * @author oms
 * @version $Id: ContractPayment.java, v 0.1 2017-8-23 下午10:00:36 oms Exp $
 */
public class ContractPayment implements Serializable {

    /**  */
    private static final long serialVersionUID = -5138868959794646906L;

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    private Integer paymentMode;

    private BigDecimal paymentAmount;
    
    private Integer status;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;
    
    /**
     * 合同付款方式名称
     */
    private String paymentPaymentModeName;

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

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
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
    
    public String getPaymentPaymentModeName() {
        return paymentPaymentModeName;
    }

    
    public void setPaymentPaymentModeName(String paymentPaymentModeName) {
        this.paymentPaymentModeName = paymentPaymentModeName;
    }
    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ContractPayment [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", paymentMode=" + paymentMode + ", paymentAmount=" + paymentAmount + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", paymentPaymentModeName=" + paymentPaymentModeName + "]";
    }


}