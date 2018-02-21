package com.zjzmjr.core.service.mapper.dao.audit;

import java.util.List;

import com.zjzmjr.core.model.audit.CashierConfirmation;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectCashierConfirmation;


public interface CashierConfirmationMapper {

    /**
     * 出纳确认数量
     * 
     * @param query
     * @return
     */
    int getProjectCashierConfirmationCount(GardenProjectAuditQuery query);
    
    /**
     * 出纳确认一览
     * 
     * @param query
     * @return
     */
    List<GardenProjectCashierConfirmation> getProjectCashierConfirmation(GardenProjectAuditQuery query);
    
    /**
     * 出纳确认修改
     * 
     * @param chiefAudit
     * @return
     */
    int updateCashierConfirmationById(CashierConfirmation cashierConfirmation);
    
    /**
     * 插入出纳确认
     * 
     * @param cashierConfirmation
     * @return
     */
    int insertCashierConfirmation(CashierConfirmation cashierConfirmation);
    
}
