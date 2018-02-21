package com.zjzmjr.core.api.talent;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.talent.Talent;
import com.zjzmjr.core.model.talent.TalentDocument;
import com.zjzmjr.core.model.talent.TalentDocumentQuery;
import com.zjzmjr.core.model.talent.TalentEducation;
import com.zjzmjr.core.model.talent.TalentEducationQuery;
import com.zjzmjr.core.model.talent.TalentFamily;
import com.zjzmjr.core.model.talent.TalentFamilyQuery;
import com.zjzmjr.core.model.talent.TalentInfo;
import com.zjzmjr.core.model.talent.TalentJob;
import com.zjzmjr.core.model.talent.TalentJobQuery;
import com.zjzmjr.core.model.talent.TalentQuery;


public interface ITalentFacade {

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
    
    /**
     * 条件查询人才附件
     * 
     */
    ResultRecord<TalentDocument> getTalentDocumentByCondition(TalentDocumentQuery query);
    
    /**
     * 新增人才附件
     * 
     * @param talentFamily
     * @return
     */
    ResultEntry<Integer> insertTalentDocument(TalentDocument talentDocument);
    
    /**
     * 修改人才附件
     * 
     * @param talentFamily
     * @return
     */
    ResultEntry<Integer> updateTalentDocument(TalentDocument talentDocument);
    
    /**
     * 删除人才附件
     * 
     * @param id
     * @return
     */
    ResultEntry<Integer> deleteTalentDocument(Integer id);
    
    /**
     * 人才新增
     * 
     * @param talent
     * @return
     */
    ResultEntry<String> insertTalent(TalentInfo talent);
    
    /**
     * 人才修改
     * 
     * @param talent
     * @return
     */
    ResultEntry<Integer> updateTalent(Talent talent);
    
    /**
     * 人才一览
     * 
     * @param talentQuery
     * @return
     */
    ResultRecord<TalentInfo> getTalentInfo(TalentQuery talentQuery);
    
    /**
     * 条件查询人才信息
     * 
     * @param query
     * @return
     */
    ResultPage<TalentInfo> getTalentByCondition(TalentQuery query);
}
