package com.zjzmjr.finance.web.home.aspect;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zjzmjr.core.base.annotation.AccessHeartBeat;
import com.zjzmjr.core.cache.redis.JedisPull;
import com.zjzmjr.security.web.util.WebUtil;

/**
 * 限定客户端对某个接口的访问次数
 * 
 * @author oms
 * @version $Id: HeartBeatAspect.java, v 0.1 2016-9-11 下午1:31:42 oms Exp $
 */
@Aspect
public class HeartBeatAspect implements Ordered{

    /**
     * 心跳检测手机
     */
    private final String BEAT_HB_MOBILE_KEY = "BEAT_SMS_HB_MOBILE#{mobile}";

    /**
     * 心跳检测IP
     */
    private final String BEAT_HB_IP_KEY = "BEAT_SMS_HB_IP#{ip}";

    /**
     * 指定的访问限制key
     */
    private final String BEAT_HB_SPEFIC_KEY = "BEAT_HB_SPEFIC_KEY#{key}";

    /**
     * 短信发送IP心跳数
     */
    private final int BEAT_IP_HEART_COUNT = 15;

    private String resultFalgField = "success";

    private String resultMsgField = "resultMsg";

    private String errorMsg = "访问过于频繁";

    private String charset = "UTF-8";

    /**
     * 
     * 
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.zjzmjr.core.base.annotation.AccessHeartBeat)")
    public Object invokeAround(ProceedingJoinPoint jp) throws Throwable {
        Method method = ((MethodSignature) jp.getSignature()).getMethod();

        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        // AbstractForm form = null;
        ModelMap model = null;

        // 解析参数列表
        Object[] args = jp.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    req = ((HttpServletRequest) arg);
                    // }else if(arg instanceof AbstractForm){
                    // form = (AbstractForm)arg;
                } else if (arg instanceof ModelMap) {
                    model = (ModelMap) arg;
                } else if (arg instanceof HttpServletResponse) {
                    resp = (HttpServletResponse) arg;
                }
            }
        }
        // 获取注解
        AccessHeartBeat beat = method.getAnnotation(AccessHeartBeat.class);
        if (beat == null) {
            throw new IllegalStateException("can not find a DuplicateCommit Annotation at action method[" + method.toString() + "]");
        }

        if (req == null) {
            if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes) {
                req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            } else {
                throw new RuntimeException("can not find a valid request at action method[" + method.toString() + "] context");
            }
        }

        try {

            String mobile = beat.mobile();
            int beatCount = beat.count();

            // 设置访问间隔数
            // ip地址的访问限制
            String ip = WebUtil.getUserIp(req);
            if (StringUtils.isNotBlank(ip)) {
                String hbIpKey = BEAT_HB_IP_KEY.replace("{ip}", ip);
                Integer count = JedisPull.getObject(hbIpKey, Integer.class);
                if (count != null && count >= BEAT_IP_HEART_COUNT) {
                    throw new IllegalArgumentException("该IP访问过于频繁");
                }
                JedisPull.setObject(hbIpKey, count == null ? 1 : count + 1, beat.interval());
            }

            if (StringUtils.isNotBlank(mobile)) {
                String hbMobileKey = BEAT_HB_MOBILE_KEY.replace("{mobile}", mobile);
                Integer count = JedisPull.getObject(hbMobileKey, Integer.class);
                if (count != null && count >= beatCount) {
                    throw new IllegalArgumentException("该手机访问过于频繁");
                }
                JedisPull.setObject(hbMobileKey, count == null ? 1 : count + 1, beat.interval());
            }

            String speficKey = beat.value();
            if (StringUtils.isNotBlank(speficKey) && StringUtils.isNotBlank(req.getParameter(speficKey))) {
                speficKey = req.getParameter(speficKey);
                String hbIpKey = BEAT_HB_SPEFIC_KEY.replace("{key}", speficKey);
                Integer count = JedisPull.getObject(hbIpKey, Integer.class);
                if (count != null && count >= beatCount) {
                    throw new IllegalArgumentException("访问过于频繁");
                }
                JedisPull.setObject(hbIpKey, count == null ? 1 : count + 1, beat.interval());
            }
        } catch (IllegalArgumentException ex) {
            setResultFailMsg(beat, method, req, resp, model, ex.getMessage());
            // 失败之后跳转
            return String.class==method.getReturnType()?beat.value():null;
        }

        return jp.proceed(jp.getArgs());
    }

    // 将错误信息返回
    protected void setResultFailMsg(AccessHeartBeat dc, Method method, HttpServletRequest req, HttpServletResponse resp, ModelMap model, String errorInfo) {

        // 如果是void，那么ajax
        if (Void.TYPE == method.getReturnType()) {
            if (resp != null) { // 如果带有response，那么将信息写入到response
                resp.setCharacterEncoding(charset);
                resp.setContentType("text/html");
                OutputStream outs = null;
                try {
                    outs = resp.getOutputStream();
                    outs.write(getJsonFailMsg(StringUtils.isBlank(dc.errorMsg())?errorInfo:dc.errorMsg()).getBytes(charset));
                } catch (IOException e) {
                } finally {
                    IOUtils.closeQuietly(outs);
                }
            }
        } else {
            if (model != null) {
                model.put(resultFalgField, false);
                model.put(resultMsgField, StringUtils.isBlank(dc.errorMsg()) ? errorMsg : StringUtils.trimToEmpty(errorMsg));
            } else if (req != null) {
                req.setAttribute(resultFalgField, false);
                req.setAttribute(resultMsgField, StringUtils.isBlank(dc.errorMsg()) ? errorMsg : StringUtils.trimToEmpty(errorMsg));
            } else {
                throw new RuntimeException("can not find ModelMap or Request to put the message from action method[" + method.toString() + "]");
            }
        }
    }

    protected String getJsonFailMsg(String customMsg) {
        return "{\"" + resultFalgField + "\":false,\"" + resultMsgField + "\":\"" + (StringUtils.isBlank(customMsg) ? errorMsg : StringUtils.trimToEmpty(customMsg)) + "\"}";
    }

    public String getResultFalgField() {
        return resultFalgField;
    }

    public void setResultFalgField(String resultFalgField) {
        this.resultFalgField = resultFalgField;
    }

    public String getResultMsgField() {
        return resultMsgField;
    }

    public void setResultMsgField(String resultMsgField) {
        this.resultMsgField = resultMsgField;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
    
}
