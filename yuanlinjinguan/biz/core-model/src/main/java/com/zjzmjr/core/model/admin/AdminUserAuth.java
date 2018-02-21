package com.zjzmjr.core.model.admin;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class AdminUserAuth implements Serializable {

    /**  */
    private static final long serialVersionUID = -6605662607856392622L;

    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 权限ID
     */
    private Integer authId;

    /**
     * 创建时间
     */
    private Date time;

    private byte[] version;

    public AdminUserAuth() {
    }

    public AdminUserAuth(Integer userId, Integer authId) {
        super();
        this.userId = userId;
        this.authId = authId;
        this.time = new Date();
    }

    public AdminUserAuth(Integer userId, Integer authId, Date time) {
        super();
        this.userId = userId;
        this.authId = authId;
        this.time = time;
    }

    public AdminUserAuth(Integer id) {
        this.id = id;
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

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
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
        result = prime * result + ((authId == null) ? 0 : authId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        AdminUserAuth other = (AdminUserAuth) obj;
        if (authId == null) {
            if (other.authId != null)
                return false;
        } else if (!authId.equals(other.authId))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
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
        return "AdminUserAuth [id=" + id + ", userId=" + userId + ", authId=" + authId + ", time=" + time + ", version=" + Arrays.toString(version) + "]";
    }

}
