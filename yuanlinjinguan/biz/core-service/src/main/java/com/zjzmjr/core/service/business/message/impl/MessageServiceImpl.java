package com.zjzmjr.core.service.business.message.impl;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.message.MessageInfo;
import com.zjzmjr.core.model.message.MessageQuery;
import com.zjzmjr.core.service.business.message.MessageService;
import com.zjzmjr.core.service.mapper.dao.coredb.message.MessageInfoMapper;
import com.zjzmjr.core.service.mapper.dao.coredb.message.MessageMapper;

/**
 * 消息模块服务层实现类
 * 
 * @author lenovo
 * @version $Id: MessageServiceImpl.java, v 0.1 2016-7-13 下午2:22:58 lenovo Exp $
 */

@Service("messageService")
public class MessageServiceImpl implements MessageService {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    
    @Resource(name="messageMapper")
    private MessageMapper messageMapper;
    
    @Resource(name="messageInfoMapper")
    private MessageInfoMapper messageInfoMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.message.MessageService#getMessage(java.lang.Integer)
     */
    @Override
    public ResultPage<MessageInfo> getMessage(Integer userId) { 
        logger.debug("getMessage入参:{}",userId);
        ResultPage<MessageInfo> result=new ResultPage<MessageInfo>();
        if(Util.isNull(userId)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),userId.toString());
            logger.debug("getMessage出参:{}",result);
            return result; 
        }
        logger.info("查询此用户的消息记录：{}", userId);
        MessageQuery query = new MessageQuery();
        query.setUserId(userId);
        List<MessageInfo> list= messageMapper.getMessageByCondition(query);
        if(list==null||list.size()==0){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false); 
        }else{
            result.setSuccess(true);
            result.setList(list);
        }
        logger.debug("getMessage出参:{}",result);
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.message.MessageService#deleteMessageById(java.lang.Integer)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteMessageById(Integer id) {
        logger.debug("deleteMessageById入参:{}",id);
        ResultEntry<Integer> result=new ResultEntry<Integer>();
        if(Util.isNull(id)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),id);
            logger.debug("deleteMessageById出参:{}",result);
            return result; 
        }
        logger.info("删除消息：{}", id);
        int cnt=messageMapper.deleteByPrimaryKey(id);
        if (cnt > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":消息删除失败");
        }
        logger.debug("deleteMessageById出参:{}",result);
        return result;
    }
  
    /**
     * 
     * @see com.zjzmjr.core.service.business.message.MessageService#getMessageCount(com.zjzmjr.core.model.message.MessageQuery)
     */
    @Override
    public ResultEntry<Integer> getMessageCount(MessageQuery query) {
        logger.debug("getMessageCount入参:{}",query);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(query) || Util.isNull(query.getUserId()) || Util.isNull(query.getStatus())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),query);
            logger.debug("getMessageCount出参:{}",result);
            return result;
        }
        logger.info("获取消息条数：{}", query.toString());
        Integer cnt = messageMapper.getMessagecount(query);
        if (cnt != null) {
            result.setSuccess(true);
            result.setT(cnt);
        } else {
            result.setSuccess(false);
        }
        logger.debug("getMessageCount出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.message.MessageService#updateMesage(java.lang.Integer)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateMessage(Message message) {
        logger.debug("updateMessage入参:{}",message);
        ResultEntry<Integer> result=new ResultEntry<Integer>();
        if(Util.isNull(message)||Util.isNull(message.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),message);
            logger.debug("updateMessage出参:{}",result);
            return result;  
        }
        logger.info("更新消息状态：{}", message.toString());
        int cnt=messageMapper.updateMessage(message);
        if(cnt>0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":消息更新失败");
        }
        logger.debug("updateMessage出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.message.MessageService#getMessageById(java.lang.Integer)
     */
    @Override
    public ResultEntry<Message> getMessageById(Integer id) {
        logger.debug("getMessageById入参:{}",id);
        ResultEntry<Message> result=new ResultEntry<Message>();
        if(Util.isNull(id)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),id.toString());
            return result;    
        }
        logger.info("获取消息内容：{}", id);
        Message message=messageMapper.getMessageById(id);
        if(message==null){
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }else{
            result.setT(message);
            result.setSuccess(true);
        }
        logger.debug("getMessageById出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.message.MessageService#insertMessage()
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertMessage(Message message) {
        logger.debug("insertMessage入参:{}",message);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(message) || Util.isNull(message.getStatus())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),message.toString());
            logger.debug("insertMessage出参:{}",result);
            return result;
        }
        // 极端情况下，没有设置的
        if(Util.isNull(message.getCreateUserId())){
            message.setCreateUserId(message.getUserId());
        }
        logger.info("插入默认消息：{}", message.toString());
        int value = messageMapper.insertMessage(message);
        if (value > 0) {
            result.setSuccess(true);
            result.setT(value);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":消息插入失败");
        }
        logger.debug("insertMessage出参:{}",result);
        return result;
    }

//    /**
//     * 
//     * @see com.zjzmjr.core.service.business.message.MessageService#save(com.zjzmjr.core.model.message.Message)
//     */
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
//    public Result save(Message message) {
//        logger.debug("save入参:{}",message);
//        Result result = new Result();
//        if (Util.isNull(message)) {
//            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
//            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
//            result.setSuccess(false);
//            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),message.toString());
//            return result;
//        }
//        logger.info("save message{0}", message.toString());
//        Pattern p = Pattern.compile("^(?:\\(\\d+\\))?(?:\\[(\\d+)\\])?(?:\\{(.*?)\\})?(\\d{11})$");
//        if (!StringUtils.isBlank(message.getUserName())) {
//            List<String> numbers = parseNumbers(message.getUserName());
//            List<Message> messages = new ArrayList<Message>(numbers.size());
//            Message n = null;
//            for (String number : numbers) {
//                if (StringUtils.isNotEmpty(number)) {
//                    Matcher m = p.matcher(number);
//                    if (!m.find()) {
//                        result.setSuccess(false);
//                        result.setErrorMsg("号码[" + number + "]格式错误");
//                        return result;
//                    }
//                    n = new Message();
//                    BeanUtils.copyProperties(message, n);
//                    n.setUserName(number);
//
////                    n.setUserId(userService.getUserByName(number).getT().getId());
//                    messages.add(n);
//
//                }
//            }
//            for (Message mes : messages) {
//                messageMapper.insertMessage(mes);
//            }
//            result.setSuccess(true);
//        } else {
//            List<User> userList = userMapper.getUser();
//            List<Message> messages = new ArrayList<Message>(userList.size());
//            Message n = null;
//            for (int i = 0; i < userList.size(); i++) {
//                String number = userList.get(i).getUserCode();
//                n = new Message();
//                BeanUtils.copyProperties(message, n);
//                n.setUserName(number);
//                n.setUserId(userList.get(i).getId());
//                messages.add(n);
//            }
//            for (Message mes : messages) {
//                messageMapper.insertMessage(mes);
//            }
//            result.setSuccess(true);
//        }
//        logger.debug("save出参:{}",result);
//        return result;
//    }
    
//    private List<String> parseNumbers(String number) {
//        return Arrays.asList(org.apache.commons.lang.StringUtils.trimToEmpty(number).split("[\\s\\,\\;\\|，]"));
//      
//    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.message.MessageService#getMessageByCondition(com.zjzmjr.core.model.message.MessageQuery)
     */
    @Override
    public ResultPage<MessageInfo> getMessageByCondition(MessageQuery query) {
        logger.debug("getMessageByCondition入参:{}",query);
        ResultPage<MessageInfo> result=new ResultPage<MessageInfo>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),query.toString());
            return result;    
        }
        logger.info("获取消息内容：{}", query);
        int total = messageMapper.getMessagecount(query);
        if(total > 0){
            List<MessageInfo> list=messageMapper.getMessageByCondition(query);
            result.setSuccess(true);
            result.setList(list);
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false); 
        }
        if(Util.isNotNull(query.getPageBean())){
            result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        }
        logger.debug("getMessageByCondition出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.message.MessageService#insertBatch(java.util.List)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertBatch(List<Message> messageLst) {
        logger.debug("insertBatch 执行开始  入参:{}", messageLst);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(messageLst)) {
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.debug("insertBatch 执行结束  出参:{}", result);
            return result;
        }
        // 一次插入数据库的集合最大值
        int pointsDataLimit = 1500;
        int size = messageLst.size();
        int cnt = 0;
        if (size > pointsDataLimit) {
            // 若集合数量大于上限值，则计算总共需要拆分成几个完整的集合
            int part = size / pointsDataLimit;
            for (int i = 0; i < part; i++) {
                List<Message> listPage = messageLst.subList(0, pointsDataLimit);
                cnt += messageMapper.insertBatch(listPage);
                messageLst.subList(0, pointsDataLimit).clear();
            }
            // 将剩余的数据插入数据库
            if(!messageLst.isEmpty()){
                cnt += messageMapper.insertBatch(messageLst);
            }
        } else {
            // 若集合数量小于上限值则直接入库
            cnt = messageMapper.insertBatch(messageLst);
        }
        if (cnt == size) {
            result.setSuccess(true);
            result.setT(cnt);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
        }
        logger.debug("insertBatch 执行结束  出参:{}", result);
        return result;
    }

}
