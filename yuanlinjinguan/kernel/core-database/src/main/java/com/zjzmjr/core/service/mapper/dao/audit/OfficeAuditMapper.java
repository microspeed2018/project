package com.zjzmjr.core.service.mapper.dao.audit;

import java.util.List;

import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectOfficeAudit;
import com.zjzmjr.core.model.audit.OfficeAudit;

/**
 * 院办审核表
 * 
 * @author lenovo
 * @version $Id: OfficeAuditMapper.java, v 0.1 2017-8-31 下午7:27:27 lenovo Exp $
 */
public interface OfficeAuditMapper {

    /**
     * 院办审核数量
     * 
     * @param query
     * @return
     */
    int getProjectOfficeAuditCount(GardenProjectAuditQuery query);
    
    /**
     * 院办审核一览
     * 
     * @param query
     * @return
     */
    List <GardenProjectOfficeAudit> getProjectOfficeAudit(GardenProjectAuditQuery query);
    
    /**
     * 更新院办审核
     * 
     * @param officeAudit
     * @return
     */
    int updateOfficeAuditById(OfficeAudit officeAudit);
    
    /**
     * 插入院办审核表
     * 
     * @param officeAudit
     * @return
     */
    int insertOfficeAudit(OfficeAudit officeAudit);
    
    /**
     * 查询审核事务最新审核不通过
     * 
     * @param query
     * @return
     */
    GardenProjectOfficeAudit getAuditBusiness(GardenProjectAuditQuery query);
    
    /**
     *  查询院办审核事务
     * 
     * @param query
     * @return
     */
    List<OfficeAudit> getProjectOfficeAuditByCondition(GardenProjectAuditQuery query);
}
