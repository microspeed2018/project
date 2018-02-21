package com.zjzmjr.core.model.company;

import java.util.List;


public class CompanyDepartmentJob extends CompanyJob{

    /**  */
    private static final long serialVersionUID = -1707817907757676336L;
    /**
     * 公司部门
     */
    private List<CompanyDepartment> companyDepartment;

    
    public List<CompanyDepartment> getCompanyDepartment() {
        return companyDepartment;
    }

    
    public void setCompanyDepartment(List<CompanyDepartment> companyDepartment) {
        this.companyDepartment = companyDepartment;
    }


    @Override
    public String toString() {
        return "CompanyDepartmentJob [companyDepartment=" + companyDepartment + "]";
    }
    
    
}
