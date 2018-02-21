package com.zjzmjr.core.service.business.transaction;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.admin.Business;
import com.zjzmjr.core.model.admin.BusinessQuery;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: TransactionService.java, v 0.1 2016-6-13 下午4:12:24 Administrator Exp $
 */
public interface TransactionService {
    
    /**
     * 
     * 
     * @param business
     * @return
     */
    ResultEntry<Business> insertTransaction(Business business);

    /**
     * 
     * 
     * @param business
     * @return
     */
    ResultEntry<Integer> updateTransaction(Business business);

    /**
     * 根据扩展字段3更新事务表
     * 
     * @param business
     * @return
     */
    ResultEntry<Integer> updateTransactionByExtend3(Business business);

    /**
     * 
     * 
     * @param business
     * @return
     */
    ResultEntry<Business> getTransaction(Business business);

    /**
     * 
     * 
     * @param query
     * @return
     */
    ResultPage<Business> getRechargePostalList(BusinessQuery query);

    /**
     * 
     * 
     * @param query
     * @return
     */
    ResultPage<Business> getTransactionList(BusinessQuery query);
    /**
     * 获取最近一条的失败的贷款事务
     * @param queryBusiness
     * @return
     */
    ResultEntry<Business> getFaliTransactionList(BusinessQuery queryBusiness);
}
