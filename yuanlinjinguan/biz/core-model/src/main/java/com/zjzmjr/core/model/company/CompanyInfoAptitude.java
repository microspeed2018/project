package com.zjzmjr.core.model.company;

import java.util.List;

import com.zjzmjr.core.model.area.Area;

/**
 * 
 * 
 * @author oms
 * @version $Id: CompanyInfoAptitude.java, v 0.1 2017-8-11 上午10:19:54 oms Exp $
 */
public class CompanyInfoAptitude extends CompanyInfo {

    /**  */
    private static final long serialVersionUID = 5361881525202551422L;
    
    private String addTime;
    
    private String statusText;
    
    private Area area;

    private List<CompanyAptitude> aptitudes;

    public List<CompanyAptitude> getAptitudes() {
        return aptitudes;
    }

    public void setAptitudes(List<CompanyAptitude> aptitudes) {
        this.aptitudes = aptitudes;
    }
       
    public Area getArea() {
        return area;
    }
   
    public void setArea(Area area) {
        this.area = area;
    }
    
    public String getAddTime() {
        return addTime;
    }

    
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    
    public String getStatusText() {
        return statusText;
    }

    
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return "CompanyInfoAptitude [addTime=" + addTime + ", statusText=" + statusText + ", area=" + area + ", aptitudes=" + aptitudes + "]";
    }

}
