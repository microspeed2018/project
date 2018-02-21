package com.zjzmjr.core.service.mapper.dao.interview;

import java.util.List;

import com.zjzmjr.core.model.interview.Interview;
import com.zjzmjr.core.model.interview.InterviewInfo;
import com.zjzmjr.core.model.interview.InterviewQuery;


public interface InterviewMapper {
    
    int getMyInterviewInfoCount(InterviewQuery query);
    
    int getInterviewInfoCount(InterviewQuery query);

    List<InterviewInfo> getMyInterviewInfo(InterviewQuery query);

    List<InterviewInfo> getInterviewInfo(InterviewQuery query);
    
    int insertInterview(Interview interview);
    
    int updateInterview(Interview interview);
    
    List<Interview> getInterviewByCondition(InterviewQuery query);
    
    int deleteInterview(Integer id);
    
    /**
     * 手机端面试一览
     * 
     * @param query
     * @return
     */
    List<InterviewInfo> getInterviewInfoByCondition(InterviewQuery query);
}
