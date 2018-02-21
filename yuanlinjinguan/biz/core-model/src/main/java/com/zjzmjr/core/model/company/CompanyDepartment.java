package com.zjzmjr.core.model.company;

import java.io.Serializable;
import java.util.Date;


/**
 * 公司部门表model
 * 
 * @author lenovo
 * @version $Id: Department.java, v 0.1 2017-8-24 下午4:43:06 lenovo Exp $
 */
public class CompanyDepartment implements Serializable{

    /**  */
    private static final long serialVersionUID = 474396787678316407L;

    private Integer id;

    /**
     * 所属公司
     */
    private Integer companyId;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门负责人
     */
    private Integer departmentLeader;

    /**
     * 状态
     */
    private Integer status;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;


    /**
     * 部门员工数量
     */
    private Integer staffCount;
    
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

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public Integer getDepartmentLeader() {
        return departmentLeader;
    }

    
    public void setDepartmentLeader(Integer departmentLeader) {
        this.departmentLeader = departmentLeader;
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

    
    public Integer getStaffCount() {
        return staffCount;
    }


    
    public void setStaffCount(Integer staffCount) {
        this.staffCount = staffCount;
    }


    @Override
    public String toString() {
        return "CompanyDepartment [id=" + id + ", companyId=" + companyId + ", name=" + name + ", departmentLeader=" + departmentLeader + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", staffCount=" + staffCount + "]";
    }
    
    
}
