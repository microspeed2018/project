package com.zjzmjr.core.service.business.audit;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectFinancialAudit;

/**
 * 财务审核处理
 * 
 * @author lenovo
 * @version $Id: FinancialAuditService.java, v 0.1 2017-9-1 下午2:55:15 lenovo Exp $
 */
public interface FinancialAuditService {

    /**
     * 财务审核一览
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectFinancialAudit> getProjectFinancialAudit(GardenProjectAuditQuery query);
    
    /**
     * 财务审核修改
     * 
     * @param chiefAudit
     * @return
     */
    ResultEntry<Integer> updateFinancialAudit(GardenProjectAuditQuery query) throws  ApplicationException;
    
    /**
     * 财务审核插入
     * 
     * @param financialAudit
     * @return
     */
    ResultEntry<Integer> insertFinancialAudit(FinancialAudit financialAudit);
    
    /**
     * 条件查询财务审核
     * 
     * @param query
     * @return
     */
    ResultRecord<FinancialAudit> getProjectFinancialAuditByCondition(GardenProjectAuditQuery query);
    
    /**
     * 财务审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectFinancialAuditCount(GardenProjectAuditQuery query);
}
