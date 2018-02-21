package com.zjzmjr.core.common.biz.weixin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.core.model.weixin.wechat.MsgTypeEnum;

/**
 * 多文件上传工具类
 * @author Administrator
 *
 */
public class MultiMediaUpload {

    private static final Logger logger = LoggerFactory.getLogger(MultiMediaUpload.class);

	/**
	 * 上传图片，只能获取url
	 */
	private static final String UPLOAD_IMAGE_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";

	/**
	 * 上传多媒体文件，旧接口
	 */
	private static final String UPLOAD_MEDIA_FILE_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * 新增临时素材接口，上传多媒体文件新接口
	 */
	private static final String CREATE_SOURCE_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	
	/**
	 * 上传文件大小限制，以新增临时素材接口要求为准
	 * 图片（image）: 2M，支持bmp/png/jpeg/jpg/gif格式
	 * 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
	 * 视频（video）：10MB，支持MP4格式
	 * 缩略图（thumb）：64KB，支持JPG格式
	 * 
	 * 群发接口上传图片获取URL：image-mass 图片仅支持jpg/png格式，大小必须在1MB以下。
	 */
	public boolean limitFileSize(File file,String type){
		String name=file.getName().toUpperCase();
		long size=file.length();
		if("image".equals(type)){
			String[] extendNameArray={".BMP",".PNG",".JPEG",".JPG",".GIF"};
			for(String extendName:extendNameArray){
				if(name.endsWith(extendName)){
					if(size<=2*1024*1024){
						return true;
					}else{
						return false;
					}
				}
			}
		}else if("voice".equals(type)&&(name.endsWith(".AMR")||name.endsWith(".MP3"))){
			if(size<=2*1024*1024){
				return true;
			}else{
				return false;
			}
		}else if("video".equals(type)&&name.endsWith(".MP4")){
			if(size<=10*1024*1024){
				return true;
			}else{
				return false;
			}
		}else if("thumb".equals(type)&&name.endsWith(".JPG")){
			if(size<=64*1024){
				return true;
			}else{
				return false;
			}
		}else if("image-mass".equals(type)&&(name.endsWith(".JPG")||name.equals(".PNG"))){
			if(size<=1*1024*1024){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 上传图片获取url
	 */
	public String uploadImage(String filePath,String access_token){
		File file=new File(filePath);
		String result=null;
		if(limitFileSize(file, "image-mass")){
			String url=UPLOAD_IMAGE_URL.replace("ACCESS_TOKEN", access_token).replace("\"", "");
			Map<String,File> fileParams=new HashMap<String, File>();
			fileParams.put("media", file);
			String data=HttpsRequestUtil.multiplyPartRequest(url, fileParams);
			result=JSONProcesserUtil.getValue(data, "url");
		}else{
			result="文件不合法！";
		}
		return result;
	}	
	
	/**
	 * 上传多媒体文件，旧接口
	 */
	public String uploadMediaFile(String filePath,String access_token,String type){
		File file=new File(filePath);
		String result=null;
		if(limitFileSize(file, type)){
			String url=UPLOAD_MEDIA_FILE_URL.replace("ACCESS_TOKEN", access_token).replace("\"", "").replace("TYPE", type);
			Map<String,File> fileParams=new HashMap<String, File>();
			fileParams.put("media", file);
			String data=HttpsRequestUtil.multiplyPartRequest(url, fileParams);
			if(type.equals(MsgTypeEnum.THUMB_MSG.getMsgEnName())){
				result=JSONProcesserUtil.getValue(data, "thumb_media_id");
			}else {
				result=JSONProcesserUtil.getValue(data, "media_id");
			}
		}else{
			result="文件不合法！";
		}
		return result;
	}	
	
	/**
	 * 新增临时素材，上传多媒体文件，新接口，在用
	 */
	public String createSourceMaterial(String filePath,String access_token,String type){
		File file=new File(filePath);
		String result=null;
		if(limitFileSize(file, type)){
			String url=CREATE_SOURCE_MATERIAL_URL.replace("ACCESS_TOKEN", access_token).replace("\"", "").replace("TYPE", type);
			Map<String,File> fileParams=new HashMap<String, File>();
			fileParams.put("media", file);
			String data=HttpsRequestUtil.multiplyPartRequest(url, fileParams);
			if(type.equals(MsgTypeEnum.THUMB_MSG.getMsgEnName())){
				result=JSONProcesserUtil.getValue(data, "thumb_media_id");
			}else {
				result=JSONProcesserUtil.getValue(data, "media_id");
			}
		}else{
			result="文件不合法！";
		}
		return result;
	}

    /**
     * 获取媒体文件
     * 
     * @param accessToken
     *            接口访问凭证
     * @param media_id
     *            媒体文件ID
     * @param savePath
     *            文件在服务器上的存储路径
     * @param fileExt
     *            文件类型
     * */
    public static String downloadMedia(String accessToken, String mediaId, String savePath, String fileExt) {
        String filePath = null;
        // 拼接请求地址
        String requestUrl = "http://api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id=" + mediaId;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
        try {
            logger.info("微信多媒体文件获取地址：{}", requestUrl);
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            if (!savePath.endsWith("/")) {
                savePath += "/";
            }
            File fileDir = new File(savePath);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            // 根据内容类型获取扩展名
            // String fileExt =
            // WeixinUtil.getFileEndWitsh(conn.getHeaderField("Content-Type"));
            // 将mediaId作为文件名
            filePath = savePath + mediaId + fileExt;

            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            byte[] buf = new byte[8096000];
            int size = 0;
            while ((size = bis.read(buf)) != -1)
                fos.write(buf, 0, size);
            fos.close();
            bis.close();

            conn.disconnect();
            logger.info("下载媒体文件成功，filePath=" + filePath);
        } catch (Exception e) {
            filePath = null;
            logger.error("下载媒体文件失败：{}", e);
        }
        return filePath;
    }

}
