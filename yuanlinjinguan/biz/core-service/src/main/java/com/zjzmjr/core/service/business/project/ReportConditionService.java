package com.zjzmjr.core.service.business.project;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.project.ReportCondition;

/**
 * 统计报表的条件
 * 
 * @author oms
 * @version $Id: ReportConditionService.java, v 0.1 2017-9-19 上午10:11:23 oms Exp $
 */
public interface ReportConditionService {

    /**
     * 根据用户编号查询该用户的统计报表检索条件
     * 
     * @param userId
     * @return
     */
    ResultEntry<ReportCondition> getReportConditionByUserId(Integer userId);

    /**
     * 插入统计报表的条件
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertReportCondition(ReportCondition record);

    /**
     * 根据主键更新统计报表查询条件
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateReportConditionById(ReportCondition record);

}
