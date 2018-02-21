package com.zjzmjr.core.model.admin;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 角色权限查询条件
 * 
 * @author UI
 * @version $Id: RoleAuthQuery.java, v 0.1 2017-6-7 下午2:28:28 UI Exp $
 */
public class RoleAuthQuery extends BasePageQuery {
    
    private static final long serialVersionUID = 7919589523950812022L;

    private Integer id;

    private Integer roleId;

    private Integer authId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    @Override
    public String toString() {
        return "RoleAuthQuery [id=" + id + ", roleId=" + roleId + ", authId=" + authId + "]";
    }

}
