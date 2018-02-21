package com.zjzmjr.admin.web.console.form;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 角色菜单form
 * 
 * @author oms
 * @version $Id: RoleMenuForm.java, v 0.1 2017-2-15 上午10:23:25 oms Exp $
 */
public class RoleMenuForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = -8174738719258938500L;

    private Integer id;

    private Integer roleType;

    private Integer menuId;

    private Integer version;

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "RoleMenuForm [id=" + id + ", roleType=" + roleType + ", menuId=" + menuId + ", version=" + version + "]";
    }

}
