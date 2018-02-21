package com.zjzmjr.admin.web.recruitment.controller;

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

import com.zjzmjr.admin.web.recruitment.form.RecruitmentForm;
import com.zjzmjr.core.api.recruitment.IRecruitmentFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.recruitment.RecruitmentIsValidEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.recruitment.Recruitment;
import com.zjzmjr.core.model.recruitment.RecruitmentInfo;
import com.zjzmjr.core.model.recruitment.RecruitmentQuery;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;


/**
 * 招聘信息控制层
 * 
 * @author lenovo
 * @version $Id: RecruitmentController.java, v 0.1 2017-12-12 下午4:51:52 lenovo Exp $
 */
@Controller
@RequestMapping("/recruitment/user")
public class RecruitmentController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RecruitmentController.class);

    private final static String index = "/WEB-INF/pages/recruitment/recruitment.jsp";

    @Resource(name = "recruitmentFacade")
    private IRecruitmentFacade recruitmentFacade;
    
    /**
     * 
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("recruitmentAddAuth", SpringSecurityUtil.hasAuth(UserAuthParams.RECRUITMENT_ADD));
        model.put("recruitmentUpdateAuth", SpringSecurityUtil.hasAuth(UserAuthParams.RECRUITMENT_UPDATE));
        model.put("recruitmentExportAuth", SpringSecurityUtil.hasAuth(UserAuthParams.RECRUITMENT_EXPORT));
        model.put("recruitmentInviteAuth", SpringSecurityUtil.hasAuth(UserAuthParams.RECRUITMENT_INVITE));
        return index;
    }
    
    /**
     * 招聘信息一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getRecruitment.htm", method = RequestMethod.POST)
    public void getRecruitment(HttpServletResponse resp, RecruitmentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            RecruitmentQuery query = new RecruitmentQuery();
            query.setId(form.getId());
            if(Util.isNotNull(form.getPositionName())){
                query.setPositionName(form.getPositionName()); 
            }
            if(Util.isNotNull(form.getAddress())){
                query.setAddress(form.getAddress());
            }
            query.setDepartmentId(form.getDepartmentId());
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", "")); 
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));
            } 
            if(Util.isNotNull(form.getEducation())){
                query.setEducation(form.getEducation()); 
            }
            if(Util.isNotNull(form.getSalary())){
                query.setSalary(form.getSalary()); 
            }
            query.setIsValid(form.getIsValid());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<RecruitmentInfo> result = recruitmentFacade.getRecruitment(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("招聘信息一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 新增招聘信息
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.RECRUITMENT_ADD}, checkSource = false)
    @RequestMapping(value = "/insertRecruitment.htm", method = RequestMethod.POST)
    public void insertRecruitment(HttpServletResponse resp, HttpServletRequest req, RecruitmentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.RECRUITMENT_INSERT.getValue());
            Recruitment recruitment = new Recruitment();
            recruitment.setCompanyId(SpringSecurityUtil.getIntCompany());
            recruitment.setPositionName(form.getPositionName());
            recruitment.setDepartmentId(form.getDepartmentId());
            recruitment.setAddress(form.getAddress());
            recruitment.setAge(form.getAge());
            recruitment.setExperience(form.getExperience());
            recruitment.setEducation(form.getEducation());
            recruitment.setSalary(form.getSalary());
            recruitment.setNumbers(form.getNumbers());
            recruitment.setPostDuties(form.getPostDuties());
            recruitment.setQualification(form.getQualification());
            recruitment.setIsOpen(form.getIsOpen());
            recruitment.setIsValid(RecruitmentIsValidEnum.EFFECTIVE.getValue());
            //recruitment.setIsValid(RecruitmentIsValidEnum.CHECK_PENDING.getValue());
            recruitment.setCreateTime(new Date());
            recruitment.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = recruitmentFacade.insertRecruitment(recruitment);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setExtend1("职位名称："+form.getPositionName());
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("招聘信息新增出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 修改招聘信息
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.RECRUITMENT_UPDATE}, checkSource = false)
    @RequestMapping(value = "/updateRecruitment.htm", method = RequestMethod.POST)
    public void updateRecruitment(HttpServletResponse resp, HttpServletRequest req, RecruitmentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.RECRUITMENT_UPDATE.getValue());
            Recruitment recruitment = new Recruitment();
            recruitment.setId(form.getId());
            recruitment.setCompanyId(SpringSecurityUtil.getIntCompany());
            recruitment.setPositionName(form.getPositionName());
            recruitment.setDepartmentId(form.getDepartmentId());
            recruitment.setAddress(form.getAddress());
            recruitment.setAge(form.getAge());
            recruitment.setExperience(form.getExperience());
            recruitment.setEducation(form.getEducation());
            recruitment.setSalary(form.getSalary());
            recruitment.setNumbers(form.getNumbers());
            recruitment.setPostDuties(form.getPostDuties());
            recruitment.setQualification(form.getQualification());
            if(Util.isNull(form.getIsOpen())){
                recruitment.setIsOpen(GenerateConstants.number_0);
            }else{
                recruitment.setIsOpen(form.getIsOpen());
            }
            recruitment.setIsValid(form.getIsValid());
            recruitment.setUpdateTime(new Date());
            recruitment.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = recruitmentFacade.updateRecruitment(recruitment);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setExtend1("职位名称："+form.getPositionName());
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("招聘信息修改出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 条件查询招聘信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getRecruitmentByCondition.htm", method = RequestMethod.POST)
    public void getRecruitmentByCondition(HttpServletResponse resp, RecruitmentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            RecruitmentQuery query = new RecruitmentQuery();
            query.setId(form.getId());
            if(Util.isNotNull(form.getPositionName())){
                query.setPositionName(form.getPositionName()); 
            }
            if(Util.isNotNull(form.getAddress())){
                query.setAddress(form.getAddress());
            }
            query.setDepartmentId(form.getDepartmentId());
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", "")); 
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));
            } 
            if(Util.isNotNull(form.getEducation())){
                query.setEducation(form.getEducation()); 
            }
            if(Util.isNotNull(form.getSalary())){
                query.setSalary(form.getSalary()); 
            }
            query.setIsValid(form.getIsValid());
            ResultRecord<Recruitment> result = recruitmentFacade.getRecruitmentByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("条件查询招聘信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 招聘信息打印
     * 
     * @param req
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.RECRUITMENT_EXPORT}, checkSource = false)
    @RequestMapping(value = "/recruitmentInfoPrint.htm", method = RequestMethod.POST)
    public void recruitmentInfoPrint(HttpServletRequest req, HttpServletResponse resp, RecruitmentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.RECRUITMENT_EXPORT.getValue());
            RecruitmentQuery query = new RecruitmentQuery();
            OutputStream outputStream = null;
            query.setId(form.getId());
            if (Util.isNotNull(form.getPositionName())) {
                query.setPositionName(form.getPositionName());
            }
            if (Util.isNotNull(form.getAddress())) {
                query.setAddress(form.getAddress());
            }
            query.setDepartmentId(form.getDepartmentId());
            if (Util.isNotNull(form.getCreateTimeStart())) {
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", ""));
            }
            if (Util.isNotNull(form.getCreateTimeEnd())) {
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));
            }
            if (Util.isNotNull(form.getEducation())) {
                query.setEducation(form.getEducation());
            }
            if (Util.isNotNull(form.getSalary())) {
                query.setSalary(form.getSalary());
            }
            if(!(GenerateConstants.number_0>form.getIsValid())){
                query.setIsValid(form.getIsValid());
            }
            
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<RecruitmentInfo> result = recruitmentFacade.getRecruitment(query);
            if (result.isSuccess()) {
                List<RecruitmentInfo> list = result.getList();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setIsValidText(RecruitmentIsValidEnum.getByValue(list.get(i).getIsValid()).getMessage());
                    if(GenerateConstants.number_1.equals(list.get(i).getIsOpen())){
                        list.get(i).setIsOpenText("是");
                    }else{
                        list.get(i).setIsOpenText("否");
                    }
                }
                // 设置文件标题行
                String[] headers = { "职位名称", "工作地点", "招聘部门", "年龄", "学历", "薪资待遇", "应聘人数", "招聘人数", "是否有效", "是否公开"};
                String[] rows = { "positionName", "address", "companyDepartment.name", "age", "education", "salary", "applicants", "numbers", "isValidText", "isOpenText" };
                // 生成excel文件
                // 标题行对应的属性名
                HSSFWorkbook wb = ExcelUtil.exportExcel("招聘信息导出", headers, rows, list, "yyyyMMddhhmmssSSS");
                resp.setContentType("application/vnd.ms-excel");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                setFileDownloadHeader(req, resp, "招聘信息导出" + sdf.format(new Date()) + ".xls");
                outputStream = resp.getOutputStream();
                wb.write(outputStream);
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("招聘信息打印出错：", ex);
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
