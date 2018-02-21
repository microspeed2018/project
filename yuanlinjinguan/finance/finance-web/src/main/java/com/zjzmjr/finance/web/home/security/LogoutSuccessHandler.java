package com.zjzmjr.finance.web.home.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;

import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.security.web.util.CookieUtil;

public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    private List<String> cookies;

    private String logoutSuccessUrl;

    /**
     * @see org.springframework.security.web.authentication.logout.LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse,
     *      org.springframework.security.core.Authentication)
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (cookies != null && !cookies.isEmpty()) {
            for (String cookie : cookies) {
                CookieUtil.deleteCookie(StringUtils.trimToEmpty(cookie), GenerateConstants.domain, response);
            }
        }
        response.sendRedirect(StringUtils.trimToEmpty(logoutSuccessUrl));
    }

    public List<String> getCookies() {
        return cookies;
    }

    public void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

}
