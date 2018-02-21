package com.zjzmjr.core.service.business.talent;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.talent.TalentFamily;
import com.zjzmjr.core.model.talent.TalentFamilyQuery;


public interface TalentFamilyService {

    /**
     * 条件查询人才家属
     * 
     */
    ResultRecord<TalentFamily> getTalentFamilyByCondition(TalentFamilyQuery query);
    
    /**
     * 新增人才家属
     * 
     * @param talentFamily
     * @return
     */
    ResultEntry<Integer> insertTalentFamily(TalentFamily talentFamily);
    
    /**
     * 修改人才家属
     * 
     * @param talentFamily
     * @return
     */
    ResultEntry<Integer> updateTalentFamily(TalentFamily talentFamily);
    
    /**
     * 删除人才家属
     * 
     * @param id
     * @return
     */
    ResultEntry<Integer> deleteTalentFamily(Integer id);
}
