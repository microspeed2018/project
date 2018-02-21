package com.zjzmjr.core.service.mapper.dao.file;

import java.util.List;

import com.zjzmjr.core.model.file.SharedFile;
import com.zjzmjr.core.model.file.SharedFileInfo;
import com.zjzmjr.core.model.file.SharedFileQuery;

/**
 * 共享文件表t_shared_file
 * 
 * @author Administrator
 * @version $Id: SharedFileMapper.java, v 0.1 2017-12-12 上午10:39:47 Administrator Exp $
 */
public interface SharedFileMapper {
    
    /**
     * 统计共享文件数量
     * 
     * @param query
     * @return
     */
    int getSharedFileCount(SharedFileQuery query);
    
    /**
     * 根据条件查询共享文件
     * 
     * @param query
     * @return
     */
    List<SharedFileInfo> getSharedFile(SharedFileQuery query);
    
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增共享文件记录
     * 
     * @param record
     * @return
     */
    int insertSharedFile(SharedFile record);

    /**
     * 批量新增
     * 
     * @param list
     * @return
     */
    int batchInsert(List<SharedFile> list);

    /**
     * 根据id更新数据
     * 
     * @param record
     * @return
     */
    int updateById(SharedFile record);
    
    SharedFileInfo getSharedFileById(Integer id);
}