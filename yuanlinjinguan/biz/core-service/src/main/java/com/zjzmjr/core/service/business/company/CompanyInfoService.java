package com.zjzmjr.core.service.business.company;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.company.CompanyInfo;
import com.zjzmjr.core.model.company.CompanyInfoAptitude;
import com.zjzmjr.core.model.company.CompanyInfoQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: CompanyInfoService.java, v 0.1 2017-8-8 下午6:50:05 oms Exp $
 */
public interface CompanyInfoService {

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
    ResultEntry<Integer> insertCompanyInfo(CompanyInfo record) throws ApplicationException;
    
    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateCompanyInfoById(CompanyInfo record) throws ApplicationException;
    
}
