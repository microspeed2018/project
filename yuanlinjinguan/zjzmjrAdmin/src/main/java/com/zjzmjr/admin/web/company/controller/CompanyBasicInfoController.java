package com.zjzmjr.admin.web.company.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.company.form.BasicDataForm;
import com.zjzmjr.core.api.company.ICompanyBasicInfoFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.company.BasicDataQuery;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 基础信息控制层
 * 
 * @author oms
 * @version $Id: CompanyBasicInfoController.java, v 0.1 2017-8-10 上午9:07:30 oms Exp $
 */
@Controller
@RequestMapping("/company/user")
public class CompanyBasicInfoController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(CompanyBasicInfoController.class);

    private static final String index = "/WEB-INF/pages/basic/basic.jsp";

    @Resource(name = "companyBasicInfoFacade")
    private ICompanyBasicInfoFacade companyBasicInfoFacade;

    @RequestMapping(value = "/basicIndex.htm")
    public String index(ModelMap model) {
        model.put("basicDataAddAuth", SpringSecurityUtil.hasAuth(UserAuthParams.BASIC_DATA_ADD));
        model.put("basicDataUpdateAuth", SpringSecurityUtil.hasAuth(UserAuthParams.BASIC_DATA_UPDATE));
        model.put("basicDataDeleteAuth", SpringSecurityUtil.hasAuth(UserAuthParams.BASIC_DATA_DELETE));
        return index;
    }

    /**
     * 查询所有基础数据
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getBasicByCategoryId.htm", method = RequestMethod.POST)
    public void getBasicByCategoryId(HttpServletResponse resp, BasicDataForm form) {
        logger.debug("getBasicByCategoryId 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ResultRecord<BasicData> result = companyBasicInfoFacade.getAllBasicData();
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("查询基础数据出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
        logger.debug("getBasicByCategoryId 执行结束  出参:{}", model);
    }

    /**
     * 获取所有的基础类别
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getCategorys.htm", method = RequestMethod.POST)
    public void getCategorys(HttpServletResponse resp, BasicDataForm form) {
        logger.debug("getCategorys 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ResultRecord<BasicData> result = companyBasicInfoFacade.getCategorys();
            if (result.isSuccess()) {
                model.put("cates", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取基础类别出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
        logger.debug("getCategorys 执行结束  出参:{}", model);
    }

    /**
     * 获取基础数据一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getBasic.htm", method = RequestMethod.POST)
    public void getBasic(HttpServletResponse resp, BasicDataForm form) {
        logger.debug("getBasic 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            BasicDataQuery query = new BasicDataQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setCategoryId(form.getCategoryId());
            query.setCategoryName(form.getCategoryName());
            query.setAttributeId(form.getAttributeId());
            query.setAttributeName(form.getAttributeName());
            PageBean pageBean = new PageBean(form.getRows(), form.getPage());
            query.setPageBean(pageBean);
            ResultPage<BasicData> result = companyBasicInfoFacade.getBasicDataByCondition(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("基础数据查询一览出错：", e);
            putError(model, e.getMessage());
        }
        responseAsJson(model, resp);
        logger.debug("getBasic 执行结束  出参:{}", model);
    }

    /**
     * 根据Id获取基础数据
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getBasicById.htm", method = RequestMethod.POST)
    public void getBasicById(HttpServletResponse resp, BasicDataForm form) {
        logger.debug("getBasicById 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            BasicDataQuery query = new BasicDataQuery();
            query.setId(form.getId());
            ResultEntry<BasicData> result = companyBasicInfoFacade.getBasicDataById(query);
            if (result.isSuccess()) {
                model.put("BasicData", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("根据Id查询基础数据出错：", e);
            putError(model, e.getMessage());
        }
        responseAsJson(model, resp);
        logger.debug("getBasicById 执行结束  出参:{}", model);
    }

    /**
     * 新增基础数据
     * 
     * @param resp
     * @param req
     * @param form
     */

    @Security(auth = { UserAuthParams.BASIC_DATA_ADD }, checkSource = false)
    @RequestMapping(value = "/insertBasic.htm", method = RequestMethod.POST)
    public void insertBasic(HttpServletResponse resp, HttpServletRequest req, BasicDataForm form) {
        logger.debug("insertBasic 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            BasicData BasicData = new BasicData();
            BasicData.setCompanyId(SpringSecurityUtil.getIntCompany());
            BasicData.setCategoryId(form.getCategoryId());
            BasicData.setCategoryName(form.getCategoryName());
            BasicData.setAttributeId(form.getAttributeId());
            BasicData.setAttributeName(form.getAttributeName());
            BasicData.setCreateTime(new Date());
            BasicData.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_INSERT_BASIC.getValue());
            ResultEntry<AdminBusiness> val = AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = companyBasicInfoFacade.insertBasicData(BasicData);
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
            logger.error("业务部门新增出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
        logger.debug("insertBasic 执行结束  出参:{}", model);
    }

    /**
     * 更新基础数据
     * 
     * @param resp
     * @param req
     * @param form
     */
    @Security(auth = { UserAuthParams.BASIC_DATA_UPDATE }, checkSource = false)
    @RequestMapping(value = "/updateBasic.htm", method = RequestMethod.POST)
    public void updateBasic(HttpServletResponse resp, HttpServletRequest req, BasicDataForm form) {
        logger.debug("updateBasic 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            BasicData BasicData = new BasicData();
            BasicData.setId(form.getId());
//            BasicData.setCategoryId(form.getCategoryId());
//            BasicData.setCategoryName(form.getCategoryName());
//            BasicData.setAttributeId(form.getAttributeId());
            BasicData.setAttributeName(form.getAttributeName());
            BasicData.setUpdateTime(new Date());
            BasicData.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_MODIFY_BASIC.getValue());
            ResultEntry<AdminBusiness> val = AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = companyBasicInfoFacade.updateBasicData(BasicData);
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
            logger.error("基础数据更新出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
        logger.debug("updateBasic 执行结束  出参:{}", model);
    }

    /**
     * 删除基础数据
     * 
     * @param resp
     * @param req
     * @param form
     */
    @Security(auth = { UserAuthParams.BASIC_DATA_DELETE }, checkSource = false)
    @RequestMapping(value = "/deleteBasic.htm", method = RequestMethod.POST)
    public void deleteBasic(HttpServletResponse resp, HttpServletRequest req, BasicDataForm form) {
        logger.debug("deleteBasic 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_DELETE_BASIC.getValue());
            ResultEntry<AdminBusiness> val = AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = companyBasicInfoFacade.deleteBasicDataById(form.getId());
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
            logger.error("基础数据删除出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
        logger.debug("deleteBasic 执行结束  出参:{}", model);
    }
    
}
