package com.zjzmjr.finance.web.project.controller;

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

import com.zjzmjr.core.api.project.IGardenProjectFacade;
import com.zjzmjr.core.api.project.IProjectFileInfoFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.NumberUtils;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.enums.user.DeviceEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.audit.ChiefAudit;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.ManageAudit;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.ProjectFileInfo;
import com.zjzmjr.core.model.project.ProjectFileQuery;
import com.zjzmjr.core.model.project.ProjectFileUpload;
import com.zjzmjr.finance.web.project.form.ProjectFileForm;
import com.zjzmjr.finance.web.project.form.ProjectSchemaForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 项目文件类处理
 * 
 * @author oms
 * @version $Id: GardenProjectFileController.java, v 0.1 2017-8-21 下午1:26:29 oms Exp $
 */
@Controller
@RequestMapping("/project/user")
public class GardenProjectFileController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(GardenProjectFileController.class);

    /**
     * 项目接口
     */
    @Resource(name = "gardenProjectFacade")
    private IGardenProjectFacade gardenProjectFacade;

    @Resource(name = "projectFileInfoFacade")
    private IProjectFileInfoFacade projectFileInfoFacade;
    
    /**
     * 录入招标公告
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/bidingAd.htm", method = RequestMethod.POST)
    public void inviteBid(HttpServletRequest req, HttpServletResponse resp, ProjectFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            
            Assert.isTrue(StringUtils.isNotBlank(form.getDeadline()), "请输入报名截止日期");
            
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setProjectId(form.getId());
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_50.getValue());
            //adminBusiness.setComment(AdminBusinessTypeEnum.B_50.getMessage());
            // 项目表
            GardenProject project = new GardenProject();
            project.setId(form.getId());
            project.setBiddingDeadline(form.getDeadline());
            project.setBiddingMemo(form.getMemo());
            project.setStep(GardenProjectStepEnum.P_60.getValue());
            project.setUpdateTime(new Date());
            project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            
            // 文件上传表
            ProjectFileUpload file = new ProjectFileUpload();
            file.setCompanyId(SpringSecurityUtil.getIntCompany());
            file.setProjectId(form.getId());
            // 文件id
            if(StringUtils.isNotBlank(form.getFileId())){
                file.setFileId(StringUtil.stringToInteger(form.getFileId()));
            }
            file.setFileName(form.getFileName());
            file.setFileAddress(form.getFileUrl());
            file.setSourceType(DeviceEnum.WAP.getValue());
            file.setCreateTime(new Date());
            file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            file.setUpdateTime(file.getCreateTime());
            file.setUpdateUserId(file.getCreateUserId());
            ResultEntry<Integer> result = projectFileInfoFacade.projectFileUpload(project, file);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("录入招标公告失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 上传报名文件
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/entryFile.htm", method = RequestMethod.POST)
    public void entryFile(HttpServletRequest req, HttpServletResponse resp, ProjectFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 获取该项目的当时信息
            GardenProject srcProject = null;
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
            if(pjRst.isSuccess()){
                srcProject = pjRst.getList().get(0);
                Message message = null;
                Message msgLeader = null;
                PointSymbol symbolLeader = null;
                PointSymbol pointSymbol = null;
                int projectLeader = 0;
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.getByValue(srcProject.getStep()).getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(srcProject.getStep()).getMessage());
                // 经营部门审核
                ManageAudit manageAudit = null;
                // 文件上传表
                ProjectFileUpload file = new ProjectFileUpload();
                file.setCompanyId(SpringSecurityUtil.getIntCompany());
                file.setProjectId(form.getId());
                // 项目表
                GardenProject project = new GardenProject();
                project.setId(form.getId());
                project.setBiddingDocumentsProducer(form.getUserId());
//                // 报名截止日期
//                project.setBiddingDeadline(form.getDeadline());
                // 报名结果  0:不成功 1:成功
                project.setRegistrationResults(form.getStatus());
                // 报名结果原因
                project.setRegistrationResultsMemo(form.getMemo());
                // 确定项目所属的阶段
                if (GardenProjectStepEnum.P_60.getValue().equals(srcProject.getStep())) {
                    project.setStep(GardenProjectStepEnum.P_70.getValue());
                    // 经营审核数据插入
                    manageAudit = new ManageAudit();
                    manageAudit.setCompanyId(SpringSecurityUtil.getIntCompany());
                    manageAudit.setProjectId(form.getId());
                    manageAudit.setApplicant(SpringSecurityUtil.getIntPrincipal());
                    // 经营审核类型   报名
                    manageAudit.setType(46);
                    manageAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    manageAudit.setCreateTime(new Date());
                    manageAudit.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                    manageAudit.setUpdateTime(manageAudit.getCreateTime());
                    manageAudit.setUpdateUserId(manageAudit.getCreateUserId());
                    // 文件id
                    if(StringUtils.isNotBlank(form.getFileId())){
                        file.setFileId(StringUtil.stringToInteger(form.getFileId()));
                    }
                    message = new Message();
                    message.setTitle(MessageContextEnum.Msg_70.getValue());
                    message.setAddress(MessageContextEnum.Msg_70.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_70.getMessage(), 
                                    pjRst.getList().get(0).getName()));
                    pointSymbol = new PointSymbol();
                    pointSymbol.setName(pjRst.getList().get(0).getName());
                    pointSymbol.setAuditType("报名文件");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_70.getTemplateCode());
                    msgLeader = new Message();
                    msgLeader.setUserId(pjRst.getList().get(0).getProjectLeader());
                    projectLeader = pjRst.getList().get(0).getProjectLeader();
                    msgLeader.setTitle(MessageContextEnum.Msg_80.getValue());
                    msgLeader.setAddress(MessageContextEnum.Msg_80.getAddress());
                    msgLeader.setContext(MessageFormat.format(MessageContextEnum.Msg_80.getMessage(), 
                                    pjRst.getList().get(0).getName()));
                    symbolLeader = new PointSymbol();
                    symbolLeader.setName(pjRst.getList().get(0).getName());
                    symbolLeader.setAuditType("报名文件");
                    symbolLeader.setTemplateCode(MessageContextEnum.Msg_80.getTemplateCode());
                } else if (GardenProjectStepEnum.P_100.getValue().equals(srcProject.getStep())) {
                    if(GenerateConstants.number_0.equals(form.getStatus())){
                        project.setStep(GardenProjectStepEnum.P_610.getValue());
                    }else{
                        project.setStep(GardenProjectStepEnum.P_110.getValue());
                    }
                    // 文件id
                    if(StringUtils.isNotBlank(form.getFileId())){
                        file.setFileId(StringUtil.stringToInteger(form.getFileId()));
                    }
                }
                project.setUpdateTime(new Date());
                project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                
                // 上传报名文件
                file.setFileName(form.getFileName());
                file.setFileAddress(form.getFileUrl());
                file.setSourceType(DeviceEnum.WAP.getValue());
                file.setCreateTime(new Date());
                file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                file.setUpdateTime(file.getCreateTime());
                file.setUpdateUserId(file.getCreateUserId());
                ResultEntry<Integer> result = projectFileInfoFacade.projectFileUpload(project, file, manageAudit);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    if (GardenProjectStepEnum.P_60.getValue().equals(srcProject.getStep())) {
                        for(int cnt = 0; cnt<MessageHandlerUtil.getJinYingManagerPersons().size();cnt++){
                            Integer id = MessageHandlerUtil.getJinYingManagerPersons().get(cnt).getUserInfo().getId();
                            message.setUserId(id);
                            MessageHandlerUtil.insertMessage(message);
                        }
                        MessageHandlerUtil.insertMessage(msgLeader);
                        MessageHandlerUtil.sendSms(symbolLeader, projectLeader);
                        MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getJinYingManagerPersons());
                    }                    
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }else{
                putError(model, "项目信息不存在");
            }
        } catch (Exception ex) {
            logger.error("上传报名文件失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 录入招标文件
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/bidingFile.htm", method = RequestMethod.POST)
    public void inviteBidFile(HttpServletRequest req, HttpServletResponse resp, ProjectFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 获取该项目的当时信息
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
            if(pjRst.isSuccess()){
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_110.getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.B_110.getMessage());
                // 项目表
                GardenProject project = new GardenProject();
                project.setId(form.getId());
                // 是否有技术标  0:否 1:是
                project.setHaveTechnical(form.getStatus());
                // 保证金截止日期
                project.setMarginDeadline(form.getMarginDeadline());
                // 投标截止日期
                project.setTenderDeadline(form.getTenderDeadline());
                // 招标文件备注
                project.setBiddingDocumentsMemo(form.getMemo());
                project.setStep(GardenProjectStepEnum.P_190.getValue());
                project.setSubStep(GardenProjectStepEnum.P_120.getValue());
                if(GenerateConstants.number_1.equals(form.getStatus())){
                    project.setSubStep2(GardenProjectStepEnum.P_240.getValue()); 
                }           
                project.setUpdateTime(new Date());
                project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                
                // 文件上传表
                ProjectFileUpload file = new ProjectFileUpload();
                file.setCompanyId(SpringSecurityUtil.getIntCompany());
                file.setProjectId(form.getId());
                // 招标文件编号id
                if(StringUtils.isNotBlank(form.getFileId())){
                    file.setFileId(StringUtil.stringToInteger(form.getFileId()));
                }
                file.setFileName(form.getFileName());
                file.setFileAddress(form.getFileUrl());
                file.setSourceType(DeviceEnum.WAP.getValue());
                file.setCreateTime(new Date());
                file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                file.setUpdateTime(file.getCreateTime());
                file.setUpdateUserId(file.getCreateUserId());
                ResultEntry<Integer> result = projectFileInfoFacade.projectFileUpload(project, file);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message msgLeader = new Message();
                    msgLeader.setUserId(pjRst.getList().get(0).getProjectLeader());
                    msgLeader.setTitle(MessageContextEnum.Msg_150.getValue());
                    msgLeader.setAddress(MessageContextEnum.Msg_150.getAddress());
                    msgLeader.setContext(MessageFormat.format(MessageContextEnum.Msg_150.getMessage(), 
                                    pjRst.getList().get(0).getName()));
                    MessageHandlerUtil.insertMessage(msgLeader);
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(pjRst.getList().get(0).getName());
                    pointSymbol.setFile1("招标文件");
                    pointSymbol.setFile2("技术标拟定");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_150.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, pjRst.getList().get(0).getProjectLeader());
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }
        } catch (Exception ex) {
            logger.error("录入招标文件失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 申请保证金
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/applyBail.htm", method = RequestMethod.POST)
    public void applyBail(HttpServletRequest req, HttpServletResponse resp, ProjectFileForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 获取该项目的当时信息
            GardenProject srcProject = null;
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
            if(pjRst.isSuccess()){
                srcProject = pjRst.getList().get(0);
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.getByValue(srcProject.getSubStep()).getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(srcProject.getSubStep()).getMessage());
                
                // 项目表
                GardenProject project = new GardenProject();
                project.setId(form.getId());
                // 保证金
                project.setBail(NumberUtils.string2BigDecimal(form.getBail()));
                project.setNobackBail(NumberUtils.string2BigDecimal(form.getBail()));
                project.setLatestRemittanceDate(form.getLatestRemittanceDate());
                project.setRemittanceCompany(form.getRemittanceCompany());
                project.setBankName(form.getBankName());
                project.setAccountNo(form.getAccountNo());
                project.setBailMemo(form.getBailMemo());
                project.setSubStep(srcProject.getSubStep() + 10);
                project.setUpdateTime(new Date());
                project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());

                // 经营部门审核
                ManageAudit manageAudit = null;
                // 经营审核数据插入
                manageAudit = new ManageAudit();
                manageAudit.setCompanyId(SpringSecurityUtil.getIntCompany());
                manageAudit.setProjectId(form.getId());
                manageAudit.setApplicant(SpringSecurityUtil.getIntPrincipal());
                // 经营审核类型   保证金
                manageAudit.setType(47);
                manageAudit.setAmount(NumberUtils.string2BigDecimal(form.getBail()));
                manageAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                manageAudit.setCreateTime(new Date());
                manageAudit.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                manageAudit.setUpdateTime(manageAudit.getCreateTime());
                manageAudit.setUpdateUserId(manageAudit.getCreateUserId());                
                ResultEntry<Integer> result = projectFileInfoFacade.projectFileUpload(project, null, manageAudit);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message msgLeader = new Message();
                    msgLeader.setTitle(MessageContextEnum.Msg_170.getValue());
                    msgLeader.setAddress(MessageContextEnum.Msg_170.getAddress());
                    msgLeader.setContext(MessageFormat.format(MessageContextEnum.Msg_170.getMessage(), 
                                        srcProject.getName()));
                    MessageHandlerUtil.insertMessage(msgLeader, MessageHandlerUtil.getJinYingManagerPersons());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(srcProject.getName());
                    pointSymbol.setAuditType("保证金");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_170.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getJinYingManagerPersons());
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }else{
                putError(model, "项目信息不存在");
            }
        } catch (Exception ex) {
            logger.error("申请保证金失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 上传商务标拟定
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/commercialBid.htm", method = RequestMethod.POST)
    public void commercialBid(HttpServletRequest req, HttpServletResponse resp, ProjectFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 获取该项目的当时信息
            GardenProject srcProject = null;
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
            if(pjRst.isSuccess()){
                srcProject = pjRst.getList().get(0);
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.getByValue(srcProject.getStep()).getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(srcProject.getStep()).getMessage());
                
                // 项目表
                GardenProject project = new GardenProject();
                project.setId(form.getId());
                // 商务标制作人
                project.setBusinessProducer(form.getProducerId());
                // 确定项目所属的阶段
                project.setStep(GardenProjectStepEnum.P_200.getValue());
                project.setUpdateTime(new Date());
                project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());

                // 经营部门审核
                ManageAudit manageAudit = null;
                // 经营审核数据插入
                manageAudit = new ManageAudit();
                manageAudit.setCompanyId(SpringSecurityUtil.getIntCompany());
                manageAudit.setProjectId(form.getId());
                manageAudit.setApplicant(SpringSecurityUtil.getIntPrincipal());
                // 经营审核类型   商务标
                manageAudit.setType(48);
                manageAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                manageAudit.setCreateTime(new Date());
                manageAudit.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                manageAudit.setUpdateTime(manageAudit.getCreateTime());
                manageAudit.setUpdateUserId(manageAudit.getCreateUserId());
                
                // 文件上传表
                ProjectFileUpload file = new ProjectFileUpload();
                file.setCompanyId(SpringSecurityUtil.getIntCompany());
                file.setProjectId(form.getId());
                // 文件id
                if(StringUtils.isNotBlank(form.getFileId())){
                    file.setFileId(StringUtil.stringToInteger(form.getFileId()));
                }
                file.setFileName(form.getFileName());
                file.setFileAddress(form.getFileUrl());
                file.setSourceType(DeviceEnum.WAP.getValue());
                file.setCreateTime(new Date());
                file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                file.setUpdateTime(file.getCreateTime());
                file.setUpdateUserId(file.getCreateUserId());
                ResultEntry<Integer> result = projectFileInfoFacade.projectFileUpload(project, file, manageAudit);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message msgLeader = new Message();
                    msgLeader.setTitle(MessageContextEnum.Msg_310.getValue());
                    msgLeader.setAddress(MessageContextEnum.Msg_310.getAddress());
                    msgLeader.setContext(MessageFormat.format(MessageContextEnum.Msg_310.getMessage(), 
                                        srcProject.getName()));
                    MessageHandlerUtil.insertMessage(msgLeader, MessageHandlerUtil.getJinYingManagerPersons());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(srcProject.getName());
                    pointSymbol.setAuditType("商务标");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_310.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getJinYingManagerPersons());

                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }else{
                putError(model, "项目信息不存在");
            }
        } catch (Exception ex) {
            logger.error("上传商务标拟定失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 
     * 上传方案成果
     * 上传扩初成果
     * 上传施工成果
     * 上传规划成果
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/schemaFileUpd.htm", method = RequestMethod.POST)
    public void schemaFileUpd(HttpServletRequest req, HttpServletResponse resp, ProjectFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 获取该项目的当时信息
            GardenProject srcProject = null;
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
            if(pjRst.isSuccess()){
                srcProject = pjRst.getList().get(0);
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.getByValue(srcProject.getStep()).getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(srcProject.getStep()).getMessage());
                
                // 项目表
                GardenProject project = new GardenProject();
                project.setId(form.getId());
                if(GardenProjectStepEnum.P_420.getValue().equals(srcProject.getStep())){
                    if(GenerateConstants.number_1.equals(pjRst.getList().get(0).getHaveDevelopment())){
                        project.setStep(GardenProjectStepEnum.P_430.getValue());
                    }else if(GenerateConstants.number_1.equals(pjRst.getList().get(0).getHaveDrawing())){
                        project.setStep(GardenProjectStepEnum.P_460.getValue());
                    }else if(GenerateConstants.number_1.equals(pjRst.getList().get(0).getHavePlanning())){
                        project.setStep(GardenProjectStepEnum.P_490.getValue());
                    }else{
                        project.setStep(GardenProjectStepEnum.P_520.getValue());
                    }
                }else if(GardenProjectStepEnum.P_450.getValue().equals(srcProject.getStep())){
                    if(GenerateConstants.number_1.equals(pjRst.getList().get(0).getHaveDrawing())){
                        project.setStep(GardenProjectStepEnum.P_460.getValue());
                    }else if(GenerateConstants.number_1.equals(pjRst.getList().get(0).getHavePlanning())){
                        project.setStep(GardenProjectStepEnum.P_490.getValue());
                    }else{
                        project.setStep(GardenProjectStepEnum.P_520.getValue());
                    }
                }else if(GardenProjectStepEnum.P_480.getValue().equals(srcProject.getStep())){
                    if(GenerateConstants.number_1.equals(pjRst.getList().get(0).getHavePlanning())){
                        project.setStep(GardenProjectStepEnum.P_490.getValue());
                    }else{
                        project.setStep(GardenProjectStepEnum.P_520.getValue());
                    }
                }else{
                    project.setStep(GardenProjectStepEnum.P_520.getValue());
                }
                project.setUpdateTime(new Date());
                project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                
                // 文件上传表
                ProjectFileUpload file = new ProjectFileUpload();
                file.setCompanyId(SpringSecurityUtil.getIntCompany());
                file.setProjectId(form.getId());
                // 文件id
                if(StringUtils.isNotBlank(form.getFileId())){
                    file.setFileId(StringUtil.stringToInteger(form.getFileId()));
                }
                file.setFileName(form.getFileName());
                file.setFileAddress(form.getFileUrl());
                file.setSourceType(DeviceEnum.WAP.getValue());
                file.setCreateTime(new Date());
                file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                file.setUpdateTime(file.getCreateTime());
                file.setUpdateUserId(file.getCreateUserId());
                ResultEntry<Integer> result = projectFileInfoFacade.projectFileUpload(project, file);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message message = new Message();
                    PointSymbol pointSymbol = null;
                    if(GardenProjectStepEnum.P_420.getValue().equals(srcProject.getStep())){
                        message.setTitle(MessageContextEnum.Msg_800.getValue());
                        message.setAddress(MessageContextEnum.Msg_800.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_800.getMessage(), 
                                            srcProject.getName()));
                        MessageHandlerUtil.insertMessage(message, srcProject.getProjectLeader()); 
                        pointSymbol = new PointSymbol();
                        pointSymbol.setName(srcProject.getName());
                        pointSymbol.setFile1("方案成果");
                        pointSymbol.setFile2("扩初初稿");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_800.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, srcProject.getProjectLeader());
                    }else if(GardenProjectStepEnum.P_450.getValue().equals(srcProject.getStep())){
                        message.setTitle(MessageContextEnum.Msg_840.getValue());
                        message.setAddress(MessageContextEnum.Msg_840.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_840.getMessage(), 
                                            srcProject.getName()));
                        MessageHandlerUtil.insertMessage(message,srcProject.getProjectLeader()); 
                        pointSymbol = new PointSymbol();
                        pointSymbol.setName(srcProject.getName());
                        pointSymbol.setFile1("扩初成果");
                        pointSymbol.setFile2("施工初稿");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_840.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, srcProject.getProjectLeader());
                    }             
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }else{
                putError(model, "项目信息不存在");
            }
        } catch (Exception ex) {
            logger.error("上传效果规划成果失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 上传技术标
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/shemaTechnicalFileChiefUpd.htm", method = RequestMethod.POST)
    public void technicalBid(HttpServletRequest req, HttpServletResponse resp, ProjectFileForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 获取该项目的当时信息
            GardenProject srcProject = null;
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
            if(pjRst.isSuccess()){
                srcProject = pjRst.getList().get(0);
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.getByValue(srcProject.getSubStep2()).getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(srcProject.getSubStep2()).getMessage());
                
                // 项目表
                GardenProject project = new GardenProject();
                project.setId(form.getId());
//                // 商务标制作人
//                project.setBusinessProducer(form.getProducerId());
                // 确定项目所属的阶段
//                project.setStep(GardenProjectStepEnum.P_250.getValue());
                project.setSubStep2(srcProject.getSubStep2() + 10);
                project.setUpdateTime(new Date());
                project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());

                // 审核
                ChiefAudit chiefAudit = new ChiefAudit();
                chiefAudit.setCompanyId(SpringSecurityUtil.getIntCompany());
                chiefAudit.setProjectId(form.getId());
                chiefAudit.setApplicant(SpringSecurityUtil.getIntPrincipal());
                // 总工审核类型类型   技术标
//                chiefAudit.setType(57);
                chiefAudit.setType(form.getType());
                chiefAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                chiefAudit.setCreateTime(new Date());
                chiefAudit.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                chiefAudit.setUpdateTime(chiefAudit.getCreateTime());
                chiefAudit.setUpdateUserId(chiefAudit.getCreateUserId());
                
                // 文件上传表
                ProjectFileUpload file = new ProjectFileUpload();
                file.setCompanyId(SpringSecurityUtil.getIntCompany());
                file.setProjectId(form.getId());
                // 文件id
//                file.setFileId(6);
                if(StringUtils.isNotBlank(form.getFileId())){
                    file.setFileId(StringUtil.stringToInteger(form.getFileId()));
                }
                file.setFileName(form.getFileName());
                file.setFileAddress(form.getFileUrl());
                file.setSourceType(DeviceEnum.WAP.getValue());
                file.setCreateTime(new Date());
                file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                file.setUpdateTime(file.getCreateTime());
                file.setUpdateUserId(file.getCreateUserId());
                ResultEntry<Integer> result = projectFileInfoFacade.projectFileUpload(project, file, chiefAudit);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message message = new Message();
                    message.setTitle(MessageContextEnum.Msg_430.getValue());
                    message.setAddress(MessageContextEnum.Msg_430.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_430.getMessage(), 
                                        srcProject.getName()));
                    MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getZongGongPersons());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(srcProject.getName());
                    pointSymbol.setAuditType("技术标");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_430.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getZongGongPersons());
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }else{
                putError(model, "项目信息不存在");
            }
        } catch (Exception ex) {
            logger.error("上传技术标拟定失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    /**
     * 
     * 上传方案初稿
     * 上传扩初初稿
     * 上传施工初稿
     * 上传规划初稿
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/shemaFileChiefUpd.htm", method = RequestMethod.POST)
    public void draftBid(HttpServletRequest req, HttpServletResponse resp, ProjectFileForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 获取该项目的当时信息
            GardenProject srcProject = null;
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
            if(pjRst.isSuccess()){
                srcProject = pjRst.getList().get(0);
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.getByValue(srcProject.getStep()).getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(srcProject.getStep()).getMessage());                
                // 项目表
                GardenProject project = new GardenProject();
                project.setId(form.getId());
                // 商务标制作人
                project.setBusinessProducer(form.getProducerId());
                // 确定项目所属的阶段
//                project.setStep(GardenProjectStepEnum.P_250.getValue());
                project.setStep(srcProject.getStep() + 10);
                project.setUpdateTime(new Date());
                project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());

                // 审核
                ChiefAudit chiefAudit = new ChiefAudit();
                chiefAudit.setCompanyId(SpringSecurityUtil.getIntCompany());
                chiefAudit.setProjectId(form.getId());
                chiefAudit.setApplicant(SpringSecurityUtil.getIntPrincipal());
                // 总工审核类型类型   技术标
//                chiefAudit.setType(57);
                chiefAudit.setType(form.getType());
                chiefAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                chiefAudit.setCreateTime(new Date());
                chiefAudit.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                chiefAudit.setUpdateTime(chiefAudit.getCreateTime());
                chiefAudit.setUpdateUserId(chiefAudit.getCreateUserId());
                
                // 文件上传表
                ProjectFileUpload file = new ProjectFileUpload();
                file.setCompanyId(SpringSecurityUtil.getIntCompany());
                file.setProjectId(form.getId());
                // 文件id
//                file.setFileId(6);
                if(StringUtils.isNotBlank(form.getFileId())){
                    file.setFileId(StringUtil.stringToInteger(form.getFileId()));
                }
                file.setFileName(form.getFileName());
                file.setFileAddress(form.getFileUrl());
                file.setSourceType(DeviceEnum.WAP.getValue());
                file.setCreateTime(new Date());
                file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                file.setUpdateTime(file.getCreateTime());
                file.setUpdateUserId(file.getCreateUserId());
                ResultEntry<Integer> result = projectFileInfoFacade.projectFileUpload(project, file, chiefAudit);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message message = new Message();
                    if(GardenProjectStepEnum.P_400.getValue().equals(srcProject.getStep())){
                        message.setTitle(MessageContextEnum.Msg_770.getValue());
                        message.setAddress(MessageContextEnum.Msg_770.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_770.getMessage(), 
                                            srcProject.getName()));
                        MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getZongGongPersons()); 
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(srcProject.getName());
                        pointSymbol.setAuditType("方案初稿");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_770.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getZongGongPersons());
                    }else if(GardenProjectStepEnum.P_430.getValue().equals(srcProject.getStep())){
                        message.setTitle(MessageContextEnum.Msg_810.getValue());
                        message.setAddress(MessageContextEnum.Msg_810.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_810.getMessage(), 
                                            srcProject.getName()));
                        MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getZongGongPersons());
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(srcProject.getName());
                        pointSymbol.setAuditType("扩初初稿");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_810.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getZongGongPersons());
                    }else if(GardenProjectStepEnum.P_460.getValue().equals(srcProject.getStep())){
                        message.setTitle(MessageContextEnum.Msg_850.getValue());
                        message.setAddress(MessageContextEnum.Msg_850.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_850.getMessage(), 
                                            srcProject.getName()));
                        MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getZongGongPersons()); 
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(srcProject.getName());
                        pointSymbol.setAuditType("施工初稿");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_850.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getZongGongPersons());
                    }else if(GardenProjectStepEnum.P_490.getValue().equals(srcProject.getStep())){
                        message.setTitle(MessageContextEnum.Msg_880.getValue());
                        message.setAddress(MessageContextEnum.Msg_880.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_880.getMessage(), 
                                            srcProject.getName()));
                        MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getZongGongPersons()); 
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(srcProject.getName());
                        pointSymbol.setAuditType("规划初稿");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_880.getTemplateCode());
                        MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getZongGongPersons());
                    }                   
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }else{
                putError(model, "项目信息不存在");
            }
        } catch (Exception ex) {
            logger.error("上传初稿失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 上传投标文件
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/tenderFile.htm", method = RequestMethod.POST)
    public void tenderFile(HttpServletRequest req, HttpServletResponse resp, ProjectFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setProjectId(form.getId());
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_280.getValue());
            //adminBusiness.setComment(AdminBusinessTypeEnum.B_280.getMessage());
            // 项目表
            GardenProject project = new GardenProject();
            project.setId(form.getId());
            // 商务标制作人
            project.setBusinessProducer(form.getUserId());
            // 投标报价
            project.setTenderOffer(NumberUtils.string2BigDecimal(form.getTenderMoney()));
            project.setStep(GardenProjectStepEnum.P_290.getValue());
            project.setUpdateTime(new Date());
            project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());

            // 文件上传表
            List<ProjectFileUpload> fileLst = new ArrayList<ProjectFileUpload>();
            if (StringUtils.isNotBlank(form.getFileId()) 
                    && StringUtils.isNotBlank(form.getFileName()) 
                    && StringUtils.isNotBlank(form.getFileUrl())) {
                String[] fileId = form.getFileId().split(",");
                String[] fileName = form.getFileName().split(",");
                String[] fileUrl = form.getFileUrl().split(",");
                Assert.isTrue(fileId.length == fileName.length, "文件选择个数不对");
                Assert.isTrue(fileId.length == fileUrl.length, "文件选择个数不对");
                ProjectFileUpload file = null;
                for (int cnt = 0; cnt < fileId.length; cnt++) {
                    file = new ProjectFileUpload();
                    file.setCompanyId(SpringSecurityUtil.getIntCompany());
                    file.setProjectId(form.getId());
                    // 文件id
                    if(StringUtils.isNotBlank(fileId[cnt])){
                        file.setFileId(StringUtil.stringToInteger(fileId[cnt]));
                    }
                    file.setFileName(fileName[cnt]);
                    file.setFileAddress(fileUrl[cnt]);
                    file.setSourceType(DeviceEnum.WAP.getValue());
                    file.setCreateTime(project.getUpdateTime());
                    file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                    file.setUpdateTime(file.getCreateTime());
                    file.setUpdateUserId(file.getCreateUserId());
                    fileLst.add(file);
                }
            }

            ResultEntry<Integer> result = projectFileInfoFacade.projectFileUpload(project, fileLst);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("上传投标文件失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 上传合同拟定版
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/uploadContract.htm", method = RequestMethod.POST)
    public void uploadContract(HttpServletRequest req, HttpServletResponse resp, ProjectFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 获取该项目的当时信息
            GardenProject srcProject = null;
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
            if(pjRst.isSuccess()){
                srcProject = pjRst.getList().get(0);

                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_300.getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.B_300.getMessage());
                // 项目表
                GardenProject project = new GardenProject();
                project.setId(form.getId());
                project.setStep(GardenProjectStepEnum.P_310.getValue());
                project.setUpdateTime(new Date());
                project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());

                // 经营部门审核
                ManageAudit manageAudit = null;
                // 经营审核数据插入
                manageAudit = new ManageAudit();
                manageAudit.setCompanyId(SpringSecurityUtil.getIntCompany());
                manageAudit.setProjectId(form.getId());
                manageAudit.setApplicant(SpringSecurityUtil.getIntPrincipal());
                // 经营审核类型   商务标
                manageAudit.setType(49);
                manageAudit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                manageAudit.setCreateTime(new Date());
                manageAudit.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                manageAudit.setUpdateTime(manageAudit.getCreateTime());
                manageAudit.setUpdateUserId(manageAudit.getCreateUserId());
                
                // 文件上传表
                ProjectFileUpload file = new ProjectFileUpload();
                file.setCompanyId(SpringSecurityUtil.getIntCompany());
                file.setProjectId(form.getId());
                // 文件id
                if(StringUtils.isNotBlank(form.getFileId())){
                    file.setFileId(StringUtil.stringToInteger(form.getFileId()));
                }
                file.setFileName(form.getFileName());
                file.setFileAddress(form.getFileUrl());
                file.setSourceType(DeviceEnum.WAP.getValue());
                file.setCreateTime(new Date());
                file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                file.setUpdateTime(file.getCreateTime());
                file.setUpdateUserId(file.getCreateUserId());
                ResultEntry<Integer> result = projectFileInfoFacade.projectFileUpload(project, file, manageAudit);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message message = new Message();
                    message.setTitle(MessageContextEnum.Msg_520.getValue());
                    message.setAddress(MessageContextEnum.Msg_520.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_520.getMessage(), 
                                        srcProject.getName()));
                    MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getJinYingManagerPersons());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(srcProject.getName());
                    pointSymbol.setAuditType("合同");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_520.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getJinYingManagerPersons());

                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }
        } catch (Exception ex) {
            logger.error("上传合同拟定版失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取合同拟定版
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getContract.htm", method = RequestMethod.POST)
    public void getContract(HttpServletRequest req, HttpServletResponse resp, ProjectSchemaForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ProjectFileQuery query = new ProjectFileQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setFileId(form.getFileId());
            query.setProjectId(form.getProjectId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<ProjectFileInfo> result = projectFileInfoFacade.getFileUploadByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取合同拟定版失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 确认合同拟定版
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/contractConfirm.htm", method = RequestMethod.POST)
    public void contractConfirm(HttpServletRequest req, HttpServletResponse resp, ProjectSchemaForm form){
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
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_320.getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.B_320.getMessage());
                // 项目表
                GardenProject project = new GardenProject();
                project.setId(form.getProjectId());
                project.setStep(GardenProjectStepEnum.P_330.getValue());
                project.setUpdateTime(new Date());
                project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                
                // 财务表
                FinancialAudit audit = new FinancialAudit();
                audit.setCompanyId(SpringSecurityUtil.getIntCompany());
                audit.setProjectId(form.getProjectId());
                // 申请人
                audit.setApplicant(form.getProposerId());
                // 财务审核类型   审核合同
                audit.setType(27);
                audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                audit.setCreateTime(project.getUpdateTime());
                audit.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                audit.setUpdateTime(audit.getCreateTime());
                audit.setUpdateUserId(audit.getCreateUserId());
                
                // 合同表
                ProjectContract record = new ProjectContract();
                record.setCompanyId(SpringSecurityUtil.getIntCompany());
                record.setProjectId(form.getProjectId());
                record.setContractMemo(form.getReason());
                record.setStatus(ProjectTableStatusEnum.VERIFIED.getValue());
                record.setUpdateTime(project.getUpdateTime());
                record.setUpdateUserId(project.getUpdateUserId());
                ResultEntry<Integer> result = projectFileInfoFacade.updateProjectContractInfo(project, record, audit);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message message = new Message();
                    // 技术审核合同
                    message.setTitle(MessageContextEnum.Msg_600.getValue());
                    message.setAddress(MessageContextEnum.Msg_600.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_600.getMessage(), 
                                        srcProject.getName()));
                    // 会计人员
                    MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getKuaijiPersons());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(srcProject.getName());
                    pointSymbol.setAuditType("合同");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_600.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getKuaijiPersons());
                    // 经营专员
                    if (ProjectTableStatusEnum.VERIFIED.getValue().equals(record.getStatus())) {
                        message.setTitle(MessageContextEnum.Msg_610.getValue());
                        message.setAddress(MessageContextEnum.Msg_610.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_610.getMessage(), 
                                            srcProject.getName()));
                        pointSymbol = new PointSymbol();
                        pointSymbol.setName(srcProject.getName());
                        pointSymbol.setAuditType("合同");
                        pointSymbol.setJob("负责人");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_610.getTemplateCode());
                    }else{
                        // 审核不通过 --- 经营专员
                        message.setTitle(MessageContextEnum.Msg_620.getValue());
                        message.setAddress(MessageContextEnum.Msg_620.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_620.getMessage(), 
                                    srcProject.getName(), form.getReason()));
                        pointSymbol = new PointSymbol();
                        pointSymbol.setName(srcProject.getName());
                        pointSymbol.setAuditType("合同拟定技术审核");
                        pointSymbol.setReason(form.getReason());
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_620.getTemplateCode());
                    }
                    MessageHandlerUtil.insertMessage(message, srcProject.getManagementPersonnel());
                    MessageHandlerUtil.sendSms(pointSymbol, srcProject.getManagementPersonnel());
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }
        } catch (Exception ex) {
            logger.error("确认合同拟定版失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 上传合同最终版
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/updConfirmContract.htm", method = RequestMethod.POST)
    public void confirmContract(HttpServletRequest req, HttpServletResponse resp, ProjectFileForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {

            // 获取该项目的当时信息
            GardenProject srcProject = null;
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
            if(pjRst.isSuccess()){
                srcProject = pjRst.getList().get(0);

                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_370.getValue());
                //adminBusiness.setComment(AdminBusinessTypeEnum.B_370.getMessage());
                // 项目表
                GardenProject project = new GardenProject();
                project.setId(form.getId());
                project.setStep(GardenProjectStepEnum.P_380.getValue());
                project.setUpdateTime(new Date());
                project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                
                // 文件上传表
                ProjectFileUpload file = new ProjectFileUpload();
                file.setCompanyId(SpringSecurityUtil.getIntCompany());
                file.setProjectId(form.getId());
                // 文件id
                if(StringUtils.isNotBlank(form.getFileId())){
                    file.setFileId(StringUtil.stringToInteger(form.getFileId()));
                }
                file.setFileName(form.getFileName());
                file.setFileAddress(form.getFileUrl());
                file.setSourceType(DeviceEnum.WAP.getValue());
                file.setCreateTime(new Date());
                file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                file.setUpdateTime(file.getCreateTime());
                file.setUpdateUserId(file.getCreateUserId());
                ResultEntry<Integer> result = projectFileInfoFacade.projectFileUpload(project, file);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    Message message = new Message();
                    message.setTitle(MessageContextEnum.Msg_730.getValue());
                    message.setAddress(MessageContextEnum.Msg_730.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_730.getMessage(), 
                                        srcProject.getName()));
                    // 技术负责人员
                    MessageHandlerUtil.insertMessage(message, srcProject.getProjectLeader());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(srcProject.getName());
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_730.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, srcProject.getProjectLeader());
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }
        } catch (Exception ex) {
            logger.error("上传合同最终版失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
