package com.zjzmjr.core.service.business.project;

import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.project.ReportCondQuery;
import com.zjzmjr.core.model.project.ReportStatisticField;

/**
 * 整个项目中的报表统计的Service层
 * 
 * @author oms
 * @version $Id: ReportStatisticService.java, v 0.1 2017-9-20 下午3:43:33 oms Exp $
 */
public interface ReportStatisticService {

    /**
     * 根据报表统计的条件，查询出项目及合同的报表
     * 
     * @param condition
     * @return
     */
    ResultPage<ReportStatisticField> getReportStaticByCondition(ReportCondQuery query) throws Exception ;
    
}
