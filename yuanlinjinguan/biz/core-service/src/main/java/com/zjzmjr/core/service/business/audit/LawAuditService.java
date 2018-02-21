package com.zjzmjr.core.service.business.audit;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectLawAudit;
import com.zjzmjr.core.model.audit.LawAudit;


public interface LawAuditService {

    /**
     * 更新法务审核表
     * 
     * @param leaderAudit
     * @return
     */
    ResultEntry<Integer> updateLawAudit (GardenProjectAuditQuery query) throws ApplicationException;
    
    /**
     * 新增法务审核表
     * 
     * @param leaderAudit
     * @return
     */
    ResultEntry<Integer> insertLawAudit (LawAudit lawAudit);
    
    /**
     * 法务审核一览
     * 
     * @return
     */
    ResultPage<GardenProjectLawAudit> getProjectLawAudit(GardenProjectAuditQuery query);
    
    /**
     * 法务审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectLawAuditCount(GardenProjectAuditQuery query);
}
