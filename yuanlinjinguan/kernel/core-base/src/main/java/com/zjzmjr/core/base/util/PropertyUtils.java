package com.zjzmjr.core.base.util;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

/**
 * 属性文件读取, 默认的属性文件是auto-config.properties
 * 
 * @author oms
 * @version $Id: PropertyUtils.java, v 0.1 2016-6-16 上午10:13:03 oms Exp $
 */
public class PropertyUtils {

    private final String proper_file = "auto-config";
    
    private static volatile PropertyUtils instance = null;
    private ResourceBundle resource = null;
    /**
     * 单例模式
     */
    private PropertyUtils(){
        resource = ResourceBundle.getBundle(proper_file);
    }
    
    /**
     * 从指定的properties文件中读取属性的值
     * 
     * @param fileName 设置不要带文件后缀的文件名称
     * @param propKey 属性的key值
     * @return
     */
    public static String getPropertyFromFile(String fileName, String propKey){
        if(StringUtils.isBlank(fileName) || StringUtils.isBlank(propKey)){
            return StringUtils.EMPTY;
        }
        if(fileName.indexOf(".") > 0){
//            fileName = fileName.substring(0, fileName.indexOf("."));
        }else{
            fileName = fileName + ".properties";
        }
//        ResourceBundle bundle = ResourceBundle.getBundle(fileName);
//        return bundle.getString(propKey);
        
        Properties property = new Properties();
        try {
            property.load(PropertyUtils.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            return "";
        }
        return property.getProperty(propKey);
    }
    
    /**
     * 实例化
     * 
     * @return
     */
    public static PropertyUtils getInstance(){
        if(instance == null){
            synchronized (PropertyUtils.class) {
                if(instance == null){
                    instance = new PropertyUtils();
                }
            }
        }
        return instance;
    }
    
    /**
     * 获取属性的值
     * 
     * @param prop
     * @return
     */
    public String getProperty(String prop){
       return resource.getString(prop);
    }
}
