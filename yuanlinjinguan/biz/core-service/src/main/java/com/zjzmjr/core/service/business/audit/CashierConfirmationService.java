package com.zjzmjr.core.service.business.audit;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.audit.CashierConfirmation;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectCashierConfirmation;


public interface CashierConfirmationService {

    /**
     * 出纳确认一览
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectCashierConfirmation> getProjectCashierConfirmation(GardenProjectAuditQuery query);
    
    /**
     * 出纳确认修改
     * 
     * @param chiefAudit
     * @return
     */
    ResultEntry<Integer> updateCashierConfirmation(GardenProjectAuditQuery query) throws ApplicationException;
    
    /**
     * 出纳确认新增
     * 
     * @param cashierConfirmation
     * @return
     */
    ResultEntry<Integer> insertCashierConfirmation(CashierConfirmation cashierConfirmation);
    
    /**
     * 出纳确认数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectCashierConfirmationCount(GardenProjectAuditQuery query);
}
