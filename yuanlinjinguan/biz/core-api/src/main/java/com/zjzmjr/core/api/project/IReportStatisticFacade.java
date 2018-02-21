package com.zjzmjr.core.api.project;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.project.ReportCondQuery;
import com.zjzmjr.core.model.project.ReportCondition;
import com.zjzmjr.core.model.project.ReportStatisticField;

/**
 * 统计报表的检索条件及统计结果对外接口
 * 
 * @author oms
 * @version $Id: IReportStatisticFacade.java, v 0.1 2017-9-19 上午10:52:07 oms Exp $
 */
public interface IReportStatisticFacade {

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

    /**
     * 根据报表统计的条件，查询出项目及合同的报表
     * 
     * @param condition
     * @return
     */
    ResultPage<ReportStatisticField> getReportStaticByCondition(ReportCondQuery query);
    
}
