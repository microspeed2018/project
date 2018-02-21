package com.zjzmjr.core.service.business.project.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.audit.ChiefAudit;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.ManageAudit;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.ProjectFileUpload;
import com.zjzmjr.core.service.business.audit.ChiefAuditService;
import com.zjzmjr.core.service.business.audit.FinancialAuditService;
import com.zjzmjr.core.service.business.audit.ManageAuditService;
import com.zjzmjr.core.service.business.project.GardenProjectFileInfoService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.business.project.ProjectContractService;
import com.zjzmjr.core.service.business.project.ProjectFileUploadService;

/**
 * 项目之中的文件处理业务层
 * 
 * @author oms
 * @version $Id: GardenProjectFileInfoServiceImpl.java, v 0.1 2017-8-21 下午7:02:36 oms Exp $
 */
@Service("projectFileInfoService")
public class GardenProjectFileInfoServiceImpl implements GardenProjectFileInfoService {

    private static final Logger logger = LoggerFactory.getLogger(GardenProjectFileInfoServiceImpl.class);

    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    /** 合同服务类 */
    @Resource(name = "projectContractService")
    private ProjectContractService projectContractService;
    
    @Resource(name = "fileUploadService")
    private ProjectFileUploadService fileUploadService;
    
    /** 经营部门审核业务 */
    @Resource(name = "manageAuditService")
    private ManageAuditService manageAuditService;
    
    /** 总工审核业务 */
    @Resource(name = "chiefAuditService")
    private ChiefAuditService chiefAuditService;
    
    /** 财务审核业务 */
    @Resource(name = "financialAuditService")
    private FinancialAuditService financialAuditService;

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectFileInfoService#projectFileUpload(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ProjectFileUpload)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> projectFileUpload(GardenProject project, List<ProjectFileUpload> fileLst) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(project) || Util.isNull(project.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        result.setSuccess(false);
        ResultEntry<Integer> projectRst = gardenProjectService.updateGardenProjectById(project);
        if (projectRst.isSuccess()) {
            if (Util.isNotNull(fileLst) && !fileLst.isEmpty()) {
//                ProjectFileQuery query = null;
                for(ProjectFileUpload file : fileLst) {
                    if(Util.isNotNull(file.getFileId())){
//                        query = new ProjectFileQuery();
//                        query.setCompanyId(file.getCompanyId());
//                        query.setFileId(file.getFileId());
//                        query.setProjectId(file.getProjectId());
//                        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
//                        ResultPage<ProjectFileInfo> fileInfoRst = fileUploadService.getFileUploadByCondition(query);
                        ResultEntry<Integer> fileRst = null;
//                        if (fileInfoRst.isSuccess() && !fileInfoRst.getList().isEmpty()) {
//                            file.setId(fileInfoRst.getList().get(0).getId());
//                            fileRst = fileUploadService.updateFileUploadById(file);
//                        } else {
                            fileRst = fileUploadService.insertFileUpload(file);
//                        }
                        if (!fileRst.isSuccess()) {
                            throw new ApplicationException("文件上传或项目表操作失败");
                        }
                    }
                }
            }
            result.setSuccess(true);
        } else {
            result.setErrorMsg("文件上传或项目表操作失败");
        }

        return result;
    }
    
    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.GardenProjectFileInfoService#inviteBidFile(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ProjectFileUpload)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> projectFileUpload(GardenProject project, ProjectFileUpload file, ManageAudit manageAudit) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(project) || Util.isNull(project.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        result.setSuccess(false);
        ResultEntry<Integer> projectRst = gardenProjectService.updateGardenProjectById(project);
        if (projectRst.isSuccess()) {
            if (Util.isNotNull(file) && Util.isNotNull(file.getFileId())) {
//                ProjectFileQuery query = new ProjectFileQuery();
//                query.setCompanyId(file.getCompanyId());
//                query.setFileId(file.getFileId());
//                query.setProjectId(file.getProjectId());
//                query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
//                ResultPage<ProjectFileInfo> fileInfoRst = fileUploadService.getFileUploadByCondition(query);
                ResultEntry<Integer> fileRst = null;
//                if (fileInfoRst.isSuccess() && !fileInfoRst.getList().isEmpty()) {
//                    file.setId(fileInfoRst.getList().get(0).getId());
//                    fileRst = fileUploadService.updateFileUploadById(file);
//                }else{
                    fileRst = fileUploadService.insertFileUpload(file);
//                }
                if (!fileRst.isSuccess()) {
                    throw new ApplicationException("文件上传或项目表操作失败");
                }
            }
            if (Util.isNotNull(manageAudit)) {
                ResultEntry<Integer> manageRst = manageAuditService.insertManageAudit(manageAudit);
                if (!manageRst.isSuccess()) {
                    throw new ApplicationException("经营部门操作失败");
                }
            }
            result.setSuccess(true);
        } else {
            result.setErrorMsg("文件上传或项目表操作失败");
        }

        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectFileInfoService#projectFileUpload(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ProjectFileUpload, com.zjzmjr.core.model.audit.ChiefAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> projectFileUpload(GardenProject project, ProjectFileUpload file, ChiefAudit chiefAudit) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(project) || Util.isNull(project.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        result.setSuccess(false);
        ResultEntry<Integer> projectRst = gardenProjectService.updateGardenProjectById(project);
        if (projectRst.isSuccess()) {
            if (Util.isNotNull(file) && Util.isNotNull(file.getFileId())) {
//                ProjectFileQuery query = new ProjectFileQuery();
//                query.setCompanyId(file.getCompanyId());
//                query.setFileId(file.getFileId());
//                query.setProjectId(file.getProjectId());
//                query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
//                ResultPage<ProjectFileInfo> fileInfoRst = fileUploadService.getFileUploadByCondition(query);
                ResultEntry<Integer> fileRst = null;
//                if (fileInfoRst.isSuccess() && !fileInfoRst.getList().isEmpty()) {
//                    file.setId(fileInfoRst.getList().get(0).getId());
//                    fileRst = fileUploadService.updateFileUploadById(file);
//                }else{
                    fileRst = fileUploadService.insertFileUpload(file);
//                }
                if (!fileRst.isSuccess()) {
                    throw new ApplicationException("文件上传或项目表操作失败");
                }
            }
            if (Util.isNotNull(chiefAudit)) {
                ResultEntry<Integer> manageRst = chiefAuditService.insertChiefAudit(chiefAudit);
                if (!manageRst.isSuccess()) {
                    throw new ApplicationException("总工审核操作失败");
                }
            }
            result.setSuccess(true);
        } else {
            result.setErrorMsg("文件上传或项目表操作失败");
        }

        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectFileInfoService#projectFileUpload(com.zjzmjr.core.model.project.ProjectFileUpload)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> projectFileUpload(List<ProjectFileUpload> file) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(file)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

//        ProjectFileQuery query = new ProjectFileQuery();
//        query.setCompanyId(file.getCompanyId());
//        query.setFileId(file.getFileId());
//        query.setProjectId(file.getProjectId());
//        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
//        ResultPage<ProjectFileInfo> fileInfoRst = fileUploadService.getFileUploadByCondition(query);
        ResultEntry<Integer> fileRst = null;
//        if (fileInfoRst.isSuccess() && !fileInfoRst.getList().isEmpty()) {
//            file.setId(fileInfoRst.getList().get(0).getId());
//            fileRst = fileUploadService.updateFileUploadById(file);
//        }else{
        int total = 0;
        for(ProjectFileUpload upload : file){
            if(Util.isNotNull(upload.getCompanyId()) 
                    //&& (Util.isNotNull(upload.getProjectId()) || Util.isNotNull(upload.getAdminId())) 
                    && Util.isNotNull(upload.getFileId())){
                fileRst = fileUploadService.insertFileUpload(upload);
                if(fileRst.isSuccess()){
                    total++;
                }
            }
        }
//        }
        if(file.size() == total){
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setErrorMsg("文件上传失败");
        }
        
        return result;
    }
    
    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.GardenProjectFileInfoService#updateProjectContractInfo(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ProjectContract)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateProjectContractInfo(GardenProject project, ProjectContract record) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(project) || Util.isNull(record) || Util.isNull(project.getId()) || Util.isNull(record.getProjectId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        result = projectContractService.updateProjectContractByProjectId(record);
        if(result.isSuccess()){
            ResultEntry<Integer> projectRst = gardenProjectService.updateGardenProjectById(project);
            if(!projectRst.isSuccess()){
                throw new ApplicationException("合同表或项目表操作失败");
            }
            result.setSuccess(true);
        }
        
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectFileInfoService#updateProjectContractInfo(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ProjectContract, com.zjzmjr.core.model.audit.FinancialAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateProjectContractInfo(GardenProject project, ProjectContract record, FinancialAudit audit) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(project) || Util.isNull(record) || Util.isNull(project.getId()) || Util.isNull(record.getProjectId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        result = projectContractService.updateProjectContractByProjectId(record);
        if(result.isSuccess()){
            ResultEntry<Integer> projectRst = gardenProjectService.updateGardenProjectById(project);
            if(!projectRst.isSuccess()){
                throw new ApplicationException("合同表或项目表操作失败");
            }
            projectRst = financialAuditService.insertFinancialAudit(audit);
            if(!projectRst.isSuccess()){
                throw new ApplicationException("插入财务审核表操作失败");
            }
            result.setSuccess(true);
        }
        
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectFileInfoService#staffFileUpLoad(java.util.List)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> staffFileUpLoad(ProjectFileUpload file) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(file)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        ResultEntry<Integer> fileRst = null;
        int total = 0;
        if(Util.isNotNull(file.getCompanyId()) 
                && (Util.isNotNull(file.getProjectId()) || Util.isNotNull(file.getAdminId())) 
                && Util.isNotNull(file.getFileId())){
            fileRst = fileUploadService.insertFileUpload(file);
            if(fileRst.isSuccess()){
                total++;
            }
        }
        if(total>0){
            result.setSuccess(true);
            result.setT(fileRst.getT());
        }else{
            result.setSuccess(false);
            result.setErrorMsg("文件上传失败");
        }
        
        return result;
    }
    
}
