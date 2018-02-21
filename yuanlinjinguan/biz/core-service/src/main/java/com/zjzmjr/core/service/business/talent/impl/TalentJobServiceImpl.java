package com.zjzmjr.core.service.business.talent.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.talent.TalentJob;
import com.zjzmjr.core.model.talent.TalentJobQuery;
import com.zjzmjr.core.service.business.talent.TalentJobService;
import com.zjzmjr.core.service.mapper.dao.talent.TalentJobMapper;

@Service("talentJobService")
public class TalentJobServiceImpl implements TalentJobService{

    private static final Logger logger = LoggerFactory.getLogger(TalentJobServiceImpl.class);
    
    /** 人才学历信息 */
    @Resource(name = "talentJobMapper")
    private TalentJobMapper talentJobMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentJobService#getTalentJobByCondition(com.zjzmjr.core.model.talent.TalentJobQuery)
     */
    @Override
    public ResultRecord<TalentJob> getTalentJobByCondition(TalentJobQuery query) {
        ResultRecord<TalentJob> result = new ResultRecord<TalentJob>();
        if(Util.isNull(query) || Util.isNull(query.getTalentId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        List<TalentJob> talentJob  = talentJobMapper.getTalentJobByCondition(query);
        if(talentJob==null || talentJob.size()==0){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);            
        }else{
            result.setSuccess(true);
            result.setList(talentJob); 
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentJobService#insertTalentJob(com.zjzmjr.core.model.talent.TalentJob)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertTalentJob(TalentJob talentJob) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(talentJob)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentJobMapper.insertTalentJob(talentJob);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才工作信息表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentJobService#updateTalentJob(com.zjzmjr.core.model.talent.TalentJob)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateTalentJob(TalentJob talentJob) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(talentJob) || Util.isNull(talentJob.getTalentId()) || Util.isNull(talentJob.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentJobMapper.updateTalentJob(talentJob);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才工作信息表修改失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentJobService#deleteTalentJob(java.lang.Integer)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteTalentJob(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(id)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentJobMapper.deleteTalentJob(id);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才工作信息表删除失败");
        }
        return result;
    }
}
