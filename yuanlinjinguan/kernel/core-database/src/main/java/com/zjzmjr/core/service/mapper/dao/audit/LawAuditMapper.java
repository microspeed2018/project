package com.zjzmjr.core.service.mapper.dao.audit;

import java.util.List;

import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectLawAudit;
import com.zjzmjr.core.model.audit.LawAudit;


public interface LawAuditMapper {
    /**
     * 更新法务审核表
     * 
     * @param leaderAudit
     * @return
     */
    int updateLawAuditById (LawAudit lawAudit);
    
    /**
     * 新增法务审核表
     * 
     * @param leaderAudit
     * @return
     */
    int insertLawAudit (LawAudit lawAudit);
    
    /**
     * 法务审核数量
     * 
     * @return
     */
    int getProjectLawAuditCount(GardenProjectAuditQuery query);
    
    /**
     * 法务审核一览
     * 
     * @return
     */
    List<GardenProjectLawAudit> getProjectLawAudit(GardenProjectAuditQuery query);
}
