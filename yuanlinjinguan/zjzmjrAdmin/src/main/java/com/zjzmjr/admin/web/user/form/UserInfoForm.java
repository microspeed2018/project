package com.zjzmjr.admin.web.user.form;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 
 * 
 * @author oms
 * @version $Id: UserInfoForm.java, v 0.1 2016-6-13 下午2:03:36 oms Exp $
 */
public class UserInfoForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = -4289295115714491772L;

    private Integer userId;

    private String userCode;

    private String name;

    private String identityNo;

    private Integer status;
    
    private Integer authorityId;
    
    private String departmentId;
    
    private Integer id;

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getAuthorityId() {
        return authorityId;
    }

    
    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    
    public String getDepartmentId() {
        return departmentId;
    }

    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserInfoForm [userId=" + userId + ", userCode=" + userCode + ", name=" + name + ", identityNo=" + identityNo + ", status=" + status + ", authorityId=" + authorityId + ", departmentId=" + departmentId + ", id=" + id + "]";
    }

    

}
