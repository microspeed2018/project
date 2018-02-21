package com.zjzmjr.core.service.business.company;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.company.CompanyAssociatedInfo;
import com.zjzmjr.core.model.company.CompanyAssociatedQuery;


public interface CompanyAssociatedInfoService {

    /**
     * 关联公司一览
     * 
     * @param query
     * @return
     */
    ResultPage<CompanyAssociatedInfo> getCompanyAssociatedInfo(CompanyAssociatedQuery query);
    
    /**
     * 插入关联公司表
     * 
     * @param companyAssociated
     * @return
     */
    ResultEntry<Integer> insertCompanyAssociated(CompanyAssociatedInfo companyAssociatedInfo) throws ApplicationException;
    
    /**
     * 更新关联公司表
     * 
     * @param companyAssociated
     * @return
     */
    ResultEntry<Integer> updateCompanyAssociatedById(CompanyAssociatedInfo companyAssociatedInfo) throws ApplicationException; 
}
