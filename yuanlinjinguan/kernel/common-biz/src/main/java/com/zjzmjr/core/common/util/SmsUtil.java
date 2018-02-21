package com.zjzmjr.core.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zjzmjr.common.transfer.TripleValueHolder;
import com.zjzmjr.common.util.RandomCodeUtil;
import com.zjzmjr.common.util.VerifyUtil;
import com.zjzmjr.core.api.sms.INotifyFacade;
import com.zjzmjr.core.base.exception.NotifySendIntervalException;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.SpringContextUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.cache.redis.JedisPull;
import com.zjzmjr.core.enums.message.NotifyContextEnum;
import com.zjzmjr.core.enums.sms.NotifyPriorityEnum;
import com.zjzmjr.core.enums.sms.NotifyStatusEnum;
import com.zjzmjr.core.enums.sms.NotifyTypeEnum;
import com.zjzmjr.core.enums.sms.SourceTypeEnum;
import com.zjzmjr.core.model.sms.Notify;
import com.zjzmjr.security.web.util.SpringSecurityUtil;


public class SmsUtil {

    private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);
    
    private static INotifyFacade notifyFacade = SpringContextUtil.getBean("notifyFacade");

    //产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    private static final String accessKeyId = "LTAIqz6fT1hZbEIc";
    private static final String accessKeySecret = "FFCXjk50Ju5EGdDMtwH7ZEN1iXo8qO";
    // 短信签名
    private static final String smsSignCode = "诚邦设计";
    
    /**
     * 注册验证码
     */
    public static final String CODE_KEY_REGISTER = "REGISTER#CODE#{mobile}";
    
    /**
     * 找回密码时的手机号码
     */
    public static final String CODE_KEY_FIND_PASSWORD_MOBILE = "FIND#LOGIN#PASSWORD#RESET#MOBILE#{mobile}";
    
    /**
     * 心跳检测手机
     */
    private static String SMS_HB_MOBILE_KEY = "SMS_HB_MOBILE#{mobile}";
    
    /**
     * 心跳检测IP
     */
    private static String SMS_HB_IP_KEY = "SMS_HB_IP#{ip}";
    
    /**
     * 短信发送IP心跳数
     */
    private static int SMS_IP_HEART_BEAT = 15;

    /**
     * 短信发送号码心跳数
     */
    private static int SMS_MOBILE_HEART_BEAT = 8;

    /**
     * 短信发送IP心跳间隔
     */
    private static int SMS_IP_HEART_INTERVAL = 1800;
    
    /**
     * 短信发送手机心跳间隔
     */
    private static int SMS_MOBILE_HEART_INTERVAL = 1800;
    
    /**
     * 有效时间(5分钟)
     */
    public static final int LIFE_TIME_FIVE_MIN = 5 * 60;

    /**
     * 发送短信
     * 
     * @param symbol
     * @return
     */
    public static boolean sendSMS(PointSymbol symbol){

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        boolean isSended = false;
        
        try {
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            
            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(symbol.getMobile());
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(smsSignCode);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(symbol.getTemplateCode());
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
            request.setTemplateParam(JSONObject.fromObject(symbol).toString());

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId(symbol.getExtendCode());

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            logger.info("请求短信的返回结果:{}", sendSmsResponse);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                isSended = true;
                saveNotify(symbol);
            }
        } catch (Exception ex){
            logger.error("发送短信失败: {}", ex);
            isSended = false;
        }
        return isSended;
    }
    
    /**
     * 获取已发送手机端的验证码
     * 
     * @param codeKey
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getMobileCheckCode(String codeKey, String mobile){
        String mobileCode = StringUtils.EMPTY;
        codeKey = codeKey.replace("{mobile}", mobile);
        TripleValueHolder<String, Long, Integer> tp = JedisPull.getObject(codeKey, TripleValueHolder.class);
        if(tp != null){
            mobileCode = tp.a;
        }
        return mobileCode;
    }
    
    /**
     * 获取手机的验证码
     * 
     * @param codeKey 验证码类型（注册，绑定手机，绑卡）
     * @param mobile 手机号码
     * @param ip ip地址
     * @param liftTime 验证码有效时间（单位秒）
     * @return
     * @throws IllegalArgumentException
     * @throws NotifySendIntervalException
     */
    @SuppressWarnings("unchecked")
    public static String getMobileCode(String codeKey, String mobile, String ip,int liftTime) throws IllegalArgumentException, NotifySendIntervalException {
        Assert.isTrue(VerifyUtil.isMobile(mobile), "手机号码格式错误");
        Assert.isTrue(VerifyUtil.isIp(ip), "IP格式错误");

        if (logger.isInfoEnabled()) {
            logger.info("用户注册发送验证码，mobile:{},ip:{}", mobile, ip);
        }
        codeKey = codeKey.replace("{mobile}", mobile);
        TripleValueHolder<String, Long, Integer> tp = JedisPull.getObject(codeKey, TripleValueHolder.class);
        if (tp != null && tp.b + 60000 > System.currentTimeMillis()) {
            throw new NotifySendIntervalException("获取验证码太频繁，请稍候再试", (60000 - (System.currentTimeMillis() - tp.b)) / 1000);
        }

        // 检测短信发送频率
        checkSmsHeartBeat(mobile, ip);
        String code;
        if (tp != null) {
            code = tp.a;
        } else {
            code = RandomCodeUtil.getRandomCode(RandomCodeUtil.MODE_NUMBER, 4);
        }

        Integer codeCount = tp == null ? 0 : tp.c;
        JedisPull.setObject(codeKey, new TripleValueHolder<String, Long, Integer>(code, System.currentTimeMillis(), codeCount == null ? 1 : codeCount + 1), liftTime);
        
        return code;
    }
    
    /**
     * 发送频率检测
     *
     * @param mobile
     *            手机号
     * @param ip
     *            ip
     * @throws IllegalArgumentException
     */
    public static void checkSmsHeartBeat(String mobile, String ip) throws IllegalArgumentException {
        if (StringUtils.isNotBlank(mobile)) {
            String hbMobileKey = SMS_HB_MOBILE_KEY.replace("{mobile}", mobile);
            Integer count = JedisPull.getObject(hbMobileKey, Integer.class);
            if (count != null && count >= SMS_MOBILE_HEART_BEAT) {
                throw new IllegalArgumentException("该手机短信发送过于频繁");
            }
            JedisPull.setObject(hbMobileKey, count == null ? 1 : count + 1, SMS_MOBILE_HEART_INTERVAL);
        }
        if (StringUtils.isNotBlank(ip)) {
            String hbIpKey = SMS_HB_IP_KEY.replace("{ip}", ip);
            Integer count = JedisPull.getObject(hbIpKey, Integer.class);
            if (count != null && count >= SMS_IP_HEART_BEAT) {
                throw new IllegalArgumentException("该IP短信发送过于频繁");
            }
            JedisPull.setObject(hbIpKey, count == null ? 1 : count + 1, SMS_IP_HEART_INTERVAL);
        }
    }
    
    public static void main(String[] args) {
        PointSymbol symbol = new PointSymbol();
        symbol.setName("诚邦设计");
        symbol.setAuditType("经营审核");
        symbol.setJob("设计");
        symbol.setTemplateCode("SMS_112470612");
        symbol.setMobile("15058101446,15925685649");
        
        sendSMS(symbol);
        
    }
    
    private static ResultEntry<Integer> saveNotify(PointSymbol symbol){
        NotifyContextEnum model = NotifyContextEnum.getByCode(symbol.getTemplateCode());
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNotNull(model)) {
            if(Util.isNotNull(symbol.getReceiveUserId())){
                String[] userIdArr = symbol.getReceiveUserId().split(",");
                String[] mobileArr = symbol.getMobile().split(",");
                for (int i = 0; i < userIdArr.length; i++) {
                    String userId = userIdArr[i];
                    if(NumberUtils.isNumber(userId)){
                        String mobile = "";
                        if(userIdArr.length == mobileArr.length){
                            mobile = mobileArr[i];
                        } else {
                            mobile = mobileArr[0];
                        }
                        String content = getContent("【" + smsSignCode + "】" + model.getMessage(), symbol, null);
                        Notify notify = createNotify(content, model.getValue(), mobile, SpringSecurityUtil.getIntPrincipal(), Integer.valueOf(userId), NotifyTypeEnum.SMS.getValue());
                        result = notifyFacade.save(notify);
                    }
                } 
            }else{
                String[] mobileArr = symbol.getMobile().split(","); 
                for (int i = 0; i < mobileArr.length; i++) {
                    //String userId = userIdArr[i];
                    String mobile = mobileArr[i];
                    String content = getContent("【" + smsSignCode + "】" + model.getMessage(), symbol, null);
                    Notify notify = createNotify(content, model.getValue(), mobile, SpringSecurityUtil.getIntPrincipal(), null, NotifyTypeEnum.SMS.getValue());
                    result = notifyFacade.save(notify);
                } 
            }
           
        } else {
            result.setSuccess(false);
            result.setErrorMsg("短信模版不存在");
        }
        return result;
    }
    
    private static String getContent(String content, PointSymbol pointSymbol, String[] symbols){
        if (null == pointSymbol) {
            if (null == symbols || symbols.length == 0) {
//                result.setT(content);
//                logger.debug("getContent出参:{}",result);
//                return result;
                return content;
            }
            content = repPoint(content, symbols);
        } else {
            content = repPoint(content, pointSymbol);
            if (null != symbols && symbols.length != 0) {
                content = repPoint(content, symbols);
            }
        }
        return content;
    }
    
    private static String repPoint(String content, PointSymbol pointSymbol) {

        Field[] fields = PointSymbol.class.getDeclaredFields();
        String value = null;
        String name = null;
        String str = null;
        try {
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    value = String.valueOf(field.get(pointSymbol));
                    if (null != value && !"null".equals(value)) {
                        name = field.getName();
                        str = "{" + name + "}";
                        if (content.contains(str)) {
                            content = content.replace(str, value);
                        }
                    }
                }

            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
        return content;
    }

    private static String repPoint(String content, String[] symbols) {
        String str = null;
        for (int i = 0; i < symbols.length; i++) {
            str = "${" + i + "}";
            if (content.contains(str)) {
                content = content.replace(str, symbols[i]);
            }
        }
        return content;
    }
    
    private static Notify createNotify(String content,String cause, String direction, Integer userID, Integer receiveUserId, Integer notifyType) {
        Notify notify = new Notify();
        notify.setUserId(userID == null ? -1 :userID.intValue());
        notify.setReceiveUserId(receiveUserId);
        notify.setType(notifyType);
        notify.setDestination(direction);
        notify.setTitle(" ");
        // 若为邮件,则需要设置标题
        if(NotifyTypeEnum.EMAIL.getValue().equals(notifyType)){
            notify.setTitle(cause);
        }
        notify.setContent(content);
        notify.setCause(cause);
        notify.setFailCount(0);
        notify.setPriority(NotifyPriorityEnum.PRIORITY_0.getValue());//通知优先级
        notify.setStatus(NotifyStatusEnum.COMPLETE.getValue());
        Date sendTime = new Date();
        notify.setScheduleTime(sendTime);
        notify.setSendTime(sendTime);
        notify.setCreateTime(new Date());
        notify.setSourceType(SourceTypeEnum.UNKNOW.getValue());
        notify.setSourceId(0);
        notify.setMobile(" ");
        notify.setUserName(" ");
        return notify;
    }
}
