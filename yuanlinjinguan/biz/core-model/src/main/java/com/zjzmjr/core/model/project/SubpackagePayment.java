package com.zjzmjr.core.model.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分包付款表
 * 
 * @author oms
 * @version $Id: SubpackagePayment.java, v 0.1 2017-9-28 上午10:22:39 oms Exp $
 */
public class SubpackagePayment implements Serializable {

    /**  */
    private static final long serialVersionUID = -1941691915351434930L;

    private Integer id;

    private Integer companyId;

    private Integer subpackageId;

    private Integer paymentMode;

    private BigDecimal paymentAmount;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;
    
    /**
     * 付款方式名称
     */
    private String paymentModeName;

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

    public Integer getSubpackageId() {
        return subpackageId;
    }

    public void setSubpackageId(Integer subpackageId) {
        this.subpackageId = subpackageId;
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
    
    public String getPaymentModeName() {
        return paymentModeName;
    }

    
    public void setPaymentModeName(String paymentModeName) {
        this.paymentModeName = paymentModeName;
    }

    @Override
    public String toString() {
        return "SubpackagePayment [id=" + id + ", companyId=" + companyId + ", subpackageId=" + subpackageId + ", paymentMode=" + paymentMode + ", paymentAmount=" + paymentAmount + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", paymentModeName=" + paymentModeName + "]";
    }

}