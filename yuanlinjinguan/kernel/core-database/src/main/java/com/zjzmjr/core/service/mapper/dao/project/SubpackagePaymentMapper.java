package com.zjzmjr.core.service.mapper.dao.project;

import java.util.List;

import com.zjzmjr.core.model.project.ContractSubpackageQuery;
import com.zjzmjr.core.model.project.SubpackagePayment;
import com.zjzmjr.core.model.project.SubpackagePaymentInfo;

/**
 * 分包付款表信息
 * 
 * @author oms
 * @version $Id: SubpackagePaymentMapper.java, v 0.1 2017-9-28 上午10:24:18 oms
 *          Exp $
 */
public interface SubpackagePaymentMapper {

    /**
     * 根据条件删除信息
     * 
     * @param id
     * @return
     */
    int deleteSubpackagePaymentByCondition(ContractSubpackageQuery query);

    /**
     * 插入分包付款表信息
     * 
     * @param record
     * @return
     */
    int insertSubpackagePayment(SubpackagePayment record);

    /**
     * 根据条件查询分包付款表信息件数
     * 
     * @param query
     * @return
     */
    int getSubpackagePaymentCount(ContractSubpackageQuery query);
    
    /**
     * 根据条件查询分包付款表信息
     * 
     * @param query
     * @return
     */
    List<SubpackagePaymentInfo> getSubpackagePaymentByCondition(ContractSubpackageQuery query);

    /**
     * 根据主键修改分包付款表信息
     * 
     * @param record
     * @return
     */
    int updateSubpackagePaymentById(SubpackagePayment record);

}