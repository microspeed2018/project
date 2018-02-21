package com.zjzmjr.core.service.business.company;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.company.WorkReport;
import com.zjzmjr.core.model.company.WorkReportInfo;
import com.zjzmjr.core.model.company.WorkReportQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: WorkReportService.java, v 0.1 2017-8-10 上午10:46:28 oms Exp $
 */
public interface WorkReportService {

    /**
     * 
     * 
     * @param query
     * @return
     */
    ResultPage<WorkReportInfo> getWorkReportByCondition(WorkReportQuery query);
    
    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertWorkReport(WorkReport record);
    
    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateWorkReportById(WorkReport record);
    
}
