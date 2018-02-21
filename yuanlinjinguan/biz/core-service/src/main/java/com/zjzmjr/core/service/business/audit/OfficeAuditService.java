package com.zjzmjr.core.service.business.audit;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectOfficeAudit;
import com.zjzmjr.core.model.audit.OfficeAudit;


public interface OfficeAuditService {

    /**
     * 院办审核一览
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectOfficeAudit> getProjectOfficeAudit(GardenProjectAuditQuery query);
    
    
    /**
     * 插入院办审核
     * 
     * @param officeAudit
     * @return
     */
    ResultEntry<Integer> insertOfficeAudit(OfficeAudit officeAudit);
    
    /**
     * 查询审核事务最新审核不通过
     * 
     * @param query
     * @return
     */
    ResultEntry<GardenProjectOfficeAudit> getAuditBusiness(GardenProjectAuditQuery query);
    
    /**
     * 查询院办审核记录
     * 
     * @param query
     * @return
     */
    ResultRecord<OfficeAudit> getProjectOfficeAuditByCondition(GardenProjectAuditQuery query);
    
    /**
     * 院办审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectOfficeAuditCount(GardenProjectAuditQuery query);
}
