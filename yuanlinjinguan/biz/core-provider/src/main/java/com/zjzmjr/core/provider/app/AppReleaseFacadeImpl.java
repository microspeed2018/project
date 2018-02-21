package com.zjzmjr.core.provider.app;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.app.IAppReleaseFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.app.AppRelease;
import com.zjzmjr.core.model.app.AppReleaseQuery;
import com.zjzmjr.core.service.business.app.AppReleaseService;

@Service("appReleaseFacade")
public class AppReleaseFacadeImpl implements IAppReleaseFacade {

    private Logger logger = LoggerFactory.getLogger(AppReleaseFacadeImpl.class);

    @Resource(name = "appReleaseService")
    private AppReleaseService appReleaseService;

    /**
     * 
     * @see com.zjzmjr.core.api.app.IAppReleaseFacade#getAppReleases(com.zjzmjr.core.model.app.AppReleaseQuery)
     */
    @Override
    public ResultPage<AppRelease> getAppReleases(AppReleaseQuery query) {
        ResultPage<AppRelease> result = new ResultPage<AppRelease>();
        try {
            result = appReleaseService.getAppReleases(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.app.IAppReleaseFacade#insertAppRelease(com.zjzmjr.core.model.app.AppRelease)
     */
    @Override
    public ResultEntry<Integer> insertAppRelease(AppRelease record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = appReleaseService.insertAppRelease(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }

        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.app.IAppReleaseFacade#getAppReleaseById(com.zjzmjr.core.model.app.AppReleaseQuery)
     */
    @Override
    public ResultEntry<AppRelease> getAppReleaseById(AppReleaseQuery query) {
        ResultEntry<AppRelease> result = new ResultEntry<AppRelease>();
        try {
            result = appReleaseService.getAppReleaseById(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.app.IAppReleaseFacade#updateAppRelease(com.zjzmjr.core.model.app.AppRelease)
     */
    @Override
    public ResultEntry<Integer> updateAppRelease(AppRelease record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = appReleaseService.updateById(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }

        return result;
    }

}
