package com.zjzmjr.core.service.business.audit;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectManageAudit;
import com.zjzmjr.core.model.audit.ManageAudit;


public interface ManageAuditService {

    /**
     * 经营审核一览
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectManageAudit> getProjectManageAudit(GardenProjectAuditQuery query);
    
    
    /**
     * 更新经营审核
     * 
     * @param officeAudit
     * @return
     */
    ResultEntry<Integer> updateManageAudit(GardenProjectAuditQuery query)  throws ApplicationException;
    
    /**
     * 插入经营审核
     * 
     * @param officeAudit
     * @return
     */
    ResultEntry<Integer> insertManageAudit(ManageAudit officeAudit);
    
    /**
     * 经营审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectManageAuditCount(GardenProjectAuditQuery query);
}
