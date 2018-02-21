package com.zjzmjr.core.service.mapper.dao.audit;

import java.util.List;

import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectFinancialAudit;


public interface FinancialAuditMapper {


    /**
     * 财务审核数量
     * 
     * @param query
     * @return
     */
    int getProjectFinancialAuditCount(GardenProjectAuditQuery query);
    
    /**
     * 财务审核一览
     * 
     * @param query
     * @return
     */
    List<GardenProjectFinancialAudit> getProjectFinancialAudit(GardenProjectAuditQuery query);
    
    /**
     * 财务审核修改
     * 
     * @param manageAudit
     * @return
     */
    int updateFinancialAuditById(FinancialAudit financialAudit);
    
    /**
     * 财务审核插入
     * 
     * @param financialAudit
     * @return
     */
    int insertFinancialAudit(FinancialAudit financialAudit);
    
    /**
     * 条件查询财务审核
     * 
     * @param query
     * @return
     */
    List<FinancialAudit> getProjectFinancialAuditByCondition(GardenProjectAuditQuery query);
}
