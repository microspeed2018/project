package com.zjzmjr.core.service.mapper.dao.audit;

import java.util.List;

import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectLeaderAudit;
import com.zjzmjr.core.model.audit.LeaderAudit;


public interface LeaderAuditMapper {

    /**
     * 更新技术审核表
     * 
     * @param leaderAudit
     * @return
     */
    int updateLeaderAuditById (LeaderAudit leaderAudit);
    
    /**
     * 新增技术审核表
     * 
     * @param leaderAudit
     * @return
     */
    int insertLeaderAudit (LeaderAudit leaderAudit);
    
    /**
     * 技术审核数量
     * 
     * @return
     */
    int getProjectLeaderAuditCount(GardenProjectAuditQuery query);
    
    /**
     * 技术审核一览
     * 
     * @return
     */
    List<GardenProjectLeaderAudit> getProjectLeaderAudit(GardenProjectAuditQuery query);
}
