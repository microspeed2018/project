package com.zjzmjr.core.api.audit;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectCashierConfirmation;
import com.zjzmjr.core.model.audit.GardenProjectChiefAudit;
import com.zjzmjr.core.model.audit.GardenProjectFinancialAudit;
import com.zjzmjr.core.model.audit.GardenProjectLawAudit;
import com.zjzmjr.core.model.audit.GardenProjectLeaderAudit;
import com.zjzmjr.core.model.audit.GardenProjectManageAudit;
import com.zjzmjr.core.model.audit.GardenProjectOfficeAudit;
import com.zjzmjr.core.model.audit.LeaderAudit;


public interface IGardenProjectAuditFacade {

    /**
     * 院办审核一览
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectOfficeAudit> getProjectOfficeAudit(GardenProjectAuditQuery query);
    
    /**
     * 更新院办审核
     * 
     * @param officeAudit
     * @return
     */
    ResultEntry<Integer> updateOfficeAudit(GardenProjectAuditQuery query);
    
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
    ResultEntry<Integer> updateManageAudit(GardenProjectAuditQuery query);
    
    /**
     * 总工审核一览
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectChiefAudit> getProjectChiefAudit(GardenProjectAuditQuery query);
    
    /**
     * 更新总工审核
     * 
     * @param officeAudit
     * @return
     */
    ResultEntry<Integer> updateChiefAudit(GardenProjectAuditQuery query);
    
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
    ResultEntry<Integer> updateFinancialAudit(GardenProjectAuditQuery query);

    /**
     * 财务审核插入数据
     * 
     * @param financialAudit
     * @return
     */
    ResultEntry<Integer> insertFinancialAudit(FinancialAudit financialAudit);
    
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
    ResultEntry<Integer> updateCashierConfirmation(GardenProjectAuditQuery query);
    
    /**
     * 技术审核一览
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectLeaderAudit> getProjectLeaderAudit(GardenProjectAuditQuery query);
        
    /**
     * 更新技术审核
     * 
     * @param officeAudit
     * @return
     */
    ResultEntry<Integer> updateLeaderAudit(GardenProjectAuditQuery query);
    
    /**
     * 技术审核插入数据
     * 
     * @param financialAudit
     * @return
     */
    ResultEntry<Integer> insertLeaderAudit(LeaderAudit leaderAudit);
    
    /**
     * 更新法务审核
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> updateLawAudit (GardenProjectAuditQuery query);
    
    /**
     * 法务审核一览
     * 
     * @return
     */
    ResultPage<GardenProjectLawAudit> getProjectLawAudit(GardenProjectAuditQuery query);
    
    /**
     * 查询审核事务最新审核不通过
     * 
     * @param query
     * @return
     */
    ResultEntry<GardenProjectOfficeAudit> getAuditBusiness(GardenProjectAuditQuery query);
    
    /**
     * 条件查询财务审核
     * 
     * @param query
     * @return
     */
    ResultRecord<FinancialAudit> getProjectFinancialAuditByCondition(GardenProjectAuditQuery query);
 
    /**
     * 出纳确认数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectCashierConfirmationCount(GardenProjectAuditQuery query);
    
    /**
     * 总工审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectChiefAuditCount(GardenProjectAuditQuery query);
    
    /**
     * 财务审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectFinancialAuditCount(GardenProjectAuditQuery query);
    
    /**
     * 法务审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectLawAuditCount(GardenProjectAuditQuery query);
    
    /**
     * 技术审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectLeaderAuditCount(GardenProjectAuditQuery query);
    
    /**
     * 经营审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectManageAuditCount(GardenProjectAuditQuery query);
    
    /**
     * 院办审核数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectOfficeAuditCount(GardenProjectAuditQuery query);
    
}
