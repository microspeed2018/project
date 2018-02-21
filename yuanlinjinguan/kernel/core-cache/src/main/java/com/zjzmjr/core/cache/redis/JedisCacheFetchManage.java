/**
 * zjzmjr.com Inc.
 * Copyright (c) 2013-2016 All Rights Reserved.
 */
package com.zjzmjr.core.cache.redis;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存管理类
 * 
 * @author liwen
 * @version $Id: JedisCacheFetchManage.java, v 0.1 2015-7-6 下午2:46:19 liwen Exp
 *          $
 */
public class JedisCacheFetchManage {

    /**  */
    private static final Logger logger = LoggerFactory.getLogger(JedisCacheFetchManage.class);

    /** 同步原子变量 */
    private static AtomicInteger atomic = new AtomicInteger(0);

    private static final int unLock = 0;

    private static final int toLock = 1;
    /**  */
    private static final Object cacheKey = new Object();
    
    /**
     * 将方案风控资产数据放入缓存中
     */
    public static void putSchemeRiskToJedis(String key, Object cacheValue) {
        synchronized (atomic) {
            if (atomic.compareAndSet(toLock, unLock)) {
                try {
                    atomic.notifyAll();
                } catch (Exception ex) {
                    logger.error("线程【{}】将缓存数据放入Jedis中出错", Thread.currentThread().getName(), ex);
                }
            }
            JedisPull.setObject(key, cacheValue);
        }
    }

    /**
     * 从缓存中把方案风控资产数据取出来
     * 
     * @return
     */
    public static <T> T getSchemeRiskFromJedis(String key, Class<T> clazz) {
        synchronized (atomic) {
            if (!atomic.compareAndSet(unLock, toLock)) {
                try {
                    atomic.wait();
                } catch (InterruptedException ex) {
                    logger.error("线程【{}】从Jedis中获取缓存数据出错", Thread.currentThread().getName(), ex);
                }
            }
            T cacheValue = JedisPull.getObject(key, clazz);
            if (cacheValue == null || (cacheValue instanceof Collection && ((Collection<?>)cacheValue).isEmpty())) {
                atomic.set(unLock);
                atomic.notifyAll();
            }
            return cacheValue;
        }
    }

    /**
     * 将数据放入缓存中
     */
    public static void putCacheToJedis(String key, Object cacheValue) {
//        cacheKey = key;
        synchronized (cacheKey) {
            JedisPull.setObject(key, cacheValue);
        }
    }

    /**
     * 从缓存中把数据取出来
     * 
     * @return
     */
    public static <T> T getCacheFromJedis(String key, Class<T> clazz) {
//        cacheKey = key;
        synchronized (cacheKey) {
            T cacheValue = JedisPull.getObject(key, clazz);
            return cacheValue;
        }
    }

}
