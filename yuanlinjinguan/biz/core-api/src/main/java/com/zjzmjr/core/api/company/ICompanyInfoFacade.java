package com.zjzmjr.core.api.company;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.company.CompanyAssociatedInfo;
import com.zjzmjr.core.model.company.CompanyAssociatedQuery;
import com.zjzmjr.core.model.company.CompanyInfo;
import com.zjzmjr.core.model.company.CompanyInfoAptitude;
import com.zjzmjr.core.model.company.CompanyInfoQuery;
import com.zjzmjr.core.model.company.WorkReport;
import com.zjzmjr.core.model.company.WorkReportInfo;
import com.zjzmjr.core.model.company.WorkReportQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: ICompanyInfoFacade.java, v 0.1 2017-8-8 下午7:29:06 oms Exp $
 */
public interface ICompanyInfoFacade {

    /**
     * 根据主键查询公司及资质信息
     * 
     * @param id
     * @return
     */
    ResultEntry<CompanyInfoAptitude> getCompanyInfoAptitudeById(Integer id);
    
    /**
     * 
     * 
     * @param query
     * @return
     */
    ResultPage<CompanyInfoAptitude> getCompanyInfoByCondition(CompanyInfoQuery query);
    
    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertCompanyInfo(CompanyInfo record);
    
    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateCompanyInfoById(CompanyInfo record);

    /**
     * 工作汇报信息
     * 
     * @param query
     * @return
     */
    ResultPage<WorkReportInfo> getWorkReportByCondition(WorkReportQuery query);
    
    /**
     * 工作汇报信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertWorkReport(WorkReport record);
    
    /**
     * 工作汇报信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateWorkReportById(WorkReport record);
    
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
    ResultEntry<Integer> insertCompanyAssociated(CompanyAssociatedInfo companyAssociatedInfo);
    
    /**
     * 更新关联公司表
     * 
     * @param companyAssociated
     * @return
     */
    ResultEntry<Integer> updateCompanyAssociatedById(CompanyAssociatedInfo companyAssociatedInfo); 
    
}
