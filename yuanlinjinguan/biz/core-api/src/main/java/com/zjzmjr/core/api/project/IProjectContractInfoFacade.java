package com.zjzmjr.core.api.project;

import java.util.List;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.ContractSubpackage;
import com.zjzmjr.core.model.project.ContractSubpackageInfo;
import com.zjzmjr.core.model.project.ContractSubpackageQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.SubpackagePayment;
import com.zjzmjr.core.model.project.SubpackagePaymentInfo;

/**
 * 项目表、合同表、分包表及与合同有关的业务处理接口
 * 
 * @author oms
 * @version $Id: IProjectContractInfoFacade.java, v 0.1 2017-9-3 下午6:09:11 oms
 *          Exp $
 */
public interface IProjectContractInfoFacade {

    /**
     * 插入项目合同分包表及更新项目表中的信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertProjectSubpackage(GardenProject project, OfficeAudit officeAudit);

    /**
     * 删除项目分包表
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> deleteSubpackageByCondition(ContractQuery query);
    
    /**
     * 修改项目分包表
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> updateContractSubpackageById(ContractSubpackage record, List<SubpackagePayment> payLst);

    /**
     * 根据条件查询分包付款表信息
     * 
     * @param query
     * @return
     */
    ResultPage<SubpackagePaymentInfo> getSubpackagePaymentByCondition(ContractSubpackageQuery query);

    /**
     * 分包信息查询
     * 
     * @param query
     * @return
     */
    ResultRecord<ContractSubpackageInfo> getContractSubpackageInfo(ContractSubpackageQuery query);
}
