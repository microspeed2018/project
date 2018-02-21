package com.zjzmjr.core.base.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类里面的变量处理的工具类
 * 
 * @author oms
 * @version $Id: PropertiesValueUtil.java, v 0.1 2016-11-10 下午2:50:51 oms Exp $
 * @param <T>
 */
public class PropertiesValueUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesValueUtil.class);

    /**
     * 设置类里面所有的BigDecimal类型的变量，给予初期值
     * 
     * @param bean
     */
    @SuppressWarnings("rawtypes")
    public static void setBigDecimalValue(Object bean) {
        Class clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Class typeClazz = null;
        try {
            for (Field field : fields) {
                typeClazz = field.getType();
                if ("java.math.BigDecimal".equals(typeClazz.getName())) {
                    field.setAccessible(true);
                    field.set(bean, BigDecimal.ZERO);
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("", e);
        } catch (IllegalAccessException e) {
            logger.error("", e);
        }
    }
    
    /**
     * 暂时没有写完全的类方法
     * 
     * @param srcClazz
     * @param destClazz
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T, V> void copyBeans(T srcClazz, V destClazz) {
        Class<T> copySrc = (Class<T>) srcClazz.getClass();
        Class<V> destSrc = (Class<V>) destClazz.getClass();
        Field[] srcFields = copySrc.getDeclaredFields();
        Field[] destFields = destSrc.getDeclaredFields();
        Class typeClazz = null;
        try {
            for (Field field : srcFields) {
                typeClazz = field.getType();
                for (Field destField : destFields) {
                    if(field.getName().equals(destField.getName())){
                        // 暂未写完全
                        if ("java.math.BigDecimal".equals(typeClazz.getName())) {
                            field.setAccessible(true);
                            field.set(destClazz, BigDecimal.ZERO);
                        }
                    }
                }
                
            }
        } catch (IllegalArgumentException e) {
            logger.error("", e);
        } catch (IllegalAccessException e) {
            logger.error("", e);
        }
    }
    
}
