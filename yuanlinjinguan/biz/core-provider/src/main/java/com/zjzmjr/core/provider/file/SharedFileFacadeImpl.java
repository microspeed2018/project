package com.zjzmjr.core.provider.file;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.file.ISharedFileFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.file.SharedFile;
import com.zjzmjr.core.model.file.SharedFileInfo;
import com.zjzmjr.core.model.file.SharedFileQuery;
import com.zjzmjr.core.service.business.file.SharedFileService;

/**
 * 共享文件管理服务层
 * 
 * @author Administrator
 * @version $Id: SharedFileFacadeImpl.java, v 0.1 2017-12-15 下午1:33:40 Administrator Exp $
 */
@Service("sharedFileFacade")
public class SharedFileFacadeImpl implements ISharedFileFacade {
    
    private static final Logger logger = LoggerFactory.getLogger(SharedFileFacadeImpl.class);

    @Resource(name = "sharedFileService")
    private SharedFileService sharedFileService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.file.ISharedFileFacade#getSharedFile(com.zjzmjr.core.model.file.SharedFileQuery)
     */
    @Override
    public ResultPage<SharedFileInfo> getSharedFile(SharedFileQuery query) {
        ResultPage<SharedFileInfo> result = new ResultPage<SharedFileInfo>();
        try {
            result = sharedFileService.getSharedFile(query);
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
     * @see com.zjzmjr.core.api.file.ISharedFileFacade#batchInsert(java.util.List)
     */
    @Override
    public ResultEntry<Integer> batchInsert(List<SharedFile> list) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = sharedFileService.batchInsert(list);
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
     * @see com.zjzmjr.core.api.file.ISharedFileFacade#insertSharedFile(com.zjzmjr.core.model.file.SharedFile)
     */
    @Override
    public ResultEntry<Integer> insertSharedFile(SharedFile file) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = sharedFileService.insertSharedFile(file);
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
     * @see com.zjzmjr.core.api.file.ISharedFileFacade#updateById(com.zjzmjr.core.model.file.SharedFile)
     */
    @Override
    public ResultEntry<Integer> updateById(SharedFile file) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = sharedFileService.updateById(file);
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
     * @see com.zjzmjr.core.api.file.ISharedFileFacade#deleteSharedFile(com.zjzmjr.core.model.file.SharedFile)
     */
    @Override
    public ResultEntry<Integer> deleteSharedFile(SharedFile record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = sharedFileService.deleteSharedFile(record);
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
