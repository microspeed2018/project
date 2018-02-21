package com.zjzmjr.admin.web.home.security;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.security.web.authentication.exception.FormLoginException;
import com.zjzmjr.security.web.authentication.hook.FormAuthenticationSuccessHook;
import com.zjzmjr.security.web.authentication.token.UsernamePasswordToken;
import com.zjzmjr.security.web.util.WebUtil;

/**
 * 登录成功后处理
 * 
 * @author oms
 * @version $Id: LoginSuccessHook.java, v 0.1 2016-5-25 上午10:10:07 oms Exp $
 */
public class LoginSuccessHook implements FormAuthenticationSuccessHook{

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHook.class);
    
    @Resource(name = "adminFacade")
    private IAdminFacade adminFacade;
    
    /**
     * 用户登录成功后更新用户信息
     * <p>
     * <ul>
     * <li>更新用户登录IP</li>
     * <li>更新用户最后登录时间</li>
     * <li>更新用户登录成功次数</li>
     * <li>将连续登录失败次数置0</li>
     * <li>更新会话中authentication的source</li>
     * </ul>
     * </p>
     *
     * @see com.zjzmjr.web.security.authentication.hook.FormAuthenticationSuccessHook#onSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) {
        if (!(authentication instanceof UsernamePasswordToken)) {
            throw new FormLoginException("登录令牌类型错误");
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authentication;
        logger.debug("onSuccess 执行开始  入参:{}", token);
        if (logger.isInfoEnabled()) {
            logger.info("用户[{}]登录成功,token:{}", token.getName(), token);
        }

        String principal = (String) token.getPrincipal();
        if (principal.matches("^\\d{1,}$")) {
            Admin user = adminFacade.getByID(Integer.parseInt((String) authentication.getPrincipal())).getT();
            if (user != null) {
                Admin mod = new Admin(user.getId());

                //更新用户登录IP
                mod.setLoginIp(WebUtil.getUserIp(req));
                //更新用户最后登录时间
                mod.setLoginTime(new Date());
                //更新用户登录成功次数
                mod.setLoginSucceed(user.getLoginSucceed() + 1);
                //将连续登录失败次数置0
                mod.setLoginError(0);

                //此处没有锁定记录，理论上同一个用户登录不存在并发问题
                try {
                    adminFacade.updateAdmin(mod);
                } catch (Exception e) {
                    logger.error("更新用户信息错误,{}", mod, e);
                }
                logger.debug("onSuccess 执行结束  出参:{}{}", user,adminFacade.updateAdmin(mod));
            } else {
                logger.error("用户名不存在，token:{}", token);
            }
        } else {
            if (logger.isWarnEnabled()) {
                logger.warn("用户登录的principal格式错误:token:{}", token);
            }
        }

        //设置用户来源
        token.setSource(WebUtil.getUserIp(req));
        //
        token.setType("web");

        clearSession(req);
    }

    protected void clearSession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("success");
            session.removeAttribute("resultMsg");
        }
    }

}
