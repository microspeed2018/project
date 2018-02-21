package com.zjzmjr.core.service.business.talent;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.talent.TalentJob;
import com.zjzmjr.core.model.talent.TalentJobQuery;


public interface TalentJobService {
    
    /**
     * 条件查询人才工作
     * 
     */
    ResultRecord<TalentJob> getTalentJobByCondition(TalentJobQuery query);
    
    /**
     * 新增人才工作
     * 
     * @param talentJob
     * @return
     */
    ResultEntry<Integer> insertTalentJob(TalentJob talentJob);
    
    /**
     * 修改人才工作
     * 
     * @param talentJob
     * @return
     */
    ResultEntry<Integer> updateTalentJob(TalentJob talentJob);
    
    /**
     * 删除人才工作
     * 
     * @param id
     * @return
     */
    ResultEntry<Integer> deleteTalentJob(Integer id);
}
