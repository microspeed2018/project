package com.zjzmjr.core.model.menu;

import java.util.List;

import com.zjzmjr.core.base.page.Base;

/**
 * 
 * 
 * @author oms
 * @version $Id: RoleMenuBind.java, v 0.1 2017-2-16 下午2:34:43 oms Exp $
 */
public class RoleMenuBind extends Base {

    /**  */
    private static final long serialVersionUID = -3104823873524924569L;

    /** 公司编号 */
    private Integer companyId;

    private Integer roleType;

    private List<Integer> menuIds;

    /** 部门id */
    private Integer departmentId;

    private Integer createUserId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public String toString() {
        return "RoleMenuBind [companyId=" + companyId + ", roleType=" + roleType + ", menuIds=" + menuIds + ", createUserId=" + createUserId + "]";
    }

}
