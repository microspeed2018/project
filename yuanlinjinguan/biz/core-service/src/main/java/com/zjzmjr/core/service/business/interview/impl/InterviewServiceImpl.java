package com.zjzmjr.core.service.business.interview.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.interview.InterviewResultEnum;
import com.zjzmjr.core.model.interview.Interview;
import com.zjzmjr.core.model.interview.InterviewInfo;
import com.zjzmjr.core.model.interview.InterviewQuery;
import com.zjzmjr.core.service.business.interview.InterviewService;
import com.zjzmjr.core.service.mapper.dao.interview.InterviewMapper;

@Service("interviewService")
public class InterviewServiceImpl implements InterviewService{

    private static final Logger logger = LoggerFactory.getLogger(InterviewServiceImpl.class);

    /** 招聘信息 */
    @Resource(name = "interviewMapper")
    private InterviewMapper interviewMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.interview.InterviewService#insertInterview(com.zjzmjr.core.model.interview.Interview)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertInterview(Interview interview) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(interview) || Util.isNull(interview.getTalentId()) || Util.isNull(interview.getRecruitmentId())
             ||  Util.isNull(interview.getCompanyId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = interviewMapper.insertInterview(interview);
        if(cnt>0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":面试信息表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.interview.InterviewService#updateInterview(com.zjzmjr.core.model.interview.Interview)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateInterview(Interview interview) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(interview) || Util.isNull(interview.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = interviewMapper.updateInterview(interview);
        if(cnt>0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":面试信息表更新失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.interview.InterviewService#getMyInterviewInfo(com.zjzmjr.core.model.interview.InterviewQuery)
     */
    @Override
    public ResultPage<InterviewInfo> getMyInterviewInfo(InterviewQuery query) {
        ResultPage<InterviewInfo> result = new ResultPage<InterviewInfo>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int total = interviewMapper.getMyInterviewInfoCount(query);
        if(total > 0){
            List<InterviewInfo> list = interviewMapper.getMyInterviewInfo(query);
            result.setList(list);
            result.setSuccess(true);
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.interview.InterviewService#getInterviewInfo(com.zjzmjr.core.model.interview.InterviewQuery)
     */
    @Override
    public ResultPage<InterviewInfo> getInterviewInfo(InterviewQuery query) {
        ResultPage<InterviewInfo> result = new ResultPage<InterviewInfo>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int total = interviewMapper.getInterviewInfoCount(query);
        if(total > 0){
            List<InterviewInfo> list = interviewMapper.getInterviewInfo(query);
            result.setList(list);
            result.setSuccess(true);
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.interview.InterviewService#getInterviewByCondition(com.zjzmjr.core.model.interview.InterviewQuery)
     */
    @Override
    public ResultRecord<Interview> getInterviewByCondition(InterviewQuery query) {
        ResultRecord<Interview> result = new ResultRecord<Interview>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<Interview> list = interviewMapper.getInterviewByCondition(query);
        if (list == null || list.size() == 0) {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setList(list);
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.interview.InterviewService#getInterviewByCheckTalent(com.zjzmjr.core.model.interview.InterviewQuery)
     */
    @Override
    public ResultRecord<String> getInterviewByCheckTalent(InterviewQuery query) {
        ResultRecord<String> result = new ResultRecord<String>();
        if (Util.isNull(query) || Util.isNull(query.getCheckTalent())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        //解析checkTalent
        //talent数组存放人才id,职位id
        String[] talent = query.getCheckTalent().split(";");
        //detail[0]为人才id,detail[1]为职位id
        String[] detail = null;
        InterviewQuery interviewQuery = null;
        String name = "";
        String talentRecruitment = "";
        for(int i=0 ; i<talent.length;i++){
            detail = talent[i].split(",");
            interviewQuery = new InterviewQuery();
            if(Util.isNotNull(detail[0]) && Util.isNotNull(detail[1])){
                interviewQuery.setTalentId(Integer.parseInt(detail[0]));
                interviewQuery.setRecruitmentId(Integer.parseInt(detail[1]));
                interviewQuery.setPageBean(new PageBean(Integer.MAX_VALUE,GenerateConstants.number_1));
                ResultPage<InterviewInfo> interviewRst =  getInterviewInfo(interviewQuery);
                if (interviewRst.isSuccess()) {
                    name += interviewRst.getList().get(0).getTalent().getName()+",";
                } else {
                    talentRecruitment += talent[i]+";";
                }
            }
           
        }
        List<String> strLst = new ArrayList<String>();
        strLst.add(name);
        strLst.add(talentRecruitment);
        result.setSuccess(true);
        result.setList(strLst);
        return result;
    }

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.interview.InterviewService#setInterview(com.zjzmjr.core.model.interview.Interview)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> setInterview(InterviewQuery query) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(query) || Util.isNull(query.getCheckTalent())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        String[] talent = query.getCheckTalent().split(";");
        String[] detail = null;
        Interview interview = null;
        for(int i=0 ; i<talent.length;i++){
            detail = talent[i].split(",");
            interview = new Interview();
            if(Util.isNotNull(detail[0]) && Util.isNotNull(detail[1])){
                interview.setTalentId(Integer.parseInt(detail[0]));
                interview.setRecruitmentId(Integer.parseInt(detail[1]));
                interview.setCompanyId(query.getCompanyId());
                interview.setCreateTime(query.getCreateTime());
                interview.setCreateUserId(query.getCreateUserId());
                interview.setRound(GenerateConstants.number_1);
                ResultEntry<Integer> interviewRst =  insertInterview(interview);
                if (!interviewRst.isSuccess()) {
                    throw new ApplicationException("新增面试操作失败"); 
                } 
            }
           
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.interview.InterviewService#setInterview(com.zjzmjr.core.model.interview.Interview)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> setInterviewResult(Interview interview) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(interview)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        InterviewQuery query = new InterviewQuery();
        // 如果面试结果为通过
        if (InterviewResultEnum.PASS.getValue().equals(interview.getResult())) {
            // 查询新一轮面试是否插入,已插入不做处理，未插入新增一条数据
            query.setTalentId(interview.getTalentId());
            query.setRecruitmentId(interview.getRecruitmentId());
            query.setCompanyId(interview.getCompanyId());
            query.setRound(interview.getRound() + 1);
            ResultRecord<Interview> interviewRst = getInterviewByCondition(query);
            if (!interviewRst.isSuccess()) {
                // 新增一条新一轮面试记录
                Interview newInterview = new Interview();
                newInterview.setCompanyId(interview.getCompanyId());
                newInterview.setTalentId(interview.getTalentId());
                newInterview.setRecruitmentId(interview.getRecruitmentId());
                newInterview.setRound(interview.getRound() + 1);
                newInterview.setCreateTime(interview.getUpdateTime());
                newInterview.setCreateUserId(interview.getUpdateUserId());
                result = insertInterview(newInterview);
                if (result.isSuccess()) {
                    result = updateInterview(interview);
                    if (!result.isSuccess()) {
                        throw new ApplicationException("修改面试操作失败");
                    }
                }
            } else {
                // 已经生成下一轮面试，只允许修改分数，结果不允许修改
                interview.setResult(interviewRst.getList().get(0).getResult());
                result = updateInterview(interview);
            }
        } else {
            // 查询新一轮面试是否插入并且是否已发送面试邀请
            query.setTalentId(interview.getTalentId());
            query.setRecruitmentId(interview.getRecruitmentId());
            query.setCompanyId(interview.getCompanyId());
            query.setRound(interview.getRound() + 1);
            ResultRecord<Interview> interviewRst = getInterviewByCondition(query);
            if (interviewRst.isSuccess()) {
                // 已有新数据，判断是否有发送面试邀请
                if (GenerateConstants.number_1.equals(interviewRst.getList().get(0).getIsSms())) {
                    result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                    result.setErrorMsg("下一轮面试邀请已发送，面试结果不可更改。");
                    result.setSuccess(false);
                } else {
                    // 删除该数据
                    // int cnt =
                    // interviewMapper.deleteInterview(interviewRst.getList().get(0).getId());
                    // if (cnt > 0) {
                    // 已经生成下一轮面试，只允许修改分数，结果不允许修改
                    interview.setResult(interviewRst.getList().get(0).getResult());
                    result = updateInterview(interview);
                    if (!result.isSuccess()) {
                        throw new ApplicationException("修改面试操作失败");
                    }
                    // }else{
                    // result.setSuccess(false);
                    // result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                    // logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() +
                    // ":面试信息表删除失败");
                    // }
                }
            } else {
                result = updateInterview(interview);
            }
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.interview.InterviewService#getInterviewByConidtion(com.zjzmjr.core.model.interview.InterviewQuery)
     */
    @Override
    public ResultRecord<InterviewInfo> getInterviewInfoByCondition(InterviewQuery query) {
        ResultRecord<InterviewInfo> result = new ResultRecord<InterviewInfo>();
        if (Util.isNull(query) || Util.isNull(query.getInterviewer())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<InterviewInfo> list = interviewMapper.getInterviewInfoByCondition(query);
        if (list == null || list.size() == 0) {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setList(list);
        }
        return result;
    }
     
}
