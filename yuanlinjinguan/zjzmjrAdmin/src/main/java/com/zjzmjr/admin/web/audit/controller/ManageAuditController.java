package com.zjzmjr.admin.web.audit.controller;

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
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.audit.form.AuditForm;
import com.zjzmjr.admin.web.audit.form.ProjectSchemaForm;
import com.zjzmjr.core.api.audit.IGardenProjectAuditFacade;
import com.zjzmjr.core.api.project.IGardenProjectFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectSchemaStatusEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectManageAudit;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.GardenProjectSchema;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/manageAudit/user")
public class ManageAuditController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ManageAuditController.class);
    private final static String index = "/WEB-INF/pages/audit/manage.jsp";

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return index;
    }

    @Resource(name = "gardenProjectAuditFacade")
    private IGardenProjectAuditFacade gardenProjectAuditFacade;
    
    @Resource(name = "gardenProjectFacade")
    private IGardenProjectFacade gardenProjectFacade;
    
    /**
     * 经营审核信息一览
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/getProjectManageAudit.htm", method = RequestMethod.POST)
    public void getProjectManageAudit(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectAuditQuery query = new GardenProjectAuditQuery();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            if(Util.isNotNull(form.getProjectNo())){
                query.setProjectNo(form.getProjectNo()); 
            }
            //项目名称
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName());  
            }
            if(Util.isNotNull(form.getAdminName())){
                query.setAdminName(form.getAdminName()); 
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart()); 
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd());                
            }
            if(Util.isNotNull(form.getType())){
                query.setType(form.getType());
            }
            if(Util.isNotNull(form.getStatus())){
                query.setStatus(form.getStatus()); 
            }
            if(Util.isNotNull(form.getVerifyTimeStart())){
                query.setVerifyTimeStart(form.getVerifyTimeStart());
            }
            if(Util.isNotNull(form.getVerifyTimeEnd())){
                query.setVerifyTimeEnd(form.getVerifyTimeEnd());
            }     
            query.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<GardenProjectManageAudit> result = gardenProjectAuditFacade.getProjectManageAudit(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("经营审核信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 经营审核修改
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/updateManageAudit.htm", method = RequestMethod.POST)
    public void updateManageAudit(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectAuditQuery manage = new GardenProjectAuditQuery();
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();  
            manage.setCompanyId(SpringSecurityUtil.getIntCompany());
            manage.setProjectId(form.getProjectId());           
            manage.setStatus(form.getStatus());
            if(Util.isNotNull(form.getAmount())){
                manage.setAmount(new BigDecimal(form.getAmount()));
            }
            manage.setStep(form.getStep());
            manage.setSubStep(form.getSubStep());
            manage.setType(form.getType());
            manage.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            manage.setMemo(form.getMemo());  
            if(Util.isNotNull(form.getStep())){
                adminBusiness.setBusinessType(form.getStep()); 
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(form.getStep()).getMessage());
                if(49==form.getType()){
                    manage.setIschief(form.getIschief());
                }
            }else if(Util.isNotNull(form.getSubStep())){
                adminBusiness.setBusinessType(form.getSubStep());
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(form.getSubStep()).getMessage());
            }else if(50==form.getType()){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_810.getValue()); 
                //adminBusiness.setComment(AdminBusinessTypeEnum.B_810.getMessage());
            }else if(51==form.getType()){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_811.getValue()); 
                //adminBusiness.setComment(AdminBusinessTypeEnum.B_811.getMessage());
            }
            if(ProjectTableStatusEnum.VERIFIED.getValue().equals(form.getStatus())){
                adminBusiness.setComment(ProjectTableStatusEnum.VERIFIED.getMessage());
            }else if(ProjectTableStatusEnum.VERIFIED_FAIL.getValue().equals(form.getStatus())){
                if(Util.isNotNull(form.getMemo())){
                    adminBusiness.setComment(ProjectTableStatusEnum.VERIFIED_FAIL.getMessage()+":"+form.getMemo());  
                }else{
                    adminBusiness.setComment(ProjectTableStatusEnum.VERIFIED_FAIL.getMessage());                    
                }                
            }
            adminBusiness.setProjectId(form.getProjectId());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = gardenProjectAuditFacade.updateManageAudit(manage);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
               // 发送消息
                MessageHandlerUtil.insertMessage(manage);
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            } 
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("经营审核修改出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
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
            Assert.isTrue(Util.isNotNull(form.getGardenProjectId()), "请确认具体的项目");
            
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getGardenProjectId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultEntry<GardenProject> projRst = gardenProjectFacade.getGardenProjectByIdAndStatus(query);
            if(projRst.isSuccess()){
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setProjectId(form.getGardenProjectId());
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_20.getValue());
                ResultEntry<Integer> result = new ResultEntry<Integer>();
                // 如果是审核不通过时
                if (2 == form.getPassed()) {
                    // 更新项目表
                    GardenProject project = new GardenProject();
                    project.setId(form.getGardenProjectId());
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
                    project.setId(form.getGardenProjectId());
                    // 经营专员
                    project.setManagementPersonnel(form.getManagementPersonnel());
                    if (StringUtils.isNotBlank(form.getApplyDeadline())) {
                        project.setApplyDeadline(form.getApplyDeadline().replace("/", ""));
                    }
                    project.setStep(GardenProjectStepEnum.P_30.getValue());
                    project.setUpdateTime(new Date());
                    project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                    // 通过审核的时候
                    List<GardenProjectSchema> recordLst = new ArrayList<GardenProjectSchema>();
                    GardenProjectSchema record = new GardenProjectSchema();
                    record.setCompanyId(SpringSecurityUtil.getIntCompany());
                    // 建议人方案
                    record.setProjectId(form.getGardenProjectId());
                    record.setSchemeProposer(form.getProposerId());
                    record.setResason(form.getReason());
                    record.setAccepted(ProjectSchemaStatusEnum.WAITING_APPLY.getValue());
                    // 0:非推荐 1:推荐
                    record.setRecommend(GenerateConstants.number_1);
                    record.setCreateTime(new Date());
                    record.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                    Message message = new Message();
                    PointSymbol pointSymbol = new PointSymbol();
                    if(Util.isNotNull(form.getProposerId())){
                        message.setTitle(MessageContextEnum.recommendLeader.getValue());
                        message.setAddress(MessageContextEnum.recommendLeader.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.recommendLeader.getMessage(), 
                                    projRst.getT().getName(), formatDateStr(project.getApplyDeadline())));
                        MessageHandlerUtil.insertMessage(message, form.getProposerId());                        
                        pointSymbol.setTemplateCode(MessageContextEnum.recommendLeader.getTemplateCode());
                        pointSymbol.setName(projRst.getT().getName());
                        pointSymbol.setType("推荐");
                        pointSymbol.setDate(formatDateStr(project.getApplyDeadline()));
                        MessageHandlerUtil.sendSms(pointSymbol, form.getProposerId()); 
                    }
                    recordLst.add(record);
                    // 邀请人方案
                    if (StringUtils.isNotBlank(form.getInviterIds())) {
                        String[] inviters = form.getInviterIds().split(",");
                        for (String inviter : inviters) {
                            record = new GardenProjectSchema();
                            record.setCompanyId(SpringSecurityUtil.getIntCompany());
                            record.setProjectId(form.getGardenProjectId());
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
