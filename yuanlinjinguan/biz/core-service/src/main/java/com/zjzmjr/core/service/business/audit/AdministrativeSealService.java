package com.zjzmjr.core.service.business.audit;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.audit.AdministrativeSeal;
import com.zjzmjr.core.model.audit.GardenProjectAdministrativeSeal;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.project.GardenProject;

/**
 * 行政盖章表业务处理层
 * 
 * @author oms
 * @version $Id: AdministrativeSealService.java, v 0.1 2017-9-1 下午3:20:46 oms
 *          Exp $
 */
public interface AdministrativeSealService {

    /**
     * 插入行政盖章表
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertAdministrativeSeal(AdministrativeSeal record);

    /**
     * 根据主键更新行政盖章表
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateAdministrativeSealById(AdministrativeSeal record);

    /**
     * 根据公司、项目及盖章类型更新行政盖章表
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateAdministrativeSealByType(AdministrativeSeal record);

    /**
     * 根据公司、项目及盖章类型更新行政盖章表，并且把项目的阶段更新
     * 
     * @param project
     * @param seal
     * @return
     */
    ResultEntry<Integer> updateAdministrativeSealByType(GardenProject project, AdministrativeSeal seal) throws ApplicationException;
    
    /**
     * 行政盖章一览
     * 
     * @param gardenProjectAuditQuery
     * @return
     */
    ResultPage<GardenProjectAdministrativeSeal> getProjectAdministrativeSeal(GardenProjectAuditQuery query);
    
    
    /**
     * 更新行政盖章表，并且把项目的阶段更新
     * 
     * @param project
     * @param seal
     * @return
     */
    ResultEntry<Integer> updateAdministrativeSealAndProjectStep(GardenProjectAuditQuery query) throws ApplicationException;
    
    /**
     * 行政盖章数量
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getProjectAdministrativeSealCount(GardenProjectAuditQuery query);
}
