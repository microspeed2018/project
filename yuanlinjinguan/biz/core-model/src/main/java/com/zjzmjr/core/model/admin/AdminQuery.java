/**
 * Yztz.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.core.model.admin;

import java.util.Date;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * @author elliott
 * @version $Id: AdminQuery.java, v 0.1 2014-1-16 下午7:24:55 elliott Exp $
 */
public class AdminQuery extends BasePageQuery {

    private static final long serialVersionUID = -3786530288057762360L;

    private Integer id;

    private String username;

    private String name;

    private Integer accStatus;

    /**
     * 职位id
     */
    private Integer jobId;

    private Integer department;
    
    /**
     * 所属部门
     */
    private Integer departmentId;

    private String mobile;

    private String email;

    private Date registerStart;

    private Date registerEnd;

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

    public Integer getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(Integer accStatus) {
        this.accStatus = accStatus;
    }
    
    public Integer getJobId() {
        return jobId;
    }

    
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Date getRegisterStart() {
        return registerStart;
    }

    public void setRegisterStart(Date registerStart) {
        this.registerStart = registerStart;
    }

    public Date getRegisterEnd() {
        return registerEnd;
    }

    public void setRegisterEnd(Date registerEnd) {
        this.registerEnd = registerEnd;
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
    
    public Integer getDepartmentId() {
        return departmentId;
    }
    
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "AdminQuery [id=" + id + ", username=" + username + ", name=" + name + ", accStatus=" + accStatus + ", jobId=" + jobId + ", department=" + department + ", departmentId=" + departmentId + ", mobile=" + mobile + ", email=" + email + ", registerStart=" + registerStart + ", registerEnd=" + registerEnd + "]";
    }
    
}
