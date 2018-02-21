package com.zjzmjr.core.model.admin;

import java.util.List;

import com.zjzmjr.core.base.page.Base;

/**
 * 角色权限绑定
 * 
 * @author UI
 * @version $Id: RoleAuthBind.java, v 0.1 2017-6-7 下午2:27:35 UI Exp $
 */
public class RoleAuthBind extends Base {
    
    private static final long serialVersionUID = -3104823873524924569L;

    private Integer roleId;

    private List<Integer> authIds;

    private Integer createUserId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getAuthIds() {
        return authIds;
    }

    public void setAuthIds(List<Integer> authIds) {
        this.authIds = authIds;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public String toString() {
        return "RoleAuthBind [roleId=" + roleId + ", authIds=" + authIds + ", createUserId=" + createUserId + "]";
    }

}
