package com.zjzmjr.core.service.mapper.dao.audit;

import java.util.List;

import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectManageAudit;
import com.zjzmjr.core.model.audit.ManageAudit;

/**
 * 经营审核表
 * 
 * @author lenovo
 * @version $Id: ManageAuditMapper.java, v 0.1 2017-8-31 下午7:27:11 lenovo Exp $
 */
public interface ManageAuditMapper {

    /**
     * 经营审核数量
     * 
     * @param query
     * @return
     */
    int getProjectManageAuditCount(GardenProjectAuditQuery query);
    
    /**
     * 经营审核一览
     * 
     * @param query
     * @return
     */
    List<GardenProjectManageAudit> getProjectManageAudit(GardenProjectAuditQuery query);
    
    /**
     * 经营审核新增
     * 
     * @param manageAudit
     * @return
     */
    int insertManageAudit(ManageAudit manageAudit);
    
    /**
     * 经营审核修改
     * 
     * @param manageAudit
     * @return
     */
    int updateManageAuditById(ManageAudit manageAudit);
}
