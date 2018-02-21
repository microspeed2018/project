package com.zjzmjr.core.service.business.talent;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.talent.Talent;
import com.zjzmjr.core.model.talent.TalentInfo;
import com.zjzmjr.core.model.talent.TalentQuery;

public interface TalentService {
 
   /**
    * 人才新增
    * 
    * @param talent
    * @return
    */
   ResultEntry<String> insertTalent(TalentInfo talent) throws ApplicationException;
   
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
