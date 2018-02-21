/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.console.form;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 用户信息修改
 * 
 * @author elliott
 * @version $Id: AdminUpdateForm.java, v 0.1 2014-1-21 上午12:28:41 elliott Exp $
 */
public class AdminUpdateForm extends AbstractForm {

    private static final long serialVersionUID = 1908700856344490567L;

    private Integer id;

    private String name;

    private Integer department;

    /**
     * 所属公司编号
     */
    private Integer companyId;

    private String mobile;

    private String email;

    private Integer status;

    private String securityIp;

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

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSecurityIp() {
        return securityIp;
    }

    public void setSecurityIp(String securityIp) {
        this.securityIp = securityIp;
    }

    /**
     * @see com.zjzmjr.web.mvc.form.AbstractForm#resolveFiled(java.lang.String)
     */
    @Override
    public String resolveFiled(String field) {
        if ("id".equals(field)) {
            return "用户ID";
        } else if ("name".equals(field)) {
            return "用户姓名";
        } else if ("department".equals(field)) {
            return "部门";
        } else if ("mobile".equals(field)) {
            return "手机";
        } else if ("email".equals(field)) {
            return "邮箱";
        } else if ("status".equals(field)) {
            return "用户状态";
        } else if ("securityIp".equals(field)) {
            return "安全IP";
        }
        return null;
    }

    @Override
    public String toString() {
        return "AdminUpdateForm [id=" + id + ", name=" + name + ", department=" + department + ", companyId=" + companyId + ", mobile=" + mobile + ", email=" + email + ", status=" + status + ", securityIp=" + securityIp + "]";
    }

}
