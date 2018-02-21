package com.zjzmjr.core.common.biz;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * 类说明
 * 
 * @author zzhao E-mail:bovenjoy@163.com
 * @version createTime:2015-10-29 上午11:26:50 v.1
 */
public class JsonUtil {

    /**
     * 输出JSON字符串
     * 
     * @param object
     * @param response
     * @throws IOException
     */
    public static void printJSON(Object object, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        if (JSONUtils.isArray(object)) {
            out.print(JSONArray.fromObject(object));
        } else {
            out.print(JSONObject.fromObject(object));
        }
        out.flush();
        out.close();
    }
}
