package com.zjzmjr.core.service.business.talent;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.talent.TalentDocument;
import com.zjzmjr.core.model.talent.TalentDocumentQuery;


public interface TalentDocumentService {

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
}
