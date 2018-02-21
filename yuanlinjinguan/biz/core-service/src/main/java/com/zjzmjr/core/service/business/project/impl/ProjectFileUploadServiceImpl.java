package com.zjzmjr.core.service.business.project.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.ProjectFile;
import com.zjzmjr.core.model.project.ProjectFileInfo;
import com.zjzmjr.core.model.project.ProjectFileQuery;
import com.zjzmjr.core.model.project.ProjectFileUpload;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.business.project.ProjectFileUploadService;
import com.zjzmjr.core.service.business.user.ExternalPersonService;
import com.zjzmjr.core.service.business.user.StaffInfoService;
import com.zjzmjr.core.service.mapper.dao.project.ProjectFileMapper;
import com.zjzmjr.core.service.mapper.dao.project.ProjectFileUploadMapper;

/**
 * 资料上传表的业务处理层
 * 
 * @author oms
 * @version $Id: ProjectFileUploadServiceImpl.java, v 0.1 2017-8-21 下午6:16:40 oms Exp $
 */
@Service("fileUploadService")
public class ProjectFileUploadServiceImpl implements ProjectFileUploadService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectFileUploadServiceImpl.class);

    @Resource(name = "projectFileUploadMapper")
    private ProjectFileUploadMapper projectFileUploadMapper;
    
    @Resource(name = "projectFileMapper")
    private ProjectFileMapper projectFileMapper;

    /**
     * 内部人员
     */
    @Resource(name = "staffInfoService")
    private StaffInfoService staffInfoService;
    
    /**
     * 外部人员
     */
    @Resource(name = "externalPersonService")
    private ExternalPersonService externalPersonService;
    
    /** 项目服务处理类 */
    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#getAllProjectFileInfo()
     */
    @Override
    public ResultRecord<ProjectFile> getAllProjectFileInfo() {
        ResultRecord<ProjectFile> result = new ResultRecord<ProjectFile>();
        
        List<ProjectFile> list = projectFileMapper.getAllProjectFileInfo();
        if(list == null || list.isEmpty()){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setList(list);
        }

        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#getFileTypeByCondition(com.zjzmjr.core.model.project.ProjectFileQuery)
     */
    @Override
    public ResultPage<ProjectFile> getFileTypeByCondition(ProjectFileQuery query) {
        ResultPage<ProjectFile> result = new ResultPage<ProjectFile>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = projectFileMapper.getProjectFileCount(query);
        if(total > 0){
            List<ProjectFile> list = projectFileMapper.getFileTypeByCondition(query);
            list = getPersonUploadFile(list, query);
            if (list == null || list.isEmpty()) {
                result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
                result.setSuccess(false);
            } else {
                result.setSuccess(true);
                result.setList(list);
            }
        } else {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }

        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#updateProjectFileById(com.zjzmjr.core.model.project.ProjectFile)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateProjectFileById(ProjectFile record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = projectFileMapper.updateProjectFileById(record);
        if(total > 0){
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":文件资料表更新失败");
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#insertProjectFile(com.zjzmjr.core.model.project.ProjectFile)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertProjectFile(ProjectFile record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getName())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = projectFileMapper.insertProjectFile(record);
        if(total > 0){
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":文件资料表插入失败");
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#deleteProjectFileById(java.lang.Integer)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteProjectFileById(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(id)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        ProjectFileQuery query = new ProjectFileQuery();
        query.setFileId(id);
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        int count = projectFileUploadMapper.getFileUploadCount(query);
        if (count > 0) {
            result.setSuccess(false);
            result.setErrorMsg("使用中的资料名称不能删除");
            result.setT(count);
        } else {
            int total = projectFileMapper.deleteProjectFileById(id);
            if(total > 0){
                result.setSuccess(true);
                result.setT(total);
            } else {
                result.setSuccess(false);
                result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":文件资料表删除失败");
            }
        }
        
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#getFileUploadByCondition(com.zjzmjr.core.model.project.ProjectFileQuery)
     */
    @Override
    public ResultPage<ProjectFileInfo> getFileUploadByCondition(ProjectFileQuery query) {
        ResultPage<ProjectFileInfo> result = new ResultPage<ProjectFileInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = projectFileUploadMapper.getFileUploadCount(query);
        if(total > 0){
            List<ProjectFileInfo> list = projectFileUploadMapper.getFileUploadByCondition(query);
            result.setSuccess(true);
            result.setList(list);
        } else {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }

        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#getFileUploadById(java.lang.Integer)
     */
    @Override
    public ResultEntry<ProjectFileUpload> getFileUploadById(Integer id) {
        ResultEntry<ProjectFileUpload> result = new ResultEntry<ProjectFileUpload>();
        if (Util.isNull(id)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        ProjectFileUpload upload = projectFileUploadMapper.getFileUploadById(id);
        if (upload == null) {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setT(upload);
        }

        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#insertFileUpload(com.zjzmjr.core.model.project.ProjectFileUpload)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertFileUpload(ProjectFileUpload record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) ||  Util.isNull(record.getFileId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = projectFileUploadMapper.insertFileUpload(record);
        if(total > 0){
            result.setSuccess(true);
            result.setT(record.getId());
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":资料上传表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#updateFileUploadById(com.zjzmjr.core.model.project.ProjectFileUpload)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateFileUploadById(ProjectFileUpload record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getProjectId()) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = projectFileUploadMapper.updateFileUploadById(record);
        if(total > 0){
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":资料上传表更新失败");
        }
        return result;
    }
    
    /**
     * 根据用户的职位编号，确定用户能够看到的文件类型
     * 
     * @param fileLst
     * @param query
     * @return
     */
    private List<ProjectFile> getPersonUploadFile(List<ProjectFile> fileLst, ProjectFileQuery query){
        if (Util.isNull(query.getUserId()) || Util.isNull(query.getHandlerJob())) {
            return fileLst;
        }

        boolean isLeader = false;
        boolean isManager = false;
        int jobId = -1;
        List<ProjectFile> result = new ArrayList<ProjectFile>();
        
        GardenProjectQuery proQuery = new GardenProjectQuery();
        proQuery.setCompanyId(query.getCompanyId());
        proQuery.setUserId(query.getUserId());
        proQuery.setSubStatus(GardenProjectStatusEnum.TEMPORARY.getValue());
        proQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<GardenProjectInfo> proRst = gardenProjectService.getGardenProjectByCondition(proQuery);
        if(proRst.isSuccess()){
            // 设计总监
            StaffInfoQuery staffquery = new StaffInfoQuery();
            staffquery.setUserId(query.getUserId());
            staffquery.setCompanyId(query.getCompanyId());
            staffquery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<StaffBasicInfo> staffRst = staffInfoService.getStaffInfoByCondition(staffquery);
            if (staffRst.isSuccess()) {
                jobId = staffRst.getList().get(0).getUserInfo().getJobId();
                switch (staffRst.getList().get(0).getUserInfo().getJobId()) {
                case 5:
                case 6:
                    isLeader = true;
                    break;
                case 4:
                    // 经营专员
                    isManager =true;
                    break;
                }
            }

            // 外部人员
            ExternalPersonQuery personquery = new ExternalPersonQuery();
            personquery.setUserId(query.getUserId());
            personquery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<ExternalPersonInfo> personRst = externalPersonService.getExternalPersonByCondition(personquery);
            if (personRst.isSuccess()) {
                switch (personRst.getList().get(0).getPersonnelNature()) {
                case 44:
                    isLeader = true;
                    break;
                }
            }

            for (ProjectFile file : fileLst) {
                if (StringUtil.stringToInteger("1").equals(query.getHandlerJob())) {
                    // 操作权限
                    if (isLeader && file.getEditJob().contains("5")) {
                        // 技术负责人的时候
                        result.add(file);
                    } else if (isManager && file.getEditJob().contains("4")) {
                        // 经营专员的时候
                        result.add(file);
                    } else if ((jobId == 11 || jobId == 12) && file.getEditJob().contains(String.valueOf(jobId))) {
                        // 属于总师办的人员
                        result.add(file);
                    }
                } else if (StringUtil.stringToInteger("2").equals(query.getHandlerJob())) {
                    // 查看权限
                    if (isLeader && file.getViewJob().contains("5")) {
                        // 技术负责人的时候
                        result.add(file);
                    } else if (isManager && file.getViewJob().contains("4")) {
                        // 经营专员的时候
                        result.add(file);
                    } else if (!(jobId == 4 || jobId == 5) && file.getViewJob().contains(String.valueOf(jobId))) {
                        // 属于总师办的人员
                        result.add(file);
                    }
                }
            }

        }
        
        return result;
    }

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#deleteFileUploadHandler(com.zjzmjr.core.model.project.ProjectFileUpload)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteFileUploadHandler(ProjectFileUpload record) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId()) || Util.isNull(record.getFileName())
                || Util.isNull(record.getFileAddress())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        ProjectFileUpload fileUpload = projectFileUploadMapper.getFileUploadById(record.getId());
        if(fileUpload == null){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }else{
            int total = projectFileUploadMapper.deleteFileUploadById(record.getId());
            if(total > 0){
                String address = fileUpload.getFileAddress();
                address = address.replace(record.getFileAddress(), record.getFileName());
                File file = new File(address);
                if(file.exists()){
                    file.delete();
                }
//                else{
//                    throw new ApplicationException("删除物理文件出错");
//                }
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setErrorMsg("删除文件失败");
            }
            result.setT(total);
        }
        
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#getProjectFileName(com.zjzmjr.core.model.project.ProjectFileQuery)
     */
    @Override
    public ResultRecord<ProjectFile> getProjectFileName(ProjectFileQuery query) {
        ResultRecord<ProjectFile> result = new ResultRecord<ProjectFile>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<ProjectFile> list =  projectFileMapper.getProjectFileName(query);
        if(list==null || list.size()==0){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }else{
            result.setList(list);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#getStaffFile(com.zjzmjr.core.model.project.ProjectFileQuery)
     */
    @Override
    public ResultRecord<ProjectFileUpload> getStaffFile(ProjectFileQuery query) {
        ResultRecord<ProjectFileUpload> result = new ResultRecord<ProjectFileUpload>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<ProjectFileUpload> list =  projectFileUploadMapper.getStaffFile(query);
        if(list==null || list.size()==0){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }else{
            result.setList(list);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectFileUploadService#getFileByBasic(com.zjzmjr.core.model.project.ProjectFileQuery)
     */
    @Override
    public ResultRecord<ProjectFileInfo> getFileByBasic(ProjectFileQuery query) {
        ResultRecord<ProjectFileInfo> result = new ResultRecord<ProjectFileInfo>();
        if(Util.isNull(query) || Util.isNull(query.getCategoryId()) || Util.isNull(query.getAttributeId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result; 
        }
        List<ProjectFileInfo> list =  projectFileUploadMapper.getFileByBasic(query);
        if(list==null || list.size()==0){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }else{
            result.setList(list);
            result.setSuccess(true);
        }
        return result;
    }
    
}
