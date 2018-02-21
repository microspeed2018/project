package com.zjzmjr.core.service.business.audit;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.audit.ChiefAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectChiefAudit;


public interface ChiefAuditService {

    /**
     * 总工审核一览
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectChiefAudit> getProjectChiefAudit(GardenProjectAuditQuery query);
    
    /**
     * 总工审核修改
     * 
     * @param chiefAudit
     * @return
     */
    ResultEntry<Integer> updateChiefAudit(GardenProjectAuditQuery query)throws ApplicationException ;
    

    /**
     * 总工审核插入
     * 
     * @param chiefAudit
     * @return
     */
    ResultEntry<Integer> insertChiefAudit(ChiefAudit chiefAudit);
    
    /**
     * 总工审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectChiefAuditCount(GardenProjectAuditQuery query);
    
}
