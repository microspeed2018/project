/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.console.form;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * @author elliott
 * @version $Id: MenuUpdateForm.java, v 0.1 2014-1-18 上午12:07:54 elliott Exp $
 */
public class MenuUpdateForm extends AbstractForm {

    private static final long serialVersionUID = 8073244938011672603L;

    private Integer id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 目标url
     */
    private String url;

    /**
     * 使用系统
     */
    private Integer project;

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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
        if ("id".equals(field)) {
            return "菜单ID";
        } else if ("name".equals(field)) {
            return "菜单名称";
        } else if ("order".equals(field)) {
            return "排序";
        } else if ("url".equals(field)) {
            return "菜单URL";
        } else if ("project".equals(field)) {
            return "project";
        }
        return null;
    }

    @Override
    public String toString() {
        return "MenuUpdateForm [id=" + id + ", name=" + name + ", order=" + order + ", url=" + url + ", project=" + project + "]";
    }

}
