package com.zjzmjr.core.model.company;

import java.io.Serializable;
import java.util.Date;


/**
 * 公司部门职位model
 * 
 * @author lenovo
 * @version $Id: Job.java, v 0.1 2017-8-24 下午4:45:00 lenovo Exp $
 */
public class CompanyJob implements Serializable{

    /**  */
    private static final long serialVersionUID = -704942981313143931L;

    private Integer id;

    /**
     * 所属公司
     */
    private Integer companyId;

    /**
     * 所属部门
     */
    private String departmentId;

    /**
     * 职位名称
     */
    private String name;

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

    
    public String getDepartmentId() {
        return departmentId;
    }

    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
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
        return "Job [id=" + id + ", companyId=" + companyId + ", departmentId=" + departmentId + ", name=" + name + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }
    
    

}
