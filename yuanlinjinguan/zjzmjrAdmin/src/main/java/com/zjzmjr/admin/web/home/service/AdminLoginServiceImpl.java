package com.zjzmjr.admin.web.home.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zjzmjr.common.util.VerifyUtil;
import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.api.admin.IAdminLoginAuthFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.SimpleGrantedAuthority;
import com.zjzmjr.security.web.userdetails.UserLoginResult;
import com.zjzmjr.security.web.userdetails.UserLoginService;

/**
 * 管理员登录检查
 * 
 * @author oms
 * @version $Id: AdminLoginServiceImpl.java, v 0.1 2016-5-25 上午9:55:58 oms Exp $
 */
public class AdminLoginServiceImpl implements UserLoginService {

    /**
     * 管理员信息
     */
    private IAdminFacade adminFacade;

    /**
     * 管理员权限
     */
    private IAdminLoginAuthFacade adminLoginAuthFacade;

    private List<String> securityIps = Collections.emptyList();

    /**
     * 
     * @see com.zjzmjr.security.web.userdetails.UserLoginService#loadUser(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public UserLoginResult loadUser(String identity, String source, String token) throws UsernameNotFoundException {
        if (StringUtils.isBlank(identity)) {
            throw new UsernameNotFoundException("手机号或密码错误");
        }

//        ResultEntry<Admin> result = adminFacade.getByUsername(identity);
        ResultEntry<Admin> result = null;
        if (VerifyUtil.isMobile(identity)) {
            result = adminFacade.getByMobile(identity);
        } else {
            result = adminFacade.getByUsername(identity);
        }
        if (!result.isSuccess() || result.getT() == null) {
            throw new UsernameNotFoundException("手机号未注册，请重新输入");
        }else if(result.isSuccess() && GenerateConstants.number_1.equals(result.getT().getAccStatus())){
            throw new UsernameNotFoundException("手机号不可登陆，请联系管理员");
        }

        Admin admin = result.getT();
        // 用户来源判断
        boolean isSource = true;
        if (StringUtils.isNotBlank(source) && StringUtils.isNotBlank(admin.getSecurityIp())) {
            if ("0.0.0.0".equals(admin.getSecurityIp())) {
                isSource = securityIps.contains(source);
            } else {
                String[] ips = admin.getSecurityIp().replace(";", ",").replace("#", ",").split(",");
                if (ips.length > 0) {
                    isSource = false;
                    for (String ip : ips) {
                        if (source.equals(ip)) {
                            isSource = true;
                            break;
                        }
                    }
                }
            }
        }
        List<SimpleGrantedAuthority> auths = Collections.<SimpleGrantedAuthority> emptyList();
        ResultRecord<SimpleGrantedAuthority> resultAuth = adminLoginAuthFacade.getUserAuths(admin);
        if (resultAuth.isSuccess()) {
            auths = resultAuth.getList();
        }
        UserLoginResult loginResult = new UserLoginResult(admin.getId().intValue(), auths, admin.getName(), admin.getPassword(), true, true, true, isSource, true);
        loginResult.setCompanyId(String.valueOf(admin.getCompanyId()));
        return loginResult;
    }

    public IAdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(IAdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public IAdminLoginAuthFacade getAdminLoginAuthFacade() {
        return adminLoginAuthFacade;
    }

    public void setAdminLoginAuthFacade(IAdminLoginAuthFacade adminLoginAuthFacade) {
        this.adminLoginAuthFacade = adminLoginAuthFacade;
    }

    public List<String> getSecurityIps() {
        return securityIps;
    }

    public void setSecurityIps(List<String> securityIps) {
        this.securityIps = securityIps;
    }

}
