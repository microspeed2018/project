package com.zjzmjr.core.api.transaction;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.admin.Business;
import com.zjzmjr.core.model.admin.BusinessQuery;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: ITransactionFacade.java, v 0.1 2016-6-13 下午4:03:28 Administrator Exp $
 */
public interface ITransactionFacade {
    
    ResultEntry<Business> insertTransaction(Business business);

    ResultEntry<Integer> updateTransaction(Business business);

    /**
     * 根据扩展字段3更新事务表
     * 
     * @param business
     * @return
     */
    ResultEntry<Integer> updateTransactionByExtend3(Business business);

    ResultEntry<Business> getTransaction(Business business);

    ResultPage<Business> getRechargePostalList(BusinessQuery query);

    ResultPage<Business> getTransactionList(BusinessQuery query);
    /**
     * 获取最近一条失败的贷款事务
     * @param queryBusiness
     * @return
     */
    ResultEntry<Business> getFaliTransactionList(BusinessQuery queryBusiness);
}
