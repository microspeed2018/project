package com.zjzmjr.core.service.mapper.dao.talent;

import java.util.List;

import com.zjzmjr.core.model.talent.TalentDocument;
import com.zjzmjr.core.model.talent.TalentDocumentQuery;


public interface TalentDocumentMapper {

    /**
     * 条件查询人才附件
     * 
     */
    List<TalentDocument> getTalentDocumentByCondition(TalentDocumentQuery query);
    
    /**
     * 新增人才附件
     * 
     * @param talentJob
     * @return
     */
    int insertTalentDocument(TalentDocument talentDocument);
    
    /**
     * 修改人才附件
     * 
     * @param talentJob
     * @return
     */
    int updateTalentDocument(TalentDocument talentDocument);
    
    /**
     * 删除人才附件
     * 
     * @param id
     * @return
     */
    int deleteTalentDocument(Integer id);
}
