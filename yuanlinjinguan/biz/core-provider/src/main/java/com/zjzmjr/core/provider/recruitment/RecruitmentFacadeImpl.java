package com.zjzmjr.core.provider.recruitment;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.recruitment.IRecruitmentFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.recruitment.Recruitment;
import com.zjzmjr.core.model.recruitment.RecruitmentInfo;
import com.zjzmjr.core.model.recruitment.RecruitmentQuery;
import com.zjzmjr.core.service.business.recruitment.RecruitmentService;

@Service("recruitmentFacade")
public class RecruitmentFacadeImpl implements IRecruitmentFacade{

    private static final Logger logger = LoggerFactory.getLogger(RecruitmentFacadeImpl.class);
    
    @Resource(name = "recruitmentService")
    private RecruitmentService recruitmentService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.recruitment.IRecruitmentFacade#insertRecruitment(com.zjzmjr.core.model.recruitment.Recruitment)
     */
    @Override
    public ResultEntry<Integer> insertRecruitment(Recruitment recruitment) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = recruitmentService.insertRecruitment(recruitment);
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
     * @see com.zjzmjr.core.api.recruitment.IRecruitmentFacade#updateRecruitment(com.zjzmjr.core.model.recruitment.Recruitment)
     */
    @Override
    public ResultEntry<Integer> updateRecruitment(Recruitment recruitment) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = recruitmentService.updateRecruitment(recruitment);
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
     * @see com.zjzmjr.core.api.recruitment.IRecruitmentFacade#getRecruitment(com.zjzmjr.core.model.recruitment.RecruitmentQuery)
     */
    @Override
    public ResultPage<RecruitmentInfo> getRecruitment(RecruitmentQuery query) {
        ResultPage<RecruitmentInfo> result = new ResultPage<RecruitmentInfo>();
        try {
            result = recruitmentService.getRecruitment(query);
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
     * @see com.zjzmjr.core.api.recruitment.IRecruitmentFacade#getRecruitmentByCondition(com.zjzmjr.core.model.recruitment.RecruitmentQuery)
     */
    @Override
    public ResultRecord<Recruitment> getRecruitmentByCondition(RecruitmentQuery query) {
        ResultRecord<Recruitment> result = new ResultRecord<Recruitment>();
        try {
            result = recruitmentService.getRecruitmentByCondition(query);
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
