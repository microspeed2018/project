package com.zjzmjr.core.model.company;

import java.util.List;

import com.zjzmjr.core.model.area.Area;


/**
 * 关联公司、联系人关联model
 * 
 * @author lenovo
 * @version $Id: CompanyAssociatedInfo.java, v 0.1 2017-8-23 下午9:32:45 lenovo Exp $
 */
public class CompanyAssociatedInfo extends CompanyAssociated{

    /**  */
    private static final long serialVersionUID = -2010608181136108577L;
    
    private List<CompanyAssociatedContact> companyAssociatedContact;
    
    private Area area;
    
    private BasicData basicData;
    
    /**
     * 状态文本
     */
    private String statusText;
    
    /**
     * 录入时间
     */
    private String addTime;
   
    public String getStatusText() {
        return statusText;
    }



    
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }



    public List<CompanyAssociatedContact> getCompanyAssociatedContact() {
        return companyAssociatedContact;
    }


    
    public void setCompanyAssociatedContact(List<CompanyAssociatedContact> companyAssociatedContact) {
        this.companyAssociatedContact = companyAssociatedContact;
    }


    public Area getArea() {
        return area;
    }

    
    public void setArea(Area area) {
        this.area = area;
    }

    
    public BasicData getBasicData() {
        return basicData;
    }

    
    public void setBasicData(BasicData basicData) {
        this.basicData = basicData;
    }

    
    public String getAddTime() {
        return addTime;
    }
    
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "CompanyAssociatedInfo [companyAssociatedContact=" + companyAssociatedContact + ", area=" + area + ", basicData=" + basicData + ", statusText=" + statusText + ", addTime=" + addTime + "]";
    }

    
}
