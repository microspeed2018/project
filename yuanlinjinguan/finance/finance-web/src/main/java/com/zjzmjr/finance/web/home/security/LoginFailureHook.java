package com.zjzmjr.finance.web.home.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.security.web.authentication.exception.CheckCodeException;
import com.zjzmjr.security.web.authentication.exception.FormLoginException;
import com.zjzmjr.security.web.authentication.hook.FormAuthenticationFailureHook;

public class LoginFailureHook implements FormAuthenticationFailureHook {

    private static final Logger logger = LoggerFactory.getLogger(LoginFailureHook.class);

//    private IUserFacade userFacade;

    @Override
    public void onFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) {
        logger.error("用户登录错误", exception);
        if (exception instanceof CheckCodeException) {
            req.setAttribute("success", false);
            req.setAttribute("resultMsg", "验证码错误");
        } else {
            if (!(exception instanceof UsernameNotFoundException)) {
                String username = StringUtils.trimToEmpty(req.getParameter("username"));
//                ResultEntry<User> result = userFacade.getUserByName(username);
//                if (result.isSuccess()) {
                    /*User user = result.getT();
                    if (user != null && UserAccStatusEnum.NORMAL.getValue().equals(user.getAccStatus()) && user.getLoginError() < SecurityUtil.getDefaultFozenLoginCnt()) {
                        User mod = new User();
                        mod.setId(user.getId());
                        // 连续登录失败次数
                        mod.setLoginError(user.getLoginError() + 1);
                        // 登录失败次数
                        mod.setLoginFail(user.getLoginFail() + 1);
                        // 设置登录时间
                        mod.setLoginTime(new Date());
                        // 设置登录IP
                        mod.setLoginIp(WebUtil.getUserIp(req));

                        if (mod.getLoginError() >= SecurityUtil.getDefaultFozenLoginCnt()) {
                            // 将帐户冻结
                            // mod.setAccStatus(UserAccStatusEnum.FROZEN.getValue());
                            // 冻结24小时
                            mod.setLoginFrozen(DateUtil.addDays(new Date(), 1));
                        }
                        // 更新用户信息
                        try {
//                            userOperFacade.updateUser(mod);
                        } catch (Exception e) {
                        }

                        if (mod.getLoginError() >= SecurityUtil.getDefaultLoginErrorCnt() && exception instanceof FormLoginException) {
                            FormLoginException exp = (FormLoginException) exception;
                            if (mod.getLoginError() >= SecurityUtil.getDefaultFozenLoginCnt()) {
                                exp.setMessage("该账户已锁定，请24小时后再重试");
                            } else {
                                exp.setMessage("登录错误次数过多，您还有" + (SecurityUtil.getDefaultFozenLoginCnt() - mod.getLoginError()) + "次尝试机会。您还可以：<a href='"+SecurityUtil.getFindLoginPassWord()+"' target='_self'>找回密码</a>");
                            }
                        }

                    } else if (user != null && (UserAccStatusEnum.LOGOFF.getValue().equals(user.getAccStatus()) || UserAccStatusEnum.FROZEN.getValue().equals(user.getAccStatus()))) {
                        if (exception instanceof FormLoginException) {
                            ((FormLoginException) exception).setMessage("用户名或密码错误");
                        }
                    }

                    if (logger.isInfoEnabled()) {
                        logger.info("用户[{}]登录失败,登录IP-[{}]", user, WebUtil.getUserIp(req));
                    }*/
//                }
            }

            // 设置错误信息
            req.setAttribute("success", false);
            if (exception instanceof FormLoginException) {
                req.setAttribute("resultMsg", exception.getMessage());
            } else {
                req.setAttribute("resultMsg", "用户名或密码错误");
            }
        }

    }

//    public IUserFacade getUserFacade() {
//        return userFacade;
//    }
//
//    public void setUserFacade(IUserFacade userFacade) {
//        this.userFacade = userFacade;
//    }

}
