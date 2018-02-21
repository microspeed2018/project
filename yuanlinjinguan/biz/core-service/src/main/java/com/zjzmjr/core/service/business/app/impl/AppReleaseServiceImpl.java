package com.zjzmjr.core.service.business.app.impl;

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
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.app.AppRelease;
import com.zjzmjr.core.model.app.AppReleaseQuery;
import com.zjzmjr.core.service.business.app.AppReleaseService;
import com.zjzmjr.core.service.mapper.dao.coredb.app.AppReleaseMapper;

/**
 * app管理服务服务层
 * 
 * @author chenning
 * @version $Id: AppReleaseServiceImpl.java, v 0.1 2016-11-17 上午11:07:38
 *          Administrator Exp $
 */
@Service("appReleaseService")
public class AppReleaseServiceImpl implements AppReleaseService {

    private Logger logger = LoggerFactory.getLogger(AppReleaseServiceImpl.class);

    @Resource(name = "appReleaseMapper")
    private AppReleaseMapper appReleaseMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.app.AppReleaseService#getAppReleases(com.zjzmjr.core.model.app.AppReleaseQuery)
     */
    @Override
    public ResultPage<AppRelease> getAppReleases(AppReleaseQuery query) {
        logger.debug("getAppReleases入参:{}",query);
        ResultPage<AppRelease> result = new ResultPage<AppRelease>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), query.toString());
            return result;
        }
        logger.info("查询app版本信息一览参数：{}", query.toString());
        int total = appReleaseMapper.getAppReleaseCount(query);
        if (total > 0) {
            List<AppRelease> list = appReleaseMapper.getAppReleases(query);
            result.setSuccess(true);
            result.setList(list);
        } else {
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }

        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        logger.debug("getAppReleases出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.app.AppReleaseService#getAppReleaseById(com.zjzmjr.core.model.app.AppReleaseQuery)
     */
    @Override
    public ResultEntry<AppRelease> getAppReleaseById(AppReleaseQuery query) {
        logger.debug("getAppReleaseById入参:{}",query);
        ResultEntry<AppRelease> result = new ResultEntry<AppRelease>();
        if(Util.isNull(query) && Util.isNull(query.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), query.toString());
            return result;
        }
        logger.info("根据Id查询app版本信息参数：{}", query.toString());
        AppRelease app = appReleaseMapper.getAppReleaseById(query);
        if(Util.isNotNull(app)){
            result.setSuccess(true);
            result.setT(app);
        }else{
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }
        logger.debug("getAppReleaseById出参:{}",result);
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.app.AppReleaseService#insertAppRelease(com.zjzmjr.core.model.app.AppRelease)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertAppRelease(AppRelease record) {
        logger.debug("insertAppRelease入参:{}",record);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getDevice()) || Util.isNull(record.getAppVersion()) || Util.isNull(record.getDownloadUrl())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), record.toString());
            return result;
        }
        logger.info("发布app参数：{}", record.toString());
        int cnt = appReleaseMapper.insertAppRelease(record);
        if (cnt > 0) {
            result.setSuccess(true);
            result.setT(cnt);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":App发布失败");
        }
        logger.debug("insertAppRelease出参{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.app.AppReleaseService#updateById(com.zjzmjr.core.model.app.AppRelease)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateById(AppRelease record) {
        logger.debug("updateById入参:{}",record);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(record) || Util.isNull(record.getId()) || Util.isNull(record.getVersion())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), record.toString());
            return result;
        }
        AppReleaseQuery query = new AppReleaseQuery();
        query.setId(record.getId());
        AppRelease app = appReleaseMapper.getAppReleaseById(query);
        if(Util.isNotNull(app)){
            if(record.getVersion().equals(app.getVersion())){
                logger.info("更新app版本信息参数：{}", record.toString());
                int cnt = appReleaseMapper.updateById(record);
                if (cnt > 0) {
                    result.setSuccess(true);
                    result.setT(cnt);
                } else {
                    result.setSuccess(false);
                    result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
                    result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
                    logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":更新App版本失败");
                }
            }else{
                result.setSuccess(false);
                result.setErrorMsg("该app版本信息已被他人修改，请重新打开页面操作！");
                logger.error("app版本信息修改：" + record.getId() + "该app版本信息已被他人修改，请重新打开页面操作！");
            }
        }else{
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }
        logger.debug("updateById出参:{}",result);
        return result;
    }

}
