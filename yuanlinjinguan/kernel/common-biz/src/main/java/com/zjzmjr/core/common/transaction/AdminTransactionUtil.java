package com.zjzmjr.core.common.transaction;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.core.api.transaction.IAdminTransactionFacade;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.SpringContextUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.user.DeviceEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.security.web.util.WebUtil;

/**
 * 
 * 管理员事物工具类
 * 
 * @author Administrator
 * @version $Id: AdminTransactionUtil.java, v 0.1 2016-10-11 上午11:09:48
 *          Administrator Exp $
 */

public class AdminTransactionUtil {

    private static final Logger logger = LoggerFactory.getLogger(AdminTransactionUtil.class);

    private static IAdminTransactionFacade adminTransactionFacade = SpringContextUtil.getBean("adminTransactionFacade");

    /**
     * 新增管理员事物
     * 
     * @param adminBusiness
     * @param request
     * @return
     */
    public static ResultEntry<AdminBusiness> insertAdminTransaction(AdminBusiness adminBusiness, HttpServletRequest request) {
        logger.debug("insertAdminTransaction 执行开始  入参:{}", adminBusiness);
        ResultEntry<AdminBusiness> result = new ResultEntry<AdminBusiness>();
        try {
            if (Util.isNull(adminBusiness.getResult())) {
                adminBusiness.setResult(HandleResultEnum.HANDLING.getValue());
            }
            if (Util.isNull(SpringSecurityUtil.getIntCompany())) {
                adminBusiness.setCompanyId(1);
            } else {
                adminBusiness.setCompanyId(SpringSecurityUtil.getIntCompany());
            }
            adminBusiness.setAccessIp(WebUtil.getUserIp(request));
            adminBusiness.setCreateTime(new Date());
            adminBusiness.setUpdateTime(adminBusiness.getCreateTime());
            if(Util.isNotNull(SpringSecurityUtil.getIntPrincipal())){
                adminBusiness.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                adminBusiness.setAdminId(SpringSecurityUtil.getIntPrincipal());
            }else{
                adminBusiness.setCreateUserId(0);
                adminBusiness.setAdminId(0); 
            }
            // 分析访问的设备信息
            analyseUserAgent(adminBusiness, request);
            result = adminTransactionFacade.insertAdminTransaction(adminBusiness);
            logger.debug("insertAdminTransaction 执行结束  出参:{}", result);
        } catch (Exception e) {
            logger.error("插入管理员事物出错：", e);
        }
        return result;
    }

   /**
    * 更新管理员事物
    * 
    * @param adminBusiness
    */
    public static void updateAdminTransaction(AdminBusiness adminBusiness) {
        logger.debug("updateAdminTransaction 执行开始  入参:{}", adminBusiness);
        try {
            adminBusiness.setUpdateTime(new Date());
            adminBusiness.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            adminTransactionFacade.updateAdminTransaction(adminBusiness);
            logger.debug("updateAdminTransaction 执行结束  出参:{}");
        } catch (Exception e) {
            logger.error("更新管理员事物出错：", e);
        }
    }

    /**
     * 分析访问的设备信息
     * 
     * @param adminBusiness
     * @param req
     */
    private static void analyseUserAgent(AdminBusiness adminBusiness, HttpServletRequest req){
        StringBuilder userAgent = new StringBuilder("[");
        userAgent.append(req.getHeader("User-Agent")).append("]");
        adminBusiness.setDeviceInfo(userAgent.toString());
        if(userAgent.indexOf("wechat") > 0){
            adminBusiness.setDeviceType(DeviceEnum.WEIXIN.getValue());
        }else if(userAgent.indexOf("dingding") > 0){
            adminBusiness.setDeviceType(DeviceEnum.DINGDING.getValue());
        }else if(userAgent.indexOf("iPhone") > 0){
            adminBusiness.setDeviceType(DeviceEnum.IPHONE.getValue());
        }else if(userAgent.indexOf("Android") > 0){
            adminBusiness.setDeviceType(DeviceEnum.ANDROID.getValue());
        }else{
            adminBusiness.setDeviceType(DeviceEnum.SYSTEM.getValue());
        }
    }
    
}
