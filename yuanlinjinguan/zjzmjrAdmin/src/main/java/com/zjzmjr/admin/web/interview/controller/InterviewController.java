package com.zjzmjr.admin.web.interview.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.interview.form.InterviewForm;
import com.zjzmjr.core.api.interview.IInterviewFacade;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.common.util.SmsUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.AdminJobStatusEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.interview.InterviewResultEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.message.NotifyContextEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.interview.Interview;
import com.zjzmjr.core.model.interview.InterviewInfo;
import com.zjzmjr.core.model.interview.InterviewQuery;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/interview/user")
public class InterviewController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(InterviewController.class);

    private final static String index = "/WEB-INF/pages/interview/interview.jsp";

    @Resource(name = "interviewFacade")
    private IInterviewFacade interviewFacade;
    
    @Resource(name = "staffPersonFacade")
    private IStaffPersonFacade staffPersonFacade;
    
    /**
     * 
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("interviewSetAuth", SpringSecurityUtil.hasAuth(UserAuthParams.INTERVIEW_SET));
        model.put("interviewTalentPrintAuth", SpringSecurityUtil.hasAuth(UserAuthParams.INTERVIEW_TALENT_PRINT));
        model.put("interviewResultAuth", SpringSecurityUtil.hasAuth(UserAuthParams.INTERVIEW_RESULT));
        model.put("interviewExportAuth", SpringSecurityUtil.hasAuth(UserAuthParams.INTERVIEW_EXPORT));
        return index;
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
            query.setId(form.getId());
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName());
            }
            if(Util.isNotNull(form.getMobile())){
                query.setMobile(form.getMobile());
            }
            if(Util.isNotNull(form.getIdentityNo())){
                query.setIdentityNo(form.getIdentityNo());
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setTalentId(form.getTalentId());
            query.setRecruitmentId(form.getRecruitmentId());
            query.setRound(form.getRound());
            if(Util.isNotNull(form.getTimeStart())){
                query.setTimeStart(form.getTimeStart()); 
            }
            if(Util.isNotNull(form.getTimeEnd())){
                query.setTimeEnd(form.getTimeEnd()); 
            }
            if(Util.isNotNull(form.getInterviewer())){
                query.setInterviewer(form.getInterviewer());
            }
            if(Util.isNotNull(form.getWrittenScoreStart())){
                query.setWrittenScoreStart(form.getWrittenScoreStart());
            }
            if(Util.isNotNull(form.getWrittenScoreEnd())){
                query.setWrittenScoreEnd(form.getWrittenScoreEnd());
            }
            if(Util.isNotNull(form.getInterviewScoreStart())){
                query.setInterviewScoreStart(form.getInterviewScoreStart());
            }
            if(Util.isNotNull(form.getInterviewScoreEnd())){
                query.setInterviewScoreEnd(form.getInterviewScoreEnd()); 
            }
            query.setResult(form.getResult());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<InterviewInfo> result = interviewFacade.getInterviewInfo(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
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
     * 新增面试
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/insertInterview.htm", method = RequestMethod.POST)
    public void insertInterview(HttpServletResponse resp, InterviewForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
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
            query.setCreateTime(new Date());
            query.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = interviewFacade.insertInterview(query);
            if(result.isSuccess()){
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("面试新增出错：", ex);
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
    @Security(auth = { UserAuthParams.INTERVIEW_SET}, checkSource = false)
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
     * 条件查询面试管理
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getInterviewByCheckTalent.htm", method = RequestMethod.POST)
    public void getInterviewByCheckTalent(HttpServletResponse resp, InterviewForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            InterviewQuery query = new InterviewQuery();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setTalentId(form.getTalentId());
            query.setRecruitmentId(form.getRecruitmentId());
            query.setCheckTalent(form.getCheckTalent());
            ResultRecord<String> result = interviewFacade.getInterviewByCheckTalent(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("提交面试出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 人才页面提交面试
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.TALENT_SET_INTERVIEW}, checkSource = false)
    @RequestMapping(value = "/setInterview.htm", method = RequestMethod.POST)
    public void setInterview(HttpServletResponse resp, HttpServletRequest req, InterviewForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_INTERVIEW.getValue());
            InterviewQuery query = new InterviewQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setCheckTalent(form.getCheckTalent());
            query.setCreateTime(new Date());
            query.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = interviewFacade.setInterview(query);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                //adminBusiness.setExtend1(form.getCheckTalent());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("人才页面提交面试出错：", ex);
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
    @Security(auth = { UserAuthParams.INTERVIEW_RESULT}, checkSource = false)
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
    
    /**
     * 面试信息打印
     * 
     * @param req
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.INTERVIEW_TALENT_PRINT}, checkSource = false)
    @RequestMapping(value = "/interviewPrint.htm", method = RequestMethod.POST)
    public void interviewPrint(HttpServletRequest req, HttpServletResponse resp, InterviewForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            InterviewQuery query = new InterviewQuery();
            OutputStream outputStream = null;
            query.setId(form.getId());
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName());
            }
            if(Util.isNotNull(form.getMobile())){
                query.setMobile(form.getMobile());
            }
            if(Util.isNotNull(form.getIdentityNo())){
                query.setIdentityNo(form.getIdentityNo());
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setTalentId(form.getTalentId());
            if(!(GenerateConstants.number_0>form.getRecruitmentId())){
                query.setRecruitmentId(form.getRecruitmentId());
            }
            query.setRound(form.getRound());
            if(Util.isNotNull(form.getTimeStart())){
                query.setTimeStart(form.getTimeStart()); 
            }
            if(Util.isNotNull(form.getTimeEnd())){
                query.setTimeEnd(form.getTimeEnd()); 
            }
            if(Util.isNotNull(form.getInterviewer())){
                query.setInterviewer(form.getInterviewer());
            }
            if(Util.isNotNull(form.getWrittenScoreStart())){
                query.setWrittenScoreStart(form.getWrittenScoreStart());
            }
            if(Util.isNotNull(form.getWrittenScoreEnd())){
                query.setWrittenScoreEnd(form.getWrittenScoreEnd());
            }
            if(Util.isNotNull(form.getInterviewScoreStart())){
                query.setInterviewScoreStart(form.getInterviewScoreStart());
            }
            if(Util.isNotNull(form.getInterviewScoreEnd())){
                query.setInterviewScoreEnd(form.getInterviewScoreEnd()); 
            }
            if(!(GenerateConstants.number_0>form.getResult())){
                query.setResult(form.getResult());
            }
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<InterviewInfo> result = interviewFacade.getInterviewInfo(query);
            if (result.isSuccess()) {
                List<InterviewInfo> list = result.getList();
                for (int i = 0; i < list.size(); i++) {
                    if(Util.isNotNull(list.get(i).getResult())){
                        list.get(i).setResultText(InterviewResultEnum.getByValue(list.get(i).getResult()).getMessage()); 
                    }
                    list.get(i).setCreateTimeText(DateUtil.format(list.get(i).getCreateTime(), "yyyy/MM/dd"));
                }
                // 设置文件标题行
                String[] headers = { "姓名", "联系电话", "身份证号", "应聘职位", "面试邀请时间", "轮次", "面试时间", "面试地点", "面试官", "笔试得分", "面试得分", "面试结果", "评审记录时间"};
                String[] rows = { "talent.name", "talent.mobile", "talent.identityNo", "recruitment.positionName", "createTimeText", "round", "time", "place", "admin.name", "writtenScore"
                        ,"interviewScore","resultText","recordTime"};
                // 生成excel文件
                // 标题行对应的属性名
                HSSFWorkbook wb = ExcelUtil.exportExcel("面试信息导出", headers, rows, list, "yyyyMMddhhmmssSSS");
                resp.setContentType("application/vnd.ms-excel");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                setFileDownloadHeader(req, resp, "面试信息导出" + sdf.format(new Date()) + ".xls");
                outputStream = resp.getOutputStream();
                wb.write(outputStream);
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("面试信息打印出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    
    /**
     * 根据当前用户的浏览器不同，对文件的名字进行不同的编码设置，解决不同浏览器下文件名中文乱码问题
     * 
     * @param request
     * @param response
     * @param fileName
     */

    private void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        final String userAgent = request.getHeader("USER-AGENT");
        try {
            String finalFileName = null;
            if (com.alibaba.dubbo.common.utils.StringUtils.isContains(userAgent, "MSIE")) {// IE浏览器
                finalFileName = URLEncoder.encode(fileName, "UTF8");
            } else if (com.alibaba.dubbo.common.utils.StringUtils.isContains(userAgent, "Mozilla")) {// google,火狐浏览器
                finalFileName = new String(fileName.getBytes(), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "\"");// 这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
        } catch (UnsupportedEncodingException e) {
            logger.error("设置浏览器字符编码集出错", e);
        }
    }
}
