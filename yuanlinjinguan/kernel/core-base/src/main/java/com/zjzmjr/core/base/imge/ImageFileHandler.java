package com.zjzmjr.core.base.imge;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.core.base.util.StringUtil;

/**
 * 图片工具类， 图片水印，文字水印，缩放，补白等
 * 
 * @author Carl He
 */
public final class ImageFileHandler {

    private static final Logger logger = LoggerFactory.getLogger(ImageFileHandler.class);

    /** 图片水印文字 */
    private static final String WATER_IMAGE_WORD = "仅限车贷使用，再次复印无效";

    /** 图片格式：JPG */
    private String pictrueFormate = "jpg";

    /** 业务处理的类型 */
    private String business = "1";

    private static ImageFileHandler instance = null;

    /** 图片格式 */
    private final String[] picture_file_format = { "jpeg", "gif", "jpg", "png", "bmp", "pic" };

    private ImageFileHandler() {
    }

    /**
     * 业务处理类型
     * 
     * @author oms
     * @version $Id: ImageFileHandler.java, v 0.1 2017-8-3 上午11:00:00 oms Exp $
     */
    public static enum BusinessTypeEnum {

        LOAN(1, "waterLoanImg.png"),

        INSURANCE(2, "waterInsuranImg.png");

        private Integer value;

        private String message;

        private BusinessTypeEnum(Integer value, String message) {
            this.value = value;
            this.message = message;
        }

        public Integer getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }

        public static BusinessTypeEnum getByValue(Integer value) {
            if (value != null) {
                for (BusinessTypeEnum enu : values()) {
                    if (enu.value.equals(value)) {
                        return enu;
                    }
                }
            }
            return null;
        }
    }

    /**
     * 获取实例对象
     * 
     * @return
     */
    public static ImageFileHandler getInstance() {
        if (instance == null) {
            synchronized (ImageFileHandler.class) {
                if (instance == null) {
                    instance = new ImageFileHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 添加文字水印
     * 
     * @param targetImg
     *            目标图片路径，如：C://myPictrue//1.jpg
     * @param pressText
     *            水印文字， 如：中国证券网
     */
    public static final void pressText(String targetImg) {
        pressText(targetImg, WATER_IMAGE_WORD);
    }

    /**
     * 添加文字水印
     * 
     * @param targetImg
     *            目标图片路径，如：C://myPictrue//1.jpg
     * @param pressText
     *            水印文字， 如：中国证券网
     */
    public static final void pressText(String targetImg, String pressText) {
        getInstance().setPictrueFormate(targetImg.substring(targetImg.lastIndexOf(".") + 1));
        getInstance().pressText(targetImg, pressText, "宋体", Font.BOLD | Font.ITALIC, 20, Color.white, 60, 70, 0.5f);
    }

    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     * 
     * @param srcImgPath
     *            源图片路径
     */
    public static void pressImage(String srcImgPath) {
        String businessType = getInstance().getBusiness();
        if (StringUtils.isBlank(businessType)) {
            businessType = BusinessTypeEnum.LOAN.getMessage();
        } else {
            businessType = BusinessTypeEnum.getByValue(StringUtil.nullToInteger(businessType)).getMessage();
        }
        String waterImgPath = ImageFileHandler.class.getResource(businessType).getFile();
        getInstance().setPictrueFormate(srcImgPath.substring(srcImgPath.lastIndexOf(".") + 1));
        getInstance().pressImage(srcImgPath, waterImgPath);
    }

    /**
     * 添加图片水印
     * 
     * @param targetImg
     *            目标图片路径，如：C://myPictrue//1.jpg
     * @param waterImg
     *            水印图片路径，如：C://myPictrue//logo.png
     * @param x
     *            水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y
     *            水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha
     *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public final void pressImage(String targetImg, String waterImg, int x, int y, float alpha) {
        try {

            // 不是图片的时候，跳出去
            if(Arrays.binarySearch(picture_file_format, pictrueFormate) < 0){
                return;
            }
            
            File file = new File(targetImg);
            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);

            Image waterImage = ImageIO.read(new File(waterImg)); // 水印文件
            int width_1 = waterImage.getWidth(null);
            int height_1 = waterImage.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            if (x < 0) {
                x = widthDiff / 2;
            } else if (x > widthDiff) {
                x = widthDiff;
            }
            if (y < 0) {
                y = heightDiff / 2;
            } else if (y > heightDiff) {
                y = heightDiff;
            }
            g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
            g.dispose();
            ImageIO.write(bufferedImage, pictrueFormate, file);
        } catch (IOException e) {
            logger.error("添加图片水印出错", e);
        }
    }

    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     * 
     * @param srcImgPath
     *            源图片路径
     * @param waterImgPath
     *            水印图片路径
     */
    public final void pressImage(String srcImgPath, String waterImgPath) {
        pressImage(srcImgPath, waterImgPath, 5, 5f, 0.6f);
    }

    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     * 
     * @param srcImgPath
     *            源图片路径
     * @param waterImgPath
     *            水印图片路径
     * @param interval
     *            水印图片间隔
     * @param degree
     *            水印图片旋转角度
     * @param alpha
     *            水印透明度
     */
    public final void pressImage(String srcImgPath, String waterImgPath, int interval, float degree, float alpha) {
        OutputStream os = null;
        try {

            // 不是图片的时候，跳出去
            if(Arrays.binarySearch(picture_file_format, pictrueFormate) < 0){
                return;
            }
            
            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();

            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 3、设置水印旋转
            if (0 != degree) {
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }

            // // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            // ImageIcon imgIcon = new ImageIcon(waterImgPath);
            // // 5、得到Image对象。
            // Image img = imgIcon.getImage();
            Image img = resize(waterImgPath, buffImg.getHeight() / 6, buffImg.getWidth() / 6, false);
            BufferedImage imgIcon = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            // 6、水印图片的位置
            // for (int height = interval + imgIcon.getIconHeight(); height <
            // buffImg.getHeight(); height = height + interval +
            // imgIcon.getIconHeight()) {
            // for (int weight = interval + imgIcon.getIconWidth(); weight <
            // buffImg.getWidth(); weight = weight + interval +
            // imgIcon.getIconWidth()) {
            // g.drawImage(img, weight - imgIcon.getIconWidth(), height -
            // imgIcon.getIconHeight(), null);
            // }
            // }
            for (int height = interval + imgIcon.getHeight(); height < buffImg.getHeight(); height = height + interval + imgIcon.getHeight()) {
                for (int weight = interval + imgIcon.getWidth(); weight < buffImg.getWidth(); weight = weight + interval + imgIcon.getWidth()) {
                    g.drawImage(img, weight - imgIcon.getWidth(), height - imgIcon.getHeight(), null);
                }
            }
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();

            // 8、生成图片
            os = new FileOutputStream(srcImgPath);
            ImageIO.write(buffImg, pictrueFormate, os);

        } catch (Exception e) {
            logger.error("图片添加水印图片出错", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                logger.error("图片添加水印图片出错", e);
            }
        }
    }

    /**
     * 添加文字水印
     * 
     * @param targetImg
     *            目标图片路径，如：C://myPictrue//1.jpg
     * @param pressText
     *            水印文字， 如：中国证券网
     * @param fontName
     *            字体名称， 如：宋体
     * @param fontStyle
     *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize
     *            字体大小，单位为像素
     * @param color
     *            字体颜色
     * @param x
     *            水印文字距离目标图片左侧的偏移量的百分比，如果x<0, 则在正中间
     * @param y
     *            水印文字距离目标图片上侧的偏移量的百分比，如果y<0, 则在正中间
     * @param alpha
     *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public final void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
        try {
            File file = new File(targetImg);

            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            fontSize = fontSize < width / 15 ? width / 15 : fontSize;
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            int width_1 = fontSize * getLength(pressText);
            int height_1 = fontSize;
            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            x = widthDiff * x / 100;
            y = heightDiff * y / 100;
            if (x < 0) {
                x = widthDiff / 2;
            } else if (x > widthDiff) {
                x = widthDiff;
            }
            if (y < 0) {
                y = heightDiff / 2;
            } else if (y > heightDiff) {
                y = heightDiff;
            }

            g.drawString(pressText, x, y + height_1);
            g.dispose();
            ImageIO.write(bufferedImage, pictrueFormate, file);
        } catch (Exception e) {
            logger.error("添加文字水印出错", e);
        }
    }

    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     * 
     * @param text
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
     */
    private int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }

    /**
     * 图片缩放
     * 
     * @param filePath
     *            图片路径
     * @param height
     *            高度
     * @param width
     *            宽度
     * @param bb
     *            比例不对时是否需要补白
     */
    public final void resizeToFile(String filePath, int height, int width, boolean bb) {
        try {
            ImageIO.write((BufferedImage) resize(filePath, height, width, bb), pictrueFormate, new File(filePath));
        } catch (IOException e) {
            logger.error("图片缩放出错", e);
        }
    }

    /**
     * 图片缩放
     * 
     * @param filePath
     *            图片路径
     * @param height
     *            高度
     * @param width
     *            宽度
     * @param bb
     *            比例不对时是否需要补白
     */
    public final Image resize(String filePath, int height, int width, boolean bb) {
        Image itemp = null;
        try {
            double ratio = 0; // 缩放比例
            File f = new File(filePath);
            BufferedImage bi = ImageIO.read(f);
            itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                g.dispose();
                itemp = image;
            }
        } catch (IOException e) {
            logger.error("图片缩放出错", e);
        }
        return itemp;
    }

    public String getPictrueFormate() {
        return pictrueFormate;
    }

    public void setPictrueFormate(String pictrueFormate) {
        this.pictrueFormate = pictrueFormate;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public static void main(String[] args) throws IOException {
        getInstance().pressImage("D:\\中茗金融云服务平台\\03_产品设计\\产品原型\\APP\\zmjr121469671635177.jpg", "D:\\中茗金融云服务平台\\03_产品设计\\产品原型\\APP\\水印xie.png");
        // File targetFile = new File("D:\\中茗金融云服务平台\\03_产品设计\\产品原型\\APP",
        // "zmjr121469671635177.jpg");
        // pressText(targetFile.getPath());
        // resize("D:\\中茗金融云服务平台\\03_产品设计\\产品原型\\APP\\b_商城.gif", 1000, 500,
        // true);
    }
}
