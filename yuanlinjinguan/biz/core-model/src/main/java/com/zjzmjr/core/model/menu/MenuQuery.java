/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.model.menu;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 
 * @author js
 * @version $Id: MenuQuery.java, v 0.1 2015年10月29日 下午3:28:57 js Exp $
 */
public class MenuQuery extends BasePageQuery {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer parentId;

    /**
     * 适用的工程
     */
    private Integer project;

    private Boolean containsUnchecked;

    private Integer roleType;

    private Integer department;

    public Boolean getContainsUnchecked() {
        return containsUnchecked;
    }

    public void setContainsUnchecked(Boolean containsUnchecked) {
        this.containsUnchecked = containsUnchecked;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "MenuQuery [userId=" + userId + ", parentId=" + parentId + ", project=" + project + ", containsUnchecked=" + containsUnchecked + ", roleType=" + roleType + ", department=" + department + "]";
    }

}
