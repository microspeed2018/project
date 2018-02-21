/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.provider.sms;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.sms.INotifyFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.sms.Notify;
import com.zjzmjr.core.model.sms.NotifyQuery;
import com.zjzmjr.core.service.business.sms.NotifyService;

/**
 * 
 * @author Administrator
 * @version $Id: NotifyFacadeImpl.java, v 0.1 2015-10-31 下午4:36:13 Administrator
 *          Exp $
 */
@Service("notifyFacade")
public class NotifyFacadeImpl implements INotifyFacade {

    private static final Logger logger = LoggerFactory.getLogger(NotifyFacadeImpl.class);

    @Resource(name = "notifyService")
    private NotifyService notifyService;

    /**
     * 
     * @see com.zjzmjr.core.api.sms.INotifyFacade#save(com.zjzmjr.core.model.sms.Notify)
     */
    @Override
    public ResultEntry<Integer> save(Notify notify) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = notifyService.save(notify);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.sms.INotifyFacade#queryPage(com.zjzmjr.core.model.sms.NotifyQuery)
     */
    @Override
    public ResultPage<Notify> queryPage(NotifyQuery notifyQuery) {
        ResultPage<Notify> result = new ResultPage<Notify>();
        try {
            result = notifyService.queryPage(notifyQuery);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
}
