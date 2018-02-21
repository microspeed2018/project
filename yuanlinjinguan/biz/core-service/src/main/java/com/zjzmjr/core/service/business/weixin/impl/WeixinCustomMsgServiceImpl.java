package com.zjzmjr.core.service.business.weixin.impl;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.common.util.DateUtil;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.common.biz.weixin.XMLUtil;
import com.zjzmjr.core.model.weixin.wechat.menu.ImageMsgPush;
import com.zjzmjr.core.model.weixin.wechat.menu.MsgReply;
import com.zjzmjr.core.model.weixin.wechat.menu.VideoMsgPush;
import com.zjzmjr.core.service.business.weixin.WeixinCustomMsgService;

/**
 * 接收用户发往微信公众号的消息
 * 
 * @author oms
 * @version $Id: WeixinCustomMsgServiceImpl.java, v 0.1 2017-3-22 下午7:33:22 oms Exp $
 */
@Service("weixinCustomMsgService")
public class WeixinCustomMsgServiceImpl implements WeixinCustomMsgService {

    private static final Logger logger = LoggerFactory.getLogger(WeixinCustomMsgServiceImpl.class);
    
    private static final String MSG_RV_FLG = "上传";
    
    /**
     * 微信上传文件的rediskey
     */
    private final String DECIDER_UPLOAD_INFO = "DECIDER_UPLOAD_INFO_JEDIS_{FROM_USER}";
        
    /**
     * 
     * @see com.zjzmjr.loan.service.business.weixin.WeixinCustomMsgService#receiveCustomTextMessage(java.lang.String)
     */
    @Override
    public synchronized ResultEntry<String> receiveCustomTextMessage(String message) {
        logger.debug("receiveCustomTextMessage 执行开始  入参:{}", message);
        ResultEntry<String> result = new ResultEntry<String>();
//        CommonMsgPush textMsg = XMLUtil.convertToObj(CommonMsgPush.class, message);

        result.setSuccess(false);
        result.setT("");
//        DeciderUploadInfo uploadInfo = JedisPull.getObject(getJedisKey(textMsg.getFromUserName()), DeciderUploadInfo.class);
//        if (uploadInfo == null) {
//            uploadInfo = new DeciderUploadInfo();
//            uploadInfo.setPage(1);
//            uploadInfo.setStep(1);
//        }
//        if (textMsg.getContent().trim().startsWith(MSG_RV_FLG) || 
//                ("1".equals(textMsg.getContent()) && StringUtil.stringToInteger("1").equals(uploadInfo.getStep()))) {
//            // 显示用户列表
//            result = dispSalesmanInfo(uploadInfo, textMsg);
//        } else {
//            result = interactWithWeiXin(uploadInfo, textMsg);
//        }
//        // 保存记录到redis缓存中
//        JedisPull.setObject(getJedisKey(textMsg.getFromUserName()), uploadInfo, 60*30);
//        logger.debug("receiveCustomTextMessage 执行结束  出参:{}", result);
        return result;
    }
    
    /**
     * 当判断是微信上传的时候，与微信交互的过程
     * 
     * @param textMsg
     * @return
     *//*
    private ResultEntry<String> interactWithWeiXin(DeciderUploadInfo uploadInfo, CommonMsgPush textMsg){
        logger.debug("interactWithWeiXin 执行开始  入参:{}", textMsg);
        ResultEntry<String> result = new ResultEntry<String>();
//        StringBuilder builder = null;
        if(uploadInfo == null || uploadInfo.getStep() == null){
            // 当没有发起上传命令时，直接退出去
            result.setSuccess(false);
            result.setT("");
            return result;
        }
        if("0".equals(textMsg.getContent())){
            // 强行终止
            uploadInfo.setStep(99);
        }
        List<LoanApplyInfo> userLst = null;
        LoanApplyInfo applyInfo = null;
        User user = null;
        switch (uploadInfo.getStep()) {
        case 88:
            userLst = uploadInfo.getLoanApplyInfo();
            applyInfo = userLst.get(Integer.parseInt(textMsg.getContent()) - 1);
            user = applyInfo.getUser();
            result = dispLoanApplierInfo(uploadInfo, user, textMsg);
            break;
        case 2:
            userLst = uploadInfo.getLoanApplyInfo();
            int cnt = Integer.parseInt(textMsg.getContent());
            if (cnt > userLst.size()) {
                // 显示用户列表
                uploadInfo.setPage(uploadInfo.getPage() + 1);
                result = dispLoanApplierInfo(uploadInfo, uploadInfo.getFromUser(), textMsg);
            } else {
                applyInfo = userLst.get(cnt - 1);
                user = applyInfo.getUser();
                if (Integer.parseInt(textMsg.getContent()) == user.getInviter()) {
                    uploadInfo.setName(user.getName());
                    uploadInfo.setIdentityNo(user.getIdentityNo());
                    uploadInfo.setLoanApplyId(applyInfo.getId());
                }
                // // 资料上传类型获取
                // result.setT(dispUploadFileType(uploadInfo,
                // textMsg.getFromUserName(), textMsg.getToUserName()));
                // uploadInfo.setStep(3);
                // 贷款上传资料名称
                uploadInfo.setStep(4);
                // 先展示优先的上传文件
                uploadInfo.setDispFlg(1);
                result.setT(dispUploadLoanFileType(uploadInfo, textMsg.getFromUserName(), textMsg.getToUserName()));
                // 清空用户信息
                uploadInfo.setLoanApplyInfo(null);
                result.setSuccess(true);
            }
            break;
        case 3:
            // 贷款上传资料名称
            uploadInfo.setType(Integer.parseInt(textMsg.getContent()));
            uploadInfo.setStep(4);
            result.setT(dispUploadLoanFileType(uploadInfo, textMsg.getFromUserName(), textMsg.getToUserName()));
            result.setSuccess(true);
            break;
        case 4:
            uploadInfo.setIsSendMS(false);
            result = dispAskUploadFile(uploadInfo, textMsg);
            result.setSuccess(true);
            break;
        case 5:
            uploadInfo.setIsSendMS(false);
//            if ("2".equalsIgnoreCase(textMsg.getContent().trim())) {
//                builder = new StringBuilder();
//                builder.append("您是否需要继续上传该客户资料\r\n");
//                builder.append("1.是\r\n");
//                builder.append("2.否\r\n");
//                uploadInfo.setStep(6);
//                result.setT(sendTextMsg(builder.toString(), textMsg.getFromUserName(), textMsg.getToUserName()));
//            } else {
                result = dispAskUploadFile(uploadInfo, textMsg);
//            }
            result.setSuccess(true);
            break;
        case 6:
            uploadInfo.setIsSendMS(false);
            if ("1".equalsIgnoreCase(textMsg.getContent().trim())) {
                // 贷款上传资料名称
                uploadInfo.setStep(4);
                result.setT(dispUploadLoanFileType(uploadInfo, textMsg.getFromUserName(), textMsg.getToUserName()));
                result.setSuccess(true);
                break;
            }
        default:
            uploadInfo.setStep(1);
            uploadInfo.setPage(1);
            result.setT(sendTextMsg(initDispMessage(), textMsg.getFromUserName(), textMsg.getToUserName()));
            result.setSuccess(true);
            break;
        }
        logger.debug("interactWithWeiXin 执行结束  出参:{}", result);
        return result;
    }*/
    
    /**
     * 
     * @see com.zjzmjr.loan.service.business.weixin.WeixinCustomMsgService#receiveCustomVideoMessage(java.lang.String)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<String> receiveCustomVideoMessage(String message, String[] savePath) throws Exception {
        logger.debug("receiveCustomVideoMessage 执行开始  入参:{}{}", message, savePath);
        VideoMsgPush videoMsg = XMLUtil.convertToObj(VideoMsgPush.class, message);
        ResultEntry<String> result = null; // receiveCustomUploadMsg(videoMsg.getFromUserName(), videoMsg.getMediaId(), videoMsg.getToUserName(), savePath, ".mp4");
        logger.debug("receiveCustomVideoMessage 执行结束  出参:{}", result);
        return result;
    }
    
    /**
     * 
     * @throws Exception 
     * @see com.zjzmjr.loan.service.business.weixin.WeixinCustomMsgService#receiveCustomImageMessage(java.lang.String)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<String> receiveCustomImageMessage(String message, String[] savePath) throws Exception {
        logger.debug("receiveCustomImageMessage 执行开始  入参:{}{}", message, savePath);
        ImageMsgPush imageMsg = XMLUtil.convertToObj(ImageMsgPush.class, message);
        ResultEntry<String> result = null; //receiveCustomUploadMsg(imageMsg.getFromUserName(), imageMsg.getMediaId(), imageMsg.getToUserName(), savePath, ".jpg");
        logger.debug("receiveCustomImageMessage 执行结束  出参:{}", result);
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.loan.service.business.weixin.WeixinCustomMsgService#receiveCustomPositionMessage(java.lang.String)
     */
    @Override
    public ResultEntry<String> receiveCustomPositionMessage(String message) {/*
        logger.debug("receiveCustomPositionMessage 执行开始  入参:{}", message);
        ResultEntry<String> result = new ResultEntry<String>();
        LocationEventPush eventPush = XMLUtil.convertToObj(LocationEventPush.class, message);
        DeciderUploadInfo uploadInfo = JedisPull.getObject(getJedisKey(eventPush.getFromUserName()), DeciderUploadInfo.class);
        if (uploadInfo == null) {
            uploadInfo = new DeciderUploadInfo();
            uploadInfo.setPage(1);
            uploadInfo.setStep(1);
            uploadInfo.setIsSendMS(false);
            result.setT(sendTextMsg(initDispMessage(), eventPush.getFromUserName(), eventPush.getToUserName()));
            result.setSuccess(true);
        }
        uploadInfo.setLatitude(eventPush.getLatitude());
        uploadInfo.setLongitude(eventPush.getLongitude());
        uploadInfo.setPrecision(eventPush.getPrecision());
        uploadInfo.setFromOpenId(eventPush.getFromUserName());
        // 保存记录到redis缓存中
        JedisPull.setObject(getJedisKey(eventPush.getFromUserName()), uploadInfo, 60*30);
        logger.debug("receiveCustomPositionMessage 执行结束  出参:{}", result);
        return result;
    */
        return new ResultEntry<String>();
    }
    
    /**
     * 初始进入微信公众号提示的消息
     * 
     * @return
     */
    private String initDispMessage(){
        StringBuilder builder = new StringBuilder();
        builder.append("请选择您需要的服务：").append("\r\n");
        builder.append("1.上传资料").append("\r\n");
        builder.append("2.打包上传(敬请期待)").append("\r\n");
        builder.append("3.智能客服(敬请期待)");
        return builder.toString();
    }
    
//    /**
//     * 根据微信号查询出业务员的相关信息
//     * 
//     * @param uploadInfo
//     * @param textMsg
//     * @return
//     */
//    private ResultEntry<String> dispSalesmanInfo(DeciderUploadInfo uploadInfo, CommonMsgPush textMsg){
//        ResultEntry<String> result = new ResultEntry<String>();
//        ResultRecord<User> userRst = userService.getUserByOpenId(textMsg.getFromUserName());
//        if (userRst.isSuccess()) {
//            if (userRst.getList().size() > 1) {
//                User userInfo = null;
//                List<LoanApplyInfo> userLst = new ArrayList<LoanApplyInfo>();
//                LoanApplyInfo applyInfo = null;
//                for (int cnt = 0; cnt < userRst.getList().size(); cnt++) {
//                    userInfo = userRst.getList().get(cnt);
//                    applyInfo = new LoanApplyInfo();
//                    userInfo.setInviter(cnt + 1);
//                    applyInfo.setUser(userInfo);
//                    userLst.add(applyInfo);
//                }
//                // 保存用户信息
//                uploadInfo.setLoanApplyInfo(userLst);
//                uploadInfo.setStep(88);
//                result.setT(dispReplyUserMsg("请选择您的姓名:\r\n", textMsg.getFromUserName(), textMsg.getToUserName(), userLst));
//            } else {
//                result = dispLoanApplierInfo(uploadInfo, userRst.getList().get(0), textMsg);
//            }
//        }
//        return result;
//    }
    
    /**
     * 显示业务员代理的申请贷款的用户列表
     * 
     * @return
     *//*
    private ResultEntry<String> dispLoanApplierInfo(DeciderUploadInfo uploadInfo, User salesman, CommonMsgPush textMsg){
        ResultEntry<String> result = new ResultEntry<String>();
        uploadInfo.setFromOpenId(textMsg.getFromUserName());
        uploadInfo.setFromUserId(salesman.getId());
        uploadInfo.setFromUser(salesman);
        if(UserTypeEnum.SALESMAN.getValue().equals(salesman.getAuthorityId()) ||
                UserTypeEnum.WORKMAN.getValue().equals(salesman.getAuthorityId())){
            // 查询该业务员下的所有用户
            LoanApplyQuery query = new LoanApplyQuery();
            query.setAgentId(salesman.getId());
            query.setExceStatusLst(Arrays.asList(LoanApplyStatusEnum.TERMINATION.getValue(), LoanApplyStatusEnum.END.getValue()));
            query.setPageBean(new PageBean(GenerateConstants.PAGE_SIZE, uploadInfo.getPage()));
            ResultPage<LoanApplyInfo> loanRst = null; // loanApplyService.getLoanApplyByCondition(query);
            if(loanRst.isSuccess()){
                User userInfo = null;
                List<LoanApplyInfo> userLst = new ArrayList<LoanApplyInfo>();
                LoanApplyInfo applyInfo = null;
                for (int cnt = 0; cnt < loanRst.getList().size(); cnt++) {
                    applyInfo = loanRst.getList().get(cnt);
                    if (uploadInfo.getFromUserId().equals(applyInfo.getAgentId())) {
                        userInfo = new User();
                        userInfo.setInviter(cnt + 1);
                        userInfo.setName(applyInfo.getUser().getName());
                        userInfo.setIdentityNo(applyInfo.getUser().getIdentityNo());
                        applyInfo.setUser(userInfo);
                        userLst.add(applyInfo);
                    }
                }
                // 保存用户信息
                if(loanRst.getList().size() > 0){
                    uploadInfo.setLoanApplyInfo(userLst);
                }
                uploadInfo.setStep(2);
                result.setT(dispReplyUserMsg("请选择您的客户:\r\n", textMsg.getFromUserName(), textMsg.getToUserName(), userLst));
            }else{
                result.setT(sendTextMsg("请赶快去代理用户申请贷款吧", textMsg.getFromUserName(), textMsg.getToUserName()));
            }
            result.setSuccess(true);
        } else {
            result.setT(sendTextMsg("非业务员不能上传，先成为中茗的业务员吧！", textMsg.getFromUserName(), textMsg.getToUserName()));
            result.setSuccess(true);
        }
        
        return result;
    }*/
    
//    /**
//     * 客户信息封装成微信消息
//     * 
//     * @param fromUserName
//     * @param toUserName
//     * @param userLst
//     * @return
//     */
//    private String dispReplyUserMsg(String title, String fromUserName, String toUserName, List<LoanApplyInfo> userLst){
//        logger.debug("dispReplyUserMsg 执行开始  入参:{}{}{}", fromUserName, toUserName, userLst);
//        StringBuilder builder = new StringBuilder();
//        if(userLst == null || userLst.isEmpty()){
//            builder.append("请输入正确的用户编码");
//        }else{
//            builder.append(title);
//        }
//        User user = null;
//        int cnt = 0;
//        for (; cnt < userLst.size(); cnt++) {
//            user = userLst.get(cnt).getUser();
//            builder.append(user.getInviter()).append(". ").append(user.getName())
//            .append(" 申请时间:").append(userLst.get(cnt).getRegisterTime()).append("\r\n");
//            builder.append("身份证号:").append(MaskUtil.maskIdentity(user.getIdentityNo())).append("\r\n");
//        }
//        if(userLst.size() > GenerateConstants.PAGE_SIZE){
//            builder.append(cnt+1).append(". 查看更多...");
//        }
//        logger.debug("dispReplyUserMsg 执行结束  出参:{}", builder);
//        return sendTextMsg(builder.toString(), fromUserName, toUserName);
//    }
    
    /**
     * 要求用户上传某个资料文件时的应答
     * 
     * @param uploadInfo
     * @param textMsg
     * @return
     *//*
    private ResultEntry<String> dispAskUploadFile(DeciderUploadInfo uploadInfo, CommonMsgPush textMsg){
        ResultEntry<String> result = new ResultEntry<String>();
        uploadInfo.setKinds(Integer.parseInt(textMsg.getContent()));
        uploadInfo.setStep(5);
        LoanBasicFile loanfile = new LoanBasicFile();
        loanfile.setCustId(uploadInfo.getKinds());
        int index = Collections.binarySearch(uploadInfo.getLoanFile(), loanfile, new Comparator<LoanBasicFile>() {
            @Override
            public int compare(LoanBasicFile src, LoanBasicFile dest) {
                return src.getCustId().compareTo(dest.getCustId());
            }
        });
        if(index >= 0 ){
            loanfile = uploadInfo.getLoanFile().get(index);
            uploadInfo.setFileName(loanfile.getFileName());
            uploadInfo.setLoanBasicId(loanfile.getId());
            uploadInfo.setType(loanfile.getUploadType());
            StringBuilder builder = new StringBuilder();
            builder.append("请您上传【").append(loanfile.getFileName()).append(loanfile.getMemo()).append("】:\r\n");
            result.setT(sendTextMsg(builder.toString(), textMsg.getFromUserName(), textMsg.getToUserName()));
        }else{
            // 先展示优先的上传文件
            uploadInfo.setDispFlg(2);
            // 回到上一步
            uploadInfo.setStep(4);
            result.setT(dispUploadLoanFileType(uploadInfo, textMsg.getFromUserName(), textMsg.getToUserName()));
//            result.setT(sendTextMsg("请选择正确的文件类型序号", textMsg.getFromUserName(), textMsg.getToUserName()));
        }
        result.setSuccess(true);
        return result;
    }*/
    
    /**
     * 上传文件的类型
     * 
     * @param fromUserName
     * @param toUserName
     * @return
     *//*
    @Deprecated
    public String dispUploadFileType(DeciderUploadInfo uploadInfo, String fromUserName, String toUserName){
        logger.debug("dispUploadFileType 执行开始  入参:{}{}{}", uploadInfo, fromUserName, toUserName);
        StringBuilder builder = new StringBuilder();
        BasicQuery query = new BasicQuery();
        // 资料上传类型
        query.setCategoryId(20);
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<Basic> basicRst = null; //basicService.getBasic(query);
        builder.append("请选择您要上传的类型:\r\n");
//        builder.append("0.所有\r\n");
        if(basicRst.isSuccess() && !basicRst.getList().isEmpty()){
            for(int cnt = 0;cnt < basicRst.getList().size();cnt++){
                basicRst.getList().get(cnt).setAttributeId(cnt + 1);
                builder.append(basicRst.getList().get(cnt).getAttributeId()).append(".")
                .append(basicRst.getList().get(cnt).getAttributeName()).append("\r\n");
            }
        }
        // 保存基础数据
        uploadInfo.setBasicInfo(basicRst.getList());
        logger.debug("dispUploadFileType 执行结束  出参:{}", builder);
        return sendTextMsg(builder.toString(), fromUserName, toUserName);
    }
    
    *//**
     * 贷款基础文件
     * 
     * @return
     *//*
    private String dispUploadLoanFileType(DeciderUploadInfo uploadInfo, String fromUserName, String toUserName){
        logger.debug("dispUploadLoanFileType 执行开始  入参:{}{}{}", uploadInfo, fromUserName, toUserName);
        
        // 资料上传类型
        BasicQuery bQuery = new BasicQuery();
        bQuery.setCategoryId(19);
        bQuery.setAttributeIdLst(Arrays.asList(3,4,5));
        bQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<Basic> basicRst = null; // basicService.getBasic(bQuery);
        
        StringBuilder builder = new StringBuilder();
        Basic basic = getBasicAttributeId(uploadInfo.getBasicInfo(), uploadInfo.getType());
        LoanBasicFileQuery query = new LoanBasicFileQuery();
        if (basic != null) {
            query.setUploadType(basic.getAttributeId());
        }
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        query.setExeFileType(Arrays.asList(1,2));
        // 文件的显示分组
        query.setDispFlg(uploadInfo.getDispFlg());
        ResultPage<LoanBasicFile> result = null; // loanBasicFileService.getLoanBasicFileList(query);
        if(result.isSuccess()){
            List<LoanBasicFile> basicFileLst = result.getList();
            LoanBasicFile basicFile = null;
            int cnt = 0;
            builder.append(cnt).append(".退出该上传(Exit)").append("\r\n");
            builder.append("请选择您要上传的文件类型:\r\n");
            for(;cnt < basicFileLst.size();cnt++){
                basicFile = basicFileLst.get(cnt);
                basicFile.setCustId(cnt+1);
                builder.append(basicFile.getCustId()).append(".").append(basicFile.getFileName());
                basic = getBasicAttributeId(basicRst.getList(), basicFile.getFileType());
                basicFile.setMemo("("+basic.getAttributeName()+")");
                builder.append(basicFile.getMemo()).append("\r\n");
            }
            if(uploadInfo.getDispFlg().intValue() == 1){
                // 当是第一次请求上传文件时，加上这一句话
                builder.append(cnt+1).append(".").append("查看更多...");
            }
            // 保存上传文件类型
            uploadInfo.setLoanFile(basicFileLst);
        } else {
            builder.append("上传的文件类型数据不存在");
        }
        logger.debug("dispUploadLoanFileType 执行结束  出参:{}", builder);
        return sendTextMsg(builder.toString(), fromUserName, toUserName);
    }

    *//**
     * 在基础数据表中的集合中查找指定的基础数据
     * 
     * @param basicLst
     * @param attributeId
     * @return
     *//*
    private Basic getBasicAttributeId(List<Basic> basicLst, Integer attributeId){
        if(basicLst == null || attributeId == null || basicLst.isEmpty()){
            return null;
        }
        Basic basic = new Basic();
        basic.setAttributeId(attributeId);
        int index = Collections.binarySearch(basicLst, basic, new Comparator<Basic>() {
            @Override
            public int compare(Basic src, Basic dest) {
                return src.getAttributeId().compareTo(dest.getAttributeId());
            }
        });
        if (index >= 0) {
            basic = basicLst.get(index);
        } else {
            basic = null;
        }
        
        return basic;
    }*/
    
    /**
     * 向客户端返回文本消息
     * 
     * @param content
     * @param fromUserName
     * @param toUserName
     * @return
     */
    private String sendTextMsg(String content, String fromUserName, String toUserName){
        logger.debug("sendTextMsg 执行开始  入参:{}{}{}", content, fromUserName, toUserName);
        MsgReply retMsg = new MsgReply();
        retMsg.setContent(content);
        retMsg.setCreateTime(System.currentTimeMillis());
        retMsg.setFromUserName(toUserName);
        retMsg.setMsgType("text");
        retMsg.setToUserName(fromUserName);
        logger.debug("sendTextMsg 执行结束  出参:{}", retMsg);
        return XMLUtil.convertToXML(retMsg);
    }
    
    /**
     * 获取redisKey
     * 
     * @param fromUserName
     * @return
     */
    private String getJedisKey(String fromUserName){
        return DECIDER_UPLOAD_INFO.replace("FROM_USER", fromUserName);
    }
    
    /**
     * 
     * 
     * @param msgPush
     * @param savePath
     * @param fileExt
     * @return
     * @throws Exception
     *//*
    private synchronized ResultEntry<String> receiveCustomUploadMsg(String fromUserName, String mediaId, String toUserName, String[] savePath, String fileExt) throws Exception{
        logger.debug("receiveCustomUploadMsg 执行开始  入参:{}{}{}{}{}", fromUserName, mediaId, toUserName, savePath, fileExt);
        ResultEntry<String> result = new ResultEntry<String>();
        
        DeciderUploadInfo uploadInfo = JedisPull.getObject(getJedisKey(fromUserName), DeciderUploadInfo.class);
        if (uploadInfo != null && uploadInfo.getStep() == 5) {
            String filePath = MultiMediaUpload.downloadMedia(WeixinMessageDigestUtil.getAccessToken(), mediaId, getSavePath(savePath[0]), fileExt);
            // 判断是否为视频文件
            if (checkContentType(filePath)) {
                ConverPhoneVideo converPhoneVideo = new ConverPhoneVideo(getSavePath(savePath[0]), filePath);
                converPhoneVideo.beginConver();
                filePath = getFlvSavePath(filePath, ".flv");
            } else {
                // 添加图片文件的水印
                ImageFileHandler.pressImage(filePath);
            }
            uploadInfo.setAddress(filePath.replace(savePath[0], savePath[1]));
            Callable<Object> callable = Executors.callable(new FileManageHandler<DeciderUploadInfo>(uploadInfo));
            callable.call();
            if(!Boolean.TRUE.equals(uploadInfo.getIsSendMS())){
//                StringBuilder builder = new StringBuilder();
//                builder.append("您是否需要继续上传该资料名称\r\n");
//                builder.append("1.是\r\n");
//                builder.append("2.否\r\n");
//                result.setT(sendTextMsg(builder.toString(), fromUserName, toUserName));
//                StringBuilder builder = new StringBuilder();
//                builder.append("您是否需要继续上传该客户资料\r\n");
//                builder.append("1.是\r\n");
//                builder.append("2.否\r\n");
//                uploadInfo.setStep(6);
//                result.setT(sendTextMsg(builder.toString(), fromUserName, toUserName));
                // 贷款上传资料名称
                uploadInfo.setStep(4);
                // 先展示优先的上传文件
                uploadInfo.setDispFlg(1);
                result.setT(dispUploadLoanFileType(uploadInfo, fromUserName, toUserName));
                uploadInfo.setIsSendMS(true);
            }
            // 保存记录到redis缓存中
            JedisPull.setObject(getJedisKey(fromUserName), uploadInfo, 60*30);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setT("");
        }
        logger.debug("receiveCustomUploadMsg 执行结束  出参:{}", result);
        return result;
    }*/
    
    /**
     * 获取保存的地址
     * 
     * @param savePath
     * @return
     */
    private String getSavePath(String savePath){
        StringBuilder builder = new StringBuilder();
        builder.append(savePath);
        if (savePath.endsWith(File.separator)) {
            builder.append("upload/decider/");
        } else {
            builder.append("/upload/decider/");
        }
        builder.append(DateUtil.format(new Date(), "yyyyMMdd"));
        return builder.toString();
    }

    /**
     * 根据原文件名返回flv的文件名称
     * 
     * @param srcFile
     * @param fileExt
     * @returns
     */
    public static String getFlvSavePath(String srcFile, String fileExt) {
        String savePath = srcFile.substring(0, srcFile.lastIndexOf(File.separator) + 1);
        String fileName = srcFile.substring(srcFile.lastIndexOf(File.separator)+1);
        fileName = fileName.substring(0, fileName.lastIndexOf(".")) + fileExt;
        return savePath.concat("flv").concat(File.separator).concat(fileName);
    }
    
    /**
     * 判断是否是视频
     * 
     * @param fileName
     * @return
     */
    private Boolean checkContentType(String fileName) {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        if (type.equals("avi")) {
            return true;
        } else if (type.equals("mpg")) {
            return true;
        } else if (type.equals("wmv")) {
            return true;
        } else if (type.equals("3gp")) {
            return true;
        } else if (type.equals("mov")) {
            return true;
        } else if (type.equals("mp4")) {
            return true;
        } else if (type.equals("asf")) {
            return true;
        } else if (type.equals("asx")) {
            return true;
        } else if (type.equals("flv")) {
            return true;
        } else if (type.equals("wmv9")) {
            return true;
        } else if (type.equals("rm")) {
            return true;
        } else if (type.equals("rmvb")) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * 
     * @author oms
     * @version $Id: WeixinCustomMsgServiceImpl.java, v 0.1 2017-3-22 下午8:33:24 oms Exp $
     */
    private class FileManageHandler<T> implements Runnable{
        
        private T imageMsg;
        
        public FileManageHandler(T imageMsg){
            this.imageMsg = imageMsg;
        }
        
        @Override
        @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
        public void run() {/*
            logger.debug("FileManageHandler 执行开始  入参:{}", imageMsg);
            CustQuery query = null;
            DeciderUploadInfo uploadInfo = (DeciderUploadInfo)imageMsg;
            query = new CustQuery();
            query.setCustName(uploadInfo.getName());
            query.setLoanApplyId(uploadInfo.getLoanApplyId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<CustInfo> custRst = custService.getCustInfoList(query);
            if(custRst.isSuccess()){
                for(CustInfo custInfo : custRst.getList()){
                    FileManage fileManage = new FileManage();
                    fileManage.setLoanApplyId(custInfo.getLoanApplyId());
                    fileManage.setFileAddress(uploadInfo.getAddress());
                    fileManage.setFileName(uploadInfo.getFileName());
                    fileManage.setLoanBasicId(uploadInfo.getLoanBasicId());
                    fileManage.setStatus(GenerateConstants.number_0);
                    fileManage.setSourceType(GenerateConstants.number_0);
                    fileManage.setType(uploadInfo.getType());
                    fileManage.setCreateTime(new Date());
                    fileManage.setCreateUserId(uploadInfo.getFromUserId());
                    fileManage.setUpdateTime(fileManage.getCreateTime());
                    fileManage.setUpdateUserId(uploadInfo.getFromUserId());
                    systemReceiveFileService.insertFileManage(fileManage);
                    InterviewRecord record = new InterviewRecord();
                    record.setLoanApplyId(custInfo.getLoanApplyId());
                    record.setFileId(fileManage.getId());
                    record.setLatitude(NumberUtils.string2BigDecimal(uploadInfo.getLatitude()));
                    record.setLongitude(NumberUtils.string2BigDecimal(uploadInfo.getLongitude()));
                    record.setCreateTime(fileManage.getCreateTime());
                    record.setCreateUserId(fileManage.getCreateUserId());
                    record.setUpdateTime(fileManage.getCreateTime());
                    record.setUpdateUserId(fileManage.getCreateUserId());
                    interviewRecordService.insertInterviewRecord(record);
                }
                
            }
            logger.debug("FileManageHandler 执行结束  出参:{}", "");
        */}
    }
}
