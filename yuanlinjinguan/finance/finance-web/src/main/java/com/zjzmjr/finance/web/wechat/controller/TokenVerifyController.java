package com.zjzmjr.finance.web.wechat.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.ConnectException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.api.weixin.IWeixinMenuFacade;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.biz.weixin.MyTrustManager;
import com.zjzmjr.core.common.biz.weixin.WeixinMessageDigestUtil;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.weixin.wechat.Token;
import com.zjzmjr.core.model.weixin.wechat.WeixinUserInfo;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 接收微信消息接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/token")
public class TokenVerifyController extends BaseController {

    private static final String WX_TOKEN="zjzmjr123";

    private static final Logger logger = LoggerFactory.getLogger(TokenVerifyController.class);
    
//    @Resource(name = "userFacade")
//    private IUserFacade userFacade;
    
    @Resource(name="weixinMenuFacade")
    private IWeixinMenuFacade weixinMenuFacade;
    
    @Resource(name = "adminFacade")
    private IAdminFacade adminFacade;
    
    /**
     * 接入测试
     * @param request
     * @param resp
     */
    @RequestMapping(value = "/verify.htm", method = RequestMethod.GET)
    public void weixinAccount(HttpServletRequest request, HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        try {
            String signature = request.getParameter("signature");//微信加密签名
            String timestamp = request.getParameter("timestamp");//时间戳
            String nonce = request.getParameter("nonce");//随机数
            String echostr = request.getParameter("echostr");//随机字符串
            /**
             * 1. 将token、timestamp、nonce三个参数进行字典序排序
             * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
             * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
             */
            String[] src = { WX_TOKEN, timestamp, nonce };
            Arrays.sort(src);
            StringBuilder before = new StringBuilder();
            for (int i = 0; i < src.length; i++) {
                before.append(src[i]);
            }
            if (Util.isNotNull(signature) && Util.isNotNull(timestamp) && Util.isNotNull(nonce) && Util.isNotNull(echostr)) {
                String result = WeixinMessageDigestUtil.encipher(before.toString());
                if (signature.equals(result)) {
                    OutputStream os = resp.getOutputStream();
                    os.write(echostr.getBytes("UTF-8"));
                    os.flush();
                    os.close();
                }
            }
        } catch (Exception ex) {
            logger.error("token验证失败失败", ex);
        }
    }
    
    /**
     * 接收微信事件推送
     * @param reader
     */
    @RequestMapping(value="/verify.htm",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String receiveData(Reader reader,@RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,@RequestParam("nonce") String nonce){
        logger.debug("getUserBankPwdIdno入参:signature:{},timestamp:{},nonce:{}",signature,timestamp,nonce);
        //验证请求来源是否为微信
        String[] src = { WX_TOKEN, timestamp, nonce };
        Arrays.sort(src);
        StringBuilder before = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            before.append(src[i]);
        }
        String result = WeixinMessageDigestUtil.encipher(before.toString());
        if (signature.equals(result)) {
            //通过流的形式读取post表单内容
            BufferedReader bufferedReader=new BufferedReader(reader);
            StringBuilder str=new StringBuilder();
            String line=null;
            try {
                while((line=bufferedReader.readLine())!=null){
                    str.append(line);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                //不能进行回应，直接回复空串：微信服务器不会对此作任何处理，并且不会发起重试。
                return "";
            }finally{
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    return "";
                }
            }
            String data=weixinMenuFacade.eventHandler(str.toString());
            // 客户消息
            String[] savePath = new String[] { PropertyUtils.getPropertyFromFile("auto-config", "file.url"), PropertyUtils.getPropertyFromFile("auto-config", "img.url") };
            String msgRst = weixinMenuFacade.receiveCustomMessage(str.toString(), savePath);
            return msgRst == null ? data : msgRst;
        }else{
            logger.error("非法请求！");
        }
        return "";
    }
    
    
    /**
     * 获取带参数的二维码
     * 
     * @param request
     * @param resp
     * @throws Exception 
     */
    @RequestMapping(value = "/getWeiXinQrcode.htm", method = RequestMethod.GET)
    public void getqrcode(HttpServletRequest request, HttpServletResponse resp) throws Exception {
        String reqUrl = "";
        InputStream inStream = null;
        try {
            reqUrl = WeixinMessageDigestUtil.getQrCode();
            TrustManager[] tm = { new MyTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(reqUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setRequestProperty("Content-Type", "image/jped");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod("GET");
            // 超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            // 通过输入流获取图片数据
            inStream = conn.getInputStream();
            // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = WeixinMessageDigestUtil.readInputStream(inStream);
            // new一个文件对象用来保存图片，默认保存当前工程根目录
            // File imageFile = new File("D:/test1.jpg");
            // 创建输出流
            // FileOutputStream outStream = new FileOutputStream(imageFile);
            // 写入数据
            // outStream.write(data);
            // 关闭输出流
            // outStream.close();
            conn.disconnect();
            InputStream buffin = new ByteArrayInputStream(data);
            BufferedImage img;
            try {
                img = ImageIO.read(buffin);
                // 禁止图像缓存。
                resp.setHeader("Pragma", "no-cache");
                resp.setHeader("Cache-Control", "no-cache");
                resp.setDateHeader("Expires", 0);

                // 将图像输出到Servlet输出流中。
                ServletOutputStream sos;
                sos = resp.getOutputStream();
                ImageIO.write(img, "png", sos);
                sos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ConnectException ce) {
            logger.error("连接超时：{}", ce);
        } catch (Exception e) {
            logger.error("https请求异常：{}", e);
        }
    }
    
    /**
     * 获取微信用户信息
     * 
     * @param req
     * @param resp
     * @param session
     * @return
     */
    @RequestMapping(value = "/getUserInfo.htm", method = RequestMethod.POST)
    public void getUserInfo(HttpServletRequest request, HttpServletResponse resp, HttpSession session) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            String code = request.getParameter("code");
            Admin admin = new Admin();
            admin.setId(SpringSecurityUtil.getIntPrincipal());
            admin.setImageAddress(code);
            Result adminRst = adminFacade.updateAdmin(admin);
            if(adminRst == null){
                Token token = WeixinMessageDigestUtil.getToken(code);
                if (token == null) {
                    logger.error("token获取失败！");
                } else {
                    String accessToken = token.getAccessToken();
                    String openId = token.getOpenid();
                    ResultEntry<WeixinUserInfo> result = WeixinMessageDigestUtil.getUserInfo(accessToken, openId);
                    // ResultEntry<Integer> data =
                    /*
                     * 用户表的信息
                     * 
                     * User user = new User();
                    user.setId(SpringSecurityUtil.getIntPrincipal());
                    user.setImageAddress(result.getT().getHeadImgUrl());
                    user.setOpenId(result.getT().getOpenId());
                    user.setUpdateTime(new Date());
                    user.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                    userFacade.updateUser(user);*/
                    // session.setAttribute("wxopenid", result.getT().getOpenId());
                    // session.setAttribute("headimgurl",
                    // result.getT().getHeadImgUrl());
//                    Admin admin = new Admin();
                    admin.setId(SpringSecurityUtil.getIntPrincipal());
                    admin.setImageAddress(result.getT().getHeadImgUrl());
                    admin.setOpenId(result.getT().getOpenId());
                    adminFacade.updateAdmin(admin);
                    // System.out.println(result.getT().getHeadImgUrl());
                    model.put("data", result.getT());
                    putSuccess(model, "");
                }
            }
            putSuccess(model, "");
        } catch (Exception ex) {
            logger.error("用户信息获取出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 请求微信
     * 
     * @param req
     * @param resp
     */
    @RequestMapping(value = "/wxOauth.htm", method = RequestMethod.POST)
    public void wxOauth(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            String home = req.getParameter("url");
            String url = WeixinMessageDigestUtil.getCodeRequest(home);
            model.put("data", url);
            putSuccess(model, "");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        logger.debug("getUserBankPwdIdno出参{}",model);
        responseAsJson(model, resp);
    }

    
    @RequestMapping(value = "/showLocation.htm", method = RequestMethod.POST)
    public void showLocation(HttpServletRequest request, HttpServletResponse resp) {
        
    }
}
