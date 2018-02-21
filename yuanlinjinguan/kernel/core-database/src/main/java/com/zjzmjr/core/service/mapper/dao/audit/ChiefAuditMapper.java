package com.zjzmjr.core.service.mapper.dao.audit;

import java.util.List;

import com.zjzmjr.core.model.audit.ChiefAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectChiefAudit;


public interface ChiefAuditMapper {

    /**
     * 总工审核数量
     * 
     * @param query
     * @return
     */
    int getProjectChiefAuditCount(GardenProjectAuditQuery query);
    
    /**
     * 总工审核一览
     * 
     * @param query
     * @return
     */
    List<GardenProjectChiefAudit> getProjectChiefAudit(GardenProjectAuditQuery query);
    
    /**
     * 总工审核修改
     * 
     * @param chiefAudit
     * @return
     */
    int updateChiefAuditById(ChiefAudit chiefAudit);
    
    /**
     * 总工审核插入
     * 
     * @param chiefAudit
     * @return
     */
    int insertChiefAudit(ChiefAudit chiefAudit);
    
}
