package com.zjzmjr.core.api.audit;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectManageAudit;
import com.zjzmjr.core.model.audit.ManageAudit;

/**
 * 经营部门审核对外接口
 * 
 * @author oms
 * @version $Id: IManageAuditFacade.java, v 0.1 2017-8-31 下午6:36:56 oms Exp $
 */
public interface IManageAuditFacade {

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
    ResultEntry<Integer> updateManageAuditById(ManageAudit officeAudit);
    
    /**
     * 插入经营审核
     * 
     * @param officeAudit
     * @return
     */
    ResultEntry<Integer> insertManageAudit(ManageAudit officeAudit);
    
}
