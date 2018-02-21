package com.zjzmjr.core.service.mapper.dao.company;

import java.util.List;

import com.zjzmjr.core.model.company.CompanyDepartment;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.company.CompanyDepartmentStaff;


public interface CompanyDepartmentMapper {

    /**
     * 部门数量
     * 
     * @param query
     * @return
     */
    int getDepartmentStaffCount(CompanyDepartmentQuery query);
    
    /**
     * 部门信息一览
     * 
     * @param query
     * @return
     */
    List<CompanyDepartmentStaff> getDepartmentStaff(CompanyDepartmentQuery query);
    
    /**
     * 部门信息插入
     * 
     * @param companyDepartment
     * @return
     */
    int insertCompanyDepartment(CompanyDepartment companyDepartment);
    
    /**
     * 更新部门信息
     * 
     * @param companyDepartment
     * @return
     */
    int updateCompanyDepartmentById(CompanyDepartment companyDepartment);
    
    /**
     * 条件查询部门信息
     * 
     * @param query
     * @return
     */
    List<CompanyDepartment> getCompanyDepartmentByCondition(CompanyDepartmentQuery query);
    
    /**
     * 查询部门名称和部门人员数量
     * 
     * @return
     */
    List<CompanyDepartment> getStaffDepartmentAndCount(CompanyDepartmentQuery query);
}
