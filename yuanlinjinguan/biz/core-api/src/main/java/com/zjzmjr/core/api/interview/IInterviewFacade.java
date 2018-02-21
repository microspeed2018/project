package com.zjzmjr.core.api.interview;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.interview.Interview;
import com.zjzmjr.core.model.interview.InterviewInfo;
import com.zjzmjr.core.model.interview.InterviewQuery;


public interface IInterviewFacade {

    /**
     * 新建面试记录
     * 
     * @param interview
     * @return
     */
    ResultEntry<Integer> insertInterview(Interview interview);
    
    /**
     * 修改面试记录
     * 
     * @param interview
     * @return
     */
    ResultEntry<Integer> updateInterview(Interview interview);
    
    /**
     * 我的面试信息取得
     * 
     * @param query
     * @return
     */
    ResultPage<InterviewInfo> getMyInterviewInfo(InterviewQuery query);
    
    /**
     * 面试记录一览
     * 
     * @param query
     * @return
     */
    ResultPage<InterviewInfo> getInterviewInfo(InterviewQuery query);
    
    /**
     * 条件查询面试管理
     * 
     * @param query
     * @return
     */
    ResultRecord<Interview> getInterviewByCondition(InterviewQuery query);
    
    /**
     * 人才管理提交面试方法
     * 
     * @param query
     * @return
     */
    ResultRecord<String> getInterviewByCheckTalent(InterviewQuery query);
    
    /**
     * 人才提交面试
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> setInterview(InterviewQuery query);
    

    /**
     * 面试结果设置
     * 
     * @param interview
     * @return
     */
    ResultEntry<Integer> setInterviewResult(Interview interview);
    
    /**
     * 手机端面试一览
     * 
     * @param query
     * @return
     */
    ResultRecord<InterviewInfo> getInterviewInfoByCondition(InterviewQuery query);
}
