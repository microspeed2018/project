package com.zjzmjr.core.provider.talent;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.talent.ITalentFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.talent.Talent;
import com.zjzmjr.core.model.talent.TalentDocument;
import com.zjzmjr.core.model.talent.TalentDocumentQuery;
import com.zjzmjr.core.model.talent.TalentEducation;
import com.zjzmjr.core.model.talent.TalentEducationQuery;
import com.zjzmjr.core.model.talent.TalentFamily;
import com.zjzmjr.core.model.talent.TalentFamilyQuery;
import com.zjzmjr.core.model.talent.TalentInfo;
import com.zjzmjr.core.model.talent.TalentJob;
import com.zjzmjr.core.model.talent.TalentJobQuery;
import com.zjzmjr.core.model.talent.TalentQuery;
import com.zjzmjr.core.service.business.talent.TalentDocumentService;
import com.zjzmjr.core.service.business.talent.TalentEducationService;
import com.zjzmjr.core.service.business.talent.TalentFamilyService;
import com.zjzmjr.core.service.business.talent.TalentJobService;
import com.zjzmjr.core.service.business.talent.TalentService;

@Service("talentFacade")
public class TalentFacadeImpl implements ITalentFacade{

    private static final Logger logger = LoggerFactory.getLogger(TalentFacadeImpl.class);
    
    @Resource(name = "talentJobService")
    private TalentJobService talentJobService;
    
    @Resource(name = "talentFamilyService")
    private TalentFamilyService talentFamilyService;
    
    @Resource(name = "talentDocumentService")
    private TalentDocumentService talentDocumentService;
    
    @Resource(name = "talentEducationService")
    private TalentEducationService talentEducationService;
    
    @Resource(name = "talentService")
    private TalentService talentService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.talent.ITalentFacade#getTalentJobByCondition(com.zjzmjr.core.model.talent.TalentJobQuery)
     */
    @Override
    public ResultRecord<TalentJob> getTalentJobByCondition(TalentJobQuery query) {
        ResultRecord<TalentJob> result = new ResultRecord<TalentJob>();
        try {
            result = talentJobService.getTalentJobByCondition(query);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#insertTalentJob(com.zjzmjr.core.model.talent.TalentJob)
     */
    @Override
    public ResultEntry<Integer> insertTalentJob(TalentJob talentJob) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentJobService.insertTalentJob(talentJob);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#updateTalentJob(com.zjzmjr.core.model.talent.TalentJob)
     */
    @Override
    public ResultEntry<Integer> updateTalentJob(TalentJob talentJob) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentJobService.updateTalentJob(talentJob);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#deleteTalentJob(java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> deleteTalentJob(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentJobService.deleteTalentJob(id);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#getTalentFamilyByCondition(com.zjzmjr.core.model.talent.TalentFamilyQuery)
     */
    @Override
    public ResultRecord<TalentFamily> getTalentFamilyByCondition(TalentFamilyQuery query) {
        ResultRecord<TalentFamily> result = new ResultRecord<TalentFamily>();
        try {
            result = talentFamilyService.getTalentFamilyByCondition(query);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#insertTalentFamily(com.zjzmjr.core.model.talent.TalentFamily)
     */
    @Override
    public ResultEntry<Integer> insertTalentFamily(TalentFamily talentFamily) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentFamilyService.insertTalentFamily(talentFamily);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#updateTalentFamily(com.zjzmjr.core.model.talent.TalentFamily)
     */
    @Override
    public ResultEntry<Integer> updateTalentFamily(TalentFamily talentFamily) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentFamilyService.updateTalentFamily(talentFamily);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#deleteTalentFamily(java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> deleteTalentFamily(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentFamilyService.deleteTalentFamily(id);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#getTalentEducationByCondition(com.zjzmjr.core.model.talent.TalentEducationQuery)
     */
    @Override
    public ResultRecord<TalentEducation> getTalentEducationByCondition(TalentEducationQuery query) {
        ResultRecord<TalentEducation> result = new ResultRecord<TalentEducation>();
        try {
            result = talentEducationService.getTalentEducationByCondition(query);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#insertTalentEducation(com.zjzmjr.core.model.talent.TalentEducation)
     */
    @Override
    public ResultEntry<Integer> insertTalentEducation(TalentEducation talentEducation) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentEducationService.insertTalentEducation(talentEducation);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#updateTalentEducation(com.zjzmjr.core.model.talent.TalentEducation)
     */
    @Override
    public ResultEntry<Integer> updateTalentEducation(TalentEducation talentEducation) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentEducationService.updateTalentEducation(talentEducation);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#deleteTalentEducation(java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> deleteTalentEducation(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentEducationService.deleteTalentEducation(id);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#getTalentDocumentByCondition(com.zjzmjr.core.model.talent.TalentDocumentQuery)
     */
    @Override
    public ResultRecord<TalentDocument> getTalentDocumentByCondition(TalentDocumentQuery query) {
        ResultRecord<TalentDocument> result = new ResultRecord<TalentDocument>();
        try {
            result = talentDocumentService.getTalentDocumentByCondition(query);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#insertTalentDocument(com.zjzmjr.core.model.talent.TalentDocument)
     */
    @Override
    public ResultEntry<Integer> insertTalentDocument(TalentDocument talentDocument) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentDocumentService.insertTalentDocument(talentDocument);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#updateTalentDocument(com.zjzmjr.core.model.talent.TalentDocument)
     */
    @Override
    public ResultEntry<Integer> updateTalentDocument(TalentDocument talentDocument) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentDocumentService.updateTalentDocument(talentDocument);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#deleteTalentDocument(java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> deleteTalentDocument(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentDocumentService.deleteTalentDocument(id);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#insertTalent(com.zjzmjr.core.model.talent.Talent)
     */
    @Override
    public ResultEntry<String> insertTalent(TalentInfo talent) {
        ResultEntry<String> result = new ResultEntry<String>();
        try {
            result = talentService.insertTalent(talent);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#updateTalent(com.zjzmjr.core.model.talent.Talent)
     */
    @Override
    public ResultEntry<Integer> updateTalent(Talent talent) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = talentService.updateTalent(talent);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#getTalentInfo(com.zjzmjr.core.model.talent.TalentQuery)
     */
    @Override
    public ResultRecord<TalentInfo> getTalentInfo(TalentQuery talentQuery) {
        ResultRecord<TalentInfo> result = new ResultRecord<TalentInfo>();
        try {
            result = talentService.getTalentInfo(talentQuery);
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
     * @see com.zjzmjr.core.api.talent.ITalentFacade#getTalentByCondition(com.zjzmjr.core.model.talent.TalentQuery)
     */
    @Override
    public ResultPage<TalentInfo> getTalentByCondition(TalentQuery query) {
        ResultPage<TalentInfo> result = new ResultPage<TalentInfo>();
        try {
            result = talentService.getTalentByCondition(query);
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
