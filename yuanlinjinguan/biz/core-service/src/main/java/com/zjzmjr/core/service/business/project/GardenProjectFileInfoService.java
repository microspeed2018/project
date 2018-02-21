package com.zjzmjr.core.service.business.project;

import java.util.List;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.audit.ChiefAudit;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.ManageAudit;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.ProjectFileUpload;

/**
 * 项目文件
 * 
 * @author oms
 * @version $Id: GardenProjectFileInfoService.java, v 0.1 2017-8-21 下午6:58:45
 *          oms Exp $
 */
public interface GardenProjectFileInfoService {

    /**
     * 上传项目中的文件并且更新项目表中的信息
     * 
     * @param project
     * @param file
     * @return
     */
    ResultEntry<Integer> projectFileUpload(GardenProject project, List<ProjectFileUpload> fileLst) throws ApplicationException;

    /**
     * 上传项目中的文件并且更新项目表和经营审核表中的信息
     * 
     * @param project
     * @param file
     * @return
     */
    ResultEntry<Integer> projectFileUpload(GardenProject project, ProjectFileUpload file, ManageAudit manageAudit) throws ApplicationException;

    /**
     * 上传项目中的文件并且更新项目表和总工审核表中的信息
     * 
     * @param project
     * @param file
     * @return
     */
    ResultEntry<Integer> projectFileUpload(GardenProject project, ProjectFileUpload file, ChiefAudit chiefAudit) throws ApplicationException;

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
    ResultEntry<Integer> updateProjectContractInfo(GardenProject project, ProjectContract record) throws ApplicationException;

    /**
     * 根据公司及项目的主键， 更新合同表和项目表中的信息，并且插入财务审核表
     * 
     * @param project
     * @param record
     * @return
     */
    ResultEntry<Integer> updateProjectContractInfo(GardenProject project, ProjectContract record, FinancialAudit audit) throws ApplicationException;
    
    /**
     * 员工文件上传
     * 
     * @param file
     * @return
     */
    ResultEntry<Integer> staffFileUpLoad(ProjectFileUpload file);

}
