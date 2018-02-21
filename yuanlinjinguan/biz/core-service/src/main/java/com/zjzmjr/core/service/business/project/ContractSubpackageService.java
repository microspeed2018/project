package com.zjzmjr.core.service.business.project;

import java.util.List;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.ContractSubpackage;
import com.zjzmjr.core.model.project.ContractSubpackageInfo;
import com.zjzmjr.core.model.project.ContractSubpackageQuery;
import com.zjzmjr.core.model.project.SubpackagePayment;

/**
 * 项目分包表的业务处理层
 * 
 * @author oms
 * @version $Id: ContractSubpackageService.java, v 0.1 2017-9-3 下午5:04:18 oms Exp $
 */
public interface ContractSubpackageService {

    /**
     * 插入项目合同分包表数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertContractSubpackage(ContractSubpackage record,List<SubpackagePayment> payLst)throws ApplicationException;
    
    /**
     * 条件查询分包信息
     * 
     * @param query
     * @return
     */
    ResultRecord<ContractSubpackage> getContractSubpackageByCondition(ContractSubpackageQuery query);
    
    /**
     * 修改分包数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateContractSubpackageById(ContractSubpackage record,List<SubpackagePayment> payLst) throws ApplicationException;
    
    /**
     * 删除分包数据
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> deleteSubpackageByCondition(ContractQuery query) throws ApplicationException ;

    /**
     * 分包信息查询
     * 
     * @param query
     * @return
     */
    ResultRecord<ContractSubpackageInfo> getContractSubpackageInfo(ContractSubpackageQuery query);
}
