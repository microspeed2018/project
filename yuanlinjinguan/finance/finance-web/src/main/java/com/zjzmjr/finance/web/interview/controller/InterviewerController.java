package com.zjzmjr.finance.web.interview.controller;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.interview.IInterviewFacade;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.common.util.SmsUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.AdminJobStatusEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.message.NotifyContextEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.interview.Interview;
import com.zjzmjr.core.model.interview.InterviewInfo;
import com.zjzmjr.core.model.interview.InterviewQuery;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.finance.web.interview.form.InterviewForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;


/**
 * 面试管理
 * 
 * @author lenovo
 * @version $Id: InterviewerController.java, v 0.1 2018-1-19 下午7:36:40 lenovo Exp $
 */
@Controller
@RequestMapping("/interview/user")
public class InterviewerController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(InterviewerController.class);

    @Resource(name = "interviewFacade")
    private IInterviewFacade interviewFacade;
    
    @Resource(name = "staffPersonFacade")
    private IStaffPersonFacade staffPersonFacade;

    /**
     * 我的面试一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getMyInterviewInfo.htm", method = RequestMethod.POST)
    public void getMyInterviewInfo(HttpServletResponse resp, InterviewForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            InterviewQuery query = new InterviewQuery();
            query.setTalentAdminId(SpringSecurityUtil.getIntPrincipal());
//            query.setInterviewer(SpringSecurityUtil.getIntPrincipal());
//            query.setPosNameOrInterview(form.getPosNameOrInterview());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<InterviewInfo> result = interviewFacade.getMyInterviewInfo(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("我的面试信息取得出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 面试一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getInterviewInfo.htm", method = RequestMethod.POST)
    public void getInterviewInfo(HttpServletResponse resp, InterviewForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            InterviewQuery query = new InterviewQuery();           
            query.setInterviewer(SpringSecurityUtil.getIntPrincipal());
            query.setPosNameOrInterview(form.getPosNameOrInterview());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<InterviewInfo> result = interviewFacade.getInterviewInfo(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("面试信息一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
   
    /**
     * 面试条件查询面试结果
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getInterviewCondition.htm", method = RequestMethod.POST)
    public void getInterviewCondition(HttpServletResponse resp, InterviewForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            InterviewQuery query = new InterviewQuery();           
            query.setRound(form.getRound());
            query.setTalentId(form.getTalentId());
            query.setRecruitmentId(form.getRecruitmentId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<InterviewInfo> result = interviewFacade.getInterviewInfo(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("面试条件查询面试结果出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 修改面试
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/updateInterview.htm", method = RequestMethod.POST)
    public void updateInterview(HttpServletResponse resp, HttpServletRequest req, InterviewForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.INTERVIEW_SET.getValue());
            Interview query = new Interview();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setTalentId(form.getTalentId());
            query.setRecruitmentId(form.getRecruitmentId());
            query.setRound(form.getRound());
            query.setResult(form.getResult());
            query.setTime(form.getTime());
            query.setPlace(form.getPlace());
            query.setInterviewer(form.getInterviewer());
            query.setWrittenScore(form.getWrittenScore());
            query.setInterviewScore(form.getInterviewScore());
            query.setTemperament(form.getTemperament());
            query.setExperience(form.getExperience());
            query.setSpecialty(form.getSpecialty());
            query.setIntention(form.getIntention());
            query.setStability(form.getStability());
            query.setDetails(form.getDetails());
            query.setEfficiency(form.getEfficiency());
            query.setExecutive(form.getExecutive());
            query.setRelationship(form.getRelationship());
            query.setComment(form.getComment());
            query.setCommunication(form.getCommunication());
            query.setMemo(form.getMemo());
            query.setIsSms(form.getIsSms());
            query.setRecordTime(form.getRecordTime());
            query.setUpdateTime(new Date());
            query.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = interviewFacade.updateInterview(query);
            if(result.isSuccess()){
                String interviewMobile = null;
                ExternalPersonQuery pquerys = new ExternalPersonQuery();
                pquerys.setCompanyId(SpringSecurityUtil.getIntCompany());
                pquerys.setMobile(form.getMobile());
                pquerys.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                ResultPage<ExternalPersonInfo> interviewerRst = staffPersonFacade.getExternalPersonByCondition(pquerys);
                if(interviewerRst.isSuccess()){
                    //发送站内信
                    Message message = new Message();
                    message.setTitle(MessageContextEnum.interview_invite.getValue());
                    message.setUserId(interviewerRst.getList().get(0).getUserId());
                    if(Util.isNotNull(interviewerRst.getList().get(0).getUserInfo())){
                        message.setClientId(interviewerRst.getList().get(0).getUserInfo().getClientId()); 
                    }
                    message.setAddress(MessageContextEnum.interview_invite.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.interview_invite.getMessage(), form.getRecruitmentName()+"第"+form.getRoundText()+"轮面试",
                            DateUtil.format(DateUtil.parse(form.getTime(), "yyyy/MM/dd hh:mm"),"yyyy年MM月dd日  hh时mm分"), form.getPlace()));
                    MessageHandlerUtil.insertMessage(message);
                }
                if(Util.isNotNull(form.getInterviewer())){
                    StaffInfoQuery querys = new StaffInfoQuery();
                    querys.setCompanyId(SpringSecurityUtil.getIntCompany());
                    querys.setUserId(form.getInterviewer());
                    querys.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
                    querys.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                    ResultPage<StaffBasicInfo> results = staffPersonFacade.getStaffInfoByCondition(querys);
                    if(results.isSuccess()){
                        interviewMobile = results.getList().get(0).getUserInfo().getMobile();
                        //发送站内信
                        Message message = new Message();
                        message.setTitle(MessageContextEnum.interview_remind.getValue());
                        message.setAddress(MessageContextEnum.interview_remind.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.interview_remind.getMessage(), form.getRecruitmentName()+"第"+form.getRoundText()+"轮面试，面试者："+form.getInterviewerName(),
                                DateUtil.format(DateUtil.parse(form.getTime(), "yyyy/MM/dd hh:mm"),"yyyy年MM月dd日  hh时mm分"), form.getPlace()));
                        MessageHandlerUtil.insertMessage(message,results.getList());
                    }
                }
                if(Util.isNotNull(form.getIsSms())){
                    //发送短信提醒
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setSome(form.getRecruitmentName()+"第"+form.getRoundText()+"轮面试");
                    pointSymbol.setTime(DateUtil.format(DateUtil.parse(form.getTime(), "yyyy/MM/dd hh:mm"),"yyyy年MM月dd日  hh时mm分"));
                    pointSymbol.setPlace(form.getPlace());
                    pointSymbol.setTemplateCode(NotifyContextEnum.interview_invitation.getTemplateCode());
                    pointSymbol.setMobile(form.getMobile());
                    SmsUtil.sendSMS(pointSymbol);                    
                    //发送短信提醒(面试官)
                    //id查询面试官手机号                   
                    if(Util.isNotNull(form.getInterviewer())){
                        if(Util.isNotNull(interviewMobile)){
                            pointSymbol = new PointSymbol();
                            pointSymbol.setSome(form.getRecruitmentName()+"第"+form.getRoundText()+"轮面试，面试者："+form.getInterviewerName());
                            pointSymbol.setTime(DateUtil.format(DateUtil.parse(form.getTime(), "yyyy/MM/dd hh:mm"),"yyyy年MM月dd日  hh时mm分"));
                            pointSymbol.setPlace(form.getPlace());
                            pointSymbol.setTemplateCode(NotifyContextEnum.interview_remind.getTemplateCode());
                            pointSymbol.setMobile(interviewMobile);
                            SmsUtil.sendSMS(pointSymbol); 
                        } 
                    }  
                }
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                adminBusiness.setExtend1("姓名："+form.getInterviewerName());
                adminBusiness.setExtend2("职位："+form.getRecruitmentName());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("面试修改出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    
    /**
     * 面试结果设置
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/setInterviewResult.htm", method = RequestMethod.POST)
    public void setInterviewResult(HttpServletResponse resp, HttpServletRequest req, InterviewForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.INTERVIEW_RESULT.getValue());
            Interview  query= new Interview();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setTalentId(form.getTalentId());
            query.setRecruitmentId(form.getRecruitmentId());
            query.setRound(form.getRound());
            query.setResult(form.getResult());
            query.setTime(form.getTime());
            query.setPlace(form.getPlace());
            query.setInterviewer(form.getInterviewer());
            query.setWrittenScore(form.getWrittenScore());
            query.setInterviewScore(form.getInterviewScore());
            query.setTemperament(form.getTemperament());
            query.setExperience(form.getExperience());
            query.setSpecialty(form.getSpecialty());
            query.setIntention(form.getIntention());
            query.setStability(form.getStability());
            query.setDetails(form.getDetails());
            query.setEfficiency(form.getEfficiency());
            query.setExecutive(form.getExecutive());
            query.setRelationship(form.getRelationship());
            query.setComment(form.getComment());
            query.setCommunication(form.getCommunication());
            query.setRecordTime(DateUtil.format(new Date(),"yyyy/MM/dd HH:mm"));
            query.setUpdateTime(new Date());
            query.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = interviewFacade.setInterviewResult(query);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                adminBusiness.setExtend1("面试id："+form.getId());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("面试结果设置出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
