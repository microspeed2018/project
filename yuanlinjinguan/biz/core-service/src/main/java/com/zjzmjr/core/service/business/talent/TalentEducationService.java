package com.zjzmjr.core.service.business.talent;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.talent.TalentEducation;
import com.zjzmjr.core.model.talent.TalentEducationQuery;


public interface TalentEducationService {

    /**
     * 条件查询人才学历
     * 
     */
    ResultRecord<TalentEducation> getTalentEducationByCondition(TalentEducationQuery query);
    
    /**
     * 新增人才学历
     * 
     * @param talentEducation
     * @return
     */
    ResultEntry<Integer> insertTalentEducation(TalentEducation talentEducation);
    
    /**
     * 修改人才学历
     * 
     * @param talentEducation
     * @return
     */
    ResultEntry<Integer> updateTalentEducation(TalentEducation talentEducation);
    
    /**
     * 删除人才学历
     * 
     * @param id
     * @return
     */
    ResultEntry<Integer> deleteTalentEducation(Integer id);
}
