package com.zjzmjr.admin.web.home.controller;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.web.mvc.controller.BaseController;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * 
 * 
 * @author liwen
 * @version $Id: CheckAuthController.java, v 0.1 2015-11-3 下午6:39:18 liwen Exp $
 */
@Controller
public class CheckAuthController extends BaseController {
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/checkAuth.htm", method=RequestMethod.GET)
    public void checkAuth(HttpServletResponse resp){
        JSONObject result = new JSONObject();
        result.put("isAuth", String.valueOf(SpringSecurityUtil.isAuthenticated()));
        responseAsJson(result, resp);
    }
    
    @RequestMapping(value="/unauthorized.htm", method={RequestMethod.POST, RequestMethod.GET})
    public void noAuth(ModelMap model, HttpServletResponse resp){
        putError(model, "没有权限");
        responseAsJson(model, resp);
    }
    
}
