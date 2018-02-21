package com.zjzmjr.finance.web.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.misc.BASE64Decoder;

import com.zjzmjr.common.util.DateUtil;
import com.zjzmjr.web.mvc.controller.BaseController;
import com.zjzmjr.core.base.imge.ImageFileHandler;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: FileUploadUtil.java, v 0.1 2016-5-12 下午7:16:40 Administrator Exp $
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping(value="/file")
public class FileUploadUtil extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    private static final String PATH = "/upload/web";

    private final String resouce_path = "file.url";

    private final String resouce_url = "img.url";
    
    /**
     * 
     * 
     * @param multipartRequest
     * @param resp
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/upload.htm", headers=("content-type=multipart/*"), method = RequestMethod.POST)
    public void uploadApk(ModelMap model, MultipartHttpServletRequest multipartRequest, HttpServletResponse resp) {
    	resp.setContentType("text/html;charset=UTF-8");
        Map<String, Object> fileMap = new HashMap<String, Object>();
        try{
        	int index = 1;
        	String uploadPath = getUploadPath(multipartRequest.getParameter("business"));
			String uploadFileUrl = getUploadFileUrl(multipartRequest, uploadPath);
            putSuccess(model, "");
        	for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
        		String key = (String) it.next();
        		logger.info("上传文件： " + key);
    			MultipartFile imgFile = multipartRequest.getFile(key);
    			if (imgFile.getOriginalFilename().length() > 0) {
    				String originFileName = getOriginFileName(imgFile.getOriginalFilename(), index++);
                    //改成自己的对象哦！
    				//Constant.UPLOAD_GOODIMG_URL 是一个配置文件路径
    				try {
                      // 这里使用Apache的FileUtils方法来进行保存
                      FileUtils.copyInputStreamToFile(imgFile.getInputStream(), new File(uploadFileUrl, originFileName));
                      fileMap.put(key, getViewWebUrl(uploadPath) + originFileName);
    				} catch (Exception e) {
                      logger.error("上传文件出错：", e);
                      putError(model, e.getMessage());
                      break;
    				}
    			}
        	}
        	model.put("fileName", fileMap);
        }catch(Exception ex){
            logger.error("上传文件出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsText(JSONObject.fromObject(model).toString(), resp);
    }
    
    /**
     * 
     * 
     * @param model
     * @param resp
     * @param req
     */
    @RequestMapping(value="/uploadErr.htm", method = RequestMethod.POST)
    public void error(ModelMap model, HttpServletResponse resp, HttpServletRequest req){
        try {
            putError(model, "上传文件出错");
        } catch (Exception ex) {
            logger.error("上传文件出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 重新生成文件名
     * 
     * @param originFileName
     * @return
     */
    private String getOriginFileName(String originFileName, int index){
        StringBuilder builder = new StringBuilder("zmjr");
        builder.append(SpringSecurityUtil.getIntPrincipal());
        builder.append(index);
        builder.append(System.currentTimeMillis());
        builder.append(originFileName.substring(originFileName.lastIndexOf("."), originFileName.length()));
        return builder.toString();
    }
    
    /**
     * 上传的地址
     * 
     * @return
     */
    private String getUploadPath(String business){
        StringBuilder builder = new StringBuilder();
        builder.append(PATH).append("/");
        builder.append(DateUtil.format(new Date(), "yyyyMMdd"));
        if(StringUtils.isNotBlank(business)){
            builder.append("/").append(business);
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
    private String getUploadFileUrl(HttpServletRequest multipartRequest, String uploadPath){
        String uploadFileUrl = PropertyUtils.getInstance().getProperty(resouce_path);
        if(StringUtils.isEmpty(uploadFileUrl)){
            uploadFileUrl = multipartRequest.getSession().getServletContext().getContextPath();//.getRealPath(uploadPath);
            uploadFileUrl = uploadFileUrl.substring(0, uploadFileUrl.lastIndexOf("/"));
        }
        uploadFileUrl = uploadFileUrl.concat(uploadPath);
        File filePath = new File(uploadFileUrl);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        
        return uploadFileUrl;
    }
    
    /**
     * 
     * 
     * @return
     */
    private String getViewWebUrl(String uploadPath){
        StringBuilder viewUrl = new StringBuilder(PropertyUtils.getInstance().getProperty(resouce_url));
        viewUrl.append(uploadPath).append("/");
        
        return viewUrl.toString();
    }
    
    /**
     * 图片压缩上传，接收base64二进制码，根据base64二进制码生成图片文件
     * 
     * @param model
     * @param resp
     * @param req
     */
    @RequestMapping(value = "/images.htm", method = RequestMethod.POST)
    public void home(ModelMap model, HttpServletResponse resp, HttpServletRequest req) {
        OutputStream os = null;
        File targetFile = null;
        try {
            String image = req.getParameter("images");
            String srcFile = req.getParameter("srcFile");
            String index = req.getParameter("index");
            if(StringUtils.isBlank(index)){
                index = "1";
            }
            String[] imageBase64 = image.split(",");
            if(imageBase64.length > 1){
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] b = decoder.decodeBuffer(imageBase64[1]);
                for (int i = 0; i < b.length; i++) {
                    if (b[i] < 0) {
                        b[i] += 256;
                    }
                }
                String uploadPath = getUploadPath(req.getParameter("business"));
                String uploadFileUrl = getUploadFileUrl(req, uploadPath);
                srcFile = StringUtils.isBlank(srcFile)? "test.jpg" : srcFile;
                String originFileName = getOriginFileName(srcFile, Integer.parseInt(index));
                targetFile = new File(uploadFileUrl, originFileName);
                os = new FileOutputStream(targetFile);
                os.write(b);
                os.flush();
                model.put("data", getViewWebUrl(uploadPath) + originFileName);
                putSuccess(model, "");
                if(targetFile != null){
//                    ImageFileHandler.pressImage(targetFile.getPath());
                    targetFile = null;
                }
            }else{
                putError(model, "上传的base64码格式不正确");
            }
        } catch (Exception ex) {
            logger.error("上传文件出错：", ex);
            putError(model, ex.getMessage());
        }finally{
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        responseAsJson(model, resp);
    }
}
