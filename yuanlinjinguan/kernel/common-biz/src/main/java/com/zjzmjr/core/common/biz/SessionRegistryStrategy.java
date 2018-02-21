package com.zjzmjr.core.common.biz;

import java.util.Date;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.util.Assert;

import com.zjzmjr.core.cache.redis.JedisPull;

/**
 * session 保存处理逻辑
 * 
 * @author oms
 * @version $Id: SessionRegistryStrategy.java, v 0.1 2017-1-25 下午4:48:47 oms Exp $
 */
public class SessionRegistryStrategy extends SessionRegistryImpl {


    public SessionInformation getSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");

        return new SessionInformation(JedisPull.getObject(sessionId, Object.class), sessionId, new Date());
    }

    public void registerNewSession(String sessionId, Object principal) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        Assert.notNull(principal, "Principal required as per interface contract");
        
        JedisPull.setObject(sessionId, principal);
    }

    public void removeSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        JedisPull.delObject(sessionId);
    }

}
