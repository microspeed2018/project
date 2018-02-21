package com.zjzmjr.core.service.business.transaction;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.admin.AdminBusinessQuery;

/**
 * 管理员事物Service层
 * 
 * @author Administrator
 * @version $Id: AdminTransactionService.java, v 0.1 2016-10-9 下午2:41:17 Administrator Exp $
 */
public interface AdminTransactionService {

    /**
     * 新增管理员事物
     * @param adminBusiness
     * @return
     */
    ResultEntry<AdminBusiness> insertAdminTransaction(AdminBusiness adminBusiness);

    /**
     * 更新管理员事物
     * @param adminBusiness
     * @return
     */
    ResultEntry<Integer> updateAdminTransaction(AdminBusiness adminBusiness);

    /**
     * 根据管理员名称，事物类型，获取管理员事物列表
     * 
     * @param query
     * @return
     */
    ResultPage<AdminBusiness> getAdminTransactionList(AdminBusinessQuery query);
}
