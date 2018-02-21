package com.zjzmjr.finance.web.home.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.web.mvc.controller.BaseController;
import com.zjzmjr.finance.web.common.Constants;

/**
 * 验证码生成器
 * 
 * @author js
 * @version $Id: CheckCodeController.java, v 0.1 2015年12月15日 上午10:18:24 js Exp $
 */
@Controller
@RequestMapping("/checkCode.htm")
public class CheckCodeController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CheckCodeController.class);
	
	// 验证码图片的宽度。  
    private int width = 150;  
    // 验证码图片的高度。  
    private int height = 40;  
    // 验证码字符个数  
    private int codeCount = 6;  
  
    private int x = width/(codeCount+1) - 3;
    // 字体高度  
    private int fontHeight=height-2;  
    private int codeY=height-4;  
    // 算法
    private String[] specialFlg = {"+", "X"};
  
    //char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ,'A', 'B', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'R', 'S', 'U', 'V', 'X', 'Y', 'Z' };
    char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    
	@RequestMapping(method=RequestMethod.GET)
	public void checkCode(HttpServletResponse resp, HttpServletRequest req){
		// 定义图像buffer  
        BufferedImage buffImg = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = buffImg.createGraphics();  
  
        // 创建一个随机数生成器类  
        Random random = new Random();  
  
        // 将图像填充为白色  
        g.setColor(Color.LIGHT_GRAY);  
        g.fillRect(0, 0, width, height);  
  
        // 创建字体，字体的大小应该根据图片的高度来定。  
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);  
        // 设置字体。  
        g.setFont(font);  
  
        // 画边框。  
//        g.setColor(Color.BLACK);  
//        g.drawRect(0, 0, width - 1, height - 1);  
  
        // 随机产生20条干扰线，使图象中的认证码不易被其它程序探测到。  
        g.setColor(Color.BLACK);  
        for (int i = 0; i < 10; i++) {  
            int x = random.nextInt(width);  
            int y = random.nextInt(height);  
            int xl = random.nextInt(12);  
            int yl = random.nextInt(12);  
            g.drawLine(x, y, x + xl, y + yl);  
        }  
  
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。  
        //StringBuffer randomCode = new StringBuffer();  
        List<String> randomCode = new ArrayList<String>();
        int red = 0, green = 0, blue = 0;  
  
        int isSpecialFlg = random.nextInt(4);
        isSpecialFlg = isSpecialFlg > 0 ? isSpecialFlg : isSpecialFlg + 1;
        // 获取计算公式
        int specialFlgIdx = random.nextInt(2);
        // 随机产生codeCount数字的验证码。  
        for (int i = 0; i < codeCount; i++) {  
            // 得到随机产生的验证码数字。  
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);  
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。  
            if(i == isSpecialFlg){
                strRand = specialFlg[specialFlgIdx];
            }
//            if(i == 3 || random.nextInt(2) == 1){
//                red = 0;
//                green = 0;
//                blue = 255;
//                
                // 将产生的四个随机数组合在一起。  
            if ((randomCode.size() == 0 || i > isSpecialFlg) && randomCode.size() < 2) {
                randomCode.add(strRand);
            } else {
                if (i != isSpecialFlg) {
                    strRand = "";
                }
            }
//
//            }else{
                red = random.nextInt(155);  
                green = random.nextInt(100);  
                blue = random.nextInt(155);
//                red = 240;
//                green = 255;
//                blue = 255;
//                
//            }
            
  
            // 用随机产生的颜色将验证码绘制到图像中。  
            g.setColor(new Color(red, green, blue));  
            g.drawString(strRand, (i + 1) * x, codeY);  
          }  
        // 将四位数字的验证码保存到Session中。  
//        req.getSession().setAttribute(Constants.CHECK_CODE, randomCode.toString());  
        switch (specialFlgIdx) {
        case 0:
            req.getSession().setAttribute(Constants.CHECK_CODE, 
                    (Integer.parseInt(randomCode.get(0)) + Integer.parseInt(randomCode.get(1))));
            break;
        case 1:
            req.getSession().setAttribute(Constants.CHECK_CODE, 
                    (Integer.parseInt(randomCode.get(0)) * Integer.parseInt(randomCode.get(1))));
            break;
        default:
            break;
        }

        // 禁止图像缓存。  
        resp.setHeader("Pragma", "no-cache");  
        resp.setHeader("Cache-Control", "no-cache");  
        resp.setDateHeader("Expires", 0);  
        resp.setContentType("image/jpeg");  
        //清空缓存  
        g.dispose();  
        
        OutputStream ous = null;
        try {
        	ous = resp.getOutputStream();
			ImageIO.write(buffImg, "jpeg", resp.getOutputStream());
		} catch (IOException e) {
			logger.error("将图片写入客户端流出错", e);
		} finally{
			IOUtils.closeQuietly(ous);
		}
	}
	
}
