package com.zjzmjr.core.service.mapper.dao.talent;

import java.util.List;

import com.zjzmjr.core.model.talent.TalentJob;
import com.zjzmjr.core.model.talent.TalentJobQuery;


public interface TalentJobMapper {

    /**
     * 条件查询人才工作
     * 
     */
    List<TalentJob> getTalentJobByCondition(TalentJobQuery query);
    
    /**
     * 新增人才工作
     * 
     * @param talentJob
     * @return
     */
    int insertTalentJob(TalentJob talentJob);
    
    /**
     * 修改人才工作
     * 
     * @param talentJob
     * @return
     */
    int updateTalentJob(TalentJob talentJob);
    
    /**
     * 删除人才工作
     * 
     * @param id
     * @return
     */
    int deleteTalentJob(Integer id);
}
