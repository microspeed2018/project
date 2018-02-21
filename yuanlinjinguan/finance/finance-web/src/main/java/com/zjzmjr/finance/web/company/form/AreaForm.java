package com.zjzmjr.finance.web.company.form;

import java.util.Date;

import com.zjzmjr.core.common.biz.BaseForm;


public class AreaForm extends BaseForm{

    /**  */
    private static final long serialVersionUID = 208404458778027686L;

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

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer udateUserId;

    private Integer version;
    
    @Override
    public String resolveFiled(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    
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
        this.provCode = provCode;
    }

    
    public String getCityCode() {
        return cityCode;
    }

    
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    
    public String getDistCode() {
        return distCode;
    }

    
    public void setDistCode(String distCode) {
        this.distCode = distCode;
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

    
    public Integer getUdateUserId() {
        return udateUserId;
    }

    
    public void setUdateUserId(Integer udateUserId) {
        this.udateUserId = udateUserId;
    }

    
    public Integer getVersion() {
        return version;
    }

    
    public void setVersion(Integer version) {
        this.version = version;
    }


    @Override
    public String toString() {
        return "AreaForm [id=" + id + ", provCode=" + provCode + ", cityCode=" + cityCode + ", distCode=" + distCode + ", areaName=" + areaName + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", udateUserId=" + udateUserId + ", version=" + version + "]";
    }

    
}
