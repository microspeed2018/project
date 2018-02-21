/**
 * Yztz.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.core.model.admin;

import com.zjzmjr.core.base.page.Base;
import com.zjzmjr.core.enums.admin.AdminAccStatusEnum;
import com.zjzmjr.core.enums.admin.DepartmentEnum;

/**
 * @author js
 * @version $Id: AdminUpdateDTO.java, v 0.1 2014-1-21 上午12:37:16 elliott Exp $
 */
public class AdminUpdate extends Base {

    private static final long serialVersionUID = -5909719811800044492L;

    private Integer id;

    private String name;

    private DepartmentEnum department;

    /**
     * 所属部门
     */
    private Integer departmentId;
    
    /**
     * 职位id
     */
    private Integer jobId;

    /**
     * 所属公司编号
     */
    private Integer companyId;

    private String mobile;

    private String email;

    private AdminAccStatusEnum status;

    private String securityIp;

    private String password;

    /**
     * 管理区域 可多个 逗号隔开
     */
    private String multiArea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentEnum getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEnum department) {
        this.department = department;
    }
    
    public Integer getJobId() {
        return jobId;
    }

    
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public AdminAccStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AdminAccStatusEnum status) {
        this.status = status;
    }

    public String getSecurityIp() {
        return securityIp;
    }

    public void setSecurityIp(String securityIp) {
        this.securityIp = securityIp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getMultiArea() {
        return multiArea;
    }

    public void setMultiArea(String multiArea) {
        this.multiArea = multiArea;
    }
    
    @Override
    public String toString() {
        return "AdminUpdate [id=" + id + ", name=" + name + ", department=" + department + ", departmentId=" + departmentId + ", jobId=" + jobId + ", companyId=" + companyId + ", mobile=" + mobile + ", email=" + email + ", status=" + status + ", securityIp=" + securityIp + ", password=" + password + ", multiArea=" + multiArea + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AdminUpdate that = (AdminUpdate) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (department != that.department)
            return false;
        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null)
            return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null)
            return false;
        if (status != that.status)
            return false;
        if (securityIp != null ? !securityIp.equals(that.securityIp) : that.securityIp != null)
            return false;
        return !(password != null ? !password.equals(that.password) : that.password != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (securityIp != null ? securityIp.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
