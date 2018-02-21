/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.service.business.sms.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.sms.Notify;
import com.zjzmjr.core.model.sms.NotifyQuery;
import com.zjzmjr.core.service.business.sms.NotifyService;
import com.zjzmjr.core.service.mapper.dao.sms.NotifyDao;

/**
 * 
 * @author Administrator
 * @version $Id: NotifyServiceImpl.java, v 0.1 2015-10-31 下午2:59:56
 *          Administrator Exp $
 */
@Service("notifyService")
public class NotifyServiceImpl implements NotifyService {

    private static final Logger logger = LoggerFactory.getLogger(NotifyServiceImpl.class);

    @Autowired
    private NotifyDao notifyDao;

    /**
     * @see com.yztz.finance.bussiness.service.SNS.NotifyService#save(com.yztz.finance.model.SMS.Notify)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> save(Notify notify) {
        logger.debug("save入参:{}",notify);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(notify)) {
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setSuccess(false);
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), notify.toString());
            logger.debug("save出参:{}",result);
            return result;
        }
        int cnt = notifyDao.save(notify);
        if (cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":短信插入失败");
        }
        return result;
    }

//    /**
//     * 更新失败的短信
//     */
//    private void executeFailQueue() {
//        Integer notifyId = null;
//        while (!ModifySMSStatusFailQueue.getSingle().isEmpty()) {
//            notifyId = ModifySMSStatusFailQueue.getSingle().poll();
//            boolean updateReuslt = updateStatus(notifyId, NotifyStatusEnum.COMPLETE.getValue());
//            if (!updateReuslt) {
//                ModifySMSStatusFailQueue.getSingle().add(notifyId);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        Notify notify = new Notify();
//        notify.setCause("aaa");
//        notify.setId(1212);
//
//        Notify n = new Notify();
//        BeanUtils.copyProperties(notify, n);
//        System.out.println(n);
//    }
//
//    /**
//     * @see com.yztz.finance.bussiness.service.SNS.NotifyService#get(java.lang.Integer)
//     */
//    @Override
//    public ResultEntry<Notify> get(Integer id) {
//        logger.debug("get入参:{}",id);
//        ResultEntry<Notify> result = new ResultEntry<Notify>();
//        if (Util.isNull(id)) {
//            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
//            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
//            result.setSuccess(false);
//            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), id.toString());
//            logger.debug("get出参:{}",result);
//            return result;
//        }
//        Notify notify = null;
//        try {
//            notify = notifyDao.get(id);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
//            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
//            result.setSuccess(false);
//            logger.debug("get出参:{}",result);
//            return result;
//        }
//
//        result.setT(notify);
//        logger.debug("get出参:{}",result);
//        return result;
//    }
//
    /**
     * 
     * @see com.zjzmjr.core.service.business.sms.NotifyService#queryPage(com.zjzmjr.core.model.sms.NotifyQuery)
     */
    @Override
    public ResultPage<Notify> queryPage(NotifyQuery notifyQuery) {
        logger.debug("queryPage入参:{}",notifyQuery);
        ResultPage<Notify> result = new ResultPage<Notify>();
        // PageBean page = new PageBean(pageSize, pageNo);
        if (null == notifyQuery || null == notifyQuery.getPageBean()) {
            // page.setTotalResults(0);
            // result.setList(new ArrayList<Notify>());
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), notifyQuery.toString());
            // result.setPage(page);
            logger.debug("queryPage出参:{}",result);
            return result;
        }

        int totalResults = notifyDao.queryPageCount(notifyQuery);
        if (totalResults > 0){
            List<Notify> list = notifyDao.queryPage(notifyQuery);
            result.setList(list);
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.RECORD_NOT_EXSIST);
        }
        result.setPage(new PageBean(totalResults, notifyQuery.getPageBean().getPageSize(), notifyQuery.getPageBean().getCurrentPage()));
        logger.debug("queryPage出参:{}",result);
        return result;
    }
//
//
//    /**
//     * @see com.yztz.finance.bussiness.service.SNS.NotifyService#update(com.yztz.finance.model.SMS.Notify)
//     */
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
//    public Result update(Notify notify) {
//        logger.debug("update入参:{}",notify);
//        Result result = new Result();
//        if (Util.isNull(notify)) {
//            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
//            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
//            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), notify.toString());
//        }
//
//        int row = notifyDao.update(notify);
//        if (row > 0) {
//            return result;
//        } else {
//            result.setSuccess(false);
//            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
//            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
//            logger.debug("update出参:{}",result);
//            return result;
//        }
//
//    }
//
//    private String repPoint(String content, PointSymbol pointSymbol) {
//
//        Field[] fields = PointSymbol.class.getDeclaredFields();
//        String value = null;
//        String name = null;
//        String str = null;
//        try {
//            for (Field field : fields) {
//                if (!Modifier.isStatic(field.getModifiers())) {
//                    field.setAccessible(true);
//                    value = String.valueOf(field.get(pointSymbol));
//                    if (null != value && !"null".equals(value)) {
//                        name = field.getName();
//                        str = "${" + name + "}";
//                        if (content.contains(str)) {
//                            content = content.replace(str, value);
//                        }
//                    }
//                }
//
//            }
//        } catch (IllegalArgumentException e) {
//            logger.error(e.getMessage(), e);
//        } catch (IllegalAccessException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return content;
//    }
//
//    private String repPoint(String content, String[] symbols) {
//        String str = null;
//        for (int i = 0; i < symbols.length; i++) {
//            str = "${" + i + "}";
//            if (content.contains(str)) {
//                content = content.replace(str, symbols[i]);
//            }
//        }
//        return content;
//    }
//
//    private List<String> parseNumbers(String number) {
//        return Arrays.asList(org.apache.commons.lang.StringUtils.trimToEmpty(number).split("[\\s\\,\\;\\|，]"));
//    }
//
//    /**
//     * @see com.yztz.finance.bussiness.service.SMS.NotifyService#updateState(java.lang.Integer)
//     */
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
//    public boolean updateStatus(Integer id, Integer status) {
//        if (null == id || null == status) {
//            return false;
//        }
//
//        Notify notify = notifyDao.get(id);
//        if (null == notify) {
//            return false;
//        }
//
//        notify.setStatus(status);
//
//        int result = notifyDao.update(notify);
//        if (result > 0) {
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * @see com.yztz.finance.bussiness.service.SMS.NotifyService#queryByStatus(com.yztz.finance.enums.SNS.NotifyStatusEnum)
//     */
//    @Override
//    public List<Notify> queryByStatus(Integer status) {
//        if (null == status) {
//            return null;
//        }
//
//        return notifyDao.queryByStatus(status);
//    }



}
