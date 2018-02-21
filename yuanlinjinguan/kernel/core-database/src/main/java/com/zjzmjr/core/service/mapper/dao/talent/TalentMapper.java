package com.zjzmjr.core.service.mapper.dao.talent;

import java.util.List;

import com.zjzmjr.core.model.talent.Talent;
import com.zjzmjr.core.model.talent.TalentInfo;
import com.zjzmjr.core.model.talent.TalentQuery;


public interface TalentMapper {

    /**
     * 人才信息数量
     * 
     * @param talentQuery
     * @return
     */
    int getTalentInfoCount(TalentQuery talentQuery);
    
    /**
     * 人才信息一览
     * 
     * @param talentQuery
     * @return
     */
    List<TalentInfo> getTalentInfo(TalentQuery talentQuery);
    
    /**
     * 人才一览新增
     * 
     * @param talent
     * @return
     */
    int insertTalent(Talent talent);
    
    /**
     * 人才一览修改
     * 
     * @param talent
     * @return
     */
    int updateTalent(Talent talent);
    
    /**
     * 条件查询人才信息
     * 
     * @param query
     * @return
     */
    List<TalentInfo> getTalentByCondition(TalentQuery query);
}
