package com.zjzmjr.core.enums.user;

/**
 * 登录设备枚举
 * 
 * @author oms
 * @version $Id: DeviceEnum.java, v 0.1 2016-6-14 上午9:34:33 oms Exp $
 */
public enum DeviceEnum
{
  HOME(
    Integer.valueOf(0), "HOME"), 

  WAP(
    Integer.valueOf(1), "WAP"), 

  ANDROID(
    Integer.valueOf(2), "ANDROID"), 

  IPHONE(
    Integer.valueOf(3), "IPHONE"), 

  ANDROID_STOCK(
    Integer.valueOf(4), "ANDROID_STOCK"), 

  IPHONE_STOCK(
    Integer.valueOf(5), "IPHONE_STOCK"), 

  SYSTEM(
    Integer.valueOf(9), "SYSTEM"), 
    
  IPAD(
    Integer.valueOf(10),"IPAD"),
  
  WINPHONE(
    Integer.valueOf(11),"WINPHONE"),
    
  WEIXIN(12 , "WEIXIN"),
  
  DINGDING(13, "DINGDING"),

  UNKNOW(
    Integer.valueOf(99), "UNKNOW");

  private Integer value;
  private String message;

  private DeviceEnum(Integer value, String message) { this.value = value;
    this.message = message; }

  public Integer getValue()
  {
    return this.value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public static DeviceEnum getByValue(Integer value) {
    if (value != null) {
      for (DeviceEnum enu : values()) {
        if (enu.value.equals(value)) {
          return enu;
        }
      }
    }
    return null;
  }

  public static DeviceEnum getByHumanName(String name)
  {
    if ("android".equalsIgnoreCase(name))
      return ANDROID;
    if ("iphone".equalsIgnoreCase(name))
      return IPHONE;
    if ("wap".equalsIgnoreCase(name))
      return WAP;
    if ("android_stock".equalsIgnoreCase(name))
      return ANDROID_STOCK;
    if ("iphone_stock".equalsIgnoreCase(name)) 
      return IPHONE_STOCK;
    if ("ipad".equalsIgnoreCase(name)) 
      return IPAD;
    if ("winphone".equalsIgnoreCase(name)) 
      return WINPHONE;
      
    return HOME;
  }

  public String toString() {
    return this.value + "|" + this.message;
  }
}