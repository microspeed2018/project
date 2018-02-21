package com.zjzmjr.admin.web.user.form;

import java.math.BigDecimal;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: LoginRecordQuery.java, v 0.1 2016-6-30 下午3:15:41 Administrator Exp $
 */
public class LoginRecordForm extends AbstractForm {

    private static final long serialVersionUID = 7545836721583661471L;

    private Integer id;

    private Integer userId;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Integer accessTimes;

    private String lastAccessDate;

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

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Integer getAccessTimes() {
        return accessTimes;
    }

    public void setAccessTimes(Integer accessTimes) {
        this.accessTimes = accessTimes;
    }

    public String getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(String lastAccessDate) {
        this.lastAccessDate = lastAccessDate == null ? null : lastAccessDate.trim();
    }

    @Override
    public String toString() {
        return "LoginRecordQuery [id=" + id + ", userId=" + userId + ", longitude=" + longitude + ", latitude=" + latitude + ", accessTimes=" + accessTimes + ", lastAccessDate=" + lastAccessDate + "]";
    }

    @Override
    public String resolveFiled(String field) {
        return null;
    }

}