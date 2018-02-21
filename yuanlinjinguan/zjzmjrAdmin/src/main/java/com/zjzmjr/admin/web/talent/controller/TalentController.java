package com.zjzmjr.admin.web.talent.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

import com.zjzmjr.admin.web.talent.form.TalentForm;
import com.zjzmjr.core.api.talent.ITalentFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.talent.TalentDocument;
import com.zjzmjr.core.model.talent.TalentDocumentQuery;
import com.zjzmjr.core.model.talent.TalentEducation;
import com.zjzmjr.core.model.talent.TalentEducationQuery;
import com.zjzmjr.core.model.talent.TalentFamily;
import com.zjzmjr.core.model.talent.TalentFamilyQuery;
import com.zjzmjr.core.model.talent.TalentInfo;
import com.zjzmjr.core.model.talent.TalentJob;
import com.zjzmjr.core.model.talent.TalentJobQuery;
import com.zjzmjr.core.model.talent.TalentQuery;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/talent/user")
public class TalentController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TalentController.class);
    
    private final static String index = "/WEB-INF/pages/talent/talentinformation.jsp";
    
    @Resource(name = "talentFacade")
    private ITalentFacade talentFacade;
    
    /** 
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("talentSetInterviewAuth", SpringSecurityUtil.hasAuth(UserAuthParams.TALENT_SET_INTERVIEW));
        model.put("talentExportAuth", SpringSecurityUtil.hasAuth(UserAuthParams.TALENT_EXPORT));
        return index;
    }
    
    /**
     * 人才一览
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getTalentInfo.htm", method = RequestMethod.POST)
    public void getTalentInfo(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentQuery talentQuery = new TalentQuery();
            talentQuery.setId(form.getId());
            talentQuery.setCompanyId(SpringSecurityUtil.getIntCompany());
            if(Util.isNotNull(form.getName())){
                talentQuery.setName(form.getName()); 
            }
            if(Util.isNotNull(form.getMobile())){
                talentQuery.setMobile(form.getMobile()); 
            }
            if(Util.isNotNull(form.getIdentityNo())){
                talentQuery.setIdentityNo(form.getIdentityNo());
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                talentQuery.setCreateTimeStart(form.getCreateTimeStart().replace("/", "")); 
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                talentQuery.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));                
            }
            if(Util.isNotNull(form.getComeDate())){
                talentQuery.setComeDate(form.getComeDate().replace("/", ""));
            }
            if(Util.isNotNull(form.getExpectedIncome())){
                talentQuery.setExpectedIncome(form.getExpectedIncome()); 
            }
            if(Util.isNotNull(form.getQualification())){
                talentQuery.setQualification(form.getQualification());
            }
            if(Util.isNotNull(form.getRecruitmentId())){
                talentQuery.setRecruitmentId(form.getRecruitmentId());
            }
            talentQuery.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<TalentInfo> result = talentFacade.getTalentByCondition(talentQuery);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("人才信息一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 条件获取人才工作
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getTalentJobByCondition.htm", method = RequestMethod.POST)
    public void getTalentJobByCondition(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentJobQuery query = new TalentJobQuery();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setTalentId(form.getTalentId());
            ResultRecord<TalentJob> result = talentFacade.getTalentJobByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");  
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("人才工作获取失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 条件获取人才家属
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getTalentFamilyByCondition.htm", method = RequestMethod.POST)
    public void getTalentFamilyByCondition(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentFamilyQuery query = new TalentFamilyQuery();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setTalentId(form.getTalentId());
            ResultRecord<TalentFamily> result = talentFacade.getTalentFamilyByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("人才家属获取失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 条件获取人才学历
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getTalentEducationByCondition.htm", method = RequestMethod.POST)
    public void getTalentEducationByCondition(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentEducationQuery query = new TalentEducationQuery();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setTalentId(form.getTalentId());
            ResultRecord<TalentEducation> result = talentFacade.getTalentEducationByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("人才学历获取失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    

    /**
     * 条件获取人才附件
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getTalentDocumentByCondition.htm", method = RequestMethod.POST)
    public void getTalentDocumentByCondition(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentDocumentQuery query = new TalentDocumentQuery();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setTalentId(form.getTalentId());
            ResultRecord<TalentDocument> result = talentFacade.getTalentDocumentByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("人才附件获取失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 关联查询人才信息
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getTalentInfomation.htm", method = RequestMethod.POST)
    public void getTalentInfomation(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentQuery talentQuery = new TalentQuery();
            talentQuery.setTalentId(form.getId());
            talentQuery.setCompanyId(SpringSecurityUtil.getIntCompany());
            if(Util.isNotNull(form.getIdentityNo())){
                talentQuery.setIdentityNo(form.getIdentityNo());
            }
            ResultRecord<TalentInfo> result = talentFacade.getTalentInfo(talentQuery);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("人才信息一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 人才导出
     * 
     * @param req
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.TALENT_EXPORT}, checkSource = false)
    @RequestMapping(value = "/talentInfoPrint.htm", method = RequestMethod.POST)
    public void talentInfoPrint(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_EXPORT.getValue());
            TalentQuery talentQuery = new TalentQuery();
            OutputStream outputStream = null;
            talentQuery.setId(form.getId());
            talentQuery.setCompanyId(SpringSecurityUtil.getIntCompany());
            if(Util.isNotNull(form.getName())){
                talentQuery.setName(form.getName()); 
            }
            if(Util.isNotNull(form.getMobile())){
                talentQuery.setMobile(form.getMobile()); 
            }
            if(Util.isNotNull(form.getIdentityNo())){
                talentQuery.setIdentityNo(form.getIdentityNo());
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                talentQuery.setCreateTimeStart(form.getCreateTimeStart().replace("/", "")); 
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                talentQuery.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));                
            }
            if(Util.isNotNull(form.getComeDate())){
                talentQuery.setComeDate(form.getComeDate().replace("/", ""));
            }
            if(Util.isNotNull(form.getExpectedIncome())){
                talentQuery.setExpectedIncome(form.getExpectedIncome()); 
            }
            if(Util.isNotNull(form.getQualification())){
                talentQuery.setQualification(form.getQualification());
            }
            if(Util.isNotNull(form.getRecruitmentId())){
                talentQuery.setRecruitmentId(form.getRecruitmentId());
            }
            talentQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<TalentInfo> result = talentFacade.getTalentByCondition(talentQuery);
            if(result.isSuccess()){
                List<TalentInfo> list = result.getList();
                for(int i=0;i<list.size();i++){
                    if(Util.isNotNull(list.get(i).getComeDate())){
                        list.get(i).setComeDate(list.get(i).getComeDate().substring(0,4)+"/"+list.get(i).getComeDate().substring(4,6)+"/"+list.get(i).getComeDate().substring(6,8));
                    }
                }
                // 设置文件标题行
                String[] headers = { "姓名", "联系电话", "身份证号", "应聘职位","可到岗日期","期望税前年收入"};
                String[] rows = { "name", "mobile", "identityNo", "recruitment.positionName", "comeDate","expectedIncome"};                
                // 生成excel文件
                // 标题行对应的属性名
                HSSFWorkbook wb = ExcelUtil.exportExcel("人才信息导出", headers, rows, list, "yyyyMMddhhmmssSSS");
                resp.setContentType("application/vnd.ms-excel");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                setFileDownloadHeader(req, resp, "人才信息导出" + sdf.format(new Date())+ ".xls");
                outputStream = resp.getOutputStream();
                wb.write(outputStream);
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("人才信息打印出错：", ex);
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
