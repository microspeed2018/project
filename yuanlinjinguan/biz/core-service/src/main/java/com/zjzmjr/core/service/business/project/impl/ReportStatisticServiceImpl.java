package com.zjzmjr.core.service.business.project.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.model.project.ReportCondQuery;
import com.zjzmjr.core.model.project.ReportCondition;
import com.zjzmjr.core.model.project.ReportStatisticField;
import com.zjzmjr.core.service.business.project.ReportConditionService;
import com.zjzmjr.core.service.business.project.ReportStatisticService;
import com.zjzmjr.core.service.mapper.dao.project.ReportStatisticMapper;

/**
 * 整个项目中的报表统计的Service层
 * 
 * @author oms
 * @version $Id: ReportStatisticServiceImpl.java, v 0.1 2017-9-20 下午3:45:45 oms Exp $
 */
@Service("reportStatisticService")
public class ReportStatisticServiceImpl implements ReportStatisticService {

    private static final Logger logger = LoggerFactory.getLogger(ReportStatisticServiceImpl.class);
    
    @Resource(name = "reportConditionService")
    private ReportConditionService reportConditionService;
    
    @Resource(name = "reportStatisticMapper")
    private ReportStatisticMapper reportStatisticMapper;
    
    /**
     * 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @see com.zjzmjr.core.service.business.project.ReportStatisticService#getReportStaticByCondition(com.zjzmjr.core.model.project.ReportCondition)
     */
    @Override
    public ResultPage<ReportStatisticField> getReportStaticByCondition(ReportCondQuery query) throws Exception {
        ResultPage<ReportStatisticField> result = new ResultPage<ReportStatisticField>();
        if (Util.isNull(query) || Util.isNull(query.getUserId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = 0;
        ResultEntry<Integer> updRst = null;
        ResultEntry<ReportCondition> condRst = reportConditionService.getReportConditionByUserId(query.getUserId());
        ReportCondition condition = new ReportCondition();
        BeanUtils.copyProperties(condition, query);
        if (condRst.isSuccess()) {
            condition.setId(condRst.getT().getId());
            condition.setCreateTime(null);
            condition.setCreateUserId(null);
            condition.setUpdateTime(new Date());
            condition.setUpdateUserId(query.getUserId());
            updRst = reportConditionService.updateReportConditionById(condition);
        } else {
            condition.setUpdateTime(new Date());
            condition.setUpdateUserId(query.getUserId());
            condition.setCreateTime(condition.getUpdateTime());
            condition.setCreateUserId(condition.getUpdateUserId());
            updRst = reportConditionService.insertReportCondition(condition);
        }
        if (updRst.isSuccess()) {
            if(StringUtils.isNotBlank(query.getManagePerson())){
                query.setManagePersonLst(Arrays.asList(query.getManagePerson().split(",")));
            }
            if(StringUtils.isNotBlank(query.getProjectLeader())){
                query.setProjectLeaderLst(Arrays.asList(query.getProjectLeader().split(",")));
            }
            if(StringUtils.isNotBlank(query.getProjectStep())){
                query.setProjectStepLst(Arrays.asList(query.getProjectStep().split(",")));
            }
//            query.setStatusLst(Arrays.asList(query.getStatus().split(",")));
            total = reportStatisticMapper.getReportStaticCount(query);
            if(total > 0) {
                List<ReportStatisticField> list = reportStatisticMapper.getReportStaticByCondition(query);
                for(ReportStatisticField field : list){
                    GardenProjectStepEnum stepEnum = GardenProjectStepEnum.getByValue(StringUtil.stringToInteger(field.getStep()));
                    if(stepEnum != null){
                        field.setStep(stepEnum.getMessage());
                    }
                    GardenProjectStatusEnum statusEnum = GardenProjectStatusEnum.getByValue(StringUtil.stringToInteger(field.getStatus()));
                    if(statusEnum != null){
                        field.setStatus(statusEnum.getMessage());
                    }
                }
                result.setSuccess(true);
                result.setList(list);
            }else{
                result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
                result.setSuccess(false);
            }
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":统计条件表操作失败");
        }

        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        
        return result;
    }

}
