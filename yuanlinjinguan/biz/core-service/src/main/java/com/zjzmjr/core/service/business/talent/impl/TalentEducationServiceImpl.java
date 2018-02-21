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
import com.zjzmjr.core.model.talent.TalentEducation;
import com.zjzmjr.core.model.talent.TalentEducationQuery;
import com.zjzmjr.core.service.business.talent.TalentEducationService;
import com.zjzmjr.core.service.mapper.dao.talent.TalentEducationMapper;

@Service("talentEducationService")
public class TalentEducationServiceImpl implements TalentEducationService{

    private static final Logger logger = LoggerFactory.getLogger(TalentEducationServiceImpl.class);
    
    /** 人才学历信息 */
    @Resource(name = "talentEducationMapper")
    private TalentEducationMapper talentEducationMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentService#getTalentEducationByCondition(com.zjzmjr.core.model.talent.TalentEducationQuery)
     */
    @Override
    public ResultRecord<TalentEducation> getTalentEducationByCondition(TalentEducationQuery query) {
        ResultRecord<TalentEducation> result = new ResultRecord<TalentEducation>();
        if(Util.isNull(query) || Util.isNull(query.getTalentId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        List<TalentEducation> talentEducation  = talentEducationMapper.getTalentEducationByCondition(query);
        if(talentEducation==null || talentEducation.size()==0){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);       
        }else{
            result.setSuccess(true);
            result.setList(talentEducation);  
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentService#insertTalentEducation(com.zjzmjr.core.model.talent.TalentEducation)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertTalentEducation(TalentEducation talentEducation) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(talentEducation)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentEducationMapper.insertTalentEducation(talentEducation);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才学历信息表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentService#updateTalentEducation(com.zjzmjr.core.model.talent.TalentEducation)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateTalentEducation(TalentEducation talentEducation) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(talentEducation) || Util.isNull(talentEducation.getTalentId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentEducationMapper.updateTalentEducation(talentEducation);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才学历信息表修改失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentService#deleteTalentEducation(java.lang.Integer)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteTalentEducation(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(id)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentEducationMapper.deleteTalentEducation(id);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才学历信息表删除失败");
        }
        return result;
    }
    
}
