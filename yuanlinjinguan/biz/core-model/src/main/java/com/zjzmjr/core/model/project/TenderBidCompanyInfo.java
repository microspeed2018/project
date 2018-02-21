package com.zjzmjr.core.model.project;

import com.zjzmjr.core.model.company.CompanyAssociated;

/**
 * 开标及投标公司信息
 * 
 * @author oms
 * @version $Id: TenderBidCompanyInfo.java, v 0.1 2017-8-23 下午4:49:52 oms Exp $
 */
public class TenderBidCompanyInfo extends BiddingInfo {

    /**  */
    private static final long serialVersionUID = 9079159253115004954L;

    /**
     * 投标关联公司信息
     */
    private CompanyAssociated company;

    /**
     * 投标项目信息
     */
    private GardenProject project;

    public CompanyAssociated getCompany() {
        return company;
    }

    public void setCompany(CompanyAssociated company) {
        this.company = company;
    }

    public GardenProject getProject() {
        return project;
    }

    public void setProject(GardenProject project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "TenderBidCompanyInfo [company=" + company + ", project=" + project + ", toString()=" + super.toString() + "]";
    }

}
