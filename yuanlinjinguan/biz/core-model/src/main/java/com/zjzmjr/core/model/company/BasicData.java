package com.zjzmjr.core.model.company;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础数据表t_basic
 * 
 * @author lenovo
 * @version $Id: Basic.java, v 0.1 2016-10-25 上午9:21:44 lenovo Exp $
 */
public class BasicData implements Serializable {

    private static final long serialVersionUID = -2061843420182447817L;

    /**
     * 主键编号
     */
    private Integer id;

    /**
     * 公司编号
     */
    private Integer companyId;

    /**
     * 类别编号
     */
    private Integer categoryId;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 区分编号
     */
    private Integer attributeId;

    /**
     * 区分名称
     */
    private String attributeName;

    /**
     * 简写
     */
    private String abbreviate;

    /**
     * 贷款期限数量
     */
    private Integer loanLimitNum;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
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

    public Integer getLoanLimitNum() {
        return loanLimitNum;
    }

    public void setLoanLimitNum(Integer loanLimitNum) {
        this.loanLimitNum = loanLimitNum;
    }

    public String getAbbreviate() {
        return abbreviate;
    }

    public void setAbbreviate(String abbreviate) {
        this.abbreviate = abbreviate;
    }

    @Override
    public String toString() {
        return "BasicData [id=" + id + ", companyId=" + companyId + ", categoryId=" + categoryId + ", categoryName=" + categoryName + ", attributeId=" + attributeId + ", attributeName=" + attributeName + ", abbreviate=" + abbreviate + ", loanLimitNum=" + loanLimitNum + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}
