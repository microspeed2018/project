package com.zjzmjr.core.service.business.company;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.company.CompanyDepartmentJob;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.company.CompanyJob;


public interface CompanyJobService {

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
}
