package com.zjzmjr.core.service.mapper.dao.company;

import java.util.List;

import com.zjzmjr.core.model.company.CompanyDepartmentJob;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.company.CompanyJob;


public interface CompanyJobMapper {

    /**
     * 职位数量
     * 
     * @param query
     * @return
     */
    int getDepartmentJobInfoCount(CompanyDepartmentQuery query);
    
    /**
     * 职位一览
     * 
     * @param query
     * @return
     */
    List<CompanyDepartmentJob> getDepartmentJobInfo(CompanyDepartmentQuery query);
    
    /**
     * 包括内部和外部职位的数量
     * 
     * @param query
     * @return
     */
    int getInnerOuterDepartmentCount(CompanyDepartmentQuery query);
    
    /**
     * 包括内部和外部所有职位的一览
     * 
     * @param query
     * @return
     */
    List<CompanyDepartmentJob> getInnerOuterDepartmentInfo(CompanyDepartmentQuery query);
    
    /**
     * 新增职位
     * 
     * @param comapnyJob
     * @return
     */
    int insertCompanyJob(CompanyJob comapnyJob);
    
    /**
     * 修改职位
     * 
     * @param comapnyJob
     * @return
     */
    int updateCompanyJobById(CompanyJob comapnyJob);
    
    /**
     * 条件查询职位信息
     * 
     * @param query
     * @return
     */
    List<CompanyJob> getCompanyJobByCondition(CompanyDepartmentQuery query);
}
