package com.zjzmjr.admin.web.common.util;

import java.util.Map;

/**
 * 类说明
 * 
 * @author zzhao E-mail:bovenjoy@163.com
 * @version createTime:2015-10-27 上午11:39:33 v.1
 */
public class JstlUtil {

    public static String selectHtml(Map<String, String> map, String htmlText, String defaultText, String selected) {
        StringBuffer sb = new StringBuffer();
        sb.append(htmlText);
        sb.append(defaultText);
        for (String key : map.keySet()) {
            sb.append("<option value='");
            sb.append(key);
            if (key.equals(selected)) {
                sb.append("' selected='selected'>");
            } else {
                sb.append("'>");
            }
            sb.append(map.get(key));
            sb.append("</option>");
        }
        sb.append("</select>");
        return sb.toString();
    }

}
