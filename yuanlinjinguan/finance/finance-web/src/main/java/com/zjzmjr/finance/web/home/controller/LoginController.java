package com.zjzmjr.finance.web.home.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 未登录时，处理
 * 
 * @author Administrator
 * @version $Id: LoginController.java, v 0.1 2016-5-12 上午9:51:12 Administrator
 *          Exp $
 */
@Controller
@RequestMapping(value = "/home")
public class LoginController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final String errInfo = "SPRING_SECURITY_LAST_EXCEPTION";

    private static final String LOGIN_VIEW = "/WEB-INF/pages/home/index.jsp";

    /**
     * 
     * 
     * @param model
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping(value="/login.htm", method = RequestMethod.GET)
    public void index(ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        logger.info("登录失败");
        putError(model, GenerateConstants.unauthorized, "请登录系统");
        if (req.getSession().getAttribute(errInfo) != null) {
            Exception exception = (Exception) req.getSession().getAttribute(errInfo);
            model.put("resultMsg", exception.getMessage());
            req.getSession().removeAttribute(errInfo);
        }
        logger.debug("index出参:{}",model);
        responseAsJson(model, resp);
        //return LOGIN_VIEW;
    }

    /**
     * 接收兑吧游客登录返回的原先地址
     * 
     * @param model
     * @param req
     * @param resp
     */
    @RequestMapping(value="/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model ,HttpServletRequest req){
        String url=req.getHeader("referer");
        model.put("url", url);
        model.put("url1", req.getParameter("duiba"));
        return LOGIN_VIEW;
    }

}
