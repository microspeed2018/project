/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.model.admin;

import com.zjzmjr.core.base.page.Base;
import com.zjzmjr.core.enums.admin.AdminAccStatusEnum;
import com.zjzmjr.core.enums.admin.DepartmentEnum;

/**
 * ADMIN帐户创建
 * 
 * @author js
 * @version $Id: AdminCreateDTO.java, v 0.1 2015年10月28日 下午6:13:58 js Exp $
 */
public class AdminCreate extends Base {

    private static final long serialVersionUID = 4148048866498286978L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门
     */
    private DepartmentEnum department;
    
    /**
     * 职位id
     */
    private Integer jobId;

    /**
     * 所属公司编号
     */
    private Integer companyId;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮件
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐户状态
     */
    private AdminAccStatusEnum accStatus;

    /**
     * 安全IP
     */
    private String securityIp;

    /**
     * 管理区域 可多个 逗号隔开
     */
    private String multiArea;

    /**
     * 创建人
     */
    private Integer createUser;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AdminAccStatusEnum getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(AdminAccStatusEnum accStatus) {
        this.accStatus = accStatus;
    }

    public String getSecurityIp() {
        return securityIp;
    }

    public void setSecurityIp(String securityIp) {
        this.securityIp = securityIp;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
    
    public Integer getJobId() {
        return jobId;
    }

    
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "AdminCreate [username=" + username + ", name=" + name + ", department=" + department + ", jobId=" + jobId + ", companyId=" + companyId + ", mobile=" + mobile + ", email=" + email + ", password=" + password + ", accStatus=" + accStatus + ", securityIp=" + securityIp + ", multiArea=" + multiArea + ", createUser=" + createUser + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AdminCreate that = (AdminCreate) o;

        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (department != that.department)
            return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        if (accStatus != that.accStatus)
            return false;
        if (securityIp != null ? !securityIp.equals(that.securityIp) : that.securityIp != null)
            return false;
        return !(createUser != null ? !createUser.equals(that.createUser) : that.createUser != null);
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (accStatus != null ? accStatus.hashCode() : 0);
        result = 31 * result + (securityIp != null ? securityIp.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        return result;
    }
}
