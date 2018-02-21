/**
 * Yztz.cn Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.core.model.admin;

import java.util.List;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 权限查询
 *
 * @author elliott
 * @version $Id: AdminAuthQuery.java, v 0.1 2014-2-20 上午9:18:24 elliott Exp $
 */
public class AdminAuthQuery extends BasePageQuery {

    private static final long serialVersionUID = 7254188223228418067L;

    private String code;

    private String name;

    private List<Integer> type;

    public AdminAuthQuery() {
    }

    public AdminAuthQuery(List<Integer> type) {
        super();
        this.type = type;
    }

    public AdminAuthQuery(String code, String name, List<Integer> type) {
        super();
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getType() {
        return type;
    }

    public void setType(List<Integer> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AdminAuthQuery [code=" + code + ", name=" + name + ", type=" + type + "]";
    }

}
