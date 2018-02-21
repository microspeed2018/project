package com.zjzmjr.finance.web.home.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.zjzmjr.web.mvc.controller.BaseController;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * 二维码生成
 * 
 * @author Administrator
 * @version $Id: GetQrCodeController.java, v 0.1 2016-6-7 下午4:05:18 Administrator Exp $
 */
@Controller
@RequestMapping("/getQrCode.htm")
public class GetQrCodeController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(GetQrCodeController.class);

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    
    /**
     * 
     * 
     * @param resp
     * @param req
     */
    @RequestMapping(method=RequestMethod.GET)
    public void getQrCode(HttpServletResponse resp, HttpServletRequest req){
        String address = "http://service.cbylsj.com/index.html?userId="+SpringSecurityUtil.getPrincipal();
        try { 
            getMatrix(address,resp);
        } catch (IOException e) {
            logger.error("将图片写入客户端流出错", e);
        }
    }
   
    public static void getMatrix(String address,HttpServletResponse resp) throws IOException {
        logger.debug("getMatrix入参:{}",address);
        String format = "png";
        int width = 300;
        int height = 300;
//        File file = new File("D://code.png");
        writeToStream(format, resp.getOutputStream(),
                width, height, address);
//        writeToFile(format, file, width, height, address);
    }
    
    public static void writeToStream(String format,
            OutputStream stream, int width, int height, String text)
            throws IOException {
        Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(text,
                    BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BufferedImage image = toBufferedImage(bitMatrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format "
                    + format);
        }
    }
    
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
    
    public static void writeToFile(String format, File file,
            int width, int height, String text) throws IOException {
        Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(text,
                    BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BufferedImage image = toBufferedImage(bitMatrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format "
                    + format + " to " + file);
        }
    }
}
