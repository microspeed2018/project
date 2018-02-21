package com.zjzmjr.finance.web.common.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.zjzmjr.web.mvc.controller.BaseController;
import com.zjzmjr.core.api.message.IMessageFacade;
import com.zjzmjr.core.api.transaction.ITransactionFacade;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.SpringContextUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.message.MessageStatusEnum;
import com.zjzmjr.core.model.admin.Business;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.security.web.util.WebUtil;

@Controller
public class TransactionUtil extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TransactionUtil.class);

    private static ITransactionFacade transactionFacade = SpringContextUtil.getBean("transactionFacade");
    
    private static IMessageFacade messageFacade = SpringContextUtil.getBean("messageFacade");

    /**
     * 新增事物
     * 
     * @param business
     * @param request
     * @return
     */
    public static ResultEntry<Business> insertTransaction(Business business, HttpServletRequest request) {
        ResultEntry<Business> result = new ResultEntry<Business>();
        // 默认处理中状态
        try {
            if(Util.isNull(business.getResult())){
                business.setResult(HandleResultEnum.HANDLING.getValue());
            }
            business.setAccessIp(WebUtil.getUserIp(request));
            business.setCreateTime(new Date());
            business.setUpdateTime(business.getCreateTime());
            if(Util.isNull(SpringSecurityUtil.getIntPrincipal())){
                business.setCreateUserId(0);
                business.setUserId(0);
            }else{
                business.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                business.setUserId(SpringSecurityUtil.getIntPrincipal());
            }
            result = transactionFacade.insertTransaction(business);
        } catch (Exception ex) {
            logger.error("插入事物出错：", ex);
        }
        return result;
    }

    /**
     * 更新事物结果状态
     * 
     * @param business
     */
    public static void updateTransaction(Business business) {
        try {
            business.setUpdateTime(new Date());
            business.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            transactionFacade.updateTransaction(business);
        } catch (Exception ex) {
            logger.error("更新事物出错：", ex);
        }
    }
    
    /**
     * 根据所设定的关键值更新事务表
     * 
     * @param business
     */
    public static void updateTransactionByExtend3(Business business) {
        try {
            business.setUpdateTime(new Date());
            business.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            transactionFacade.updateTransactionByExtend3(business);
        } catch (Exception ex) {
            logger.error("更新事物出错：", ex);
        }
    }
    
    /**
     * 插入站内消息
     */
    public static void insertMessage(Message message) {
        try {
            message.setTitle(message.getTitle());
            message.setUserId(SpringSecurityUtil.getIntPrincipal());
            message.setStatus(MessageStatusEnum.UNREAD.getValue());
            message.setCreateTime(new Date());
            message.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            messageFacade.insertMessage(message);
        } catch (Exception ex) {
            logger.error("插入站内消息出错：", ex);
        }
    }

}
