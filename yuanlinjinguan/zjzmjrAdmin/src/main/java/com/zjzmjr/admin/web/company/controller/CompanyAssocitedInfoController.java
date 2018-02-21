package com.zjzmjr.admin.web.company.controller;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.company.form.CompanyAssociatedInfoForm;
import com.zjzmjr.core.api.company.ICompanyInfoFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.AreaKeyValue;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.biz.weixin.JSONProcesserUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.company.CompanyAssociatedInfo;
import com.zjzmjr.core.model.company.CompanyAssociatedQuery;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 关联公司控制层
 * 
 * @author lenovo
 * @version $Id: CompanyAssocitedInfoController.java, v 0.1 2017-8-24 上午10:24:24
 *          lenovo Exp $
 */
@RequestMapping("/companyAssocited/user")
@Controller
public class CompanyAssocitedInfoController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyAssocitedInfoController.class);

    private final static String index = "/WEB-INF/pages/company/associtedInfo.jsp";

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
        model.put("companyAssocitedAddAuth", SpringSecurityUtil.hasAuth(UserAuthParams.COMPANY_ASSOCITED_ADD));
        model.put("companyAssocitedUpdateAuth", SpringSecurityUtil.hasAuth(UserAuthParams.COMPANY_ASSOCITED_UPDATE));
        model.put("companyAssocitedExportAuth", SpringSecurityUtil.hasAuth(UserAuthParams.COMPANY_ASSOCITED_EXPORT));
        return index;
    }

    /**
     * 关联公司一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getCompanyAssociatedInfo.htm", method = RequestMethod.POST)
    public void getCompanyAssociatedInfo(HttpServletResponse resp, CompanyAssociatedInfoForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyAssociatedQuery query = new CompanyAssociatedQuery();
            if (Util.isNotNull(form.getId())) {
                query.setId(form.getId());
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            if (Util.isNotNull(form.getCompanyName())) {
                query.setCompanyName(form.getCompanyName());
            }
            if (Util.isNotNull(form.getLegalPerson())) {
                query.setLegalPerson(form.getLegalPerson());
            }
            if (Util.isNotNull(form.getCompanyDistinguish())) {
                query.setCompanyDistinguish(form.getCompanyDistinguish());
            }
            if (Util.isNotNull(form.getAddress())) {
                query.setAddress(form.getAddress());
            }
            if (Util.isNotNull(form.getCreateTimeStart())) {
                query.setCreateTimeStart(form.getCreateTimeStart());
            }
            if (Util.isNotNull(form.getCreateTimeEnd())) {
                query.setCreateTimeEnd(form.getCreateTimeEnd());
            }
            if (Util.isNotNull(form.getStatus())) {
                query.setStatus(form.getStatus());
            }
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<CompanyAssociatedInfo> result = companyInfoFacade.getCompanyAssociatedInfo(query);
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
     * 新增关联公司
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.COMPANY_ASSOCITED_ADD }, checkSource = false)
    @RequestMapping(value = "/insertCompanyAssocitedInfo.htm", method = RequestMethod.POST)
    public void insertCompanyAssocitedInfo(HttpServletResponse resp, CompanyAssociatedInfoForm form, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(!StringUtils.isEmpty(form.getCompanyName()), "请输入企业全称");// 判断获取到的企业全称是否为空
            CompanyAssociatedInfo companyAssociatedInfo = new CompanyAssociatedInfo();
            companyAssociatedInfo.setCompanyId(SpringSecurityUtil.getIntCompany());
            companyAssociatedInfo.setCompanyDistinguish(form.getCompanyDistinguish());
            companyAssociatedInfo.setCompanyName(form.getCompanyName());
            companyAssociatedInfo.setLegalPerson(form.getLegalPerson());
            companyAssociatedInfo.setLegalMobile(form.getLegalMobile());
            companyAssociatedInfo.setCityId(form.getCityId());
            companyAssociatedInfo.setAddress(form.getAddress());
            companyAssociatedInfo.setIntroduction(form.getIntroduction());
            companyAssociatedInfo.setBankName(form.getBankName());
            companyAssociatedInfo.setAccountNo(form.getAccountNo());
            companyAssociatedInfo.setLicenseNumber(form.getLicenseNumber());
            companyAssociatedInfo.setMobile(form.getMobile());
            companyAssociatedInfo.setFaxPhone(form.getFaxPhone());
            companyAssociatedInfo.setStatus(GenerateConstants.number_1);
            companyAssociatedInfo.setCreateTime(new Date());
            companyAssociatedInfo.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            companyAssociatedInfo.setCompanyAssociatedContact(form.getCompanyAssociatedContact());
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_INSERT_ASSOCITED.getValue());
            adminBusiness.setComment(AdminBusinessTypeEnum.B_INSERT_ASSOCITED.getMessage());
            ResultEntry<AdminBusiness> val = AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = companyInfoFacade.insertCompanyAssociated(companyAssociatedInfo);
            if (result.isSuccess()) {
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setExtend1(result.getErrorMsg());
            }
            // 更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("新增关联公司出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 更新关联公司
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.COMPANY_ASSOCITED_UPDATE }, checkSource = false)
    @RequestMapping(value = "/updateCompanyAssocitedInfo.htm", method = RequestMethod.POST)
    public void updateCompanyAssocitedInfo(HttpServletResponse resp, CompanyAssociatedInfoForm form, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(!StringUtils.isEmpty(form.getCompanyName()), "请输入企业全称");// 判断获取到的企业全称是否为空
            CompanyAssociatedInfo companyAssociatedInfo = new CompanyAssociatedInfo();
            companyAssociatedInfo.setId(form.getId());
            companyAssociatedInfo.setCompanyId(SpringSecurityUtil.getIntCompany());
            companyAssociatedInfo.setCompanyDistinguish(form.getCompanyDistinguish());
            companyAssociatedInfo.setCompanyName(form.getCompanyName());
            companyAssociatedInfo.setLegalPerson(form.getLegalPerson());
            companyAssociatedInfo.setLegalMobile(form.getLegalMobile());
            companyAssociatedInfo.setCityId(form.getCityId());
            companyAssociatedInfo.setAddress(form.getAddress());
            companyAssociatedInfo.setIntroduction(form.getIntroduction());
            companyAssociatedInfo.setBankName(form.getBankName());
            companyAssociatedInfo.setAccountNo(form.getAccountNo());
            companyAssociatedInfo.setLicenseNumber(form.getLicenseNumber());
            companyAssociatedInfo.setMobile(form.getMobile());
            companyAssociatedInfo.setFaxPhone(form.getFaxPhone());
            companyAssociatedInfo.setStatus(GenerateConstants.number_1);
            companyAssociatedInfo.setUpdateTime(new Date());
            companyAssociatedInfo.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            companyAssociatedInfo.setCompanyAssociatedContact(form.getCompanyAssociatedContact());
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_MODIFY_ASSOCITED.getValue());
            // adminBusiness.setComment(AdminBusinessTypeEnum.B_MODIFY_ASSOCITED.getMessage());
            ResultEntry<AdminBusiness> val = AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = companyInfoFacade.updateCompanyAssociatedById(companyAssociatedInfo);
            if (result.isSuccess()) {
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            // 更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("更新关联公司出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 关联公司下载
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.COMPANY_INFO_EXPORT}, checkSource = false)
    @RequestMapping(value = "/downLoadCompanyAssociatedInfo.htm", method = RequestMethod.POST)
    public void downLoadCompanyAssociatedInfo(HttpServletResponse resp, CompanyAssociatedInfoForm form, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        OutputStream outputStream = null;
        try {
            CompanyAssociatedQuery query = new CompanyAssociatedQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            if (Util.isNotNull(form.getCompanyName())) {
                query.setCompanyName(form.getCompanyName());
            }
            if (Util.isNotNull(form.getLegalPerson())) {
                query.setLegalPerson(form.getLegalPerson());
            }
            if (Util.isNotNull(form.getCompanyDistinguish())) {
                query.setCompanyDistinguish(form.getCompanyDistinguish());
            }
            if (Util.isNotNull(form.getAddress())) {
                query.setAddress(form.getAddress());
            }
            if (Util.isNotNull(form.getCreateTimeStart())) {
                query.setCreateTimeStart(form.getCreateTimeStart());
            }
            if (Util.isNotNull(form.getCreateTimeEnd())) {
                query.setCreateTimeEnd(form.getCreateTimeEnd());
            }
            if (Util.isNotNull(form.getStatus())) {
                query.setStatus(form.getStatus());
            }
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<CompanyAssociatedInfo> result = companyInfoFacade.getCompanyAssociatedInfo(query);
            if (result.isSuccess()) {
                List<CompanyAssociatedInfo> list = result.getList();
                for (int i = 0; i < list.size(); i++) {
                    if (Util.isNotNull(list.get(i).getArea())) {
                        list.get(i).setAddress(list.get(i).getArea().getAreaName() + list.get(i).getAddress());
                    }
                    if (GenerateConstants.number_1.equals(list.get(i).getStatus())) {
                        list.get(i).setStatusText("正常");
                    } else if (GenerateConstants.number_0.equals(list.get(i).getStatus())) {
                        list.get(i).setStatusText("冻结");
                    }
                    list.get(i).setAddTime(DateUtil.format(list.get(i).getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
                }
                // 设置文件标题行
                String[] headers = { "企业全称", "法人代表", "统一社会信用代码  ", "开户银行", "银行账号", "联系方式", "所在地址", "属性", "录入时间", "状态" };
                String[] rows = { "companyName", "legalPerson", "licenseNumber", "bankName", "accountNo", "mobile", "address", "basicData.attributeName", "addTime", "statusText" };
                // 生成excel文件
                // 标题行对应的属性名
                HSSFWorkbook wb = ExcelUtil.exportExcel("关联公司导出", headers, rows, list, "yyyyMMddhhmmssSSS");
                resp.setContentType("application/vnd.ms-excel");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                setFileDownloadHeader(req, resp, "关联公司导出" + sdf.format(new Date()) + ".xls");
                outputStream = resp.getOutputStream();
                wb.write(outputStream);
            } else {
                putError(model, result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("关联公司导出出错：", ex);
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

    /**
     * 获取可以选择人员的字段
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getCompanyAssociatedInfoData.htm", method = RequestMethod.POST)
    public void getCompanyAssociatedInfoData(HttpServletResponse resp, CompanyAssociatedInfoForm form) {
        resp.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = resp.getWriter();
            CompanyAssociatedQuery query = new CompanyAssociatedQuery();
            if (Util.isNotNull(form.getCompanyDistinguish())) {
                query.setCompanyDistinguish(form.getCompanyDistinguish());
            }
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<CompanyAssociatedInfo> result = companyInfoFacade.getCompanyAssociatedInfo(query);
            List<AreaKeyValue> newList = new ArrayList<AreaKeyValue>();
            if (result.isSuccess()) {
                for (int i = 0; i < result.getList().size(); i++) {
                    AreaKeyValue areaKeyValue = new AreaKeyValue();
                    areaKeyValue.setText(result.getList().get(i).getCompanyName());
                    areaKeyValue.setValue(result.getList().get(i).getId());
                    newList.add(areaKeyValue);
                }
            }
            resp.setContentType("application/json; charset=utf-8");
            out.print(JSONProcesserUtil.parseObj(newList));
            out.flush();
            out.close();
        } catch (Exception ex) {
            logger.error("获取可以进行数据修改的字段出错：", ex);
        }
    }
}
