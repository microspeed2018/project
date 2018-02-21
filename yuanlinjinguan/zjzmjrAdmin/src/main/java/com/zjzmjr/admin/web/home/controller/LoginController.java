/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.home.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * @author elliott
 * @version $Id: LoginController.java, v 0.1 2014-1-16 上午11:30:54 elliott Exp $
 */
@Controller
@RequestMapping("/login.htm")
public class LoginController extends BaseController {

    private final String loginView = "/WEB-INF/pages/home/login.jsp";

//    @Resource(name = "loginCheckFacade")
//    private ILoginCheckFacade loginCheckFacade;
    
    @RequestMapping(method = RequestMethod.GET)
    public String loginView(ModelMap model, HttpServletRequest req) {
        clearSession(model, req);
        return loginView;
    }
    
//    @RequestMapping(method=RequestMethod.POST)
//    public String checkUser(ModelMap model, HttpServletRequest req){
//        String userName = req.getParameter("username");
//        String password = req.getParameter("password");
//        String redirect = "redirect:/home/index.htm";
//        Admin admin = loginCheckFacade.checkUserLogin(userName);
//        UsernamePasswordToken authentication = new UsernamePasswordToken(userName, password);
//        authentication.setAuthenticated(true);
//        SecurityContextImpl context = new SecurityContextImpl();
//        context.setAuthentication(authentication);
//        SecurityContextHolder.setContext(context);
//        if(admin == null){
//            redirect = loginView;
//        }
//        
//        return redirect;
//    }

    protected void clearSession(ModelMap model, HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            if (session.getAttribute("success") != null) {
                req.setAttribute("success", session.getAttribute("success"));
                req.setAttribute("resultMsg", session.getAttribute("resultMsg"));

                session.removeAttribute("success");
                session.removeAttribute("resultMsg");
            } else {
                putSuccess(model, "");
            }
        } else {
            putSuccess(model, "");
        }
    }
}
