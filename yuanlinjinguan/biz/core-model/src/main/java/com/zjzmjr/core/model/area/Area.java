package com.zjzmjr.core.model.area;

import java.io.Serializable;
import java.util.Date;

/**
 * 地区表t_area
 * 
 * @author lenovo
 * @version $Id: Area.java, v 0.1 2016-7-12 上午10:33:46 lenovo Exp $
 */
public class Area implements Serializable {

    /**  */
    private static final long serialVersionUID = 730978006288421637L;

    /**
     * 地区编号
     */
    private Integer id;

    /**
     * 省级代码
     */
    private String provCode;

    /**
     * 市级代码
     */
    private String cityCode;

    /**
     * 县级代码
     */
    private String distCode;

    /**
     * 地区名称
     */
    private String areaName;
    
    private String provName;
    
    private String cityName;
    
    private String distName;

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

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode == null ? null : provCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode == null ? null : distCode.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
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

    
    public String getProvName() {
        return provName;
    }

    
    public void setProvName(String provName) {
        this.provName = provName;
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

    @Override
    public String toString() {
        return "Area [id=" + id + ", provCode=" + provCode + ", cityCode=" + cityCode + ", distCode=" + distCode + ", areaName=" + areaName + ", provName=" + provName + ", cityName=" + cityName + ", distName=" + distName + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }


}