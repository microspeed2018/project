package com.zjzmjr.admin.web.user.form;

import javax.validation.constraints.NotNull;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 修改帐户用户信息User
 * 
 * @author js
 * @version $Id: UserAccountUpdateForm.java, v 0.1 2015年10月30日 上午10:36:12 js Exp
 *          $
 */
public class UserUpdateForm extends AbstractForm {

    /**  */
    private static final long serialVersionUID = -4613691194698128499L;

    @NotNull(message = "{userId}不能为空")
    private Long userId;

    private Integer type;

    @NotNull(message = "{status}不能为空")
    private Integer status;

    // 协保员部门id
    private String departmentId;

    private String userCode;

    // 业务员、内勤部门id
    private String departmentid;

    private Integer version;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @see com.zjzmjr.web.mvc.form.AbstractForm#resolveFiled(java.lang.String)
     */
    @Override
    public String resolveFiled(String field) {
        return null;
    }

    @Override
    public String toString() {
        return "UserUpdateForm [userId=" + userId + ", type=" + type + ", status=" + status + ", departmentId=" + departmentId + ", userCode=" + userCode + ", departmentid=" + departmentid + ", version=" + version + "]";
    }

}
