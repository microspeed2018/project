package com.zjzmjr.core.service.business.project;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.project.ProjectFile;
import com.zjzmjr.core.model.project.ProjectFileInfo;
import com.zjzmjr.core.model.project.ProjectFileQuery;
import com.zjzmjr.core.model.project.ProjectFileUpload;

/**
 * 文件上传的表的业务处理层
 * 
 * @author oms
 * @version $Id: ProjectFileUploadService.java, v 0.1 2017-8-21 下午3:45:26 oms Exp $
 */
public interface ProjectFileUploadService {

    /**
     * 查询出所有的资料表数据
     * 
     * @return
     */
    ResultRecord<ProjectFile> getAllProjectFileInfo();
    
    /**
     * 根据文件资料的条件，查询文件资料表的详细信息
     * 
     * @param query
     * @return
     */
    ResultPage<ProjectFile> getFileTypeByCondition(ProjectFileQuery query);

    /**
     * 根据主键更新资料信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateProjectFileById(ProjectFile record);

    /**
     * 插入文件资料信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertProjectFile(ProjectFile record);

    /**
     * 根据主键删除资料信息
     * 
     * @param id
     * @return
     */
    ResultEntry<Integer> deleteProjectFileById(Integer id);

    /**
     * 查询上传文件及文件类型的详细信息
     * 
     * @param query
     * @return
     */
    ResultPage<ProjectFileInfo> getFileUploadByCondition(ProjectFileQuery query);
    
    /**
     * 
     * 
     * @param id
     * @return
     */
    ResultEntry<ProjectFileUpload> getFileUploadById(Integer id);

    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertFileUpload(ProjectFileUpload record);

    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateFileUploadById(ProjectFileUpload record);

    /**
     * 根据主键删除上传文件记录，并且删除物理文件处理逻辑
     * record.getFileAddress() 存储文件的访问地址
     * record.getFileName()  存储文件的物理地址
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> deleteFileUploadHandler(ProjectFileUpload record) throws ApplicationException;
    
    /**
     * 根据基础表获取文件类型名称
     * 
     * @param query
     * @return
     */
    ResultRecord<ProjectFile> getProjectFileName(ProjectFileQuery query);
    
   /**
    * 获取员工上传的文件
    * 
    * @param query
    * @return
    */
    ResultRecord<ProjectFileUpload> getStaffFile(ProjectFileQuery query);
    
    /**
     * 基础数据查询文件
     * 
     * @param query
     * @return
     */
    ResultRecord<ProjectFileInfo> getFileByBasic(ProjectFileQuery query);

}
