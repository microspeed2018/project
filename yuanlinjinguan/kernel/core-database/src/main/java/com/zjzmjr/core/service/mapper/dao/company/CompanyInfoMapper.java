package com.zjzmjr.core.service.mapper.dao.company;

import java.util.List;

import com.zjzmjr.core.model.company.CompanyInfo;
import com.zjzmjr.core.model.company.CompanyInfoAptitude;
import com.zjzmjr.core.model.company.CompanyInfoQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: CompanyInfoMapper.java, v 0.1 2017-8-8 下午6:16:28 oms Exp $
 */
public interface CompanyInfoMapper {
    
    int deleteCompanyInfoById(Integer id);

    int insertCompanyInfo(CompanyInfo record);

    int getCompanyInfoCount(CompanyInfoQuery query);
    
    List<CompanyInfoAptitude> getCompanyInfoByCondition(CompanyInfoQuery query);

    int updateCompanyInfoById(CompanyInfo record);

    /**
     * 根据主键查询公司及资质信息
     * 
     * @param id
     * @return
     */
    CompanyInfoAptitude getCompanyInfoAptitudeById(Integer id);
    
}