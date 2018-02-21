package com.zjzmjr.core.service.mapper.dao.company;

import java.util.List;

import com.zjzmjr.core.model.company.WorkReport;
import com.zjzmjr.core.model.company.WorkReportInfo;
import com.zjzmjr.core.model.company.WorkReportQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: WorkReportMapper.java, v 0.1 2017-8-10 上午10:43:05 oms Exp $
 */
public interface WorkReportMapper {

    int deleteWorkReportById(Integer id);

    int insertWorkReport(WorkReport record);

    int getWorkReportCount(WorkReportQuery query);

    List<WorkReportInfo> getWorkReportByCondition(WorkReportQuery query);

    int updateWorkReportById(WorkReport record);

}