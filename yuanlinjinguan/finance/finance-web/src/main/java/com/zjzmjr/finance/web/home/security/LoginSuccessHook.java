package com.zjzmjr.finance.web.home.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.owasp.esapi.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.security.web.authentication.exception.FormLoginException;
import com.zjzmjr.security.web.authentication.hook.FormAuthenticationSuccessHook;
import com.zjzmjr.security.web.authentication.strategy.PasswordEncodeStrategy;
import com.zjzmjr.security.web.authentication.token.UsernamePasswordToken;
import com.zjzmjr.security.web.util.CookieUtil;
import com.zjzmjr.security.web.util.WebUtil;

public class LoginSuccessHook implements FormAuthenticationSuccessHook {

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHook.class);

    /**
     * 登录名记住一个月
     */
    private final int remeberNameExpire = 60 * 60 * 24 * 30;

//    private IUserFacade userFacade;

    private PasswordEncodeStrategy passwordEncodeStrategy;

    @Override
    public void onSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) {
        if (!(authentication instanceof UsernamePasswordToken)) {
            throw new FormLoginException("登录令牌类型错误");
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authentication;

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("success", true);
        model.put("code", "0");
        model.put("resultMsg", "");
        model.put("userId", token.getPrincipal());
        model.put("userName", token.getName());
        // 若账号已有姓名，则直接传给前端
        model.put("realName", "");
        model.put("idCard", "");
//        if(StringUtils.isNotBlank(token.getPrincipal()) && NumberUtils.isDigits(token.getPrincipal())){
//            ResultEntry<User> userRest =userFacade.getUserById(Long.valueOf(StringUtils.trimToEmpty(token.getPrincipal())));
//            if(userRest.isSuccess()){
//                if(StringUtils.isNotBlank(userRest.getT().getName())){
//                    model.put("realName", userRest.getT().getName());
//                    model.put("idCard", userRest.getT().getIdentityNo());
//                }
//            }
//        }
        StringBuilder builder = new StringBuilder();
        builder.append(token.getPrincipal());
        builder.append(token.getName());
//        builder.append(WebUtil.getUserIp(req));
        builder.append(GenerateConstants.token);
        model.put("springtoken", passwordEncodeStrategy.encodePassword(builder.toString()));
        if(req.getAttribute("jsonp") != null && Boolean.TRUE.equals(req.getAttribute("jsonp"))){
        }else{
            responseAsJson(model, resp);
        }
        
        if (logger.isInfoEnabled()) {
            logger.info("用户[{}]登录成功,token:{}", token.getName(), token);
        }

        String principal = (String) token.getPrincipal();
        if (principal.matches("^\\d{1,}$")) {
            /*ResultEntry<User> result = new ResultEntry<User>(); //userFacade.getUserById(Long.parseLong((String) authentication.getPrincipal()));
            if (result.isSuccess()) {
                User user = result.getT();
                if (user != null) {
                    User mod = new User();
                    mod.setId(user.getId());
                    // 更新用户登录IP
                    mod.setLoginIp(WebUtil.getUserIp(req));
                    // 更新用户最后登录时间
                    mod.setLoginTime(new Date());
                    // 更新用户登录成功次数
                    mod.setLoginSucceed(user.getLoginSucceed() + 1);
                    // 将连续登录失败次数置0
                    mod.setLoginError(0);

//                    LoginLog loginLog = new LoginLog();
//                    loginLog.setDevice(DeviceEnum.getByHumanName(req.getParameter("device")).getValue());
//                    loginLog.setUserid(user.getId());
//                    loginLog.setIp(WebUtil.getUserIp(req));
//                    loginLog.setSource(token.getSource());
//                    loginLog.setTime(new Date());
//                    // 此处没有锁定记录，理论上同一个用户登录不存在并发问题
//                    try {
//                        userOperFacade.updateUser(mod);
//                        userOperFacade.addUserLoginLogs(loginLog);
//                    } catch (Exception e) {
//                        logger.error("更新用户信息错误,{}", mod, e);
//                    }
                } else {
                    logger.error("用户名不存在，token:{}", token);
                }

                
                 * 写入登录信息到cookie
                 
                writeLoginCookie(resp, authentication);
                
                 * 写入分析站Cookie
                 
                writeFxCookie(user, resp);
                
                 * 多用户跟踪
                 
                traceUser(req, resp, user);
                
                 * 写入记住用户名
                 
                String remeber = req.getParameter("remeber");
                if ("1".equals(remeber) || "true".equalsIgnoreCase(remeber)) {
                    writeRemeberCookie(resp, req.getParameter("username"));
                } else if ("0".equals(remeber) || "false".equalsIgnoreCase(remeber)) {
                    clearRemeberName(resp);
                }
            }*/

        } else {
            if (logger.isWarnEnabled()) {
                logger.warn("用户登录的principal格式错误:token:{}", token);
            }
        }

        // 设置用户来源
        token.setSource(WebUtil.getUserIp(req));
        //
        token.setType("HOME");
    }

    protected void writeLoginCookie(HttpServletResponse resp, Authentication authentication) {
        UsernamePasswordToken token = (UsernamePasswordToken) authentication;
        CookieUtil.addCookie("userId", token.getPrincipal(), StringUtils.isNotBlank(GenerateConstants.domain) ? GenerateConstants.domain : ".jintouxing.com", -1, resp);
    }

    /**
     * 跟踪用户登录，多用户绑定
     * 
     * @param req
     * @param resp
     * @param user
     */
    protected void traceUser(HttpServletRequest req, HttpServletResponse resp, User user) {
        // if (user != null) {
        // String cookie = CookieUtil.getCookie(req, "X-TCE");
        // if (StringUtils.isNotBlank(cookie)) {
        // String[] str = StringUtils.trimToEmpty(cookie).split("\\|");
        // if (str.length == 2 && str[0].matches("^\\d{5,}$") &&
        // MD5Util.getMD5WithSalt(str[0],
        // SystemConstants.SECURITY_TRACE_SALT).equalsIgnoreCase(str[1])) {
        // Integer uid = Integer.parseInt(str[0], 10);
        // if (!user.getUid().equals(uid) && userDAO.getById(uid) != null) {
        // User umod = new User(user.getId());
        // if (user.getUid() <= 0) {
        // umod.setUid(uid);
        // } else {
        // int cnt1 = userDAO.getCountByUid(user.getUid());
        // if (cnt1 <= 1) {
        // umod.setUid(uid);
        // } else {
        // int cnt2 = userDAO.getCountByUid(uid);
        // if (cnt1 <= cnt2) {
        // umod.setUid(uid);
        // }
        // }
        // }
        //
        // if (umod.getUid() != null) {
        // try {
        // userDAO.update(umod);
        // logger.info("多用户跟踪绑定[userId:{},uId:{}]", umod.getId(),
        // umod.getUid());
        // } catch (Exception e) {
        // logger.error("更新用户跟踪信息出错,user:{}", umod);
        // }
        // user.setUid(umod.getUid());
        // }
        // }
        // }
        // }
        // Integer uid = user.getUid() <= 0 ? user.getId() : user.getUid();
        // // 加入跟踪标记
        // CookieUtil.addCookie("X-TCE", uid + "|" + MD5Util.getMD5WithSalt("" +
        // uid, SystemConstants.SECURITY_TRACE_SALT),
        // StringUtils.isNotBlank(GeneralizeConstants.domain) ?
        // GeneralizeConstants.domain : ".cbylsj.com", 60 * 60 * 24 * 3, resp);
        // }
    }

    protected void writeFxCookie(User user, HttpServletResponse resp) {/*
        StringBuilder sb = new StringBuilder();
        sb.append(user.getId());
        sb.append('`');
        sb.append(user.getUserName());
        sb.append('`');
        sb.append(user.getPassWord().length() == 32 ? user.getPassWord().substring(6, 12) : user.getPassWord());
        sb.append('`');
        sb.append("null");
        sb.append('`');
        sb.append("0");
        String md5 = passwordEncodeStrategy.encodePassword(sb.toString());
        sb.append('`');
        sb.append(System.currentTimeMillis());
        sb.append('`');
        sb.append(md5);

        CookieUtil.addCookie("YSESSION", sb.toString(), GeneralizeConstants.domain, Integer.MAX_VALUE, "GBK", resp);
    */}

    protected void writeRemeberCookie(HttpServletResponse resp, String name) {
        CookieUtil.addCookie("X-NAME", StringUtils.trimToEmpty(name), StringUtils.isNotBlank(GenerateConstants.domain) ? GenerateConstants.domain : ".cbylsj.com", remeberNameExpire, resp);
    }

    protected void clearRemeberName(HttpServletResponse resp) {
        CookieUtil.deleteCookie("X-NAME", resp);
    }

    /**
     * 以json方式返回结果
     * 
     * @param model   数据模型
     * @param resp    Http响应
     */
    protected void responseAsJson(Map<String, Object> model,HttpServletResponse resp){
        //resp.reset();
        if(!resp.isCommitted()){
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/plain");
            
            OutputStream outs = null;
            try {
                outs = resp.getOutputStream();
                outs.write(JSONObject.fromObject(model).toString().getBytes("UTF-8"));
            } catch (IOException e) {
            }finally{
                IOUtils.closeQuietly(outs);
            }
        }
    }
    
    public PasswordEncodeStrategy getPasswordEncodeStrategy() {
        return passwordEncodeStrategy;
    }

    public void setPasswordEncodeStrategy(PasswordEncodeStrategy passwordEncodeStrategy) {
        this.passwordEncodeStrategy = passwordEncodeStrategy;
    }

//    public IUserFacade getUserFacade() {
//        return userFacade;
//    }
//
//    public void setUserFacade(IUserFacade userFacade) {
//        this.userFacade = userFacade;
//    }

}
