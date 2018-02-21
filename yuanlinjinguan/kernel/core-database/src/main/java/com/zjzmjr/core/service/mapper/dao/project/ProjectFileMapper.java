package com.zjzmjr.core.service.mapper.dao.project;

import java.util.List;

import com.zjzmjr.core.model.project.ProjectFile;
import com.zjzmjr.core.model.project.ProjectFileQuery;

/**
 * 资料表
 * 
 * @author oms
 * @version $Id: ProjectFileMapper.java, v 0.1 2017-8-21 下午1:55:24 oms Exp $
 */
public interface ProjectFileMapper {

    /**
     * 根据主键删除资料信息
     * 
     * @param id
     * @return
     */
    int deleteProjectFileById(Integer id);

    /**
     * 插入文件资料信息
     * 
     * @param record
     * @return
     */
    int insertProjectFile(ProjectFile record);

    /**
     * 获取资料表的件数
     * 
     * @param query
     * @return
     */
    int getProjectFileCount(ProjectFileQuery query);
    
    /**
     * 获取资料表的详细信息
     * 
     * @param query
     * @return
     */
    List<ProjectFile> getFileTypeByCondition(ProjectFileQuery query);

    /**
     * 根据主键更新资料信息
     * 
     * @param record
     * @return
     */
    int updateProjectFileById(ProjectFile record);

    /**
     * 查询出所有的资料表数据
     * 
     * @return
     */
    List<ProjectFile> getAllProjectFileInfo();

    /**
     * 文件类型名称
     * 
     * @param query
     * @return
     */
    List<ProjectFile> getProjectFileName(ProjectFileQuery query);
}