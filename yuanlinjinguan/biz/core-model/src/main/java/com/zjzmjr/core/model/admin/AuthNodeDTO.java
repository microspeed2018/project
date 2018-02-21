package com.zjzmjr.core.model.admin;

import java.io.Serializable;

/**
 * 用户权限节点
 * 
 * @author js
 * @version $Id: AuthNodeDTO.java, v 0.1 2015年11月19日 上午10:46:05 js Exp $
 */
public class AuthNodeDTO implements Serializable {

    /**  */
    private static final long serialVersionUID = -2086252331107892340L;

    private Integer id;

    private String authCode;

    private String authName;

    private boolean checked;

    public AuthNodeDTO() {
    }

    public AuthNodeDTO(Integer id, String authCode, String authName, boolean checked) {
        super();
        this.id = id;
        this.authCode = authCode;
        this.authName = authName;
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "AuthNodeDTO [id=" + id + ", authCode=" + authCode + ", authName=" + authName + ", checked=" + checked
                + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AuthNodeDTO that = (AuthNodeDTO) o;

        if (checked != that.checked)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (authCode != null ? !authCode.equals(that.authCode) : that.authCode != null)
            return false;
        return !(authName != null ? !authName.equals(that.authName) : that.authName != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (authCode != null ? authCode.hashCode() : 0);
        result = 31 * result + (authName != null ? authName.hashCode() : 0);
        result = 31 * result + (checked ? 1 : 0);
        return result;
    }
}
