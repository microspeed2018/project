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
import com.zjzmjr.core.model.talent.TalentDocument;
import com.zjzmjr.core.model.talent.TalentDocumentQuery;
import com.zjzmjr.core.service.business.talent.TalentDocumentService;
import com.zjzmjr.core.service.mapper.dao.talent.TalentDocumentMapper;

@Service("talentDocumentService")
public class TalentDocumentServiceImpl implements TalentDocumentService{

    private static final Logger logger = LoggerFactory.getLogger(TalentDocumentServiceImpl.class);

    /** 人才附件信息 */
    @Resource(name = "talentDocumentMapper")
    private TalentDocumentMapper talentDocumentMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentDocumentService#getTalentDocumentByCondition(com.zjzmjr.core.model.talent.TalentDocumentQuery)
     */
    @Override
    public ResultRecord<TalentDocument> getTalentDocumentByCondition(TalentDocumentQuery query) {
        ResultRecord<TalentDocument> result = new ResultRecord<TalentDocument>();
        if(Util.isNull(query) || Util.isNull(query.getTalentId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        List<TalentDocument> talentDocument  = talentDocumentMapper.getTalentDocumentByCondition(query);
        if(talentDocument==null || talentDocument.size()==0){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);      
        }else{
            result.setSuccess(true);
            result.setList(talentDocument);  
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentDocumentService#insertTalentDocument(com.zjzmjr.core.model.talent.TalentDocument)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertTalentDocument(TalentDocument talentDocument) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(talentDocument)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentDocumentMapper.insertTalentDocument(talentDocument);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才附件信息表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentDocumentService#updateTalentDocument(com.zjzmjr.core.model.talent.TalentDocument)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateTalentDocument(TalentDocument talentDocument) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(talentDocument) || Util.isNull(talentDocument.getTalentId()) || Util.isNull(talentDocument.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentDocumentMapper.updateTalentDocument(talentDocument);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才附件信息表修改失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentDocumentService#deleteTalentDocument(java.lang.Integer)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteTalentDocument(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(id)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = talentDocumentMapper.deleteTalentDocument(id);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才附件信息表删除失败");
        }
        return result;
    }
}
