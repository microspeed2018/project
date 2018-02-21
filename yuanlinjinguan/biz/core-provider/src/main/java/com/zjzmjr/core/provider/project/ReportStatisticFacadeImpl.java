package com.zjzmjr.core.provider.project;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.project.IReportStatisticFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.project.ReportCondQuery;
import com.zjzmjr.core.model.project.ReportCondition;
import com.zjzmjr.core.model.project.ReportStatisticField;
import com.zjzmjr.core.service.business.project.ReportConditionService;
import com.zjzmjr.core.service.business.project.ReportStatisticService;

/**
 * 统计报表的检索条件及统计结果对外接口
 * 
 * @author oms
 * @version $Id: ReportStatisticFacadeImpl.java, v 0.1 2017-9-19 上午10:54:20 oms Exp $
 */
@Service("reportStatisticFacade")
public class ReportStatisticFacadeImpl implements IReportStatisticFacade {

    private static final Logger logger = LoggerFactory.getLogger(ReportStatisticFacadeImpl.class);
    
    @Resource(name = "reportConditionService")
    private ReportConditionService reportConditionService;
    
    @Resource(name = "reportStatisticService")
    private ReportStatisticService reportStatisticService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IReportStatisticFacade#getReportConditionByUserId(java.lang.Integer)
     */
    @Override
    public ResultEntry<ReportCondition> getReportConditionByUserId(Integer userId) {
        ResultEntry<ReportCondition> result = new ResultEntry<ReportCondition>();
        try {
            result = reportConditionService.getReportConditionByUserId(userId);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IReportStatisticFacade#insertReportCondition(com.zjzmjr.core.model.project.ReportCondition)
     */
    @Override
    public ResultEntry<Integer> insertReportCondition(ReportCondition record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = reportConditionService.insertReportCondition(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IReportStatisticFacade#updateReportConditionById(com.zjzmjr.core.model.project.ReportCondition)
     */
    @Override
    public ResultEntry<Integer> updateReportConditionById(ReportCondition record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = reportConditionService.updateReportConditionById(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IReportStatisticFacade#getReportStaticByCondition(com.zjzmjr.core.model.project.ReportCondition)
     */
    @Override
    public ResultPage<ReportStatisticField> getReportStaticByCondition(ReportCondQuery query) {
        ResultPage<ReportStatisticField> result = new ResultPage<ReportStatisticField>();
        try {
            result = reportStatisticService.getReportStaticByCondition(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
}
