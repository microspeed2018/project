package com.zjzmjr.finance.web.home.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.security.web.authentication.strategy.CheckCodeStrategy;

@Service("checkCodeStrategy")
public class ImgCheckCodeStrategy implements CheckCodeStrategy {

    @Override
    public boolean isCheckCodeValid(HttpServletRequest req, String checkCode) {
        if(StringUtils.isEmpty(checkCode)){
            return true;
        }
        if(checkCode.equalsIgnoreCase(String.valueOf(req.getSession().getAttribute(GenerateConstants.CHECK_CODE)))){
            return true;
        }
        return false;
    }

    @Override
    public String getServerCheckCode(HttpServletRequest req) {
        return null;
    }

    @Override
    public void putServerCheckCode(HttpServletRequest req, String checkCode) {
    }

    @Override
    public void clearServerCheckCode(HttpServletRequest req) {
    }

    @Override
    public boolean isCheckCodeRequired(HttpServletRequest req) {
        return false;
    }

}
