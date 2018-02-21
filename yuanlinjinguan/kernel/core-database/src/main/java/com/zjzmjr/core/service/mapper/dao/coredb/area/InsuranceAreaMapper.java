package com.zjzmjr.core.service.mapper.dao.coredb.area;

import java.util.List;

import com.zjzmjr.core.model.area.InsuranceArea;
import com.zjzmjr.core.model.area.InsuranceAreaQuery;


public interface InsuranceAreaMapper {

    int getInsuranceAreaCount(InsuranceAreaQuery query);
    
    List<InsuranceArea> getInsuranceArea(InsuranceAreaQuery query);
    
    int insertInsuranceArea(InsuranceArea insuranceArea);
    
    int deleteInsuranceArea(Integer id);
}
