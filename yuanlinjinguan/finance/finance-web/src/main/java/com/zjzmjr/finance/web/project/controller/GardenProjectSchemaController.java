package com.zjzmjr.finance.web.project.controller;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.audit.IGardenProjectAuditFacade;
import com.zjzmjr.core.api.project.IGardenProjectFacade;
import com.zjzmjr.core.api.project.IProjectContractFacade;
import com.zjzmjr.core.api.project.IProjectContractInfoFacade;
import com.zjzmjr.core.api.project.IProjectFileInfoFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.NumberUtils;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectSchemaStatusEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.LeaderAudit;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.ContractPayment;
import com.zjzmjr.core.model.project.ContractPaymentQuery;
import com.zjzmjr.core.model.project.ContractSubpackage;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.GardenProjectSchema;
import com.zjzmjr.core.model.project.GardenProjectSchemaInfo;
import com.zjzmjr.core.model.project.ProjectFileInfo;
import com.zjzmjr.core.model.project.ProjectFileQuery;
import com.zjzmjr.core.model.project.ProjectSchemaQuery;
import com.zjzmjr.core.model.project.SubpackagePayment;
import com.zjzmjr.finance.web.project.form.GardenProjectForm;
import com.zjzmjr.finance.web.project.form.ProjectSchemaForm;
import com.zjzmjr.finance.web.project.form.SubpackageForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 立项审核
 * 
 * @author oms
 * @version $Id: GardenProjectSchemaController.java, v 0.1 2017-8-16 下午2:53:43
 *          oms Exp $
 */
@Controller
@RequestMapping("/project/user")
public class GardenProjectSchemaController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(GardenProjectSchemaController.class);

    @Resource(name = "gardenProjectFacade")
    private IGardenProjectFacade gardenProjectFacade;

    /** 合同处理接口 */
    @Resource(name = "projectContractInfoFacade")
    private IProjectContractInfoFacade projectContractInfoFacade;

    /** 合同处理接口 */
    @Resource(name = "projectContractFacade")
    private IProjectContractFacade projectContractFacade;

    /**
     * 系统中审核类型接口
     */
    @Resource(name = "gardenProjectAuditFacade")
    private IGardenProjectAuditFacade gardenProjectAuditFacade;
    
    /**
     * 上传文件接口
     */
    @Resource(name = "projectFileInfoFacade")
    private IProjectFileInfoFacade projectFileInfoFacade;

    /**
     * 立项审核功能
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/approval.htm", method = RequestMethod.POST)
    public void projectApproval(HttpServletRequest req, HttpServletResponse resp, ProjectSchemaForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(Util.isNotNull(form.getProjectId()), "请确认具体的项目");
            
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getProjectId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultEntry<GardenProject> projRst = gardenProjectFacade.getGardenProjectByIdAndStatus(query);
            if(projRst.isSuccess()){
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getProjectId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_20.getValue());
                ResultEntry<Integer> result = new ResultEntry<Integer>();
                // 如果是审核不通过时
                if (2 == form.getPassed()) {
                    // 更新项目表
                    GardenProject project = new GardenProject();
                    project.setId(form.getProjectId());
                    project.setNopassReason(form.getReason());
                    project.setStatus(ProjectTableStatusEnum.VERIFIED_FAIL.getValue());
                    project.setStep(GardenProjectStepEnum.P_20.getValue());
                    project.setUpdateTime(new Date());
                    project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                    result = gardenProjectFacade.updateProjectSchema(project);
                    adminBusiness.setComment("审核不通过");
                } else if (1 == form.getPassed()) {
                    // 更新项目表
                    GardenProject project = new GardenProject();
                    project.setId(form.getProjectId());
                    // 经营专员
                    project.setManagementPersonnel(form.getManagementPersonnel());
                    if (StringUtils.isNotBlank(form.getApplyDeadline())) {
                        project.setApplyDeadline(form.getApplyDeadline());
                    }
                    project.setStep(GardenProjectStepEnum.P_30.getValue());
                    project.setUpdateTime(new Date());
                    project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                    // 通过审核的时候
                    List<GardenProjectSchema> recordLst = new ArrayList<GardenProjectSchema>();
                    GardenProjectSchema record = new GardenProjectSchema();
                    record.setCompanyId(SpringSecurityUtil.getIntCompany());
                    // 建议人方案
                    record.setProjectId(form.getProjectId());
                    record.setSchemeProposer(form.getProposerId());
                    record.setResason(form.getReason());
                    record.setAccepted(ProjectSchemaStatusEnum.WAITING_APPLY.getValue());
                    // 0:非推荐 1:推荐
                    record.setRecommend(GenerateConstants.number_1);
                    record.setCreateTime(new Date());
                    record.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                    Message message = new Message();
                    message.setTitle(MessageContextEnum.recommendLeader.getValue());
                    message.setAddress(MessageContextEnum.recommendLeader.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.recommendLeader.getMessage(), 
                                projRst.getT().getName(), formatDateStr(project.getApplyDeadline())));
                    MessageHandlerUtil.insertMessage(message, form.getProposerId());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setTemplateCode(MessageContextEnum.recommendLeader.getTemplateCode());
                    pointSymbol.setName(projRst.getT().getName());
                    pointSymbol.setType("推荐");
                    pointSymbol.setDate(formatDateStr(project.getApplyDeadline()));
                    MessageHandlerUtil.sendSms(pointSymbol, form.getProposerId());
                    recordLst.add(record);
                    // 邀请人方案
                    if (StringUtils.isNotBlank(form.getInviterIds())) {
                        String[] inviters = form.getInviterIds().split(",");
                        for (String inviter : inviters) {
                            record = new GardenProjectSchema();
                            record.setCompanyId(SpringSecurityUtil.getIntCompany());
                            record.setProjectId(form.getProjectId());
                            record.setSchemeProposer(StringUtil.nullToInteger(inviter));
                            record.setResason(form.getReason());
                            // 0:非推荐 1:推荐
                            record.setRecommend(GenerateConstants.number_0);
                            record.setAccepted(ProjectSchemaStatusEnum.WAITING_APPLY.getValue());
                            record.setCreateTime(new Date());
                            record.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                            recordLst.add(record);
                            message = new Message();
                            message.setTitle(MessageContextEnum.inviteLeader.getValue());
                            message.setAddress(MessageContextEnum.inviteLeader.getAddress());
                            message.setContext(MessageFormat.format(MessageContextEnum.inviteLeader.getMessage(), 
                                        projRst.getT().getName(), formatDateStr(project.getApplyDeadline())));
                            MessageHandlerUtil.insertMessage(message, StringUtil.nullToInteger(inviter));
                            pointSymbol = new PointSymbol();
                            pointSymbol.setTemplateCode(MessageContextEnum.inviteLeader.getTemplateCode());
                            pointSymbol.setName(projRst.getT().getName());
                            pointSymbol.setType("邀请");
                            pointSymbol.setDate(formatDateStr(project.getApplyDeadline()));
                            MessageHandlerUtil.sendSms(pointSymbol, StringUtil.nullToInteger(inviter));
                        }
                    }
                    result = gardenProjectFacade.insertGardenProjectSchema(project, recordLst);
                    adminBusiness.setComment("审核通过");
                }
                if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                }

                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            } else {
                putError(model, "所选择的项目不存在");
            }
        } catch (Exception ex) {
            logger.error("项目立项审核失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 项目方案申请展示查询
     * 
     * @param model
     * @param resp
     * @param projectId
     */
    @RequestMapping(value = "/getSchemaApply.htm", method = RequestMethod.POST)
    public void getSchemaApply(HttpServletResponse resp, ProjectSchemaForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ProjectSchemaQuery query = new ProjectSchemaQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setProjectId(form.getProjectId());
            if (Util.isNotNull(form.getProposerId())) {
                query.setSchemeProposer(form.getProposerId());
            } else {
                query.setSchemeProposer(SpringSecurityUtil.getIntPrincipal());
            }
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectSchemaInfo> result = gardenProjectFacade.getGardenProjectSchemaByCondition(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取项目方案申请失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 项目方案申请
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/schemaApply.htm", method = RequestMethod.POST)
    public void schemaApply(HttpServletRequest req, HttpServletResponse resp, ProjectSchemaForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(Util.isNotNull(form.getProjectId()), "请确认具体的项目");
            
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getProjectId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultEntry<GardenProject> projRst = gardenProjectFacade.getGardenProjectByIdAndStatus(query);
            if(projRst.isSuccess()){
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getProjectId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_30.getValue());

                // 项目方案申请
                GardenProjectSchema record = new GardenProjectSchema();
                record.setId(form.getId());
                record.setProjectId(form.getProjectId());
                record.setCompanyId(SpringSecurityUtil.getIntCompany());
                record.setSchemeProposer(SpringSecurityUtil.getIntPrincipal());
                if (2 == form.getPassed()) {
                    // 项目方案不申请的时候
                    record.setAccepted(ProjectSchemaStatusEnum.REFUSED_APPLY.getValue());
                    record.setResason(form.getReason());
                    adminBusiness.setComment(ProjectSchemaStatusEnum.REFUSED_APPLY.getMessage());
                } else if (1 == form.getPassed()) {
                    // 项目方案申请的时候
                    record.setAccepted(ProjectSchemaStatusEnum.APPLIED.getValue());
                    record.setContent(form.getContent());
                    record.setResason(form.getReason());
                    adminBusiness.setComment(ProjectSchemaStatusEnum.APPLIED.getMessage());
                }
                record.setUpdateTime(new Date());
                record.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                ResultEntry<Integer> result = gardenProjectFacade.updateGardenProjectSchema(record);
                if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message message = null;
                    message = new Message();
                    message.setTitle(MessageContextEnum.Msg_30.getValue());
                    message.setAddress(MessageContextEnum.Msg_30.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_30.getMessage(), 
                                projRst.getT().getName(), SpringSecurityUtil.getUserName()));
                    MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getYuanBanPersons());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(projRst.getT().getName());
                    pointSymbol.setLeader(SpringSecurityUtil.getUserName());
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_30.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getYuanBanPersons());
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }
        } catch (Exception ex) {
            logger.error("项目方案申请失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 负责人确立查询
     * 
     * @param model
     * @param resp
     * @param projectId
     */
    @RequestMapping(value = "/getLeaders.htm", method = RequestMethod.POST)
    public void getProjectLeader(HttpServletResponse resp, ProjectSchemaForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ProjectSchemaQuery query = new ProjectSchemaQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setProjectId(form.getProjectId());
            query.setSchemeProposer(form.getProposerId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectSchemaInfo> result = gardenProjectFacade.getGardenProjectSchemaByCondition(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取项目方案申请失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 技术负责人确立
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/projectLeader.htm", method = RequestMethod.POST)
    public void projectLeader(HttpServletRequest req, HttpServletResponse resp, ProjectSchemaForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Message message = null;
            Message msgLeader = null;
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getProjectId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> proRst = gardenProjectFacade.getGardenProjectByCondition(query);
            if(proRst.isSuccess()){

                PointSymbol pointSymbol = null;
                PointSymbol symbolLeader = null;
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getProjectId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_40.getValue());
                // 更新项目
                GardenProjectSchemaInfo schemaInfo = new GardenProjectSchemaInfo();
                schemaInfo.setCompanyId(SpringSecurityUtil.getIntCompany());
                schemaInfo.setId(form.getProjectId());
                if (2 == form.getPassed()) {
                    // 负责人确立不通过时
                    schemaInfo.setStatus(GardenProjectStatusEnum.SUSPEND.getValue());
                    schemaInfo.setNopassReason(form.getReason());
                    adminBusiness.setComment("负责人确立不通过");
                } else if (1 == form.getPassed()) {
                    // 负责人确立通过时
                    schemaInfo.setProjectLeader(form.getProposerId());
                    msgLeader = new Message();
                    msgLeader.setUserId(form.getProposerId());
                    msgLeader.setTitle(MessageContextEnum.Msg_40.getValue());
                    msgLeader.setAddress(MessageContextEnum.Msg_40.getAddress());
                    msgLeader.setContext(MessageFormat.format(MessageContextEnum.Msg_40.getMessage(), 
                                proRst.getList().get(0).getName()));
                    symbolLeader = new PointSymbol();
                    symbolLeader.setName(proRst.getList().get(0).getName());
                    symbolLeader.setTemplateCode(MessageContextEnum.Msg_40.getTemplateCode());
                    pointSymbol = new PointSymbol();
                    message = new Message();
                    if(proRst.getList().get(0).getIntentionalCooperation().equals(63) ||
                            proRst.getList().get(0).getIntentionalCooperation().equals(64)){
                        // 63   16  意向合作方式  2   邀标
                        // 64   16  意向合作方式  3   公开招标
                        schemaInfo.setStep(GardenProjectStepEnum.P_50.getValue());
                        message.setTitle(MessageContextEnum.Msg_50.getValue());
                        message.setAddress(MessageContextEnum.Msg_50.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_50.getMessage(), 
                                proRst.getList().get(0).getName()));
                        pointSymbol.setName(proRst.getList().get(0).getName());
                        pointSymbol.setFile("招标公告");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_50.getTemplateCode());
                    }else{
                         // 62   16  意向合作方式  1   委托
                         // 74   16  意向合作方式  4   集团合作
                        schemaInfo.setStep(GardenProjectStepEnum.P_300.getValue());
                        message.setTitle(MessageContextEnum.Msg_60.getValue());
                        message.setAddress(MessageContextEnum.Msg_60.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_60.getMessage(), 
                                proRst.getList().get(0).getName()));
                        pointSymbol.setName(proRst.getList().get(0).getName());
                        pointSymbol.setFile("合同拟定版");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_60.getTemplateCode());
                    }
                    adminBusiness.setComment("负责人确立通过");
                }
                // 是否重点项目 0:否 1:是
                schemaInfo.setIsMajorProject(form.getStatus());
                schemaInfo.setUpdateTime(new Date());
                schemaInfo.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                schemaInfo.setSchemaContent(form.getContent());
                ResultEntry<Integer> result = gardenProjectFacade.updateGardenProjectSchema(schemaInfo);
                if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    MessageHandlerUtil.insertMessage(msgLeader);
                    MessageHandlerUtil.sendSms(symbolLeader, form.getProposerId());
                    for(int cnt = 0; cnt<MessageHandlerUtil.getJinYingPersons().size();cnt++){
                        message.setUserId(MessageHandlerUtil.getJinYingPersons().get(cnt).getUserInfo().getId());
                        MessageHandlerUtil.insertMessage(message);
                    }
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getJinYingPersons());
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }else{
                putError(model, "不存在该项目");
            }
        } catch (Exception ex) {
            logger.error("负责人确立失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 插入项目分包信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/insSubpackage.htm", method = RequestMethod.POST)
    public void insSubpackage(HttpServletResponse resp, SubpackageForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 项目分包表
            ContractSubpackage subpackage = new ContractSubpackage();
            int flag = 0;
            subpackage.setId(form.getId());
            subpackage.setCompanyId(SpringSecurityUtil.getIntCompany());
            subpackage.setProjectId(form.getProjectId());
            subpackage.setSubpackageLeader(form.getSubpackageLeader());
            subpackage.setSubpackageCapital(NumberUtils.string2BigDecimal(form.getSubpackageCapital()));
            subpackage.setSubpackageContent(form.getSubpackageContent());
            subpackage.setPaymentMemo(form.getPaymentMemo());
            subpackage.setSubpackageMemo(form.getSubpackageMemo());
            subpackage.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            subpackage.setCreateTime(new Date());
            subpackage.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            subpackage.setUpdateTime(subpackage.getCreateTime());
            subpackage.setUpdateUserId(subpackage.getCreateUserId());
            subpackage.setStep(form.getStep());
            List<SubpackagePayment> payLst = new ArrayList<SubpackagePayment>();
            if(Util.isNotNull(form.getPayModes()) && Util.isNotNull(form.getPayAmounts())){
                String[] modes = form.getPayModes().split(",");  
                String[] amounts = form.getPayAmounts().split(",");
                for(int i=0;i<modes.length;i++){
                    SubpackagePayment payment = new SubpackagePayment();
                    if(Util.isNotNull(form.getId())){
                        BigDecimal pay = BigDecimal.ZERO;
                        BigDecimal contractPay = BigDecimal.ZERO;
                        if(Util.isNotNull(modes[i])){
                            //查询合同付款金额
                            ContractPaymentQuery contractPaymentQuery = new ContractPaymentQuery();
                            contractPaymentQuery.setProjectId(form.getProjectId());
                            contractPaymentQuery.setCompanyId(SpringSecurityUtil.getIntCompany());
                            contractPaymentQuery.setPaymentMode(Integer.parseInt(modes[i]));
                            contractPaymentQuery.setStatus(ProjectTableStatusEnum.VERIFIED.getValue());
                            ResultEntry<ContractPayment> payResult = projectContractFacade.getContractPaymentByCondition(contractPaymentQuery);
                            if(payResult.isSuccess()){
                                contractPay = payResult.getT().getPaymentAmount();
                            }else{
                                flag = GenerateConstants.number_3;
                                break;
                            }
                            //查询财务审核该公司该项目该付款方式的项目支出是否大于修改的
                            GardenProjectAuditQuery gardenProjectAuditQuery = new GardenProjectAuditQuery();
                            gardenProjectAuditQuery.setProjectId(form.getProjectId());
                            gardenProjectAuditQuery.setCompanyId(SpringSecurityUtil.getIntCompany());
                            gardenProjectAuditQuery.setPaymentMode(Integer.parseInt(modes[i]));
                            gardenProjectAuditQuery.setType(29);
                            gardenProjectAuditQuery.setOtherStatus(GenerateConstants.number_2);
                            ResultRecord<FinancialAudit> financialResult = gardenProjectAuditFacade.getProjectFinancialAuditByCondition(gardenProjectAuditQuery);                           
                            if(financialResult.isSuccess()){
                               for(int j=0;j<financialResult.getList().size();j++){
                                   pay= pay.add(financialResult.getList().get(j).getAmount());
                               }
                            }
                            payment.setPaymentMode(Integer.parseInt(modes[i])); 
                        }
                        if(Util.isNotNull(amounts[i])){
                            if(contractPay.compareTo(new BigDecimal(amounts[i]))<0){
                                flag = GenerateConstants.number_2;
                                break; 
                            }
                            if(pay.compareTo(new BigDecimal(amounts[i]))>0){
                                flag = GenerateConstants.number_1;
                                break;
                            }else{
                                payment.setPaymentAmount(new BigDecimal(amounts[i]));  
                            } 
                            
                        }
                        payLst.add(payment);
                    }else{
                        BigDecimal contractPay = BigDecimal.ZERO;
                        if(Util.isNotNull(modes[i])){
                            // 查询合同付款金额
                            ContractPaymentQuery contractPaymentQuery = new ContractPaymentQuery();
                            contractPaymentQuery.setProjectId(form.getProjectId());
                            contractPaymentQuery.setCompanyId(SpringSecurityUtil.getIntCompany());
                            contractPaymentQuery.setPaymentMode(Integer.parseInt(modes[i]));
                            contractPaymentQuery.setStatus(ProjectTableStatusEnum.VERIFIED.getValue());
                            ResultEntry<ContractPayment> payResult = projectContractFacade.getContractPaymentByCondition(contractPaymentQuery);
                        if(payResult.isSuccess()){
                            contractPay = payResult.getT().getPaymentAmount();
                        }else{
                            flag = GenerateConstants.number_3;
                            break;
                        }
                        payment.setPaymentMode(Integer.parseInt(modes[i])); 
                    }
                    if(Util.isNotNull(amounts[i])){
                        if(contractPay.compareTo(new BigDecimal(amounts[i]))<0){
                            flag = GenerateConstants.number_2;
                            break; 
                        }
                        payment.setPaymentAmount(new BigDecimal(amounts[i]));
                    }
                    payLst.add(payment);
                    }                   
                }
            }
            if(GenerateConstants.number_1.equals(flag)){
                putError(model, "-1","付款方式金额少于已申请支出金额");
            }else if(GenerateConstants.number_2.equals(flag)){
                putError(model, "-1","同一种付款阶段分包付款金额不得大于合同付款金额");
            }else if(GenerateConstants.number_3.equals(flag)){
                putError(model, "-1","合同中无此类付款方式");
            }else{
                ResultEntry<Integer> result = projectContractFacade.insertContractSubpackage(subpackage,payLst);
                if (result.isSuccess()) {
                    putSuccess(model, "");
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                } 
            }           
        } catch (Exception ex) {
            logger.error("上传分包数据失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 确认分包团队，根据插入的分包信息，更新项目及院办审核表
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/contractSubpackage.htm", method = RequestMethod.POST)
    public void contractSubpackage(HttpServletRequest req, HttpServletResponse resp, SubpackageForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {

            // 获取该项目的当时信息
            GardenProject srcProject = null;
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getProjectId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
            if(pjRst.isSuccess()){
                srcProject = pjRst.getList().get(0);
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getProjectId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_380.getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.B_380.getMessage());
                // 更新项目
                GardenProject record = new GardenProject();
                record.setId(form.getProjectId());
                record.setStep(GardenProjectStepEnum.P_390.getValue());
                record.setUpdateTime(new Date());
                record.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());

                // 院办审核表
                OfficeAudit officeAudit = new OfficeAudit();
                officeAudit.setCompanyId(SpringSecurityUtil.getIntCompany());
                officeAudit.setProjectId(form.getProjectId());
                officeAudit.setApplicant(SpringSecurityUtil.getIntPrincipal());
                // 院办审核类型 分包
                officeAudit.setType(56);
                officeAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                officeAudit.setCreateTime(new Date());
                officeAudit.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                officeAudit.setUpdateTime(officeAudit.getCreateTime());
                officeAudit.setUpdateUserId(officeAudit.getCreateUserId());
                ResultEntry<Integer> result = projectContractInfoFacade.insertProjectSubpackage(record, officeAudit);
                if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message message = new Message();
                    message.setTitle(MessageContextEnum.Msg_740.getValue());
                    message.setAddress(MessageContextEnum.Msg_740.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_740.getMessage(), 
                                        srcProject.getName()));
                    // 技术负责人员
                    MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getYuanBanPersons());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(srcProject.getName());
                    pointSymbol.setAuditType("合作比例拟定");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_740.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getYuanBanPersons());
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }
        } catch (Exception ex) {
            logger.error("上传分包数据失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 开发票申请
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/applyBill.htm", method = RequestMethod.POST)
    public void applyBill(HttpServletRequest req, HttpServletResponse resp, ProjectSchemaForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 获取该项目的当时信息
            GardenProject srcProject = null;
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getProjectId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
            ResultEntry<Integer> result = null;
            if (pjRst.isSuccess()) {
                srcProject = pjRst.getList().get(0);
                checkApplyBillAuthority(form.getPaymentMode(), srcProject.getId());
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getProjectId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_809.getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.B_809.getMessage());
                if (SpringSecurityUtil.getIntPrincipal().equals(srcProject.getProjectLeader())) {
                    // 如果是技术负责人开发票的情况
                    // 财务审核表
                    FinancialAudit audit = new FinancialAudit();
                    audit.setCompanyId(SpringSecurityUtil.getIntCompany());
                    audit.setProjectId(form.getProjectId());
                    // 申请人
                    audit.setApplicant(SpringSecurityUtil.getIntPrincipal());
                    // 发票的金额
                    audit.setAmount(NumberUtils.string2BigDecimal(form.getAmount()));
                    // 发票的付款方式
                    audit.setPaymentMode(form.getPaymentMode());
                    // 财务审核类型 28 : 审核开发票 29 : 项目支出
                    audit.setType(form.getBillType());
                    audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    audit.setCreateTime(new Date());
                    audit.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                    audit.setUpdateTime(audit.getCreateTime());
                    audit.setUpdateUserId(audit.getCreateUserId());
                    result = gardenProjectAuditFacade.insertFinancialAudit(audit);
                } else {
                    // 经营专员开发票的情况
                    // 技术技术审核表
                    LeaderAudit audit = new LeaderAudit();
                    audit.setCompanyId(SpringSecurityUtil.getIntCompany());
                    audit.setProjectId(form.getProjectId());
                    // 申请人
                    audit.setApplicant(SpringSecurityUtil.getIntPrincipal());
                    // 发票的金额
                    audit.setAmount(NumberUtils.string2BigDecimal(form.getAmount()));
                    // 发票的付款方式
                    audit.setPaymentMode(form.getPaymentMode());
                    // 财务审核类型 审核开发票
                    audit.setType(28);
                    audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    audit.setCreateTime(new Date());
                    audit.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                    audit.setUpdateTime(audit.getCreateTime());
                    audit.setUpdateUserId(audit.getCreateUserId());
                    result = gardenProjectAuditFacade.insertLeaderAudit(audit);
                }
                if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message message = new Message();
                    message.setTitle(MessageContextEnum.Msg_910.getValue());
                    message.setAddress(MessageContextEnum.Msg_910.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_910.getMessage(), srcProject.getName()));
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(srcProject.getName());
                    pointSymbol.setAuditType("开发票");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_910.getTemplateCode()); 
                    MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getKuaijiPersons());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getKuaijiPersons());
                    message.setTitle(MessageContextEnum.Msg_920.getValue());
                    message.setAddress(MessageContextEnum.Msg_920.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_920.getMessage(), srcProject.getName()));
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_920.getTemplateCode()); 
                    MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getJinYingManagerPersons());
                    MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getYuanBanPersons());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getJinYingManagerPersons());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getYuanBanPersons());
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            } else {
                putError(model, "该项目不存在");
            }
        } catch (Exception ex) {
            logger.error("开发票申请失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 退保证金申请
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/applyBackBill.htm", method = RequestMethod.POST)
    public void applyBackBill(HttpServletRequest req, HttpServletResponse resp, GardenProjectForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectQuery query = new GardenProjectQuery();
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setProjectId(form.getId());
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_823.getValue());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setId(form.getId());
            if (Util.isNotNull(form.getNobackBail())) {
                query.setNobackBail(new BigDecimal(form.getNobackBail()));
            }
            query.setBailType(form.getBailType());
            query.setUpdateTime(new Date());
            query.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = gardenProjectFacade.applyBackBail(query);
            if (result.isSuccess()) {
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("退保证金申请失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 退保证金申请一览
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/applyBackBillList.htm", method = RequestMethod.POST)
    public void applyBackBillList(HttpServletRequest req, HttpServletResponse resp, GardenProjectForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectQuery query = new GardenProjectQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setManagementPersonnel(SpringSecurityUtil.getIntPrincipal());
            query.setNobackBail(BigDecimal.ZERO);
            query.setStatus(GardenProjectStatusEnum.NOT_VERIFY.getValue());
            query.setStep(GardenProjectStepEnum.P_290.getValue());
            ResultRecord<GardenProjectInfo> result = gardenProjectFacade.getGardenProjectCanBackBail(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取退保证金一览失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 判断是否已经可以有开发票的时候
     * 
     * @param paymentMode
     * @param projectId
     */
    private void checkApplyBillAuthority(Integer paymentMode, Integer projectId){
        ProjectFileQuery query = new ProjectFileQuery();
        query.setCompanyId(SpringSecurityUtil.getIntCompany());
        query.setProjectId(projectId);
        String strMessage = "";
        switch (paymentMode) {
        case 38:
            // 方案设计
            query.setFileId(12);
            strMessage = "您尚未上传【方案设计】，不能申请该发票";
            break;
        case 39:
            // 扩初设计
            query.setFileId(15);
            strMessage = "您尚未上传【扩初设计】，不能申请该发票";
            break;
        case 40:
            // 施工图设计
            query.setFileId(18);
            strMessage = "您尚未上传【施工图设计】，不能申请该发票";
            break;
        case 41:
            // 后期服务
//            query.setFileId(18);
            break;
        case 42:
            // 规划初稿
            query.setFileId(20);
            strMessage = "您尚未上传【规划初稿】，不能申请该发票";
            break;
        case 43:
            // 规划成果
            query.setFileId(22);
            strMessage = "您尚未上传【规划成果】，不能申请该发票";
            break;
        }
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<ProjectFileInfo> result = projectFileInfoFacade.getFileUploadByCondition(query);
        Assert.isTrue(result.isSuccess(), strMessage);
    }
    
    /**
     * 格式化字符类型的时间
     * 
     * @param strDate
     * @return
     */
    private String formatDateStr(String strDate){
        if(StringUtils.isBlank(strDate) || strDate.length() != 8){
            return strDate;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(strDate.substring(0, 4)).append("-");
        builder.append(strDate.substring(4, 6)).append("-");
        builder.append(strDate.substring(6, 8));
        return builder.toString();
    }
    
}
