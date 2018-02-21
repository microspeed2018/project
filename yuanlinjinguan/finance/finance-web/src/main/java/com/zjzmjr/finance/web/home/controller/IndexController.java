package com.zjzmjr.finance.web.home.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.security.ZjzmjrPasswordStrategy;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
public class IndexController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 注入密码加密
     */
    @Resource(name = "passwordEncodeStrategy")
    private ZjzmjrPasswordStrategy passwordEncodeStrategy;
    
    @RequestMapping(value="/index.htm", method = RequestMethod.GET)
    public void index(ModelMap model, HttpServletResponse resp) {
        logger.info("session过期");
        model.put("sessionExp", "true");
        putError(model, GenerateConstants.unauthorized, "session过期");
        responseAsJson(model, resp);
    }

    @RequestMapping(value="/user/index.htm", method = RequestMethod.GET)
    public void login(ModelMap model, HttpServletResponse resp, HttpServletRequest req){
        try{
            logger.info("登录成功");
            model.put("userId", SpringSecurityUtil.getIntPrincipal());
            model.put("userName", SpringSecurityUtil.getUserName());
            StringBuilder builder = new StringBuilder();
            builder.append(SpringSecurityUtil.getIntPrincipal());
            builder.append(SpringSecurityUtil.getUserName());
//            builder.append(WebUtil.getUserIp(req));
            builder.append(GenerateConstants.token);
            model.put("springtoken", passwordEncodeStrategy.encodePassword(builder.toString()));
            putSuccess(model, "");
        }catch(Exception ex){
            logger.error("登录出错：", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("login入参:{}",model);
        responseAsJson(model, resp);
    }

}
