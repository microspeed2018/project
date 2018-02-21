package com.zjzmjr.core.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.zjzmjr.common.util.DateUtil;
import com.zjzmjr.core.base.imge.ImageFileHandler;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.core.base.video.ConverPhoneVideo;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: FileUploadUtil.java, v 0.1 2016-5-12 下午7:16:40 Administrator
 *          Exp $
 */
public class FileUploadUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    private String savePath = "upload/admin";

    private final String resouce_path = "file.url";

    private final String resouce_url = "img.url";

    private String business;

    private Integer index = 1;

    private static volatile FileUploadUtil instance = null;

    private FileUploadUtil() {
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * 实例化
     * 
     * @return
     */
    public static FileUploadUtil getInstance(SavePathEnums path) {
        if (instance == null) {
            synchronized (FileUploadUtil.class) {
                if (instance == null) {
                    instance = new FileUploadUtil();
                }
            }
        }
        instance.savePath = path.getMessage();
        return instance;
    }

    /**
     * web前端上传文件
     * 
     * @param multipartRequest
     * @param resp
     */
    public String uploadApk(MultipartFile multipartRequest) {
        logger.debug("uploadApk 执行开始  入参:{}", multipartRequest);
        String filePath = StringUtils.EMPTY;
        try {
            //Random random = new Random();
            //int index = this.getIndex();
            String uploadPath = getUploadPath(business);
            String uploadFileUrl = getUploadFileUrl(uploadPath);
            if (multipartRequest.getOriginalFilename().length() > 0) {
                String originFileName = getOriginFileName(multipartRequest.getOriginalFilename(), index);
                // 改成自己的对象哦！
                // Constant.UPLOAD_GOODIMG_URL 是一个配置文件路径
                try {
                    File targetFile = new File(uploadFileUrl, originFileName);
                    FileUtils.copyInputStreamToFile(multipartRequest.getInputStream(), targetFile);
                    // 判断是否为视频文件
                    if (checkContentType(originFileName)) {
                        ConverPhoneVideo converPhoneVideo = new ConverPhoneVideo(uploadFileUrl, uploadFileUrl + "\\"
                                + originFileName);
                        ResultEntry<String> result = converPhoneVideo.beginConver();
                        if (result.isSuccess()) {
                            StringBuilder fileUrl = new StringBuilder(getViewWebUrl(uploadPath));
                            fileUrl.append(result.getT());
                            filePath = fileUrl.toString();
                        } else {
                            filePath = getViewWebUrl(uploadPath) + originFileName;
                        }
                    } else {
//                        ImageFileHandler.getInstance().setBusiness(business);
//                        ImageFileHandler.pressImage(targetFile.getPath());
                        filePath = getViewWebUrl(uploadPath) + originFileName;
                    }
                    index = 1;
                } catch (Exception e) {
                    logger.error("上传文件出错：", e);
                }
            }
        } catch (Exception ex) {
            logger.error("上传文件出错：", ex);
        }
        logger.debug("uploadApk 执行结束  出参:{}", filePath);
        return filePath;
    }

    /**
     * web前端上传文件
     * 
     * @param multipartRequest
     * @param resp
     */
    public String uploadApk(InputStream inputStream, String fileName) {
        String filePath = StringUtils.EMPTY;
        try {
            if(inputStream != null){
                String uploadPath = getUploadPath(business);
                String uploadFileUrl = getUploadFileUrl(uploadPath);
                String originFileName = getOriginFileName(fileName, index);

                File targetFile = new File(uploadFileUrl, originFileName);
                FileUtils.copyInputStreamToFile(inputStream, targetFile);
                // 判断是否为视频文件
                if (checkContentType(originFileName)) {
                    ConverPhoneVideo converPhoneVideo = new ConverPhoneVideo(uploadFileUrl, uploadFileUrl + "\\"
                            + originFileName);
                    ResultEntry<String> result = converPhoneVideo.beginConver();
                    if (result.isSuccess()) {
                        StringBuilder fileUrl = new StringBuilder(getViewWebUrl(uploadPath));
                        fileUrl.append(result.getT());
                        filePath = fileUrl.toString();
                    } else {
                        filePath = getViewWebUrl(uploadPath) + originFileName;
                    }
                } else {
//                    ImageFileHandler.getInstance().setBusiness(business);
//                    ImageFileHandler.pressImage(targetFile.getPath());
                    filePath = getViewWebUrl(uploadPath) + originFileName;
                }
                index = 1;
            }
        } catch (Exception ex) {
            logger.error("上传文件出错：", ex);
        }finally{
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("上传文件出错", e);
                }
            }
        }
        return filePath;
    }

    /**
     * 从OutputStream转换成InputStream
     * 
     * @param out
     * @return
     * @throws Exception
     */
    public ByteArrayInputStream parse(OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }

    /**
     * 重新生成文件名
     * 
     * @param originFileName
     * @return
     */
    private String getOriginFileName(String originFileName, int index) {
        StringBuilder builder = new StringBuilder("zmjr");
        builder.append(SpringSecurityUtil.getIntPrincipal());
        builder.append(index);
        builder.append(System.nanoTime());
        builder.append(originFileName.substring(originFileName.lastIndexOf("."), originFileName.length()));
        return builder.toString();
    }

    /**
     * 上传的地址
     * 
     * @return
     */
    private String getUploadPath(String business) {
        StringBuilder builder = new StringBuilder();
        builder.append(savePath).append(File.separator);
        builder.append(DateUtil.format(new Date(), "yyyyMMdd"));
        if (StringUtils.isNotBlank(business)) {
            builder.append(File.separator).append(business);
        }

        return builder.toString();
    }

    /**
     * 
     * 
     * @param multipartRequest
     * @param uploadPath
     * @return
     */
    private String getUploadFileUrl(String uploadPath) {
        String uploadFileUrl = PropertyUtils.getInstance().getProperty(resouce_path);
        // uploadFileUrl = uploadFileUrl.concat(uploadPath);
        File filePath = new File(uploadFileUrl, uploadPath);
        if (!filePath.exists()) {
            if (!filePath.mkdirs()){
                logger.error("生成目录失败！");                
            }
        }

        return filePath.getPath();
    }

    /**
     * 
     * 
     * @return
     */
    private String getViewWebUrl(String uploadPath) {
        StringBuilder viewUrl = new StringBuilder(PropertyUtils.getInstance().getProperty(resouce_url));
        if (viewUrl.toString().endsWith(File.separator) || uploadPath.startsWith(File.separator)) {
            viewUrl.append(uploadPath).append(File.separator);
        } else {
            viewUrl.append(File.separator).append(uploadPath).append(File.separator);
        }

        return viewUrl.toString();
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    /**
     * 文件上传路径的枚举类型
     * 
     * @author Administrator
     * @version $Id: FileUploadUtil.java, v 0.1 2017-6-9 下午2:58:14 Administrator
     *          Exp $
     */
    public static enum SavePathEnums {

        PATH_ADMIN(1, "upload/admin"),

        PATH_FINANCE(3, "upload/finance"),
        ;

        private Integer value;

        private String message;

        private SavePathEnums(Integer value, String message) {
            this.value = value;
            this.message = message;
        }

        public Integer getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }

        public static SavePathEnums getByValue(Integer value) {
            if (value != null) {
                for (SavePathEnums enu : values()) {
                    if (enu.value.equals(value)) {
                        return enu;
                    }
                }
            }
            return null;
        }

    }

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
}
