package com.zjzmjr.core.api.project;

import java.util.List;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.audit.ChiefAudit;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.ManageAudit;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.ProjectFile;
import com.zjzmjr.core.model.project.ProjectFileInfo;
import com.zjzmjr.core.model.project.ProjectFileQuery;
import com.zjzmjr.core.model.project.ProjectFileUpload;

/**
 * 项目之中的文件处理对外接口
 * 
 * @author oms
 * @version $Id: IProjectFileInfoFacade.java, v 0.1 2017-8-21 下午7:27:49 oms Exp
 *          $
 */
public interface IProjectFileInfoFacade {

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
    ResultPage<ProjectFile> getBasicFileTypeByCondition(ProjectFileQuery query);

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
     * 上传项目中的文件并且更新项目表中的信息
     * 
     * @param project
     * @param file
     * @return
     */
    ResultEntry<Integer> projectFileUpload(GardenProject project, ProjectFileUpload file);

    /**
     * 上传项目中的文件并且更新项目表中的信息
     * 
     * @param project
     * @param file
     * @return
     */
    ResultEntry<Integer> projectFileUpload(GardenProject project, List<ProjectFileUpload> fileLst);

    /**
     * 上传项目中的文件并且更新项目表中的信息
     * 
     * @param project
     * @param file
     * @return
     */
    ResultEntry<Integer> projectFileUpload(GardenProject project, ProjectFileUpload file, ManageAudit manageAudit);

    /**
     * 上传项目中的文件并且更新项目表和总工审核表中的信息
     * 
     * @param project
     * @param file
     * @return
     */
    ResultEntry<Integer> projectFileUpload(GardenProject project, ProjectFileUpload file, ChiefAudit chiefAudit);

    /**
     * 只上传项目中的文件信息
     * 
     * @param project
     * @param file
     * @return
     */
    ResultEntry<Integer> projectFileUpload(List<ProjectFileUpload> file);

    /**
     * 根据公司及项目的主键， 更新合同表及项目表信息
     * 
     * @param project
     * @param record
     * @return
     */
    ResultEntry<Integer> updateProjectContractInfo(GardenProject project, ProjectContract record);

    /**
     * 根据公司及项目的主键， 更新合同表和项目表中的信息，并且插入财务审核表
     * 
     * @param project
     * @param record
     * @return
     */
    ResultEntry<Integer> updateProjectContractInfo(GardenProject project, ProjectContract record, FinancialAudit audit);

    /**
     * 根据主键删除上传文件记录，并且删除物理文件处理逻辑
     * record.getFileAddress() 存储文件的访问地址
     * record.getFileName()  存储文件的物理地址
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> deleteFileUploadHandler(ProjectFileUpload record);

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
      * 员工文件上传
      * 
      * @param file
      * @return
      */
     ResultEntry<Integer> staffFileUpLoad(ProjectFileUpload file);
     
     /**
      * 基础数据查询文件
      * 
      * @param query
      * @return
      */
     ResultRecord<ProjectFileInfo> getFileByBasic(ProjectFileQuery query);
}
