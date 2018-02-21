/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.model.menu;

import java.util.List;

import com.zjzmjr.core.base.page.Base;

/**
 * 菜单绑定
 * 
 * @author js
 * @version $Id: MenuUserBind.java, v 0.1 2015年10月30日 下午2:47:50 js Exp $
 */
public class MenuUserBind extends Base {

    /**  
    */
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private List<Integer> menuIds;

    /**
     * 使用的系统
     */
    private Integer project;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "MenuUserBind [userId=" + userId + ", menuIds=" + menuIds + ", project=" + project + "]";
    }

}
