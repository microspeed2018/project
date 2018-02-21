package com.zjzmjr.admin.web.home.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.enums.admin.AdminAccStatusEnum;
import com.zjzmjr.core.enums.user.UserStatusEnum;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.security.web.authentication.exception.CheckCodeException;
import com.zjzmjr.security.web.authentication.exception.FormLoginException;
import com.zjzmjr.security.web.authentication.hook.FormAuthenticationFailureHook;
import com.zjzmjr.security.web.util.WebUtil;


public class LoginFailureHook implements FormAuthenticationFailureHook {

  private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHook.class);
    
    @Autowired
    private IAdminFacade adminFacade;
    
    @Override
    public void onFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) {
        logger.debug("onFailure 执行开始  入参:{}", req.getParameter("username"));
        if (!(exception instanceof UsernameNotFoundException)) {
            String username = StringUtils.trimToEmpty(req.getParameter("username"));
            Admin admin = adminFacade.getByUsername(username).getT();
            if (admin != null && AdminAccStatusEnum.NORMAL.getValue().equals(admin.getAccStatus())) {
                Admin mod = new Admin(admin.getId());

                // 连续登录失败次数
                mod.setLoginError(admin.getLoginError() + 1);
                // 登录失败次数
                mod.setLoginFail(admin.getLoginFail() + 1);
                // 设置登录时间
                mod.setLoginTime(new Date());
                // 设置登录IP
                mod.setLoginIp(WebUtil.getUserIp(req));

                if (mod.getLoginError() >= 10) {
                    // 将帐户冻结
                    mod.setAccStatus(UserStatusEnum.FROZEN.getValue());
                }
                // 更新用户信息
                try {
                    adminFacade.updateAdmin(admin);
                } catch (Exception e) {
                }

                if (logger.isInfoEnabled()) {
                    logger.info("用户[{}]登录失败{},登录IP-[{}]", username,
                            AdminAccStatusEnum.FROZEN.getValue().equals(mod.getAccStatus()) ?
                                    ("-帐户冻结[" + mod.getLoginError() + "]") :
                                    "", mod.getLoginIp());
                }
            }
            logger.debug("onFailure 执行结束  出参:{}{}", admin,adminFacade.updateAdmin(admin));
            // 设置错误信息
            HttpSession session = req.getSession();
            session.setAttribute("success", false);
            if (exception instanceof FormLoginException) {
                session.setAttribute("resultMsg", exception.getMessage());
            } else if (exception instanceof CheckCodeException) {
                session.setAttribute("resultMsg", "验证码错误");
            } else {
                session.setAttribute("resultMsg", "用户名或密码错误");
            }
        }
    }

}
