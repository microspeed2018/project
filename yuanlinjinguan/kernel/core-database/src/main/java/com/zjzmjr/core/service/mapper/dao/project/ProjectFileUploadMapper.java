package com.zjzmjr.core.service.mapper.dao.project;

import java.util.List;

import com.zjzmjr.core.model.project.ProjectFileInfo;
import com.zjzmjr.core.model.project.ProjectFileQuery;
import com.zjzmjr.core.model.project.ProjectFileUpload;

/**
 * 
 * 
 * @author oms
 * @version $Id: ProjectFileUploadMapper.java, v 0.1 2017-8-21 下午1:55:48 oms Exp
 *          $
 */
public interface ProjectFileUploadMapper {

    int deleteFileUploadById(Integer id);

    int insertFileUpload(ProjectFileUpload record);

    ProjectFileUpload getFileUploadById(Integer id);

    int updateFileUploadById(ProjectFileUpload record);

    /**
     * 查询上传文件及文件类型的件数
     * 
     * @param query
     * @return
     */
    int getFileUploadCount(ProjectFileQuery query);
    
    /**
     * 查询上传文件及文件类型的详细信息
     * 
     * @param query
     * @return
     */
    List<ProjectFileInfo> getFileUploadByCondition(ProjectFileQuery query);
    
    /**
     * 获取员工文件
     * 
     * @param query
     * @return
     */
    List<ProjectFileUpload> getStaffFile(ProjectFileQuery query);
    
    /**
     * 基础数据查询文件
     * 
     * @param query
     * @return
     */
    List<ProjectFileInfo> getFileByBasic(ProjectFileQuery query);
    
}