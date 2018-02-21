package com.zjzmjr.finance.web.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.app.IAppReleaseFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.enums.user.DeviceEnum;
import com.zjzmjr.core.model.app.AppRelease;
import com.zjzmjr.core.model.app.AppReleaseQuery;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * app版本信息控制层
 * 
 * @author Administrator
 * @version $Id: AppReleaseController.java, v 0.1 2016-11-17 下午1:05:11
 *          Administrator Exp $
 */
@Controller
@RequestMapping("/appRelease")
public class AppReleaseController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AppReleaseController.class);

    @Resource(name = "appReleaseFacade")
    private IAppReleaseFacade appReleaseFacade;

    /**
     * 获取最新版的app版本信息
     * 
     * @param model
     * @param req
     * @param resp
     */
    @RequestMapping(value = "/getNewAppVertion.htm", method = RequestMethod.POST)
    public void getNewestApp(ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("getNewestApp入参：{}",model);
        try {
            Assert.notNull(req.getParameter("device"), "设备信息错误，请重试");
            Integer device = DeviceEnum.getByHumanName(req.getParameter("device").toUpperCase()).getValue();
            Assert.notNull(device, "设备信息错误，请重试");
            AppReleaseQuery query = new AppReleaseQuery();
            // Integer device = Integer.parseInt(req.getParameter("device"));
            query.setDevice(device);
            PageBean pageBean = new PageBean(Integer.MAX_VALUE, 1);
            query.setPageBean(pageBean);
            ResultPage<AppRelease> result = appReleaseFacade.getAppReleases(query);
            if (result.isSuccess()) {
                model.put("appVersion", result.getList().get(0));
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
            logger.debug("getNewestApp出参：{}",model);
        } catch (Exception ex) {
            logger.error("获取最新app版本信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

}
