package com.zjzmjr.admin.web.area.form;

import com.zjzmjr.core.common.biz.BaseForm;


public class InsuranceAreaForm extends BaseForm{

    /**  */
    private static final long serialVersionUID = -626986506271978866L;

    private Integer id;
    
    private Integer areaId;
    
    private Integer distCode;
    
    private String areaName;

    
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

    
    public Integer getDistCode() {
        return distCode;
    }

    
    public void setDistCode(Integer distCode) {
        this.distCode = distCode;
    }

    
    public String getAreaName() {
        return areaName;
    }

    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }


    @Override
    public String toString() {
        return "InsuranceAreaForm [id=" + id + ", areaId=" + areaId + ", distCode=" + distCode + ", areaName=" + areaName + "]";
    }


    @Override
    public String resolveFiled(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
