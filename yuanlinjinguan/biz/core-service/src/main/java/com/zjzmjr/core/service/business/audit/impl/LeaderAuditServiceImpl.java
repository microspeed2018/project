package com.zjzmjr.core.service.business.audit.impl;

import java.text.MessageFormat;
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
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.message.MessageStatusEnum;
import com.zjzmjr.core.enums.message.MessageTypeEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.AdministrativeSeal;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectLeaderAudit;
import com.zjzmjr.core.model.audit.LeaderAudit;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.service.business.audit.AdministrativeSealService;
import com.zjzmjr.core.service.business.audit.FinancialAuditService;
import com.zjzmjr.core.service.business.audit.LeaderAuditService;
import com.zjzmjr.core.service.business.audit.OfficeAuditService;
import com.zjzmjr.core.service.business.common.CommonMessageUtil;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.mapper.dao.audit.LeaderAuditMapper;

/**
 * 技术审核处理
 * 
 * @author lenovo
 * @version $Id: LeaderAuditServiceImpl.java, v 0.1 2017-9-4 下午3:38:50 lenovo Exp $
 */
@Service("leaderAuditService")
public class LeaderAuditServiceImpl implements LeaderAuditService{

    private static final Logger logger = LoggerFactory.getLogger(ManageAuditServiceImpl.class);

    @Resource(name = "leaderAuditMapper")
    private LeaderAuditMapper leaderAuditMapper;    

    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;

    @Resource(name = "administrativeSealService")
    private AdministrativeSealService administrativeSealService;
    
    @Resource(name = "officeAuditService")
    private OfficeAuditService officeAuditService;
    
    @Resource(name = "financialAuditService")
    private FinancialAuditService  financialAuditService;

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.LeaderAuditService#getProjectLeaderAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectLeaderAudit> getProjectLeaderAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectLeaderAudit> result = new ResultPage<GardenProjectLeaderAudit>();
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
            total = leaderAuditMapper.getProjectLeaderAuditCount(query);
            if (total > 0) {
                List<GardenProjectLeaderAudit> list = leaderAuditMapper.getProjectLeaderAudit(query);
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
     * @see com.zjzmjr.core.service.business.audit.LeaderAuditService#updateLeaderAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateLeaderAudit(GardenProjectAuditQuery query) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        LeaderAudit leader = new LeaderAudit();
        GardenProject project = new GardenProject();
        GardenProjectAuditQuery manageQuery = new GardenProjectAuditQuery();
        ResultPage<GardenProjectLeaderAudit> manageRst = null;
        leader.setStatus(query.getStatus());
        leader.setUpdateTime(new Date());
        leader.setUpdateUserId(query.getUpdateUserId());
        leader.setMemo(query.getMemo());
        if (Util.isNull(query.getId())) {
            // 通过 项目id 项目步骤 公司id查询
            manageQuery.setCompanyId(query.getCompanyId());
            manageQuery.setProjectId(query.getProjectId());
            manageQuery.setType(query.getType());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            manageRst = getProjectLeaderAudit(manageQuery);
            if (!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())) {
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                result.setSuccess(false);
                result.setErrorMsg("此项目已被审核!");
                return result;
            } else if (manageRst.isSuccess()) {
                leader.setId(manageRst.getList().get(0).getId());
            }
        } else {
            manageQuery.setId(query.getId());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            manageRst = getProjectLeaderAudit(manageQuery);
            if (!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())) {
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                result.setSuccess(false);
                result.setErrorMsg("此项目已被审核!");
                return result;
            } else if (manageRst.isSuccess()) {
                leader.setId(manageRst.getList().get(0).getId());
            }
        }        
        int cnt = leaderAuditMapper.updateLeaderAuditById(leader);
        if (cnt > 0) {
            Message message = new Message();
            message.setCompanyId(query.getCompanyId());
            message.setStatus(MessageStatusEnum.UNREAD.getValue());
            message.setType(MessageTypeEnum.REMIND.getValue());
            message.setCreateTime(new Date());
            message.setCreateUserId(query.getUpdateUserId());
            message.setUpdateTime(message.getCreateTime());
            message.setUpdateUserId(message.getCreateUserId());
            // 获取项目
            GardenProject msgProject = CommonMessageUtil.getProjectInfoById(query.getProjectId());
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                if (Util.isNotNull(query.getStep())) {
                    if (GardenProjectStepEnum.P_80.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_90.getValue());
                        // 新增数据至行政盖章表
                        AdministrativeSeal administrativeSeal = new AdministrativeSeal();
                        administrativeSeal.setCompanyId(query.getCompanyId());
                        administrativeSeal.setProjectId(query.getProjectId());
                        administrativeSeal.setType(33);
                        administrativeSeal.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        administrativeSeal.setCreateTime(new Date());
                        administrativeSeal.setCreateUserId(query.getUpdateUserId());
                        result = administrativeSealService.insertAdministrativeSeal(administrativeSeal);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("技术审核报名时,行政盖章表插入失败");
                        }
                        message.setTitle(MessageContextEnum.Msg_110.getValue());
                        message.setAddress(MessageContextEnum.Msg_110.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_110.getMessage(), 
                                    msgProject.getName()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, CommonMessageUtil.getXingZhengPersons(query.getCompanyId()));
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(msgProject.getName());
                        pointSymbol.setSealType("报名文件拟定");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_110.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, CommonMessageUtil.getXingZhengPersons(query.getCompanyId()));
                        message.setTitle(MessageContextEnum.Msg_120.getValue());
                        message.setAddress(MessageContextEnum.Msg_120.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_120.getMessage(), 
                                    msgProject.getName()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, msgProject.getManagementPersonnel());
                        pointSymbol.setAuditType("报名文件");
                        pointSymbol.setJob("负责人");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_120.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, msgProject.getManagementPersonnel());
                    } else if (GardenProjectStepEnum.P_210.getValue().equals(query.getStep())) {
                        //项目是否 为重点项目
                        if(GenerateConstants.number_0.equals(query.getIsMajorProject())){
                            project.setStep(GardenProjectStepEnum.P_230.getValue());
                            // 新增数据至行政盖章表
                            AdministrativeSeal administrativeSeal = new AdministrativeSeal();
                            administrativeSeal.setCompanyId(query.getCompanyId());
                            administrativeSeal.setProjectId(query.getProjectId());
                            administrativeSeal.setType(34);
                            administrativeSeal.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                            administrativeSeal.setCreateTime(new Date());
                            administrativeSeal.setCreateUserId(query.getUpdateUserId());
                            result = administrativeSealService.insertAdministrativeSeal(administrativeSeal);
                            if (!result.isSuccess()) {
                                throw new ApplicationException("技术审核商务标时,行政盖章表插入失败");
                            }
                        }else{
                            project.setStep(GardenProjectStepEnum.P_220.getValue());
                            // 新增数据至院办审核表
                            OfficeAudit office = new OfficeAudit();
                            office.setCompanyId(query.getCompanyId());
                            office.setProjectId(query.getProjectId());
                            office.setApplicant(manageRst.getList().get(0).getApplicant());
                            office.setType(53);
                            office.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                            office.setCreateTime(new Date());
                            office.setCreateUserId(query.getUpdateUserId());
                            result = officeAuditService.insertOfficeAudit(office);
                            if (!result.isSuccess()) {
                                throw new ApplicationException("技术审核商务标时,院办审核表插入失败");
                            } 
                        }
                        
                    } else if (GardenProjectStepEnum.P_320.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_330.getValue());
                        // 新增数据至财务审核表
                        FinancialAudit audit = new FinancialAudit();
                        audit.setCompanyId(query.getCompanyId());
                        audit.setProjectId(query.getProjectId());
                        audit.setAmount(manageRst.getList().get(0).getAmount());
                        audit.setApplicant(manageRst.getList().get(0).getApplicant());
                        audit.setType(27);
                        audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        audit.setCreateTime(new Date());
                        audit.setCreateUserId(query.getUpdateUserId());
                        result = financialAuditService.insertFinancialAudit(audit);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("技术审核合同时,财务审核表插入失败");
                        }
                    }
                } else if (Util.isNotNull(query.getSubStep())) {
                    if (GardenProjectStepEnum.P_140.getValue().equals(query.getSubStep())) {
                        project.setSubStep(GardenProjectStepEnum.P_150.getValue());
                        // 新增数据至财务审核表
                        FinancialAudit audit = new FinancialAudit();
                        audit.setCompanyId(query.getCompanyId());
                        audit.setProjectId(query.getProjectId());
                        audit.setAmount(manageRst.getList().get(0).getAmount());
                        audit.setApplicant(manageRst.getList().get(0).getApplicant());
                        audit.setType(26);
                        audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        audit.setCreateTime(new Date());
                        audit.setCreateUserId(query.getUpdateUserId());
                        result = financialAuditService.insertFinancialAudit(audit);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("技术审核合同时,财务审核表插入失败");
                        }
                        // 技术审核保证金 --- 会计
                        message.setTitle(MessageContextEnum.Msg_200.getValue());
                        message.setAddress(MessageContextEnum.Msg_200.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_200.getMessage(), 
                                    msgProject.getName()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, CommonMessageUtil.getKuaijiPersons(query.getCompanyId()));
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(msgProject.getName());
                        pointSymbol.setAuditType("保证金");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_200.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, CommonMessageUtil.getKuaijiPersons(query.getCompanyId()));

                        // 技术审核保证金 --- 经营专员
                        message.setTitle(MessageContextEnum.Msg_210.getValue());
                        message.setAddress(MessageContextEnum.Msg_210.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_210.getMessage(), 
                                    msgProject.getName()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, msgProject.getManagementPersonnel());
                        pointSymbol = new PointSymbol();
                        pointSymbol.setName(msgProject.getName());
                        pointSymbol.setAuditType("保证金申请");
                        pointSymbol.setJob("负责人");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_210.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, msgProject.getManagementPersonnel());
                    }
                }else if(83==query.getType()){
                    //新增财务审核开发票
                    FinancialAudit audit = new FinancialAudit();
                    audit.setCompanyId(query.getCompanyId());
                    audit.setProjectId(query.getProjectId());
                    audit.setAmount(manageRst.getList().get(0).getAmount());
                    audit.setApplicant(manageRst.getList().get(0).getApplicant());
                    audit.setPaymentMode(manageRst.getList().get(0).getPaymentMode());
                    audit.setType(28);
                    audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    audit.setCreateTime(new Date());
                    audit.setCreateUserId(query.getUpdateUserId());
                    result = financialAuditService.insertFinancialAudit(audit);
                    if (!result.isSuccess()) {
                        throw new ApplicationException("技术审核合同时,财务审核表插入失败");
                    }
                }
            } else {
                if (Util.isNotNull(query.getStep())) {
                    if (GardenProjectStepEnum.P_80.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_60.getValue());
                        message.setTitle(MessageContextEnum.Msg_130.getValue());
                        message.setAddress(MessageContextEnum.Msg_130.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_130.getMessage(), 
                                    msgProject.getName(), query.getMemo()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, msgProject.getManagementPersonnel());
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(msgProject.getName());
                        pointSymbol.setAuditType("报名文件拟定");
                        pointSymbol.setReason(query.getMemo());
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_130.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, msgProject.getManagementPersonnel());
                    } else if (GardenProjectStepEnum.P_210.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_190.getValue());
                    } else if (GardenProjectStepEnum.P_320.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_300.getValue());
                    }
                } else if (Util.isNotNull(query.getSubStep())) {
                    if (GardenProjectStepEnum.P_140.getValue().equals(query.getSubStep())) {
                        project.setSubStep(GardenProjectStepEnum.P_120.getValue());
                        // 技术审核保证金 --- 经营专员
                        message.setTitle(MessageContextEnum.Msg_220.getValue());
                        message.setAddress(MessageContextEnum.Msg_220.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_220.getMessage(), 
                                    msgProject.getName(), query.getMemo()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, msgProject.getManagementPersonnel());
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(msgProject.getName());
                        pointSymbol.setAuditType("保证金申请");
                        pointSymbol.setReason(query.getMemo());
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_220.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, msgProject.getManagementPersonnel());

                    }
                }
            }
            // 更新项目进度
            project.setId(query.getProjectId());
            project.setUpdateUserId(query.getUpdateUserId());
            project.setUpdateTime(new Date());
            result = gardenProjectService.updateGardenProjectById(project);
            if (!result.isSuccess()) {
                throw new ApplicationException("经营审核时,项目进度更新失败");
            }
            result.setT(cnt);
            result.setSuccess(true);
        } else {
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":经营审核更新失败");
        }
        return result;
    }


    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.LeaderAuditService#insertLeaderAudit(com.zjzmjr.core.model.audit.LeaderAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertLeaderAudit(LeaderAudit leaderAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(leaderAudit)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int cnt = leaderAuditMapper.insertLeaderAudit(leaderAudit);
        if(cnt > 0){
            result.setT(cnt);
            result.setSuccess(true);
        }else{
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
        }
        return result;
    }


    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.LeaderAuditService#getProjectLawAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectLeaderAuditCount(GardenProjectAuditQuery query) {
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
            total = leaderAuditMapper.getProjectLeaderAuditCount(query);
        }
        result.setSuccess(true);
        result.setT(total);
        return result;
    }
}
