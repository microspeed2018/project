package com.zjzmjr.core.service.business.logo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.logo.LogoManage;
import com.zjzmjr.core.model.logo.LogoManageQuery;
import com.zjzmjr.core.service.business.logo.LogoManageService;
import com.zjzmjr.core.service.mapper.dao.coredb.logo.LogoManageMapper;

/**
 * 图标管理处理类
 * 
 * @author lenovo
 * @version $Id: LogoManageServiceImpl.java, v 0.1 2016-9-20 下午5:09:00 lenovo
 *          Exp $
 */
@Service("logoManageService")
public class LogoManageServiceImpl implements LogoManageService {

    private static final Logger logger = LoggerFactory.getLogger(LogoManageServiceImpl.class);

    @Resource(name = "logoManageMapper")
    private LogoManageMapper logoManageMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.logo.LogoManageService#getLogoManage(com.zjzmjr.core.model.logo.LogoManageQuery)
     */
    @Override
    public ResultPage<LogoManage> getLogoManage(LogoManageQuery query) {
        logger.debug("getLogoManage入参:{}",query);
        ResultPage<LogoManage> result = new ResultPage<LogoManage>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), query.toString());
            return result;
        }
        logger.info("图标管理一览参数：{}", query.toString());
        int total = logoManageMapper.getLogoManageCount(query);
        if (total > 0) {
            List<LogoManage> list = logoManageMapper.getLogoManage(query);
            result.setSuccess(true);
            result.setList(list);
            result.setSuccess(true);
        } else {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        logger.debug("getLogoManage出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.logo.LogoManageService#updateLogoManage(com.zjzmjr.core.model.logo.LogoManage)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateLogoManage(LogoManage logoManage) {
        logger.debug("updateLogoManage入参:{}",logoManage);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(logoManage) || Util.isNull(logoManage.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), logoManage.toString());
            logger.debug("updateLogoManage出参:{}",result);
            return result;
        }
        logger.info("图标管理修改：{}", logoManage.toString());
        int cnt = logoManageMapper.updateLogoManage(logoManage);
        if (cnt > 0) {
            result.setSuccess(true);
            result.setT(cnt);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":图标更新失败");
        }
        logger.debug("updateLogoManage出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.logo.LogoManageService#insertLogoManage(com.zjzmjr.core.model.logo.LogoManage)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertLogoManage(LogoManage logoManage) {
        logger.debug("insertLogoManage入参:{}",logoManage);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(logoManage) || Util.isNull(logoManage.getLogoTypeNo()) || Util.isNull(logoManage.getLogoNo()) || Util.isNull(logoManage.getLogoComment()) || Util.isNull(logoManage.getLogoAddress())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), logoManage.toString());
            logger.debug("insertLogoManage出参:{}",result);
            return result;
        }
        logger.info("图标新增：{}", logoManage.toString());
        int cnt = logoManageMapper.insertLogoManage(logoManage);
        if (cnt > 0) {
            result.setSuccess(true);
            result.setT(cnt);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":图标新增失败");
        }
        logger.debug("insertLogoManage出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.logo.LogoManageService#getMaxLogoNo()
     */
    @Override
    public ResultEntry<Integer> getMaxLogoNo(Integer logoTypeNo) {
        logger.debug("getMaxLogoNo入参:{}",logoTypeNo);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(logoTypeNo)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), logoTypeNo);
            logger.debug("getMaxLogoNo出参:{}",result);
            return result;
        }
        int date = 0;
        if (Util.isNull(logoManageMapper.getMaxLogoNo(logoTypeNo))) {
            date = 1;
        } else {
            date = logoManageMapper.getMaxLogoNo(logoTypeNo) + 1;
        }
        result.setT(date);
        result.setSuccess(true);
        logger.debug("getMaxLogoNo出参:{}",result);
        return result;

    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.logo.LogoManageService#getAllLogoManage(com.zjzmjr.core.model.logo.LogoManageQuery)
     */
    @Override
    public ResultRecord<LogoManage> getAllLogoManage(LogoManageQuery query) {
        logger.debug("getAllLogoManage入参:{}",query);
        ResultRecord<LogoManage> result = new ResultRecord<LogoManage>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), query.toString());
            logger.debug("getAllLogoManage出参:{}",result);
            return result;
        }
        logger.info("图标管理一览参数：{}", query.toString());
        List<LogoManage> list = logoManageMapper.getAllLogoManage(query);
        if (list == null || list.size() == 0) {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setList(list);
        }
        logger.debug("getAllLogoManage出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.logo.LogoManageService#deleteLogoManage(java.lang.Integer)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteLogoManage(Integer id) {
        logger.debug("deleteLogoManage入参:{}",id);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(id)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), id);
            logger.debug("deleteLogoManage出参:{}",result);
            return result;
        }
        logger.info("删除图标：{}", id);
        int cnt = logoManageMapper.deleteLogoManage(id);
        if (cnt > 0) {
            result.setT(cnt);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":删除图标失败");
        }
        logger.debug("deleteLogoManage出参:{}",result);
        return result;
    }

}
