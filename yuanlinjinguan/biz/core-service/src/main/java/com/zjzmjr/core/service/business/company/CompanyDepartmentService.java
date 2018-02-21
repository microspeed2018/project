package com.zjzmjr.core.service.business.company;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.company.CompanyDepartment;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.company.CompanyDepartmentStaff;


public interface CompanyDepartmentService {

    /**
     * 部门信息插入
     * 
     * @param companyDepartment
     * @return
     */
    ResultEntry<Integer> insertCompanyDepartment(CompanyDepartment companyDepartment);
    
    /**
     * 部门信息一览
     * 
     * @param query
     * @return
     */
    ResultPage<CompanyDepartmentStaff> getDepartmentStaff(CompanyDepartmentQuery query);
    
    /**
     * 更新部门信息
     * 
     * @param companyDepartment
     * @return
     */
    ResultEntry<Integer> updateCompanyDepartmentById(CompanyDepartment companyDepartment);
    
    /**
     * 条件查询部门信息
     *  
     * @param query
     * @return
     */
    ResultRecord<CompanyDepartment> getCompanyDepartmentByCondition(CompanyDepartmentQuery query);
    
    /**
     * 查询部门名称和部门人员数量
     * 
     * @return
     */
    ResultRecord<CompanyDepartment> getStaffDepartmentAndCount(CompanyDepartmentQuery query);
}
