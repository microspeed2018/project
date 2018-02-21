package com.zjzmjr.core.service.business.company.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.company.WorkReport;
import com.zjzmjr.core.model.company.WorkReportInfo;
import com.zjzmjr.core.model.company.WorkReportQuery;
import com.zjzmjr.core.service.business.company.WorkReportService;
import com.zjzmjr.core.service.mapper.dao.company.WorkReportMapper;

/**
 * 工作汇报业务处理
 * 
 * @author oms
 * @version $Id: WorkReportServiceImpl.java, v 0.1 2017-8-10 上午10:48:57 oms Exp $
 */
@Service("workReportService")
public class WorkReportServiceImpl implements WorkReportService {

    private static final Logger logger = LoggerFactory.getLogger(WorkReportServiceImpl.class);

    @Resource(name = "workReportMapper")
    private WorkReportMapper workReportMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.company.WorkReportService#getWorkReportByCondition(com.zjzmjr.core.model.company.WorkReportQuery)
     */
    @Override
    public ResultPage<WorkReportInfo> getWorkReportByCondition(WorkReportQuery query) {
        ResultPage<WorkReportInfo> result = new ResultPage<WorkReportInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = workReportMapper.getWorkReportCount(query);
        if (total > 0) {
            List<WorkReportInfo> list = workReportMapper.getWorkReportByCondition(query);
            result.setSuccess(true);
            result.setList(list);
        } else {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }

        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.WorkReportService#insertWorkReport(com.zjzmjr.core.model.company.WorkReport)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertWorkReport(WorkReport record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getUserId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = workReportMapper.insertWorkReport(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":工作汇报表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.WorkReportService#updateWorkReportById(com.zjzmjr.core.model.company.WorkReport)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateWorkReportById(WorkReport record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = workReportMapper.updateWorkReportById(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":工作汇报表更新失败");
        }
        return result;
    }

}
