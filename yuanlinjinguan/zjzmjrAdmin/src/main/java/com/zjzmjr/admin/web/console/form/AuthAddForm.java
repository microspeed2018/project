/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.console.form;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * @author elliott
 * @version $Id: AuthAddForm.java, v 0.1 2014-1-17 下午2:21:29 elliott Exp $
 */
public class AuthAddForm extends AbstractForm {

    private static final long serialVersionUID = -8160890758661688190L;

    /**
     * 权限代码
     */
    private String authCode;

    /**
     * 权限名称
     */
    private String authName;

    /**
     * 权限类型
     */
    private Integer type;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @see com.zjzmjr.web.mvc.form.AbstractForm#resolveFiled(java.lang.String)
     */
    @Override
    public String resolveFiled(String field) {
        if ("authCode".equals(field)) {
            return "权限代码";
        } else if ("authName".equals(field)) {
            return "权限名称";
        } else if ("type".equals(field)) {
            return "权限类型";
        }
        return null;
    }

    @Override
    public String toString() {
        return "AuthAddForm [authCode=" + authCode + ", authName=" + authName + ", type=" + type + "]";
    }

}
