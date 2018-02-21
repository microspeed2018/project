/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.finance.web.user.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 密码修改
 * 
 * @author elliott
 * @version $Id: PasswordChangeForm.java, v 0.1 2014-2-19 下午6:43:38 elliott Exp
 *          $
 */
public class PasswordChangeForm extends AbstractForm {

    private static final long serialVersionUID = 8998203572241166675L;

    @NotNull(message = "{oldPassword}不能为空")
    private String oldPassword;

    @NotNull(message = "{newPassword}不能为空")
    private String newPassword;

    @NotNull(message = "{renewPassword}不能为空")
    @NotEmpty(message = "{renewPassword}不能为空")
    private String renewPassword;

    /**
     * @see com.zjzmjr.web.mvc.form.AbstractForm#resolveFiled(java.lang.String)
     */
    @Override
    public String resolveFiled(String field) {
        if ("oldPassword".equals(field)) {
            return "原密码";
        } else if ("newPassword".equals(field)) {
            return "新密码";
        } else if ("renewPassword".equals(field)) {
            return "确认密码";
        }
        return null;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRenewPassword() {
        return renewPassword;
    }

    public void setRenewPassword(String renewPassword) {
        this.renewPassword = renewPassword;
    }

    @Override
    public String toString() {
        return "PasswordChangeForm [oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", renewPassword=" + renewPassword + "]";
    }

}
