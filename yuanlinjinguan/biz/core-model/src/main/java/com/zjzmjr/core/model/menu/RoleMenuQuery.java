package com.zjzmjr.core.model.menu;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 角色菜单查询条件
 * 
 * @author oms
 * @version $Id: RoleMenuQuery.java, v 0.1 2017-2-14 上午10:30:53 oms Exp $
 */
public class RoleMenuQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = 7919589523950812022L;

    private Integer id;

    private Integer roleType;

    private Integer menuId;

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

    @Override
    public String toString() {
        return "RoleMenuQuery [id=" + id + ", roleType=" + roleType + ", menuId=" + menuId + "]";
    }

}
