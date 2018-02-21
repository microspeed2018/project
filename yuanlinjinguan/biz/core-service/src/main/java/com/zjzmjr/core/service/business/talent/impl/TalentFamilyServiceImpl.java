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
import com.zjzmjr.core.model.talent.TalentFamily;
import com.zjzmjr.core.model.talent.TalentFamilyQuery;
import com.zjzmjr.core.service.business.talent.TalentFamilyService;
import com.zjzmjr.core.service.mapper.dao.talent.TalentFamilyMapper;

@Service("talentFamilyService")
public class TalentFamilyServiceImpl implements TalentFamilyService{


    private static final Logger logger = LoggerFactory.getLogger(TalentFamilyServiceImpl.class);
    
    /** 人才工作信息 */
    @Resource(name = "talentFamilyMapper")
    private TalentFamilyMapper talentFamilyMapper;
    
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentFamilyService#getTalentFamilyByCondition(com.zjzmjr.core.model.talent.TalentFamilyQuery)
     */
    @Override
    public ResultRecord<TalentFamily> getTalentFamilyByCondition(TalentFamilyQuery query) {
        ResultRecord<TalentFamily>  result = new ResultRecord<TalentFamily>();
        if(Util.isNull(query) || Util.isNull(query.getTalentId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        List<TalentFamily>  talentFamily  = talentFamilyMapper.getTalentFamilyByCondition(query);
        if(talentFamily==null || talentFamily.size()==0){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);        
        }else{
            result.setSuccess(true);
            result.setList(talentFamily);  
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentFamilyService#insertTalentFamily(com.zjzmjr.core.model.talent.TalentFamily)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertTalentFamily(TalentFamily talentFamily) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(talentFamily)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentFamilyMapper.insertTalentFamily(talentFamily);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才家属信息表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentFamilyService#updateTalentFamily(com.zjzmjr.core.model.talent.TalentFamily)
     */
    @Override
    public ResultEntry<Integer> updateTalentFamily(TalentFamily talentFamily) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(talentFamily) || Util.isNull(talentFamily.getTalentId()) || Util.isNull(talentFamily.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentFamilyMapper.updateTalentFamily(talentFamily);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才家属信息表修改失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentFamilyService#deleteTalentFamily(java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> deleteTalentFamily(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(id)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentFamilyMapper.deleteTalentFamily(id);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才家属信息表删除失败");
        }
        return result;
    }

}
