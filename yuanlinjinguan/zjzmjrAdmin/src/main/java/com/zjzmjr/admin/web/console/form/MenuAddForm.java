/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.console.form;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 菜单添加
 * 
 * @author elliott
 * @version $Id: MenuAddForm.java, v 0.1 2014-1-16 上午9:59:14 elliott Exp $
 */
public class MenuAddForm extends AbstractForm {

    private static final long serialVersionUID = 2635573656376592130L;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 父菜单
     */
    private Integer parent;

    /**
     * url
     */
    private String url;

    /**
     * 使用系统
     */
    private Integer project;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    /**
     * @see com.zjzmjr.web.mvc.form.AbstractForm#resolveFiled(java.lang.String)
     */
    @Override
    public String resolveFiled(String field) {
        if ("name".equals(field)) {
            return "菜单名称";
        } else if ("order".equals(field)) {
            return "排序";
        } else if ("parent".equals(field)) {
            return "父菜单";
        } else if ("url".equals(field)) {
            return "目标URL";
        } else if ("project".equals(field)) {
            return "使用系统";
        }
        return null;
    }

    @Override
    public String toString() {
        return "MenuAddForm [name=" + name + ", order=" + order + ", parent=" + parent + ", url=" + url + ", project=" + project + "]";
    }

}
