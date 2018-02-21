/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2016 All Rights Reserved.
 */
package com.zjzmjr.finance.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 环境上下文参数配置过滤器
 * 
 * @author elliott
 * @version $Id: CommonRequestContextAwareFilter.java, v 0.1 2014-1-14 下午3:14:11
 *          elliott Exp $
 */
public class CommonRequestContextAwareFilter implements Filter {

    @Value("${ctx.url}")
    private String ctx;

    @Value("${res.js.url}")
    private String resJs;

    @Value("${res.css.url}")
    private String resCss;

    @Value("${res.image.url}")
    private String resImage;

    @Value("${file.version}")
    private String version;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext())
                .getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        req.setAttribute("ctx", ctx);
        req.setAttribute("res_js", resJs);
        req.setAttribute("res_css", resCss);
        req.setAttribute("res_img", resImage);
        req.setAttribute("ver", version);
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

}
