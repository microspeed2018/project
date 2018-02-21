package com.zjzmjr.core.service.mapper.dao.project;

import java.util.List;

import com.zjzmjr.core.model.project.ReportCondQuery;
import com.zjzmjr.core.model.project.ReportStatisticField;

/**
 * 整个项目中的报表统计的DAO
 * 
 * @author oms
 * @version $Id: ReportStatisticMapper.java, v 0.1 2017-9-20 下午3:37:54 oms Exp $
 */
public interface ReportStatisticMapper {

    /**
     * 根据报表统计的条件，查询出项目及合同的报表
     * 
     * @param condition
     * @return
     */
    List<ReportStatisticField> getReportStaticByCondition(ReportCondQuery query);
    
    /**
     * 件数
     * 
     * @param query
     * @return
     */
    int getReportStaticCount(ReportCondQuery query);
    
}
