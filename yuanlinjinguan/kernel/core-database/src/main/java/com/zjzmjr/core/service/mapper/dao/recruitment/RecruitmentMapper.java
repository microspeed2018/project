package com.zjzmjr.core.service.mapper.dao.recruitment;

import java.util.List;

import com.zjzmjr.core.model.recruitment.Recruitment;
import com.zjzmjr.core.model.recruitment.RecruitmentInfo;
import com.zjzmjr.core.model.recruitment.RecruitmentQuery;


public interface RecruitmentMapper {

    /**
     * 新增招聘信息
     * 
     * @param recruitment
     * @return
     */
    int insertRecruitment(Recruitment recruitment);
    
    /**
     * 修改招聘信息
     * 
     * @param recruitment
     * @return
     */
    int updateRecruitment(Recruitment recruitment);
    
    /**
     * 招聘信息数量
     * 
     * @param query
     * @return
     */
    int getRecruitmentCount(RecruitmentQuery query);
    
    /**
     * 招聘信息一览
     * 
     * @param query
     * @return
     */
    List<RecruitmentInfo> getRecruitment(RecruitmentQuery query);
    
    /**
     * 条件查询招聘岗位
     * 
     * @param query
     * @return
     */
    List<Recruitment> getRecruitmentByCondition(RecruitmentQuery query);
}
