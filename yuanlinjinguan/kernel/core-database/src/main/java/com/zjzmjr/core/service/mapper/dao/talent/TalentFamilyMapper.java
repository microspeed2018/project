package com.zjzmjr.core.service.mapper.dao.talent;

import java.util.List;

import com.zjzmjr.core.model.talent.TalentFamily;
import com.zjzmjr.core.model.talent.TalentFamilyQuery;


public interface TalentFamilyMapper {

    /**
     * 条件查询人才家属
     * 
     */
    List<TalentFamily> getTalentFamilyByCondition(TalentFamilyQuery query);
    
    /**
     * 新增人才家属
     * 
     * @param talentFamily
     * @return
     */
    int insertTalentFamily(TalentFamily talentFamily);
    
    /**
     * 修改人才家属
     * 
     * @param talentFamily
     * @return
     */
    int updateTalentFamily(TalentFamily talentFamily);
    
    /**
     * 删除人才家属
     * 
     * @param id
     * @return
     */
    int deleteTalentFamily(Integer id);
}
