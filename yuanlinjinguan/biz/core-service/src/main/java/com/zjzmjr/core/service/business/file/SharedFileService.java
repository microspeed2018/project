package com.zjzmjr.core.service.business.file;

import java.util.List;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.file.SharedFile;
import com.zjzmjr.core.model.file.SharedFileInfo;
import com.zjzmjr.core.model.file.SharedFileQuery;

/**
 * 共享文件管理服务层
 * 
 * @author Administrator
 * @version $Id: SharedFileService.java, v 0.1 2017-12-15 下午1:18:44 Administrator Exp $
 */
public interface SharedFileService {
    
    /**
     * 根据条件查询共享文件
     * 
     * @param query
     * @return
     */
    ResultPage<SharedFileInfo> getSharedFile(SharedFileQuery query);
    
    /**
     * 批量新增
     * 
     * @param list
     * @return
     */
    ResultEntry<Integer> batchInsert(List<SharedFile> list);
    
    /**
     * 新增共享文件
     * 
     * @param file
     * @return
     */
    ResultEntry<Integer> insertSharedFile(SharedFile file);
    
    /**
     * 根据id更新
     * 
     * @param file
     * @return
     */
    ResultEntry<Integer> updateById(SharedFile file);
    
    /**
     * 删除文件
     * 
     * @param file
     * @return
     */
    ResultEntry<Integer> deleteSharedFile(SharedFile file);
    
}
