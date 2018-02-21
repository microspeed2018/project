package com.zjzmjr.core.model.area;

import java.io.Serializable;
import java.util.Date;

public class LoanArea implements Serializable {

    /**  */
    private static final long serialVersionUID = -5450567440663280571L;

    private Integer id;

    private Integer areaId;

    private Integer departmentId;

    private String departmentName;

    private String areaName;
    
    private String cityName;
    
    private String distName;
    
    private String provCode;
    
    private String cityCode;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public String getCityName() {
        return cityName;
    }

    
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    
    public String getDistName() {
        return distName;
    }

    
    public void setDistName(String distName) {
        this.distName = distName;
    }
    
    public String getProvCode() {
        return provCode;
    }

    
    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    
    public String getCityCode() {
        return cityCode;
    }

    
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public String toString() {
        return "LoanArea [id=" + id + ", areaId=" + areaId + ", departmentId=" + departmentId + ", departmentName=" + departmentName + ", areaName=" + areaName + ", cityName=" + cityName + ", distName=" + distName + ", provCode=" + provCode + ", cityCode=" + cityCode + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

   

    

}
