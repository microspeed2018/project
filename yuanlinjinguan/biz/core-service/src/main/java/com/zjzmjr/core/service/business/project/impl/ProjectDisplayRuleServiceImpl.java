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
import com.zjzmjr.core.model.project.ProjectDisplayRule;
import com.zjzmjr.core.service.business.project.ProjectDisplayRuleService;
import com.zjzmjr.core.service.mapper.dao.project.ProjectDisplayRuleMapper;

/**
 * 项目一览展示条件表的业务处理类
 * 
 * @author oms
 * @version $Id: ProjectDisplayRuleServiceImpl.java, v 0.1 2017-9-4 下午5:06:27 oms Exp $
 */
@Service("projectDisplayRuleService")
public class ProjectDisplayRuleServiceImpl implements ProjectDisplayRuleService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectDisplayRuleServiceImpl.class);

    @Resource(name = "projectDisplayRuleMapper")
    private ProjectDisplayRuleMapper projectDisplayRuleMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectDisplayRuleService#getProjectDisplayRuleByUserId(java.lang.Integer)
     */
    @Override
    public ResultEntry<ProjectDisplayRule> getProjectDisplayRuleByUserId(Integer userId) {
        ResultEntry<ProjectDisplayRule> result = new ResultEntry<ProjectDisplayRule>();
        if (Util.isNull(userId)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        ProjectDisplayRule displayRule = projectDisplayRuleMapper.getProjectDisplayRuleByUserId(userId);
        if (displayRule == null) {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setT(displayRule);
        }

        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectDisplayRuleService#insertProjectDisplayRule(com.zjzmjr.core.model.project.ProjectDisplayRule)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertProjectDisplayRule(ProjectDisplayRule record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getUserId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = projectDisplayRuleMapper.insertProjectDisplayRule(record);
        if(total > 0){
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目一览展示条件表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectDisplayRuleService#updateProjectDisplayRuleById(com.zjzmjr.core.model.project.ProjectDisplayRule)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateProjectDisplayRuleById(ProjectDisplayRule record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = projectDisplayRuleMapper.updateProjectDisplayRuleById(record);
        if(total > 0){
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目一览展示条件表更新失败");
        }
        return result;
    }

}
