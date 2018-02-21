package com.zjzmjr.core.service.mapper.dao.company;

import java.util.List;

import com.zjzmjr.core.model.company.CompanyAssociated;
import com.zjzmjr.core.model.company.CompanyAssociatedInfo;
import com.zjzmjr.core.model.company.CompanyAssociatedQuery;


public interface CompanyAssociatedMapper {

    
    /**
     * 查询关联公司数量
     * 
     * @param query
     * @return
     */
    int getCompanyAssociatedInfoCount(CompanyAssociatedQuery query);
       
    
    /**
     * 关联公司查询一览
     * 
     * @param query
     * @return
     */
    List<CompanyAssociatedInfo> getCompanyAssociatedInfo(CompanyAssociatedQuery query);
    
    /**
     * 插入关联公司
     * 
     * @param companyAssociated
     * @return
     */
    int insertCompanyAssociated(CompanyAssociated companyAssociated);
    
    /**
     * 更新关联公司
     * 
     * @param companyAssociated
     * @return
     */
    int updateCompanyAssociatedById(CompanyAssociated companyAssociated);
}
