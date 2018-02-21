/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.console.form;

import java.io.Serializable;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * @author elliott
 * @version $Id: AuthQueryForm.java, v 0.1 2014-1-17 下午4:54:53 elliott Exp $
 */
public class AuthQueryForm extends AbstractForm implements Serializable {

    private static final long serialVersionUID = -7381567630453555058L;

    private String authCode;

    private String authName;

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
        return "AuthQueryForm [authCode=" + authCode + ", authName=" + authName + ", type=" + type + "]";
    }

}
