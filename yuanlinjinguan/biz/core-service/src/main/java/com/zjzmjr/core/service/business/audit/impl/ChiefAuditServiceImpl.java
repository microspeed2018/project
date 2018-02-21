package com.zjzmjr.core.service.business.audit.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.AdministrativeSeal;
import com.zjzmjr.core.model.audit.ChiefAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectChiefAudit;
import com.zjzmjr.core.model.audit.LeaderAudit;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.service.business.audit.AdministrativeSealService;
import com.zjzmjr.core.service.business.audit.ChiefAuditService;
import com.zjzmjr.core.service.business.audit.LeaderAuditService;
import com.zjzmjr.core.service.business.audit.OfficeAuditService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.mapper.dao.audit.ChiefAuditMapper;
import com.zjzmjr.core.service.mapper.dao.project.ProjectContractMapper;


/**
 * 总工审核处理层
 * 
 * @author lenovo
 * @version $Id: ChiefAuditServiceImpl.java, v 0.1 2017-9-1 上午10:21:25 lenovo Exp $
 */
@Service("chiefAuditService")
public class ChiefAuditServiceImpl implements ChiefAuditService{

    private static final Logger logger = LoggerFactory.getLogger(ChiefAuditServiceImpl.class);

    @Resource(name = "chiefAuditMapper")
    private ChiefAuditMapper chiefAuditMapper;
    
    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    @Resource(name = "officeAuditService")
    private OfficeAuditService officeAuditService;
    
    @Resource(name = "administrativeSealService")
    private AdministrativeSealService administrativeSealService;
    
    @Resource(name = "leaderAuditService")
    private LeaderAuditService leaderAuditService;
    
    @Resource(name = "projectContractMapper")
    private ProjectContractMapper  projectContractMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.ChiefAuditService#getProjectChiefAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectChiefAudit> getProjectChiefAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectChiefAudit> result = new ResultPage<GardenProjectChiefAudit>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        int total = 0;
        GardenProjectQuery gQuery = new GardenProjectQuery();
        gQuery.setCompanyId(query.getCompanyId());
        gQuery.setProjectNo(query.getProjectNo());
        gQuery.setUserId(query.getUpdateUserId());
        gQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<GardenProjectInfo> projectRst = gardenProjectService.getGardenProjectByCondition(gQuery);
        if (projectRst.isSuccess()) {
            List<Integer> projectLst = new ArrayList<Integer>();
            for (GardenProjectInfo projectInfo : projectRst.getList()) {
                projectLst.add(projectInfo.getId());
            }
            query.setProjectLst(projectLst);
            total = chiefAuditMapper.getProjectChiefAuditCount(query);
            if (total > 0) {
                List<GardenProjectChiefAudit> list = chiefAuditMapper.getProjectChiefAudit(query);
                result.setList(list);
                result.setSuccess(true);
            } else {
                result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
                result.setSuccess(false);
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
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.audit.ChiefAuditService#updateChiefAuditById(com.zjzmjr.core.model.audit.ChiefAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateChiefAudit(GardenProjectAuditQuery query) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        ChiefAudit chiefAudit = new ChiefAudit();
        GardenProject project = new GardenProject();
        GardenProjectAuditQuery manageQuery= new GardenProjectAuditQuery();
        ResultPage<GardenProjectChiefAudit> manageRst = null;
        chiefAudit.setStatus(query.getStatus());
        chiefAudit.setUpdateTime(new Date());
        chiefAudit.setUpdateUserId(query.getUpdateUserId());
        chiefAudit.setMemo(query.getMemo());
        if(Util.isNull(query.getId())){
            //通过 项目id 项目步骤  公司id查询 
            manageQuery.setCompanyId(query.getCompanyId());
            manageQuery.setProjectId(query.getProjectId());
            manageQuery.setType(query.getType());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            manageRst = getProjectChiefAudit(manageQuery);
            if(!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())){
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                result.setErrorMsg("此项目已被审核!");
                result.setSuccess(false);
                return result;
            }else if(manageRst.isSuccess()){
                chiefAudit.setId(manageRst.getList().get(0).getId()); 
            }
        }else{
            chiefAudit.setId(query.getId()); 
        }  
        int cnt = chiefAuditMapper.updateChiefAuditById(chiefAudit);
        if(cnt > 0){
            if(ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())){
                if (Util.isNotNull(query.getStep())) {
                    if (GardenProjectStepEnum.P_410.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_420.getValue());
                    }else if(GardenProjectStepEnum.P_440.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_450.getValue());
                    }else if(GardenProjectStepEnum.P_470.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_480.getValue());
                    }else if(GardenProjectStepEnum.P_500.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_510.getValue());
                    }else if(GardenProjectStepEnum.P_315.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_320.getValue());
                        //新增技术审核表
                        LeaderAudit leaderAudit = new LeaderAudit();
                        leaderAudit.setCompanyId(query.getCompanyId());
                        leaderAudit.setProjectId(query.getProjectId());
                        leaderAudit.setType(72);
                        leaderAudit.setApplicant(manageRst.getList().get(0).getApplicant());
                        leaderAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        leaderAudit.setCreateTime(new Date());
                        leaderAudit.setCreateUserId(query.getUpdateUserId());
                        result = leaderAuditService.insertLeaderAudit(leaderAudit);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("总工审核时,技术审核表插入失败");
                        }
                    }
                }else if(Util.isNotNull(query.getSubStep2())){
                    if (GardenProjectStepEnum.P_250.getValue().equals(query.getSubStep2())){
                        //判断是否重点标
                        if(GenerateConstants.number_0.equals(query.getIsMajorProject())){
                            project.setSubStep2(GardenProjectStepEnum.P_270.getValue());
                            // 新增数据至行政盖章表
                            AdministrativeSeal administrativeSeal = new AdministrativeSeal();
                            administrativeSeal.setCompanyId(query.getCompanyId());
                            administrativeSeal.setProjectId(query.getProjectId());
                            administrativeSeal.setType(35);
                            administrativeSeal.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                            administrativeSeal.setCreateTime(new Date());
                            administrativeSeal.setCreateUserId(query.getUpdateUserId());
                            result = administrativeSealService.insertAdministrativeSeal(administrativeSeal);
                            if (!result.isSuccess()) {
                                throw new ApplicationException("总工审核技术标时,行政盖章表插入失败");
                            }
                        }else{
                            project.setSubStep2(GardenProjectStepEnum.P_260.getValue());
                            // 新增数据至院办审核表
                            OfficeAudit office = new OfficeAudit();
                            office.setCompanyId(query.getCompanyId());
                            office.setProjectId(query.getProjectId());
                            office.setApplicant(manageRst.getList().get(0).getApplicant());
                            office.setType(54);
                            office.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                            office.setCreateTime(new Date());
                            office.setCreateUserId(query.getUpdateUserId());
                            result = officeAuditService.insertOfficeAudit(office);
                            if (!result.isSuccess()) {
                                throw new ApplicationException("总工审核技术标时,院办审核表插入失败");
                            } 
                        }                       
                    } 
                }               
            }else{
                if(Util.isNotNull(query.getStep())){
                     if(GardenProjectStepEnum.P_410.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_400.getValue());
                    }else if(GardenProjectStepEnum.P_440.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_430.getValue());
                    }else if(GardenProjectStepEnum.P_470.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_460.getValue());
                    }else if(GardenProjectStepEnum.P_500.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_490.getValue());
                    }else if(GardenProjectStepEnum.P_315.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_300.getValue());
                    }
                }else if(Util.isNotNull(query.getSubStep2())){
                    if(GardenProjectStepEnum.P_250.getValue().equals(query.getSubStep2())){
                        project.setSubStep2(GardenProjectStepEnum.P_240.getValue());          
                    } 
                }            
            }
            //更新项目进度           
            project.setId(query.getProjectId());           
            project.setUpdateUserId(query.getUpdateUserId());
            project.setUpdateTime(new Date());
            result = gardenProjectService.updateGardenProjectById(project);
            if(!result.isSuccess()){
                throw new ApplicationException("总工审核时,项目进度更新失败");
            }
        }else{
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":总工审核表更新失败"); 
        }     
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.ChiefAuditService#insertChiefAudit(com.zjzmjr.core.model.audit.ChiefAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertChiefAudit(ChiefAudit chiefAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(chiefAudit) || Util.isNull(chiefAudit.getProjectId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        int total = chiefAuditMapper.insertChiefAudit(chiefAudit);
        if (total > 0) {
            result.setT(total);
            result.setSuccess(true);
        } else {
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":总工审核表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.ChiefAuditService#getProjectChiefAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectChiefAuditCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        int total = 0;
        GardenProjectQuery gQuery = new GardenProjectQuery();
        gQuery.setCompanyId(query.getCompanyId());
        gQuery.setProjectNo(query.getProjectNo());
        gQuery.setUserId(query.getUpdateUserId());
        gQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<GardenProjectInfo> projectRst = gardenProjectService.getGardenProjectByCondition(gQuery);
        if (projectRst.isSuccess()) {
            List<Integer> projectLst = new ArrayList<Integer>();
            for (GardenProjectInfo projectInfo : projectRst.getList()) {
                projectLst.add(projectInfo.getId());
            }
            query.setProjectLst(projectLst);
            total = chiefAuditMapper.getProjectChiefAuditCount(query);
        }
        result.setSuccess(true);
        result.setT(total);
        return result;
    }
    
}
