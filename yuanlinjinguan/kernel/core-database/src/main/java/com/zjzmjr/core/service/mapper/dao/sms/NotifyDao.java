/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.service.mapper.dao.sms;

import java.util.List;

import com.zjzmjr.core.model.sms.Notify;
import com.zjzmjr.core.model.sms.NotifyQuery;

/**
 * 
 * @author Administrator
 * @version $Id: NotifyDao.java, v 0.1 2015-10-31 下午2:56:55 Administrator Exp $
 */
public interface NotifyDao {

    int save(Notify notify);

    Notify get(Integer id);
    
    List<Notify> queryPage(NotifyQuery notifyQuery);
    
    int queryPageCount(NotifyQuery notifyQuery);
    
    int update(Notify notify);
    
    List<Notify> queryByStatus(Integer status);
    
    /**
     * 还款提醒前查询发送记录
     * 
     * @param query
     * @return
     */
    Notify getNotifyByDestination(NotifyQuery query);
}
