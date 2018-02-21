package com.zjzmjr.core.service.business.admin.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

import com.zjzmjr.core.cache.redis.JedisPull;
import com.zjzmjr.core.cache.redis.JedisUtil;
import com.zjzmjr.core.model.admin.Admin;

/**
 * 管理管理员的信息
 * 
 * @author liwen
 * @version $Id: AdminInfoHolder.java, v 0.1 2015-11-11 下午6:06:49 liwen Exp $
 */
public class AdminInfoHolder {

    private static AdminInfoHolderStrategy strategy;
    

    static {
        initialize();
    }

    private static void initialize() {
        if(strategy == null){
            strategy = new AdminInfoHolderStrategy();
        }
    }

    /**
     * Explicitly clears the context value from the current thread.
     */
    public static void clearContext() {
        strategy.clearContext();
    }

    /**
     * Obtain the current <code>List<Admin></code>.
     *
     * @return the List<Admin> (never <code>null</code>)
     */
    public static List<Admin> getContext() {
        return strategy.getContext();
    }
    
    /**
     * 根据管理员id获取管理员信息
     * 
     * @param userId
     * @return
     */
    public Admin getContext(Integer userId){
        return strategy.getContext(userId);
    }

    /**
     * 
     * 
     * @param context
     */
    public static void setContext(List<Admin> context) {
        strategy.setContext(context);
    }

    private static class AdminInfoHolderStrategy{

        private static final String contextHolder = "fnserver:contextHolderThreadLocal_{userId}";

        @SuppressWarnings("unchecked")
        public List<Admin> getContext(){
            List<Admin> adminLst = JedisPull.getObject(contextHolder, List.class);
            if(adminLst == null){
                adminLst = Collections.emptyList();
                setContext(adminLst);
            }
            return adminLst;
        }
        
        /**
         * 
         * 
         * @param userId
         * @return
         */
        public Admin getContext(Integer userId){
            String keyUser = contextHolder.replace("userId", String.valueOf(userId));
            Admin admin = JedisPull.getObject(keyUser, Admin.class);
            if(admin != null){
                return admin;
            }
            for(Admin tmpAdmin : getContext()){
                if(userId == tmpAdmin.getId()){
                    admin = tmpAdmin;
                    break;
                }
            }
            JedisPull.setObject(keyUser, admin, 60*60*2);
            return admin;
        }
        
        public void setContext(List<Admin> context){
            Assert.notNull(context, "Only non-null List<Admin> instances are permitted");
            JedisPull.setObject(contextHolder, context, 60*60*2);
        }
        
        public void clearContext() {
            JedisUtil.getInstance().del(contextHolder);
        }

    }
}
