package com.zjzmjr.core.service.mapper.dao.project;

import com.zjzmjr.core.model.project.ReportCondition;

/**
 * 统计条件表
 * 
 * @author oms
 * @version $Id: ReportConditionMapper.java, v 0.1 2017-9-18 下午9:02:04 oms Exp $
 */
public interface ReportConditionMapper {
    
    /**
     * 删除
     * 
     * @param userId
     * @return
     */
    int deleteReportConditionByUserId(Integer userId);
    
    /**
     * 
     * n
     * @param record
     * @return
     */
    int insertReportCondition(ReportCondition record);

    /**
     * 
     * 
     * @param userId
     * @return
     */
    ReportCondition getReportConditionByUserId(Integer userId);

    /**
     * 根据主键更新统计报表查询条件
     * 
     * @param record
     * @return
     */
    int updateReportConditionById(ReportCondition record);

}