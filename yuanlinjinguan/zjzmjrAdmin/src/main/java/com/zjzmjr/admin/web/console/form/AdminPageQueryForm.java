/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.console.form;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * admin分页查询
 *
 * @author elliott
 * @version $Id: AdminPageQueryForm.java, v 0.1 2014-1-17 上午9:49:10 elliott Exp $
 */
public class AdminPageQueryForm extends AbstractForm {

    private static final long serialVersionUID = 7507188070457733591L;

    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 部门
     */
    private Integer department;

    /**
     * 手机
     */
    private String mobile;

    /**
     *
     */
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
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

    @Override
    public String resolveFiled(String field) {
        if ("id".equals(field)) {
            return "用户ID";
        } else if ("username".equals(field)) {
            return "用户名";
        } else if ("name".equals(field)) {
            return "姓名";
        } else if ("status".equals(field)) {
            return "用户状态";
        } else if ("department".equals(field)) {
            return "部门";
        } else if ("mobile".equals(field)) {
            return "手机";
        }
        return null;
    }

    @Override
    public String toString() {
        return "AdminPageQueryForm [id=" + id + ", username=" + username + ", name=" + name + ", status=" + status
                + "]";
    }

}
