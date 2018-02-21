package com.zjzmjr.core.model.menu;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色菜单
 * 
 * @author oms
 * @version $Id: RoleMenu.java, v 0.1 2017-2-14 上午9:40:41 oms Exp $
 */
public class RoleMenu implements Serializable {

    /**  */
    private static final long serialVersionUID = 7442085536214824820L;

    private Integer id;

    /** 公司编号 */
    private Integer companyId;

    private Integer roleType;

    private Integer menuId;

    private Date createTime;

    private Integer version;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    public RoleMenu(Integer companyId, Integer roleType, Integer menuId, Date createTime, Integer createUserId) {
        super();
        this.companyId = companyId;
        this.roleType = roleType;
        this.menuId = menuId;
        this.createTime = createTime;
        this.createUserId = createUserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Override
    public String toString() {
        return "RoleMenu [id=" + id + ", roleType=" + roleType + ", menuId=" + menuId + ", createTime=" + createTime + ", version=" + version + ", createUserId=" + createUserId + "]";
    }

}