package com.zjzmjr.core.service.business.project;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.project.ContractSubpackageQuery;
import com.zjzmjr.core.model.project.SubpackagePayment;
import com.zjzmjr.core.model.project.SubpackagePaymentInfo;

/**
 * 分包付款表业务信息处理
 * 
 * @author oms
 * @version $Id: SubpackagePaymentService.java, v 0.1 2017-9-28 下午2:02:05 oms Exp $
 */
public interface SubpackagePaymentService {

    /**
     * 根据条件查询分包付款表信息
     * 
     * @param query
     * @return
     */
    ResultPage<SubpackagePaymentInfo> getSubpackagePaymentByCondition(ContractSubpackageQuery query);

    /**
     * 插入分包付款表信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertSubpackagePayment(SubpackagePayment record);

    /**
     * 根据主键修改分包付款表信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateSubpackagePaymentById(SubpackagePayment record);

}
