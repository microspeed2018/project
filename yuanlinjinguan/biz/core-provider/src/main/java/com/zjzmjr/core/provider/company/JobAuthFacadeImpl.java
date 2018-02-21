package com.zjzmjr.core.provider.company;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.company.IJobAuthFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.admin.AuthNodeDTO;
import com.zjzmjr.core.model.company.JobAuthority;
import com.zjzmjr.core.service.business.company.JobAuthService;

/**
 * 职位权限服务接口
 * 
 * @author oms
 * @version $Id: JobAuthFacadeImpl.java, v 0.1 2017-8-29 下午3:16:15 oms Exp $
 */
@Service("jobAuthFacade")
public class JobAuthFacadeImpl implements IJobAuthFacade {

    private static final Logger logger = LoggerFactory.getLogger(JobAuthFacadeImpl.class);

    @Resource(name = "jobAuthService")
    private JobAuthService jobAuthService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.company.IJobAuthFacade#getGroupJobAuth(java.lang.Long)
     */
    @Override
    public ResultEntry<Map<String, List<AuthNodeDTO>>> getGroupJobAuth(Long jobId) {
        ResultEntry<Map<String, List<AuthNodeDTO>>> result = new ResultEntry<Map<String,List<AuthNodeDTO>>>();
        try {
            result = jobAuthService.getGroupJobAuth(jobId);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.company.IJobAuthFacade#bindJobAuth(java.lang.Integer, java.util.List, java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> bindJobAuth(JobAuthority jobAuth, List<Integer> authIds) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = jobAuthService.bindJobAuth(jobAuth, authIds);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

}
