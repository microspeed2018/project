/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.model.menu;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 
 * @author js
 * @version $Id: AdminUserMenu.java, v 0.1 2015年10月30日 下午3:56:29 js Exp $
 */
public class AdminUserMenu implements Serializable {

    private static final long serialVersionUID = 7304070872346737397L;

    private Integer id;

    private Integer userId;

    private Integer menuId;

    private Date time;

    private byte[] version;

    public AdminUserMenu() {
    }

    public AdminUserMenu(Integer userId, Integer menuId) {
        super();
        this.userId = userId;
        this.menuId = menuId;
        this.time = new Date();
    }

    public AdminUserMenu(Integer userId, Integer menuId, Date time) {
        super();
        this.userId = userId;
        this.menuId = menuId;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public byte[] getVersion() {
        return version;
    }

    public void setVersion(byte[] version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AdminUserMenu other = (AdminUserMenu) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (menuId == null) {
            if (other.menuId != null)
                return false;
        } else if (!menuId.equals(other.menuId))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AdminUserMenu [id=" + id + ", userId=" + userId + ", menuId=" + menuId + ", time=" + time + ", version=" + Arrays.toString(version) + "]";
    }
}
