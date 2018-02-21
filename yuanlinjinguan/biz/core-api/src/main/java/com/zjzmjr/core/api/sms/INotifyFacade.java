package com.zjzmjr.core.api.sms;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.sms.Notify;
import com.zjzmjr.core.model.sms.NotifyQuery;


public interface INotifyFacade {

    /**
     * 保存短信
     * 
     * @param notify
     * @return
     */
    ResultEntry<Integer> save(Notify notify);
    
    ResultPage<Notify> queryPage(NotifyQuery notifyQuery);
}
