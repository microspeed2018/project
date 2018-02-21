package com.zjzmjr.core.service.business.audit;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectLeaderAudit;
import com.zjzmjr.core.model.audit.LeaderAudit;


public interface LeaderAuditService {
    /**
     * 技术审核一览
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectLeaderAudit> getProjectLeaderAudit(GardenProjectAuditQuery query);
    
    
    /**
     * 更新技术审核
     * 
     * @param officeAudit
     * @return
     */
    ResultEntry<Integer> updateLeaderAudit(GardenProjectAuditQuery query)  throws ApplicationException;
    
    /**
     * 插入技术审核
     * 
     * @param officeAudit
     * @return
     */
    ResultEntry<Integer> insertLeaderAudit(LeaderAudit leaderAudit);
    
    /**
     * 技术审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectLeaderAuditCount(GardenProjectAuditQuery query);
}