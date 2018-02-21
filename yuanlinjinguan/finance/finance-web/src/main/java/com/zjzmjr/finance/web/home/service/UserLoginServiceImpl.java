package com.zjzmjr.finance.web.home.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zjzmjr.common.util.VerifyUtil;
import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.api.admin.IAdminLoginAuthFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.security.ZjzmjrPasswordStrategy;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.SimpleGrantedAuthority;
import com.zjzmjr.security.web.userdetails.UserLoginResult;
import com.zjzmjr.security.web.userdetails.UserLoginService;

/**
 * 
 * 
 * @author liwen
 * @version $Id: UserLoginServiceImpl.java, v 0.1 2015-12-8 上午11:05:33 liwen Exp
 *          $
 */
public class UserLoginServiceImpl implements UserLoginService {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    /**
     * 注入密码加密
     */
    private ZjzmjrPasswordStrategy passwordEncodeStrategy;

    private IAdminFacade userFacade;

    /**
     * 管理员权限
     */
    private IAdminLoginAuthFacade adminLoginAuthFacade;

    @Override
    public UserLoginResult loadUser(String identity, String source, String token) throws UsernameNotFoundException {
        if (StringUtils.isBlank(identity)) {
            throw new UsernameNotFoundException("手机号或密码错误");
        }
//        ResultEntry<Admin> result = userFacade.getByMobile(identity);
        ResultEntry<Admin> result = null;
        if (VerifyUtil.isMobile(identity)) {
            result = userFacade.getByMobile(identity);
        } else {
            result = userFacade.getByUsername(identity);
        }
        if (!result.isSuccess() || result.getT() == null) {
            throw new UsernameNotFoundException("手机号未注册，请重新输入");
        } else if(result.isSuccess() && GenerateConstants.number_1.equals(result.getT().getAccStatus())){
            throw new UsernameNotFoundException("手机号不可登陆，请联系管理员");
        }
        Admin user = result.getT();
        // 如果登录冻结超时
        /*
         * if (user.getLoginError() >= SecurityUtil.getDefaultFozenLoginCnt() &&
         * user.getLoginFrozen().getTime() > System.currentTimeMillis()) { int
         * offset = (int) ((user.getLoginFrozen().getTime() -
         * System.currentTimeMillis()) / (1000 * 60)); if
         * (logger.isInfoEnabled()) { logger.info("登录错误次数过多,{},{}", identity,
         * source); } throw new FormLoginException("登录错误次数过多，请于" + (offset <= 0
         * ? 1 : offset) + "分钟后重试"); }
         */
        // return new UserLoginResult(user.getId().intValue(),
        // Collections.<GrantedAuthority> emptyList(), user.getUserName(),
        // user.getPassWord(), true,
        // !UserAccStatusEnum.FROZEN.getValue().equals(user.getAccStatus()),
        // true,
        // !UserAccStatusEnum.LOGOFF.getValue().equals(user.getAccStatus()));
        // 判断用户是否需要自动登录
        boolean isAutoLogin = false;
        StringBuilder builder = new StringBuilder();
        builder.append(user.getId());
        builder.append(user.getMobile());
        // builder.append(source);
        builder.append(GenerateConstants.token);
        if (passwordEncodeStrategy.encodePassword(builder.toString()).equals(token)) {
            logger.info("用户手机号{}姓名{}自动登录成功", user.getMobile(), user.getName());
            isAutoLogin = true;
        }

        List<SimpleGrantedAuthority> auths = Collections.<SimpleGrantedAuthority> emptyList();
        ResultRecord<SimpleGrantedAuthority> resultAuth = adminLoginAuthFacade.getUserAuths(user);
        if (resultAuth.isSuccess()) {
            auths = resultAuth.getList();
        }
        UserLoginResult loginResult = new UserLoginResult(user.getId().intValue(), auths, user.getName(), user.getPassword(), true, true, true, true, true, isAutoLogin);
        loginResult.setCompanyId(String.valueOf(user.getCompanyId()));
        return loginResult;
    }

    public IAdminFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(IAdminFacade userFacade) {
        this.userFacade = userFacade;
    }

    public IAdminLoginAuthFacade getAdminLoginAuthFacade() {
        return adminLoginAuthFacade;
    }

    public void setAdminLoginAuthFacade(IAdminLoginAuthFacade adminLoginAuthFacade) {
        this.adminLoginAuthFacade = adminLoginAuthFacade;
    }

    public ZjzmjrPasswordStrategy getPasswordEncodeStrategy() {
        return passwordEncodeStrategy;
    }

    public void setPasswordEncodeStrategy(ZjzmjrPasswordStrategy passwordEncodeStrategy) {
        this.passwordEncodeStrategy = passwordEncodeStrategy;
    }

}
