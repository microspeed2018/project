package com.zjzmjr.core.model.company;

import java.io.Serializable;
import java.util.Date;

/**
 * 关联公司联系人model
 * 
 * @author lenovo
 * @version $Id: CompanyAssociatedContact.java, v 0.1 2017-8-23 下午8:56:20 lenovo Exp $
 */
public class CompanyAssociatedContact implements Serializable{

    /**  */
    private static final long serialVersionUID = 3771833027903360833L;

    private Integer id;

    /**
     * 所属公司
     */
    private Integer companyId;

    /**
     * 关联公司编号
     */
    private Integer associateCompany;

    /**
     * 姓名
     */
    private String name;

    /**
     * 职务
     */
    private String job;

    /**
     * 联系方式
     */
    private String mobile;

    /**
     * email
     */
    private String email;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 状态
     */
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

    
    public Integer getAssociateCompany() {
        return associateCompany;
    }

    
    public void setAssociateCompany(Integer associateCompany) {
        this.associateCompany = associateCompany;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getJob() {
        return job;
    }

    
    public void setJob(String job) {
        this.job = job;
    }

    
    public String getMobile() {
        return mobile;
    }

    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
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
        return "CompanyAssociatedContact [id=" + id + ", companyId=" + companyId + ", associateCompany=" + associateCompany + ", name=" + name + ", job=" + job + ", mobile=" + mobile + ", email=" + email + ", address=" + address + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }   

}
