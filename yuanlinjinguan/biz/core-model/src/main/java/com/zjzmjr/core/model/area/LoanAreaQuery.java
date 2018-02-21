package com.zjzmjr.core.model.area;

import com.zjzmjr.core.base.page.BasePageQuery;


public class LoanAreaQuery extends BasePageQuery { 
    /**  */
    private static final long serialVersionUID = -4394686440100985800L;


    private Integer id;
    
    private Integer areaId;
    
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

    
    public String getAreaName() {
        return areaName;
    }

    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }



    @Override
    public String toString() {
        return "LoanAreaQuery [id=" + id + ", areaId=" + areaId + ", areaName=" + areaName + "]";
    }


   
    
    
}
