package com.zjzmjr.core.service.business.project;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.project.GardenProject;

/**
 * 项目表、合同表、分包表及与合同有关的业务处理层
 * 
 * @author oms
 * @version $Id: ProjectContractInfoService.java, v 0.1 2017-9-3 下午5:43:52 oms Exp $
 */
public interface ProjectContractInfoService {

    /**
     * 插入项目合同分包表及更新项目表中的信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertProjectSubpackage(GardenProject project, OfficeAudit officeAudit) throws ApplicationException;

}
