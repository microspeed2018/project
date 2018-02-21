package com.zjzmjr.admin.web.company.controller;

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

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.company.form.CompanyInfoForm;
import com.zjzmjr.core.api.company.ICompanyInfoFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.NumberUtils;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.company.CompanyInfoAptitude;
import com.zjzmjr.core.model.company.CompanyInfoQuery;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 
 * 
 * @author oms
 * @version $Id: CompanyInfoController.java, v 0.1 2017-8-8 下午8:00:32 oms Exp $
 */
@RequestMapping("/company/user")
@Controller
public class CompanyInfoController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyInfoController.class);

    private final static String index = "/WEB-INF/pages/company/baseInfo.jsp";

    @Resource(name = "companyInfoFacade")
    private ICompanyInfoFacade companyInfoFacade;

    /**
     * 
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("companyInfoAddAuth", SpringSecurityUtil.hasAuth(UserAuthParams.COMPANY_INFO_ADD));
        model.put("companyInfoUpdateAuth", SpringSecurityUtil.hasAuth(UserAuthParams.COMPANY_INFO_UPDATE));
        model.put("companyInfoExportAuth", SpringSecurityUtil.hasAuth(UserAuthParams.COMPANY_INFO_EXPORT));
        return index;
    }

    /**
     * 公司信息查询一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/companyInfo.htm", method = RequestMethod.POST)
    public void queryByPage(HttpServletResponse resp, CompanyInfoForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyInfoQuery query = new CompanyInfoQuery();
            if(form.getId() != null && form.getId().compareTo(0) > 0){
                query.setId(form.getId());
            }
            if(StringUtils.isNotBlank(form.getCompanyName())){
                query.setCompanyName(form.getCompanyName());
            }
            if(StringUtils.isNotBlank(form.getLegalPerson())){
                query.setLegalPerson(form.getLegalPerson());
            }
            if(StringUtils.isNotBlank(form.getCapitalFrom())){
                query.setCapitalFrom(NumberUtils.string2BigDecimal(form.getCapitalFrom()));
            }
            if(StringUtils.isNotBlank(form.getCapitalTo())){
                query.setCapitalTo(NumberUtils.string2BigDecimal(form.getCapitalTo()));
            }
            if(StringUtils.isNotBlank(form.getDurationFrom())){
                query.setDurationFrom(form.getDurationFrom());
            }
            if(StringUtils.isNotBlank(form.getDurationTo())){
                query.setDurationTo(form.getDurationTo());
            }
            if(StringUtils.isNotBlank(form.getAddress())){
                query.setAddress(form.getAddress());
            }
            if(StringUtils.isNotBlank(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart());
            }
            if(StringUtils.isNotBlank(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd());
            }
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<CompanyInfoAptitude> result = companyInfoFacade.getCompanyInfoByCondition(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("公司信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 查询公司的详细信息
     * 
     * @param resp
     * @param companyId
     */
    @RequestMapping(value="/{companyId}/Aptitude.htm", method = RequestMethod.POST)
    public void detailInfo(HttpServletResponse resp, @PathVariable Integer companyId){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(companyId != null, "请选择需要查询的公司");
            
            ResultEntry<CompanyInfoAptitude> result = companyInfoFacade.getCompanyInfoAptitudeById(companyId);
            if (result.isSuccess()){
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("公司信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 公司信息插入
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.COMPANY_INFO_ADD }, checkSource = false)
    @RequestMapping(value = "/instCompany.htm", method = RequestMethod.POST)
    public void insertCompany(HttpServletResponse resp, CompanyInfoAptitude form,HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            form.setCreateTime(new Date());
            form.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            form.setUpdateTime(form.getCreateTime());
            form.setUpdateUserId(form.getCreateUserId());
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_INSERT_COMPANY.getValue());
            //adminBusiness.setComment(AdminBusinessTypeEnum.B_INSERT_COMPANY.getMessage());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = companyInfoFacade.insertCompanyInfo(form);
            if (result.isSuccess()) {
                putSuccess(model, ""); 
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("公司信息插入出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 公司信息更新
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.COMPANY_INFO_UPDATE }, checkSource = false)
    @RequestMapping(value = "/updCompany.htm", method = RequestMethod.POST)
    public void updateCompany(HttpServletResponse resp, CompanyInfoAptitude form,HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            form.setUpdateTime(new Date());
            form.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_MODIFY_COMPANY.getValue());
            adminBusiness.setComment(AdminBusinessTypeEnum.B_MODIFY_COMPANY.getMessage());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = companyInfoFacade.updateCompanyInfoById(form);
            if (result.isSuccess()) {
                putSuccess(model, "");adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setExtend1(result.getErrorMsg());
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("公司信息更新出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 公司信息导出
     * 
     * @param resp
     * @param form
     * @param req
     */
    @Security(auth = { UserAuthParams.COMPANY_INFO_EXPORT }, checkSource = false)
    @RequestMapping(value = "/downLoadCompanyInfo.htm", method = RequestMethod.POST)
    public void downLoadCompanyInfo(HttpServletResponse resp, CompanyInfoForm form,HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        OutputStream outputStream = null;
        try {
            CompanyInfoQuery query = new CompanyInfoQuery();
            if(form.getId() != null && form.getId().compareTo(0) > 0){
                query.setId(form.getId());
            }
            if(StringUtils.isNotBlank(form.getCompanyName())){
                query.setCompanyName(form.getCompanyName());
            }
            if(StringUtils.isNotBlank(form.getLegalPerson())){
                query.setLegalPerson(form.getLegalPerson());
            }
            if(StringUtils.isNotBlank(form.getCapitalFrom())){
                query.setCapitalFrom(NumberUtils.string2BigDecimal(form.getCapitalFrom()));
            }
            if(StringUtils.isNotBlank(form.getCapitalTo())){
                query.setCapitalTo(NumberUtils.string2BigDecimal(form.getCapitalTo()));
            }
            if(StringUtils.isNotBlank(form.getDurationFrom())){
                query.setDurationFrom(form.getDurationFrom());
            }
            if(StringUtils.isNotBlank(form.getDurationTo())){
                query.setDurationTo(form.getDurationTo());
            }
            if(StringUtils.isNotBlank(form.getAddress())){
                query.setAddress(form.getAddress());
            }
            if(StringUtils.isNotBlank(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart());
            }
            if(StringUtils.isNotBlank(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd());
            }
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<CompanyInfoAptitude> result = companyInfoFacade.getCompanyInfoByCondition(query);
            if(result.isSuccess()){
                List<CompanyInfoAptitude> list = result.getList();  
                for(int i=0;i<list.size();i++){
                    if(Util.isNotNull(list.get(i).getArea())){
                        list.get(i).setAddress(list.get(i).getArea().getAreaName()+list.get(i).getAddress()); 
                    }
                    if(GenerateConstants.number_1.equals(list.get(i).getStatus())){
                        list.get(i).setStatusText("正常");
                    }else if(GenerateConstants.number_0.equals(list.get(i).getStatus())){
                        list.get(i).setStatusText("冻结");
                    }
                    list.get(i).setAddTime(DateUtil.format(list.get(i).getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
                    list.get(i).setDuration(DateUtil.format(DateUtil.parse(list.get(i).getDuration(), DateUtil.YYYYMMDD),"yyyy/MM/dd"));
                }
                // 设置文件标题行
                String[] headers = { "企业全称", "法人代表", "统一社会信用代码  ", "注册资金", "成立日期", "开户银行", "银行账号", "联系方式", "传真", "所在城市",  "IOS管理体系", "员工总数", "录入时间", "状态"};
                String[] rows = { "companyName", "legalPerson", "licenseNumber", "registeredCapital", "duration", "bankName", "accountNo", "mobile", "faxPhone", "address","iosQuality", "staffCount", "addTime", "statusText"};                
                // 生成excel文件
                // 标题行对应的属性名
                HSSFWorkbook wb = ExcelUtil.exportExcel("公司信息导出", headers, rows, list, "yyyyMMddhhmmssSSS");
                resp.setContentType("application/vnd.ms-excel");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                setFileDownloadHeader(req, resp, "公司信息导出" + sdf.format(new Date())+ ".xls");
                outputStream = resp.getOutputStream();
                wb.write(outputStream);
            }else{
                putError(model, result.getErrorMsg());
            } 
        } catch (Exception ex) {
            logger.error("公司信息导出出错：", ex);
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
