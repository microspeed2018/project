/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.service.business.sms;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.sms.Notify;
import com.zjzmjr.core.model.sms.NotifyQuery;

/**
 * 
 * @author Administrator
 * @version $Id: NotifyService.java, v 0.1 2015-10-31 下午2:59:33 Administrator
 *          Exp $
 */
public interface NotifyService {

    ResultEntry<Integer> save(Notify notify);

//    ResultEntry<Notify> get(Integer id);
//
    ResultPage<Notify> queryPage(NotifyQuery notifyQuery);
//
//    Result update(Notify notify);
//
//    boolean updateStatus(Integer id, Integer status);
//
//    List<Notify> queryByStatus(Integer status);


}
