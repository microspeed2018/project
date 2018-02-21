package com.zjzmjr.core.sms.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.script.ScriptException;

import org.apache.commons.lang.StringUtils;

public class MD5Utils {

    /**
     * 将指定的字符串用MD5加密 originstr 需要加密的字符串
     *
     * @param originstr
     *
     * @return
     */

    public static String ecodeByMD5(String originstr) {

        String result = null;

        char hexDigits[] = {// 用来将字节转换成 16 进制表示的字符

                            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        if (originstr != null) {

            try {

                // 返回实现指定摘要算法的 MessageDigest 对象

                MessageDigest md = MessageDigest.getInstance("MD5");

                // 使用utf-8编码将originstr字符串编码并保存到source字节数组

                byte[] source = originstr.getBytes("utf-8");

                // 使用指定的 byte 数组更新摘要

                md.update(source);

                // 通过执行诸如填充之类的最终操作完成哈希计算，结果是一个128位的长整数

                byte[] tmp = md.digest();

                // 用16进制数表示需要32位

                char[] str = new char[32];

                for (int i = 0, j = 0; i < 16; i++) {

                    // j表示转换结果中对应的字符位置

                    // 从第一个字节开始，对 MD5 的每一个字节

                    // 转换成 16 进制字符

                    byte b = tmp[i];

                    // 取字节中高 4 位的数字转换

                    // 无符号右移运算符>>> ，它总是在左边补0

                    // 0x代表它后面的是十六进制的数字. f转换成十进制就是15

                    str[j++] = hexDigits[b >>> 4 & 0xf];

                    // 取字节中低 4 位的数字转换

                    str[j++] = hexDigits[b & 0xf];

                }

                result = new String(str);// 结果转换成字符串用于返回

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static String selfMd5(String originVal, String key) {

        //先倒序，拼接key
        final String v1 = new StringBuilder(originVal).reverse().append(key).toString();

        //MD5计算
        String result = ecodeByMD5(v1);

        return result;
    }

    /**
     * MD加密
     *
     * @param value
     * @param charset
     * @return 返回byte数组
     */
    public static byte[] MD5(String value, String charset) {
        if (StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(value.getBytes(charset));
            byte[] encryption = md5.digest();

            return encryption;
        } catch (Exception ex) {
            ex.printStackTrace();
            return value.getBytes();
        }
    }
    
    public static void main(String[] args) throws ScriptException, IOException {
        
//        ZjzmjrPasswordStrategy passwordEncodeStrategy = new ZjzmjrPasswordStrategy();
        String  aa = "{\"data\":{\"idCard\":\"35042319960123456X\",\"phone\":\"13512345678\",\"name\":\"xxx\"},\"serviceCode\":\"9MYqTtEj\"}";
//        System.out.println(passwordEncodeStrategy.encodePassword(aa));
        System.out.println(ecodeByMD5(aa));

        /* String keyStr = "c3G0KRquVJFi9sWAY89k";//
        long timestamp = System.currentTimeMillis();

        System.out.println(keyStr);
        System.out.println(timestamp);
        final String s = MD5Utils.selfMd5(
//                "{\"data\":{\"idCard\":\"35042319960329101X\",\"phone\":\"13512345678\",\"name\":\"余启斌\"},\"serviceCode\":\"wHEkX4Za\"}"
          "{\"data\":{\"idCard\":\"35042319960123456X\",\"phone\":\"13512345678\",\"name\":\"xxx\"},\"serviceCode\":\"9MYqTtEj\"}"
                + timestamp, keyStr);
        System.out.println(s);*/
    }
}