package com.zjzmjr.core.service.business.file.impl;

import java.io.File;
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
import com.zjzmjr.core.model.file.SharedFile;
import com.zjzmjr.core.model.file.SharedFileInfo;
import com.zjzmjr.core.model.file.SharedFileQuery;
import com.zjzmjr.core.service.business.file.SharedFileService;
import com.zjzmjr.core.service.mapper.dao.file.SharedFileMapper;

@Service("sharedFileService")
public class SharedFileServiceImpl implements SharedFileService {

    private static final Logger logger = LoggerFactory.getLogger(SharedFileServiceImpl.class);

    @Resource(name = "sharedFileMapper")
    private SharedFileMapper sharedFileMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.file.SharedFileService#getSharedFile(com.zjzmjr.core.model.file.SharedFileQuery)
     */
    @Override
    public ResultPage<SharedFileInfo> getSharedFile(SharedFileQuery query) {
        logger.debug("getSharedFile执行开始");
        ResultPage<SharedFileInfo> result = new ResultPage<SharedFileInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.PARAM_ERROR);
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.debug("getSharedFile执行结束");
            return result;
        }
        int total = sharedFileMapper.getSharedFileCount(query);
        if (total > 0) {
            List<SharedFileInfo> list = sharedFileMapper.getSharedFile(query);
            result.setSuccess(true);
            result.setList(list);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.RECORD_NOT_EXSIST);
        }
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        logger.debug("getSharedFile执行结束");
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.file.SharedFileService#batchInsert(java.util.List)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> batchInsert(List<SharedFile> list) {
        logger.debug("insertSharedFile执行开始");
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(list)) {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.PARAM_ERROR);
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.debug("getSharedFile执行结束");
            return result;
        }
        int total = sharedFileMapper.batchInsert(list);
        if (total == list.size()) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
        }
        logger.debug("insertSharedFile执行结束");
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.file.SharedFileService#insertSharedFile(com.zjzmjr.core.model.file.SharedFile)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertSharedFile(SharedFile file) {
        logger.debug("insertSharedFile执行开始");
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(file)) {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.PARAM_ERROR);
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.debug("getSharedFile执行结束");
            return result;
        }
        int total = sharedFileMapper.insertSharedFile(file);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
        }
        logger.debug("insertSharedFile执行结束");
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.file.SharedFileService#updateById(com.zjzmjr.core.model.file.SharedFile)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateById(SharedFile file) {
        logger.debug("insertSharedFile执行开始");
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(file) || Util.isNull(file.getId()) || Util.isNull(file.getVersion())) {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.PARAM_ERROR);
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.debug("getSharedFile执行结束");
            return result;
        }
        SharedFile record = sharedFileMapper.getSharedFileById(file.getId());
        if (Util.isNotNull(record) && record.getVersion().equals(file.getVersion())) {
            int total = sharedFileMapper.updateById(file);
            if (total > 0) {
                result.setSuccess(true);
                result.setT(total);
            } else {
                result.setSuccess(false);
                result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            }
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.RECORD_CHANGE);
        }
        logger.debug("insertSharedFile执行结束");
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.file.SharedFileService#deleteSharedFile(com.zjzmjr.core.model.file.SharedFile)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteSharedFile(SharedFile record) {
        logger.debug("deleteSharedFile执行开始");
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.PARAM_ERROR);
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.debug("getSharedFile执行结束");
            return result;
        }
        SharedFile fileRecord = sharedFileMapper.getSharedFileById(record.getId());
        if (Util.isNull(fileRecord)) {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.RECORD_CHANGE);
        } else {
            int total = sharedFileMapper.deleteByPrimaryKey(record.getId());
            if(total > 0){
                String address = fileRecord.getSaveAddress();
                address = address.replace(record.getSaveAddress(), record.getFileName());
                File file = new File(address);
                if(file.exists()){
                    file.delete();
                }
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setErrorMsg("删除文件失败");
            }
            result.setT(total);
        }
        logger.debug("deleteSharedFile执行结束");
        return result;
    }

}
