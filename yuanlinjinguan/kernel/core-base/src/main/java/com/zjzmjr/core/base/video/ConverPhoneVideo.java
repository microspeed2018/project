package com.zjzmjr.core.base.video;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.PropertyUtils;

public class ConverPhoneVideo {

    private static final Logger logger = LoggerFactory.getLogger(ConverPhoneVideo.class);

    private Date dt;

    private long begintime;

    private String PATH; // 源文件的路径，需要转换的源文件路径及名称

    private String filerealname; // 文件名 不包括扩展名

    private String filename; // 包括扩展名

    private String videofolder = "\\other\\"; // 别的格式视频的目录

    private String flvfolder = "\\flv\\"; // flv视频的目录

    private String ffmpegpath = PropertyUtils.getPropertyFromFile("commBiz", "VIDEO_CONVER_ADDRESS"); // ffmpeg.exe的目录

    private String mencoderpath = "\\mencoder\\"; // mencoder的目录

    private String videoRealPath = "\\flv\\"; // 截图的视频目录;

    private String imageRealPath = "\\img\\"; // 截图的存放目录

    /**
     * 生成的真实的视频文件
     */
    private String videoRealFile;

    /**
     * 生成的真实的图片文件
     */
    private String imageRealFile;

    /**
     * 视频转换工具类
     * 
     * @param savePath
     * @param srcFile
     */
    public ConverPhoneVideo(String savePath, String srcFile) {
        this.videofolder = checkPath(savePath, this.videofolder);
        this.flvfolder = checkPath(savePath, this.flvfolder);
        this.mencoderpath = checkPath(savePath, this.mencoderpath);
        this.videoRealPath = checkPath(savePath, this.videoRealPath);
        this.imageRealPath = checkPath(savePath, this.imageRealPath);
//        checkPath(this.videofolder);
//        checkPath(this.flvfolder);
//        checkPath(this.mencoderpath);
//        checkPath(this.videoRealPath);
//        checkPath(this.imageRealPath);
        this.PATH = srcFile;
    }

    public ConverPhoneVideo(String path) {
        PATH = path;
    }

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String path) {
        PATH = path;
    }
    
    public ResultEntry<String> beginConver() {
        ResultEntry<String> result = new ResultEntry<String>();
        File fi = new File(PATH);
        filename = fi.getName();
        filerealname = filename.substring(0, filename.lastIndexOf(".")).toLowerCase();
        logger.info("----接收到文件(" + PATH + ")需要转换-------------------------- ");
        if (!checkfile(PATH)) {
            logger.info(PATH + "文件不存在" + " ");
            result.setErrorMsg(PATH + "文件不存在" + " ");
            result.setSuccess(false);
            return result;
        }
        dt = new Date();
        begintime = dt.getTime();
        logger.info("----开始转文件(" + PATH + ")-------------------------- ");
        result = process();
        if (result.isSuccess()) {
            Date dt2 = new Date();
            logger.info("转换成功 ");
            long endtime = dt2.getTime();
            long timecha = (endtime - begintime);
            String totaltime = sumTime(timecha);
            logger.info("共用了:" + totaltime + " ");
            if (processImg()) {
                logger.info("截图成功了 ");
            } else {
                logger.info("截图不成功了 ");
            }
            PATH = null;
            return result;
        } else {
            PATH = null;
            return result;
        }
    }

    public boolean processImg() {

        List<String> commend = new java.util.ArrayList<String>();
        commend.add(ffmpegpath);
        commend.add("-i");
        commend.add(videoRealPath + "\\" + filerealname + ".flv");
        commend.add("-y");
        commend.add("-f");
        commend.add("image2");
        commend.add("-ss");
        commend.add("38");
        commend.add("-t");
        commend.add("0.001");
        commend.add("-s");
        commend.add("320x240");
        commend.add(imageRealPath + "\\" + filerealname + ".jpg");
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.start();
            setImageRealFile("img/" + filerealname + ".jpg");
            return true;
        } catch (Exception e) {
            logger.error("processImg", e);
            return false;
        }
    }

    private ResultEntry<String> process() {
        int type = checkContentType();
        ResultEntry<String> status = new ResultEntry<String>();
        if (type == 0) {

            // status = processFLV(PATH);// 直接将文件转为flv文件
            status = processFLV(PATH);
        } else if (type == 1) {
            String avifilepath = processAVI(type);
            if (avifilepath == null)
                status.setSuccess(false);
                // avi文件没有得到
            else {
                logger.info("开始转换视频文件");
                status = processFLV(avifilepath);// 将avi转为flv
            }
        }
        return status;
    }

    private int checkContentType() {
        String type = PATH.substring(PATH.lastIndexOf(".") + 1, PATH.length()).toLowerCase();
        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mpg")) {
            return 0;
        } else if (type.equals("wmv")) {
            return 0;
        } else if (type.equals("3gp")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("asf")) {
            return 0;
        } else if (type.equals("asx")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        }
        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if (type.equals("wmv9")) {
            return 1;
        } else if (type.equals("rm")) {
            return 1;
        } else if (type.equals("rmvb")) {
            return 1;
        }
        return 9;
    }

    private boolean checkfile(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 
     * 
     * @param path
     * @return
     */
    private String checkPath(String savePath, String path){
        File file = new File(savePath, path);
        if (file.exists()) {
//            return true;
        } else {
            file.mkdirs();
//            return true;
        }
        return file.getPath();
    }

    // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
    private String processAVI(int type) {
        List<String> commend = new java.util.ArrayList<String>();
        commend.add(mencoderpath);
        commend.add(PATH);
        commend.add("-oac");
        commend.add("mp3lame");
        commend.add("-lameopts");
        commend.add("preset=64");
        commend.add("-ovc");
        commend.add("xvid");
        commend.add("-xvidencopts");
        commend.add("bitrate=600");
        commend.add("-of");
        commend.add("avi");
        commend.add("-o");
        commend.add(videofolder + filerealname + ".avi");
        // 命令类型：mencoder 1.rmvb -oac mp3lame -lameopts preset=64 -ovc xvid
        // -xvidencopts bitrate=600 -of avi -o rmvb.avi
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();

            doWaitFor(p);
            return videofolder + filerealname + ".avi";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
    private ResultEntry<String> processFLV(String oldfilepath) {
        ResultEntry<String> result = new ResultEntry<String>();
        if (!checkfile(PATH)) {
            logger.error(oldfilepath + " is not file");
            result.setErrorMsg(oldfilepath + " is not file");
            result.setSuccess(false);
            return result;
        }

        List<String> commend = new java.util.ArrayList<String>();
        commend.add(ffmpegpath);
        commend.add("-i");
        commend.add(oldfilepath);
        commend.add("-ab");
        commend.add("64");
        commend.add("-acodec");
        commend.add("libmp3lame");
        commend.add("-ac");
        commend.add("2");
        commend.add("-ar");
        commend.add("22050");
        commend.add("-b");
        commend.add("230");
        commend.add("-r");
        commend.add("24");
        commend.add("-y");
        commend.add(flvfolder + "\\" +filerealname + ".flv");
        try {
            ProcessBuilder builder = new ProcessBuilder();
            String cmd = commend.toString();
            System.out.println(cmd);
            builder.command(commend);
            Process p = builder.start();
            int exitValue = doWaitFor(p);
            p.destroy();
            if (GenerateConstants.number_0.equals(exitValue)){
                deleteFile(oldfilepath);
                setVideoRealFile("flv/" + filerealname + ".flv");
                result.setT(videoRealFile);
                result.setSuccess(true);
            } else {
                result.setErrorMsg("转码处理失败");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @SuppressWarnings("static-access")
    public int doWaitFor(Process p) {
        InputStream in = null;
        InputStream err = null;
        int exitValue = -1; // returned to caller when p is finished
        try {
            System.out.println("comeing");
            in = p.getInputStream();
            err = p.getErrorStream();
            boolean finished = false; // Set to true when p is finished

            while (!finished) {
                try {
                    while (in.available() > 0) {
                        Character c = new Character((char) in.read());
                        System.out.print(c);
                    }
                    while (err.available() > 0) {
                        Character c = new Character((char) err.read());
                        System.out.print(c);
                    }

                    exitValue = p.exitValue();
                    finished = true;

                } catch (IllegalThreadStateException e) {
                    Thread.currentThread().sleep(500);
                }
            }
        } catch (Exception e) {
            logger.error("doWaitFor();: unexpected exception - " + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return exitValue;
    }

    public void deleteFile(String filepath) {
        File file = new File(filepath);
        if (PATH.equals(filepath)) {
            if (file.delete()) {
                logger.info("文件" + filepath + "已删除");
            }
        } else {
            if (file.delete()) {
                logger.info("文件" + filepath + "已删除 ");
            }
            File filedelete2 = new File(PATH);
            if (filedelete2.delete()) {
                logger.info("文件" + PATH + "已删除");
            }
        }
    }

    public String sumTime(long ms) {
        int ss = 1000;
        long mi = ss * 60;
        long hh = mi * 60;
        long dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day + "天" : "" + day + "天";
        String strHour = hour < 10 ? "0" + hour + "小时" : "" + hour + "小时";
        String strMinute = minute < 10 ? "0" + minute + "分" : "" + minute + "分";
        String strSecond = second < 10 ? "0" + second + "秒" : "" + second + "秒";
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond + "毫秒" : "" + strMilliSecond + " 毫秒";
        return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " " + strMilliSecond;

    }

    /**
     * 生成的真实的视频文件
     * 
     * @return
     */
    public String getVideoRealFile() {
        return videoRealFile;
    }

    /**
     * 生成的真实的视频文件
     * 
     * @param videoRealFile
     */
    public void setVideoRealFile(String videoRealFile) {
        this.videoRealFile = videoRealFile;
    }

    /**
     * 生成的真实的图片文件
     * 
     * @return
     */
    public String getImageRealFile() {
        return imageRealFile;
    }

    /**
     * 生成的真实的图片文件
     * 
     * @param imageRealFile
     */
    public void setImageRealFile(String imageRealFile) {
        this.imageRealFile = imageRealFile;
    }

    /**
     * 获取视频信息
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public Map<String, Integer> getVideoWidthAndHeight(String path) throws Exception {
        Map<String, Integer> map = new HashMap<String, Integer>();
        IContainer container = IContainer.make();
        File file = new File(path);
        if(file.isFile() && file.exists()){
            int result = container.open(path,IContainer.Type.READ, null);
            if (result < 0) {
                throw new Exception("Failed to open media file");
            }
            for (int i = 0; i < container.getNumStreams(); i++) {
                IStream stream = container.getStream(i);
                IStreamCoder coder = stream.getStreamCoder();
                if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
                    // 音频
                } else if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                    // 视频
                    map.put("width", coder.getWidth());
                    map.put("height", coder.getHeight());
                }
            }
        } else {
            // 视频
            map.put("width", 150);
            map.put("height", 150);
        }
        return map;
    }
}
