package com.zjzmjr.core.service.mapper.dao.talent;

import java.util.List;

import com.zjzmjr.core.model.talent.TalentEducation;
import com.zjzmjr.core.model.talent.TalentEducationQuery;


public interface TalentEducationMapper {

    /**
     * 条件查询人才学历
     * 
     */
    List<TalentEducation> getTalentEducationByCondition(TalentEducationQuery query);
    
    /**
     * 新增人才学历
     * 
     * @param talentEducation
     * @return
     */
    int insertTalentEducation(TalentEducation talentEducation);
    
    /**
     * 修改人才学历
     * 
     * @param talentEducation
     * @return
     */
    int updateTalentEducation(TalentEducation talentEducation);
    
    /**
     * 删除人才学历
     * 
     * @param id
     * @return
     */
    int deleteTalentEducation(Integer id);
}
