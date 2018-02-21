package com.zjzmjr.admin.web.holiday.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.web.mvc.controller.BaseController;
import com.zjzmjr.admin.web.holiday.form.HolidayForm;
import com.zjzmjr.core.api.holiday.IHolidayFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.holiday.Holiday;
import com.zjzmjr.core.model.holiday.HolidayQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * 节假日维护控制层
 * 
 * @author chenning
 * @version $Id: HolidayController.java, v 0.1 2016-10-12 下午2:49:46 chenning Exp
 *          $
 */
@Controller
@RequestMapping("/holiday/user/holiday.htm")
public class HolidayController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(HolidayController.class);

    /**
     * 节假日查询视图
     */
    private static final String HOLIDAY_VIEW = "/WEB-INF/pages/holiday/holiday.jsp";

    /**
     * 节假日表facade层
     */
    @Resource(name = "holidayFacade")
    private IHolidayFacade holidayFacade;

    /**
     * 节假日查询视图
     * 
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, params = "action=holidayView")
    public String holidayIndex(ModelMap model) {
        return HOLIDAY_VIEW;
    }

    /**
     * 新增节假日
     * 
     * @param resp
     * @param req
     * @param form
     * @param bindResult
     */
    @RequestMapping(method = RequestMethod.POST, params = { "action=insertHoliday" })
    public void addHoliday(HttpServletResponse resp, HttpServletRequest req, HolidayForm form, BindingResult bindResult) {
        logger.debug("addHoliday 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        if (!resolveBindingError(form, bindResult, model)) {
            try {
                // 设置参数
                Holiday holiday = new Holiday();
                String time = form.getHolidayTime().replaceAll("-", "");
                holiday.setHolidayTime(time.trim());
                holiday.setCreateTime(new Date());
                holiday.setUpdateTime(holiday.getCreateTime());
                holiday.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                holiday.setFlag(form.getFlag());
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_INSERT_HOLIDAY.getValue());
                String message = StringUtils.isEmpty(form.getHolidayTime()) ? "未输入节假日时间" : "节假日时间：" + form.getHolidayTime();
                adminBusiness.setExtend1(message);
                ResultEntry<AdminBusiness> val = AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
                // 新增节假日
                Result result = holidayFacade.addHoliday(holiday);
                logger.debug("addHoliday 执行结束  出参:{}", result);
                if (result.isSuccess()) {
                    putSuccess(model, "添加成功");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                } else {
                    putError(model, result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment("错误消息：" + result.getErrorMsg());
                }
                // 更新管理员事物
                adminBusiness.setId(val.getT().getId());
                AdminTransactionUtil.updateAdminTransaction(adminBusiness);
            } catch (Exception e) {
                logger.error("新增节假日出错：", e);
                putError(model, e.getMessage());
            }
        }
        responseAsJson(model, resp);
    }

    /**
     * 更新节假日
     * 
     * @param resp
     * @param req
     * @param form
     * @param bindResult
     */
    @RequestMapping(method = RequestMethod.POST, params = { "action=updateHoliday" })
    public void updateHoliday(HttpServletResponse resp, HttpServletRequest req, HolidayForm form, BindingResult bindResult) {
        logger.debug("updateHoliday 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        if (!resolveBindingError(form, bindResult, model)) {
            try {
                // 设置参数
                Holiday holiday = new Holiday();
                holiday.setId(form.getId());
                String time = form.getHolidayTime().replaceAll("-", "");
                holiday.setHolidayTime(time.trim());
                holiday.setUpdateTime(new Date());
                holiday.setFlag(form.getFlag());
                holiday.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_MODIFY_HOLIDAY.getValue());
                String message = StringUtils.isEmpty(form.getHolidayTime()) ? "未输入节假日时间" : "节假日时间：" + form.getHolidayTime();
                adminBusiness.setExtend1(message);
                ResultEntry<AdminBusiness> val = AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
                // 更新节假日
                Result result = holidayFacade.updateHoliday(holiday);
                logger.debug("updateHoliday 执行结束  出参:{}", result);
                if (result.isSuccess()) {
                    putSuccess(model, "更新成功");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                } else {
                    putError(model, result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment("错误消息：" + result.getErrorMsg());
                }
                // 更新管理员事物
                adminBusiness.setId(val.getT().getId());
                AdminTransactionUtil.updateAdminTransaction(adminBusiness);
            } catch (Exception e) {
                logger.error("更新节假日出错：", e);
                putError(model, e.getMessage());
            }
            responseAsJson(model, resp);
        }
    }

    /**
     * 删除节假日
     * 
     * @param resp
     * @param req
     * @param form
     * @param bindResult
     */
    @RequestMapping(method = RequestMethod.POST, params = { "action=deleteHoliday" })
    public void deleteHoliday(HttpServletResponse resp, HttpServletRequest req, HolidayForm form, BindingResult bindResult) {
        logger.debug("deleteHoliday 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        if (!resolveBindingError(form, bindResult, model)) {
            try {
                Holiday holiday = new Holiday();
                holiday.setId(form.getId());
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_DELETE_HOLIDAY.getValue());
                adminBusiness.setExtend1("删除节假日id：" + form.getId());
                ResultEntry<AdminBusiness> val = AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
                // 删除节假日
                Result result = holidayFacade.deleteHoliday(holiday);
                logger.debug("deleteHoliday 执行结束  出参:{}", result);
                if (result.isSuccess()) {
                    putSuccess(model, "删除成功");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                } else {
                    putError(model, result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment("错误消息：" + result.getErrorMsg());
                }
                // 更新管理员事物
                adminBusiness.setId(val.getT().getId());
                AdminTransactionUtil.updateAdminTransaction(adminBusiness);
            } catch (Exception e) {
                logger.error("删除节假日出错：", e);
                putError(model, e.getMessage());
            }
            responseAsJson(model, resp);
        }
    }

    /**
     * 获取节假日列表
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(method = RequestMethod.POST, params = { "action=getHolidayList" })
    public void getHolidayList(HttpServletResponse resp, HolidayForm form) {
        logger.debug("getHolidayList 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        HolidayQuery query = new HolidayQuery();
        try {
            // 设置分页查询条件
            PageBean pageBean = new PageBean(form.getRows(), form.getPage());
            query.setPageBean(pageBean);
            query.setId(form.getId());
            // 如果有节假日时间，则设置节假日时间
            if (StringUtils.isNotBlank(form.getHolidayTime())) {
                Date holidayDate = DateUtil.parse(form.getHolidayTime(), DateUtil.YY_MM_DD);
                query.setHolidayTime(DateUtil.format(holidayDate, DateUtil.YYYYMMDD));
            }
            // 根据条件查询
            ResultPage<Holiday> result = holidayFacade.getHolidayList(query);
            logger.debug("getHolidayList 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("查询节假日列表出错：", e);
            putError(model, e.getMessage());
        }
        responseAsJson(model, resp);
    }

}
