package com.zjzmjr.core.model.admin;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限表
 * 
 * @author UI
 * @version $Id: RoleAuth.java, v 0.1 2017-6-6 下午3:40:07 UI Exp $
 */
public class RoleAuth implements Serializable {
    
    private static final long serialVersionUID = 7442085536214824820L;

    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer authId;

    private Date createTime;

    private Integer createUserId;
    
    private Date updateTime;

    private Integer updateUserId;
    
    private Integer version;

    public RoleAuth(Integer roleId, Integer authId) {
        super();
        this.roleId = roleId;
        this.authId = authId;
    }
    
    public RoleAuth(Integer roleId, Integer authId, Date createTime, Integer createUserId) {
        super();
        this.roleId = roleId;
        this.authId = authId;
        this.createTime = createTime;
        this.createUserId = createUserId;
    }

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
        return "RoleAuth [id=" + id + ", roleId=" + roleId + ", authId=" + authId + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }
    
}