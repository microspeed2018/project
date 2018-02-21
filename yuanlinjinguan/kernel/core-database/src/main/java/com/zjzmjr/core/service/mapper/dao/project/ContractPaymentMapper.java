package com.zjzmjr.core.service.mapper.dao.project;

import java.util.List;

import com.zjzmjr.core.model.project.ContractPayment;
import com.zjzmjr.core.model.project.ContractPaymentInfo;
import com.zjzmjr.core.model.project.ContractPaymentQuery;
import com.zjzmjr.core.model.project.ContractQuery;

/**
 * 合同付款表
 * 
 * @author oms
 * @version $Id: ContractPaymentMapper.java, v 0.1 2017-8-23 下午10:03:42 oms Exp
 *          $
 */
public interface ContractPaymentMapper {

    int deleteContractPaymentByCondition(ContractQuery query);

    int insertContractPayment(ContractPayment record);

    ContractPayment getContractPaymentById(Integer id);

    int updateContractPaymentById(ContractPayment record);

    /**
     * 根据合同付款表信息查询出开发票信息
     * 
     * @param query
     * @return
     */
    int getProjectContractPaymentCount(ContractPaymentQuery query);

    /**
     * 根据合同付款表信息查询出开发票信息
     * 
     * @param query
     * @return
     */
    List<ContractPaymentInfo> getProjectContractPaymentInfo(ContractPaymentQuery query);
    
    /**
     * 条件更新合同付款表
     * 
     * @param record
     * @return
     */
    int updateContractPaymentByCondition(ContractPayment record);
    
    /**
     * 条件查询合同付款内容
     * 
     * @param query
     * @return
     */
    ContractPayment getContractPaymentByCondition(ContractPaymentQuery query);

}