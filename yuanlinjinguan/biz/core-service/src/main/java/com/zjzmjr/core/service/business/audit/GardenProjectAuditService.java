package com.zjzmjr.core.service.business.audit;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;


/**
 * 业务操作审核处理
 * 
 * @author lenovo
 * @version $Id: GardenProjectAuditService.java, v 0.1 2017-9-3 下午5:51:20 lenovo Exp $
 */
public interface GardenProjectAuditService {

    /**
     * 更新院办审核
     * 
     * @param officeAudit
     * @return
     */
    ResultEntry<Integer> updateOfficeAudit(GardenProjectAuditQuery query) throws ApplicationException;
}
