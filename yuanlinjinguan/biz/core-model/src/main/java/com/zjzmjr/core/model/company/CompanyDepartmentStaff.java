package com.zjzmjr.core.model.company;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.user.StaffInfo;


public class CompanyDepartmentStaff extends CompanyDepartment{

    /**  */
    private static final long serialVersionUID = 3599533481650536054L;
    
    
    private StaffInfo staffInfo;
    
    private Admin admin;

    private CompanyJob companyJob;
    
    public StaffInfo getStaffInfo() {
        return staffInfo;
    }

    
    public void setStaffInfo(StaffInfo staffInfo) {
        this.staffInfo = staffInfo;
    }

    
    public Admin getAdmin() {
        return admin;
    }

    
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }


    
    public CompanyJob getCompanyJob() {
        return companyJob;
    }


    
    public void setCompanyJob(CompanyJob companyJob) {
        this.companyJob = companyJob;
    }


    @Override
    public String toString() {
        return "CompanyDepartmentStaff [staffInfo=" + staffInfo + ", admin=" + admin + ", companyJob=" + companyJob + "]";
    }

    
    
}
