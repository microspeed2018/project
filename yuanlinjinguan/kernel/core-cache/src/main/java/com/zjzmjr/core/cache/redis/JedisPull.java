/**
 * zjzmjr.com Inc.
 * Copyright (c) 2013-2016 All Rights Reserved.
 */
package com.zjzmjr.core.cache.redis;

import java.util.Collection;
import java.util.Map;

import redis.clients.util.SafeEncoder;
/**
 * 
 * @author Administrator
 * @version $Id: JedisPull.java, v 0.1 2015-6-26 下午4:50:02 Administrator Exp $
 */
public class JedisPull {
	
    /**
     * 将对象存入redis
     * 
     * @param key
     * @param object
     * @return
     */
    public static String setObject(String key, Object object) {
        byte[] bytes = SerializeUtil.serialize(object);
        return JedisUtil.getInstance().set(key, bytes);
    }

    /**
     * 将对象存入redis
     * 
     * @param key
     * @param object
     * @return
     */
    public static String setObject(String key, Object object, int expireTime) {
        byte[] bytes = SerializeUtil.serialize(object);
        byte[] keys = SerializeUtil.serialize(key);
        return JedisUtil.getInstance().setEx(keys, expireTime, bytes);
    }
    
    /**
     * 删除对象
     * 
     * @param key
     */
    public static void delObject(String key){
        JedisUtil.getInstance().del(SafeEncoder.encode(key));
        JedisUtil.getInstance().del(SerializeUtil.serialize(key));
    }
    
    /**
     * 取redis值，转为对象
     * 
     * @param key
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObject(String key,Class<T> clazz) {
        byte[] bytes = JedisUtil.getInstance().get(SafeEncoder.encode(key));
        if (!isNull(bytes)) {
            T t = (T) SerializeUtil.unserialize(bytes);
            return t;
        }else{
            bytes = JedisUtil.getInstance().get(SerializeUtil.serialize(key));
            if (!isNull(bytes)) {
                T t = (T) SerializeUtil.unserialize(bytes);
                return t;
            }
        }
        return null;
    }

    /**
     * 将map从redis读取
     * 
     * @param key
     * @return
     */
    public static Map<String, String> getMap(String key) {
        return JedisUtil.getInstance().hgetAll(key);
    }
    
    //判断对象为空
    @SuppressWarnings("rawtypes")
    public static boolean isNull(Object argObj) {
        if (argObj == null) {
            return true;
        }

        if (argObj instanceof String) {

            if (((String) argObj).trim().equals("")
                    || ((String) argObj).trim().equals(" ")
                    || ((String) argObj).trim().equals("null")) {
                return true;
            }
        }

        if (argObj instanceof Collection) {

            if (((Collection) argObj).size() == 0) {
                return true;
            }
        }

        if (argObj instanceof Map) {

            if (((Map) argObj).size() == 0) {
                return true;
            }
        }

        return false;
    }
    
    
}
