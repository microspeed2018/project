package com.zjzmjr.core.service.mapper.dao.audit;

import java.util.List;

import com.zjzmjr.core.model.audit.AdministrativeSeal;
import com.zjzmjr.core.model.audit.GardenProjectAdministrativeSeal;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;

/**
 * 行政盖章表
 * 
 * @author oms
 * @version $Id: AdministrativeSealMapper.java, v 0.1 2017-9-1 下午3:05:33 oms Exp $
 */
public interface AdministrativeSealMapper {

    /**
     * 插入行政盖章表
     * 
     * @param record
     * @return
     */
    int insertAdministrativeSeal(AdministrativeSeal record);

    /**
     * 根据主键更新行政盖章表
     * 
     * @param record
     * @return
     */
    int updateAdministrativeSealById(AdministrativeSeal record);

    /**
     * 根据公司、项目及盖章类型更新行政盖章表
     * 
     * @param record
     * @return
     */
    int updateAdministrativeSealByType(AdministrativeSeal record);
    
    /**
     * 行政盖章数量
     * 
     * @param gardenProjectAuditQuery
     * @return
     */
    int getProjectAdministrativeSealCount(GardenProjectAuditQuery gardenProjectAuditQuery);
    
    /**
     * 行政盖章一览
     * 
     * @param gardenProjectAuditQuery
     * @return
     */
    List<GardenProjectAdministrativeSeal> getProjectAdministrativeSeal(GardenProjectAuditQuery gardenProjectAuditQuery);
}