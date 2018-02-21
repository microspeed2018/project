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
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.ChiefAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectManageAudit;
import com.zjzmjr.core.model.audit.LeaderAudit;
import com.zjzmjr.core.model.audit.ManageAudit;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.ContractPayment;
import com.zjzmjr.core.model.project.ContractPaymentInfo;
import com.zjzmjr.core.model.project.ContractPaymentQuery;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.service.business.audit.ChiefAuditService;
import com.zjzmjr.core.service.business.audit.LeaderAuditService;
import com.zjzmjr.core.service.business.audit.ManageAuditService;
import com.zjzmjr.core.service.business.common.CommonMessageUtil;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.mapper.dao.audit.ManageAuditMapper;
import com.zjzmjr.core.service.mapper.dao.project.ContractPaymentMapper;
import com.zjzmjr.core.service.mapper.dao.project.ProjectContractMapper;

@Service("manageAuditService")
public class ManageAuditServiceImpl implements ManageAuditService{

    private static final Logger logger = LoggerFactory.getLogger(ManageAuditServiceImpl.class);

    @Resource(name = "manageAuditMapper")
    private ManageAuditMapper manageAuditMapper;    

    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    @Resource(name = "leaderAuditService")
    private LeaderAuditService leaderAuditService;
    
    @Resource(name = "chiefAuditService")
    private ChiefAuditService chiefAuditService;
    
    @Resource(name = "projectContractMapper")
    private ProjectContractMapper  projectContractMapper;
    
    @Resource(name = "contractPaymentMapper")
    private ContractPaymentMapper  contractPaymentMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.ManageAuditService#getProjectManageAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectManageAudit> getProjectManageAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectManageAudit> result = new ResultPage<GardenProjectManageAudit>();
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
            total = manageAuditMapper.getProjectManageAuditCount(query);
            if (total > 0) {
                List<GardenProjectManageAudit> list = manageAuditMapper.getProjectManageAudit(query);
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
     * @see com.zjzmjr.core.service.business.audit.ManageAuditService#updateManageAuditById(com.zjzmjr.core.model.audit.OfficeAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateManageAudit(GardenProjectAuditQuery query) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        ManageAudit manage = new ManageAudit();
        GardenProject project = new GardenProject();
        GardenProjectAuditQuery manageQuery = new GardenProjectAuditQuery();
        manage.setStatus(query.getStatus());
        manage.setUpdateTime(new Date());
        manage.setUpdateUserId(query.getUpdateUserId());
        ResultPage<GardenProjectManageAudit> manageRst = null;
        if (Util.isNull(query.getId())) {
            // 通过 项目id 项目步骤 公司id查询
            manageQuery.setCompanyId(query.getCompanyId());
            manageQuery.setProjectId(query.getProjectId());
            manageQuery.setType(query.getType());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            manageRst = getProjectManageAudit(manageQuery);
            if (!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())) {
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                result.setSuccess(false);
                result.setErrorMsg("此项目已被审核!");
                return result;
            } else if (manageRst.isSuccess()) {
                manage.setId(manageRst.getList().get(0).getId());
            }
        } else {
            manage.setId(query.getId());
        }
        manage.setMemo(query.getMemo());
        int cnt = manageAuditMapper.updateManageAuditById(manage);
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
                    if (GardenProjectStepEnum.P_70.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_80.getValue());
                        //新增技术审核表
                        LeaderAudit leaderAudit = new LeaderAudit();
                        leaderAudit.setCompanyId(query.getCompanyId());
                        leaderAudit.setProjectId(query.getProjectId());
                        leaderAudit.setType(69);
                        leaderAudit.setApplicant(manageRst.getList().get(0).getApplicant());
                        leaderAudit.setAmount(manageRst.getList().get(0).getAmount());
                        leaderAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        leaderAudit.setCreateTime(new Date());
                        leaderAudit.setCreateUserId(query.getUpdateUserId());
                        result = leaderAuditService.insertLeaderAudit(leaderAudit);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("经营审核时,技术审核表插入失败");
                        }
                        message.setTitle(MessageContextEnum.Msg_90.getValue());
                        message.setAddress(MessageContextEnum.Msg_90.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_90.getMessage(), 
                                msgProject.getName()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, msgProject.getManagementPersonnel());
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(msgProject.getName());
                        pointSymbol.setAuditType("报名文件");
                        pointSymbol.setJob("经营");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_90.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, msgProject.getManagementPersonnel());
                    } else if (GardenProjectStepEnum.P_200.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_210.getValue());
                        //新增技术审核表
                        LeaderAudit leaderAudit = new LeaderAudit();
                        leaderAudit.setCompanyId(query.getCompanyId());
                        leaderAudit.setProjectId(query.getProjectId());
                        leaderAudit.setType(71);
                        leaderAudit.setApplicant(manageRst.getList().get(0).getApplicant());
                        leaderAudit.setAmount(manageRst.getList().get(0).getAmount());
                        leaderAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        leaderAudit.setCreateTime(new Date());
                        leaderAudit.setCreateUserId(query.getUpdateUserId());
                        result = leaderAuditService.insertLeaderAudit(leaderAudit);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("经营审核时,技术审核表插入失败");
                        }
                    } else if (GardenProjectStepEnum.P_310.getValue().equals(query.getStep())) {
                        if(GenerateConstants.number_1.equals(query.getIschief())){
                            project.setStep(GardenProjectStepEnum.P_315.getValue());
                            //新增总工审核表
                            ChiefAudit chiefAudit = new ChiefAudit();
                            chiefAudit.setCompanyId(query.getCompanyId());
                            chiefAudit.setProjectId(query.getProjectId());
                            chiefAudit.setType(77);
                            chiefAudit.setApplicant(manageRst.getList().get(0).getApplicant());
                            chiefAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                            chiefAudit.setCreateTime(new Date());
                            chiefAudit.setCreateUserId(query.getUpdateUserId());
                            result = chiefAuditService.insertChiefAudit(chiefAudit);
                            if (!result.isSuccess()) {
                                throw new ApplicationException("经营审核时,总工审核表插入失败");
                            } 
                        }else if(GenerateConstants.number_2.equals(query.getIschief())){
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
                                throw new ApplicationException("经营审核时,技术审核表插入失败");
                            } 
                        }
                        
                    }
                } else if (Util.isNotNull(query.getSubStep())) {
                    if (GardenProjectStepEnum.P_130.getValue().equals(query.getSubStep())) {
                        project.setSubStep(GardenProjectStepEnum.P_140.getValue());
                      //新增技术审核表
                        LeaderAudit leaderAudit = new LeaderAudit();
                        leaderAudit.setCompanyId(query.getCompanyId());
                        leaderAudit.setProjectId(query.getProjectId());
                        leaderAudit.setType(70);
                        leaderAudit.setApplicant(manageRst.getList().get(0).getApplicant());
                        leaderAudit.setAmount(manageRst.getList().get(0).getAmount());
                        leaderAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        leaderAudit.setCreateTime(new Date());
                        leaderAudit.setCreateUserId(query.getUpdateUserId());
                        result = leaderAuditService.insertLeaderAudit(leaderAudit);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("经营审核时,技术审核表插入失败");
                        }
                        message.setTitle(MessageContextEnum.Msg_180.getValue());
                        message.setAddress(MessageContextEnum.Msg_180.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_180.getMessage(), 
                                msgProject.getName()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, msgProject.getManagementPersonnel());
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(msgProject.getName());
                        pointSymbol.setAuditType("保证金申请");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_180.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, msgProject.getManagementPersonnel());
                        message.setUserId(msgProject.getProjectLeader());
                        message.setTitle(MessageContextEnum.Msg_160.getValue());
                        message.setAddress(MessageContextEnum.Msg_160.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_160.getMessage(), 
                                msgProject.getName()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message);
                        pointSymbol = new PointSymbol();
                        pointSymbol.setName(msgProject.getName());
                        pointSymbol.setAuditType("保证金");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_160.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, msgProject.getProjectLeader());
                    }
                }
                if(query.getType()==50){
                    // 审核项目修改通过将临时数据付给原有数据
                    GardenProjectQuery projectQuery = new GardenProjectQuery();
                    projectQuery.setId(query.getProjectId());
                    projectQuery.setStatus(GardenProjectStatusEnum.NOT_VERIFY.getValue());
                    projectQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                    ResultPage<GardenProjectInfo> projectRst = gardenProjectService.getGardenProjectByCondition(projectQuery);
                    if (projectRst.isSuccess()) {
                        // 查询临时数据
                        GardenProject projects = projectRst.getList().get(0);
                        projectQuery.setId(Integer.parseInt(projectRst.getList().get(0).getMemo()));
                        projectQuery.setStatus(GardenProjectStatusEnum.TEMPORARY.getValue());
                        projectQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                        projectRst = gardenProjectService.getGardenProjectByCondition(projectQuery);
                        if (projectRst.isSuccess()) {
                            int id = projectRst.getList().get(0).getId();
                            //GardenProject projects = projectRst.getList().get(0);
                            projects.setId(query.getProjectId());
                            projects.setName(projectRst.getList().get(0).getName()); 
                            projects.setContractAwardCompany(projectRst.getList().get(0).getContractAwardCompany());
                            projects.setDesignArea(projectRst.getList().get(0).getDesignArea());
                            projects.setInvestmentMount(projectRst.getList().get(0).getInvestmentMount());
                            projects.setCategory(projectRst.getList().get(0).getCategory());
                            projects.setType(projectRst.getList().get(0).getType());
                            projects.setCity(projectRst.getList().get(0).getCity());
                            projects.setAddress(projectRst.getList().get(0).getAddress());
                            projects.setHaveScheme(projectRst.getList().get(0).getHaveScheme());
                            projects.setHaveDevelopment(projectRst.getList().get(0).getHaveDevelopment());
                            projects.setHaveDrawing(projectRst.getList().get(0).getHaveDrawing());
                            projects.setHavePlanning(projectRst.getList().get(0).getHavePlanning());
                            projects.setUpdateTime(new Date());
                            projects.setUpdateUserId(query.getUpdateUserId());
                            result = gardenProjectService.updateGardenProjectById(projects);
                            if (!result.isSuccess()) {
                                throw new ApplicationException("经营审核项目修改时,修改项目表失败");
                            } else {
                                // 删除临时数据
                                result = gardenProjectService.deleteGardenProject(id);
                                if (!result.isSuccess()) {
                                    throw new ApplicationException("经营审核项目修改时,原项目删除失败");
                                }
                            }
                        }
                    }
                   
                }else if(query.getType()==51){                    
                    // 审核合同
                    // 获取修改合同信息
                    ContractQuery contractQuery = new ContractQuery();
                    contractQuery.setProjectId(query.getProjectId());
                    contractQuery.setStatus(GenerateConstants.number_1);
                    List<ProjectContract> contractRst = projectContractMapper.getProjectContractByProjectIdAndStatus(contractQuery);
                    if (Util.isNotNull(contractRst) && contractRst.size() > 0) {
                        ProjectContract contract = contractRst.get(0);
                        contractQuery.setStatus(GenerateConstants.number_0);
                        List<ProjectContract> contractRsts = projectContractMapper.getProjectContractByProjectIdAndStatus(contractQuery);
                        if (Util.isNotNull(contractRsts) && contractRsts.size() > 0) {
                            // 将临时数据更新到原有数据，并将临时数据删除
                            int id = contractRsts.get(0).getId();
                            //ProjectContract contract = contractRsts.get(0);
                            contract.setContractCapital(contractRsts.get(0).getContractCapital());
                            contract.setSignDate(contractRsts.get(0).getSignDate());
                            contract.setContractMemo(contractRsts.get(0).getContractMemo());
                            contract.setUpdateTime(new Date());
                            contract.setStatus(GenerateConstants.number_1);
                            contract.setUpdateUserId(query.getUpdateUserId());
                            int total = projectContractMapper.updateProjectContractById(contract);
                            if (total > 0) {
                                total = projectContractMapper.deleteProjectContractById(id);
                                if (total <= 0) {
                                    throw new ApplicationException("经营审核合同修改时,删除合同表失败");
                                }
                                //查询有无未审核状态数据
                                ContractPaymentQuery contractPaymentQuery = new ContractPaymentQuery();
                                contractPaymentQuery.setProjectId(query.getProjectId());
                                contractPaymentQuery.setStatus(GenerateConstants.number_1);
                                PageBean bean = new PageBean(Integer.MAX_VALUE, 1);
                                contractPaymentQuery.setPageBean(bean);
                                List<ContractPaymentInfo> payLst = contractPaymentMapper.getProjectContractPaymentInfo(contractPaymentQuery);
                                if(payLst!=null && payLst.size()>0){
                                    //将未审核状态的合同付款设置为审核通过，并将原有的审核通过状态的删除
                                    ContractQuery contractpayQuery = new ContractQuery();
                                    contractpayQuery.setStatus(GenerateConstants.number_1);
                                    contractpayQuery.setProjectId(query.getProjectId());
                                    contractpayQuery.setCompanyId(query.getCompanyId());
                                    contractPaymentMapper.deleteContractPaymentByCondition(contractpayQuery);
                                    //修改未审核状态为审核通过
                                        ContractPayment record = new ContractPayment();
                                        record.setProjectId(query.getProjectId());
                                        record.setCompanyId(query.getCompanyId());
                                        record.setStatus(GenerateConstants.number_1);
                                        int count = contractPaymentMapper.updateContractPaymentByCondition(record);
                                        if (count <= 0) {
                                            throw new ApplicationException("经营审核合同修改时,更新合同付款表失败");
                                        }
                                }
                               
                            } else {
                                throw new ApplicationException("经营审核合同修改时,修改合同表失败");
                            }
                        }
                    }
                }
            } else {
                if (Util.isNotNull(query.getStep())) {
                    if (GardenProjectStepEnum.P_70.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_60.getValue());
                        message.setTitle(MessageContextEnum.Msg_100.getValue());
                        message.setAddress(MessageContextEnum.Msg_100.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_100.getMessage(), 
                                msgProject.getName(), query.getMemo()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, msgProject.getManagementPersonnel());
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(msgProject.getName());
                        pointSymbol.setAuditType("报名文件拟定");
                        pointSymbol.setReason(query.getMemo());
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_100.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, msgProject.getManagementPersonnel());
                    } else if (GardenProjectStepEnum.P_200.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_190.getValue());
                    } else if (GardenProjectStepEnum.P_310.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_300.getValue());
                    }
                } else if (Util.isNotNull(query.getSubStep())){
                    if (GardenProjectStepEnum.P_130.getValue().equals(query.getSubStep())) {
                        project.setSubStep(GardenProjectStepEnum.P_120.getValue());
                        message.setTitle(MessageContextEnum.Msg_190.getValue());
                        message.setAddress(MessageContextEnum.Msg_190.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_190.getMessage(), 
                                msgProject.getName(), query.getMemo()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, msgProject.getManagementPersonnel());
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(msgProject.getName());
                        pointSymbol.setAuditType("保证金申请");
                        pointSymbol.setReason(query.getMemo());
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_190.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, msgProject.getManagementPersonnel());

                    }
                }
                if(query.getType()==50){
                 // 审核项目修改通过将临时数据付给原有数据
                    GardenProjectQuery projectQuery = new GardenProjectQuery();
                    projectQuery.setId(query.getProjectId());
                    projectQuery.setStatus(GardenProjectStatusEnum.NOT_VERIFY.getValue());
                    projectQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                    ResultPage<GardenProjectInfo> projectRst = gardenProjectService.getGardenProjectByCondition(projectQuery);
                    if (projectRst.isSuccess()) {
                        projectQuery.setId(Integer.parseInt(projectRst.getList().get(0).getMemo()));
                        projectQuery.setStatus(GardenProjectStatusEnum.TEMPORARY.getValue());
                        projectQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                        projectRst = gardenProjectService.getGardenProjectByCondition(projectQuery);
                        if (projectRst.isSuccess()) {
                         // 删除临时数据
                            result = gardenProjectService.deleteGardenProject(projectRst.getList().get(0).getId());
                            if(!result.isSuccess()){
                                throw new ApplicationException("经营审核项目修改不通过时,删除项目表失败");  
                            }
                        }
                    }
                }else if(query.getType()==51){
                  //获取临时合同信息
                    ContractQuery contractQuery = new ContractQuery();
                    contractQuery.setProjectId(query.getProjectId());
                    contractQuery.setStatus(GenerateConstants.number_0);
                    List<ProjectContract> contractRst = projectContractMapper.getProjectContractByProjectIdAndStatus(contractQuery);
                    if(Util.isNotNull(contractRst) && contractRst.size()>0){
                        int total = projectContractMapper.deleteProjectContractById(contractRst.get(0).getId());
                        if(total<=0){
                            throw new ApplicationException("经营审核合同修改不通过时,删除合同表失败");  
                        } 
                    }

                }
            }
            if (query.getType() != 50 && query.getType() != 51) {
                // 更新项目进度
                project.setId(query.getProjectId());
                project.setUpdateUserId(query.getUpdateUserId());
                project.setUpdateTime(new Date());
                result = gardenProjectService.updateGardenProjectById(project);
                if (!result.isSuccess()) {
                    throw new ApplicationException("经营审核时,项目进度更新失败");
                }
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
     * @see com.zjzmjr.core.service.business.audit.ManageAuditService#insertManageAudit(com.zjzmjr.core.model.audit.OfficeAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertManageAudit(ManageAudit manageAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(manageAudit)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int cnt = manageAuditMapper.insertManageAudit(manageAudit);
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
     * @see com.zjzmjr.core.service.business.audit.ManageAuditService#getProjectManageAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectManageAuditCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(query)){
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
            total = manageAuditMapper.getProjectManageAuditCount(query);
           
        }
        result.setSuccess(true);
        result.setT(total);
        return result;
    }

}
