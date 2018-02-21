package com.zjzmjr.core.base.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * javaBean与map类型相互转换 的工具类
 * 
 * @author oms
 * @version $Id: BeanMapConvertUtil.java, v 0.1 2017-4-14 下午7:38:18 oms Exp $
 */
public class BeanMapConvertUtil {

    /**
     * 把Map键值对转化为javaBean对象
     * 
     * @param type
     * @param map
     * @return
     * @throws Exception
     */
    public static Object transforMapToBean(Class<? extends Object> type, Map<String, String> map) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象
        // 给 JavaBean对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                try {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(obj, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * 把javaBean对象转换为Map键值对
     * 
     * @param bean
     * @return
     * @throws Exception
     */
    public static Map<String, String> transforBeanToMap(Object bean) throws Exception {
        Class<? extends Object> type = bean.getClass();
        Map<String, String> returnMap = new HashMap<String, String>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result.toString());
                }
            }
        }
        return returnMap;
    }

}
