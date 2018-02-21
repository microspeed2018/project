package com.zjzmjr.core.service.mapper.dao.company;

import com.zjzmjr.core.model.company.CompanyAptitude;

/**
 * 公司资质表
 * 
 * @author oms
 * @version $Id: CompanyAptitudeMapper.java, v 0.1 2017-8-11 上午9:53:37 oms Exp $
 */
public interface CompanyAptitudeMapper {

    int deleteCompanyAptitudeById(Integer id);
    
    int deleteCompanyAptitudeByCompanyId(Integer companyId);

    int insertCompanyAptitude(CompanyAptitude record);

    CompanyAptitude getCompanyAptitudeById(Integer id);

    int updateCompanyAptitudeById(CompanyAptitude record);

}