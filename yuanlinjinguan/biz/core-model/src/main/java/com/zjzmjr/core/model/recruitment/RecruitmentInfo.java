package com.zjzmjr.core.model.recruitment;

import com.zjzmjr.core.model.company.CompanyDepartment;


public class RecruitmentInfo extends Recruitment{

    /**  */
    private static final long serialVersionUID = -2924383206443177788L;
   
    /**
     * 应聘人数
     */
    private Integer applicants;
    
    private CompanyDepartment companyDepartment;
    
    private String isValidText;
    
    private String isOpenText;

    
    public Integer getApplicants() {
        return applicants;
    }


    
    public void setApplicants(Integer applicants) {
        this.applicants = applicants;
    }


    public CompanyDepartment getCompanyDepartment() {
        return companyDepartment;
    }

    
    public void setCompanyDepartment(CompanyDepartment companyDepartment) {
        this.companyDepartment = companyDepartment;
    }

    
    public String getIsValidText() {
        return isValidText;
    }



    
    public void setIsValidText(String isValidText) {
        this.isValidText = isValidText;
    }



    
    public String getIsOpenText() {
        return isOpenText;
    }



    
    public void setIsOpenText(String isOpenText) {
        this.isOpenText = isOpenText;
    }



    @Override
    public String toString() {
        return "RecruitmentInfo [applicants=" + applicants + ", companyDepartment=" + companyDepartment + ", isValidText=" + isValidText + ", isOpenText=" + isOpenText + "]";
    }
    
    
}
