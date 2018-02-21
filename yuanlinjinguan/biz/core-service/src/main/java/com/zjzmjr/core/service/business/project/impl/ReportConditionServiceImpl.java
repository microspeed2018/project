package com.zjzmjr.core.service.business.project.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.project.ReportCondition;
import com.zjzmjr.core.service.business.project.ReportConditionService;
import com.zjzmjr.core.service.mapper.dao.project.ReportConditionMapper;

/**
 * 统计报表的查询条件
 * 
 * @author oms
 * @version $Id: ReportConditionServiceImpl.java, v 0.1 2017-9-19 上午10:15:53 oms Exp $
 */
@Service("reportConditionService")
public class ReportConditionServiceImpl implements ReportConditionService {

    private static final Logger logger = LoggerFactory.getLogger(ReportConditionServiceImpl.class);
    
    @Resource(name = "reportConditionMapper")
    private ReportConditionMapper reportConditionMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ReportConditionService#getReportConditionByUserId(java.lang.Integer)
     */
    @Override
    public ResultEntry<ReportCondition> getReportConditionByUserId(Integer userId) {
        ResultEntry<ReportCondition> result = new ResultEntry<ReportCondition>();
        if (Util.isNull(userId)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        ReportCondition report = reportConditionMapper.getReportConditionByUserId(userId);
        if(report == null){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setT(report);
        }
        
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ReportConditionService#insertReportCondition(com.zjzmjr.core.model.project.ReportCondition)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertReportCondition(ReportCondition record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getUserId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = reportConditionMapper.insertReportCondition(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":统计条件表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ReportConditionService#updateReportConditionById(com.zjzmjr.core.model.project.ReportCondition)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateReportConditionById(ReportCondition record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = reportConditionMapper.updateReportConditionById(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":统计条件表更新失败");
        }
        return result;
    }

}
