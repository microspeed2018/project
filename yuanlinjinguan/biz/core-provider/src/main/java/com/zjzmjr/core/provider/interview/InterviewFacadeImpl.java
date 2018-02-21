package com.zjzmjr.core.provider.interview;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.interview.IInterviewFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.interview.Interview;
import com.zjzmjr.core.model.interview.InterviewInfo;
import com.zjzmjr.core.model.interview.InterviewQuery;
import com.zjzmjr.core.service.business.interview.InterviewService;

@Service("interviewFacade")
public class InterviewFacadeImpl implements IInterviewFacade{

    private static final Logger logger = LoggerFactory.getLogger(InterviewFacadeImpl.class);
    
    @Resource(name = "interviewService")
    private InterviewService interviewService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.interview.IInterviewFacade#insertInterview(com.zjzmjr.core.model.interview.Interview)
     */
    @Override
    public ResultEntry<Integer> insertInterview(Interview interview) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = interviewService.insertInterview(interview);
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
     * @see com.zjzmjr.core.api.interview.IInterviewFacade#updateInterview(com.zjzmjr.core.model.interview.Interview)
     */
    @Override
    public ResultEntry<Integer> updateInterview(Interview interview) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = interviewService.updateInterview(interview);
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
     * @see com.zjzmjr.core.api.interview.IInterviewFacade#getMyInterviewInfo(com.zjzmjr.core.model.interview.InterviewQuery)
     */
    @Override
    public ResultPage<InterviewInfo> getMyInterviewInfo(InterviewQuery query) {
        ResultPage<InterviewInfo> result = new ResultPage<InterviewInfo>();
        try {
            result = interviewService.getMyInterviewInfo(query);
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
     * @see com.zjzmjr.core.api.interview.IInterviewFacade#getInterviewInfo(com.zjzmjr.core.model.interview.InterviewQuery)
     */
    @Override
    public ResultPage<InterviewInfo> getInterviewInfo(InterviewQuery query) {
        ResultPage<InterviewInfo> result = new ResultPage<InterviewInfo>();
        try {
            result = interviewService.getInterviewInfo(query);
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
     * @see com.zjzmjr.core.api.interview.IInterviewFacade#getInterviewByCondition(com.zjzmjr.core.model.interview.InterviewQuery)
     */
    @Override
    public ResultRecord<Interview> getInterviewByCondition(InterviewQuery query) {
        ResultRecord<Interview> result = new ResultRecord<Interview>();
        try {
            result = interviewService.getInterviewByCondition(query);
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
     * @see com.zjzmjr.core.api.interview.IInterviewFacade#getInterviewByCheckTalent(com.zjzmjr.core.model.interview.InterviewQuery)
     */
    @Override
    public ResultRecord<String> getInterviewByCheckTalent(InterviewQuery query) {
        ResultRecord<String> result = new ResultRecord<String>();
        try {
            result = interviewService.getInterviewByCheckTalent(query);
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
     * @see com.zjzmjr.core.api.interview.IInterviewFacade#setInterview(com.zjzmjr.core.model.interview.InterviewQuery)
     */
    @Override
    public ResultEntry<Integer> setInterview(InterviewQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = interviewService.setInterview(query);
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
     * @see com.zjzmjr.core.api.interview.IInterviewFacade#setInterview(com.zjzmjr.core.model.interview.Interview)
     */
    @Override
    public ResultEntry<Integer> setInterviewResult(Interview interview) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = interviewService.setInterviewResult(interview);
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
     * @see com.zjzmjr.core.api.interview.IInterviewFacade#getInterviewByConidtion(com.zjzmjr.core.model.interview.InterviewQuery)
     */
    @Override
    public ResultRecord<InterviewInfo> getInterviewInfoByCondition(InterviewQuery query) {
        ResultRecord<InterviewInfo> result = new ResultRecord<InterviewInfo>();
        try {
            result = interviewService.getInterviewInfoByCondition(query);
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
