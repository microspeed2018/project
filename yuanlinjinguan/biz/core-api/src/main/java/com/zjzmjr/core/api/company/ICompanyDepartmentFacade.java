package com.zjzmjr.core.api.company;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.company.CompanyDepartment;
import com.zjzmjr.core.model.company.CompanyDepartmentJob;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.company.CompanyDepartmentStaff;
import com.zjzmjr.core.model.company.CompanyJob;


public interface ICompanyDepartmentFacade {

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
     * 职位一览
     * 
     * @param query
     * @return
     */
    ResultPage<CompanyDepartmentJob> getDepartmentJobInfo(CompanyDepartmentQuery query);

    /**
     * 包括内部和外部所有职位的一览
     * 
     * @param query
     * @return
     */
    ResultPage<CompanyDepartmentJob> getInnerOuterDepartmentInfo(CompanyDepartmentQuery query);
    
    /**
     * 新增职位
     * 
     * @param comapnyJob
     * @return
     */
    ResultEntry<Integer> insertCompanyJob(CompanyJob comapnyJob);
    
    /**
     * 
     * 修改职位
     * 
     * @param comapnyJob
     * @return
     */
    ResultEntry<Integer> updateCompanyJobById(CompanyJob comapnyJob);
    
    /**
     * 条件查询职位信息
     * 
     * @param query
     * @return
     */
    ResultRecord<CompanyJob> getCompanyJobByCondition(CompanyDepartmentQuery query);
    
    /**
     * 查询部门名称和部门人员数量
     * 
     * @return
     */
    ResultRecord<CompanyDepartment> getStaffDepartmentAndCount(CompanyDepartmentQuery query);
}
