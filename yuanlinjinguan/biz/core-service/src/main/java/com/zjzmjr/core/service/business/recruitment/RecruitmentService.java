package com.zjzmjr.core.service.business.recruitment;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.recruitment.Recruitment;
import com.zjzmjr.core.model.recruitment.RecruitmentInfo;
import com.zjzmjr.core.model.recruitment.RecruitmentQuery;


public interface RecruitmentService {

    /**
     * 新增招聘信息
     * 
     * @param recruitment
     * @return
     */
    ResultEntry<Integer> insertRecruitment(Recruitment recruitment);
    
    /**
     * 修改招聘信息
     * 
     * @param recruitment
     * @return
     */
    ResultEntry<Integer> updateRecruitment(Recruitment recruitment);
    
    /**
     * 招聘信息一览
     * 
     * @param query
     * @return
     */
    ResultPage<RecruitmentInfo> getRecruitment(RecruitmentQuery query);
    
    /**
     * 条件查询招聘岗位
     * 
     * @param query
     * @return
     */
    ResultRecord<Recruitment> getRecruitmentByCondition(RecruitmentQuery query);
}
