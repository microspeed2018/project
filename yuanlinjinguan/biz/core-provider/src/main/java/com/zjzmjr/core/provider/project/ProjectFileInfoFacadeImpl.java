package com.zjzmjr.core.provider.project;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.project.IProjectFileInfoFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
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
import com.zjzmjr.core.service.business.project.GardenProjectFileInfoService;
import com.zjzmjr.core.service.business.project.ProjectFileUploadService;

/**
 * 项目之中的文件处理对外接口
 * 
 * @author oms
 * @version $Id: ProjectFileInfoFacadeImpl.java, v 0.1 2017-8-21 下午7:34:22 oms Exp $
 */
@Service("projectFileInfoFacade")
public class ProjectFileInfoFacadeImpl implements IProjectFileInfoFacade {

    private static final Logger logger = LoggerFactory.getLogger(ProjectFileInfoFacadeImpl.class);

    @Resource(name = "projectFileInfoService")
    private GardenProjectFileInfoService projectFileInfoService;
    
    @Resource(name = "fileUploadService")
    private ProjectFileUploadService fileUploadService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#getAllProjectFileInfo()
     */
    @Override
    public ResultRecord<ProjectFile> getAllProjectFileInfo() {
        ResultRecord<ProjectFile> result = new ResultRecord<ProjectFile>();
        try {
            result = fileUploadService.getAllProjectFileInfo();
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#getFileTypeByCondition(com.zjzmjr.core.model.project.ProjectFileQuery)
     */
    @Override
    public ResultPage<ProjectFile> getBasicFileTypeByCondition(ProjectFileQuery query) {
        ResultPage<ProjectFile> result = new ResultPage<ProjectFile>();
        try {
            result = fileUploadService.getFileTypeByCondition(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#updateProjectFileById(com.zjzmjr.core.model.project.ProjectFile)
     */
    @Override
    public ResultEntry<Integer> updateProjectFileById(ProjectFile record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = fileUploadService.updateProjectFileById(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#insertProjectFile(com.zjzmjr.core.model.project.ProjectFile)
     */
    @Override
    public ResultEntry<Integer> insertProjectFile(ProjectFile record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = fileUploadService.insertProjectFile(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#deleteProjectFileById(java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> deleteProjectFileById(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = fileUploadService.deleteProjectFileById(id);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#getFileUploadByCondition(com.zjzmjr.core.model.project.ProjectFileQuery)
     */
    @Override
    public ResultPage<ProjectFileInfo> getFileUploadByCondition(ProjectFileQuery query) {
        ResultPage<ProjectFileInfo> result = new ResultPage<ProjectFileInfo>();
        try {
            result = fileUploadService.getFileUploadByCondition(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#projectFileUpload(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ProjectFileUpload)
     */
    @Override
    public ResultEntry<Integer> projectFileUpload(GardenProject project, ProjectFileUpload file) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            List<ProjectFileUpload> fileLst = new ArrayList<ProjectFileUpload>();
            fileLst.add(file);
            result = projectFileInfoService.projectFileUpload(project, fileLst);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#projectFileUpload(com.zjzmjr.core.model.project.GardenProject, java.util.List)
     */
    @Override
    public ResultEntry<Integer> projectFileUpload(GardenProject project, List<ProjectFileUpload> fileLst) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = projectFileInfoService.projectFileUpload(project, fileLst);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#inviteBidFile(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ProjectFileUpload)
     */
    @Override
    public ResultEntry<Integer> projectFileUpload(GardenProject project, ProjectFileUpload file, ManageAudit manageAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = projectFileInfoService.projectFileUpload(project, file, manageAudit);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#projectFileUpload(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ProjectFileUpload, com.zjzmjr.core.model.audit.ChiefAudit)
     */
    @Override
    public ResultEntry<Integer> projectFileUpload(GardenProject project, ProjectFileUpload file, ChiefAudit chiefAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = projectFileInfoService.projectFileUpload(project, file, chiefAudit);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#projectFileUpload(com.zjzmjr.core.model.project.ProjectFileUpload)
     */
    @Override
    public ResultEntry<Integer> projectFileUpload(List<ProjectFileUpload> file) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = projectFileInfoService.projectFileUpload(file);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#updateProjectContractInfo(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ProjectContract)
     */
    @Override
    public ResultEntry<Integer> updateProjectContractInfo(GardenProject project, ProjectContract record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = projectFileInfoService.updateProjectContractInfo(project, record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#updateProjectContractInfo(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ProjectContract, com.zjzmjr.core.model.audit.FinancialAudit)
     */
    @Override
    public ResultEntry<Integer> updateProjectContractInfo(GardenProject project, ProjectContract record, FinancialAudit audit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = projectFileInfoService.updateProjectContractInfo(project, record, audit);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#deleteFileUploadHandler(com.zjzmjr.core.model.project.ProjectFileUpload)
     */
    @Override
    public ResultEntry<Integer> deleteFileUploadHandler(ProjectFileUpload record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = fileUploadService.deleteFileUploadHandler(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#getProjectFileName(com.zjzmjr.core.model.project.ProjectFileQuery)
     */
    @Override
    public ResultRecord<ProjectFile> getProjectFileName(ProjectFileQuery query) {
        ResultRecord<ProjectFile> result = new ResultRecord<ProjectFile>();
        try {
            result = fileUploadService.getProjectFileName(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#getStaffFile(com.zjzmjr.core.model.project.ProjectFileQuery)
     */
    @Override
    public ResultRecord<ProjectFileUpload> getStaffFile(ProjectFileQuery query) {
        ResultRecord<ProjectFileUpload> result = new ResultRecord<ProjectFileUpload>();
        try {
            result = fileUploadService.getStaffFile(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#staffFileUpLoad(com.zjzmjr.core.model.project.ProjectFileUpload)
     */
    @Override
    public ResultEntry<Integer> staffFileUpLoad(ProjectFileUpload file) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = projectFileInfoService.staffFileUpLoad(file);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectFileInfoFacade#getFileByBasic(com.zjzmjr.core.model.project.ProjectFileQuery)
     */
    @Override
    public ResultRecord<ProjectFileInfo> getFileByBasic(ProjectFileQuery query) {
        ResultRecord<ProjectFileInfo> result = new ResultRecord<ProjectFileInfo>();
        try {
            result = fileUploadService.getFileByBasic(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
}
