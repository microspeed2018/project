package com.zjzmjr.core.base.video;

import java.io.File;
import java.util.List;

public class ProcessFlvImg {

    public String ffmpegpath = "D:\\video\\FFmpeg\\bin\\ffmpeg.exe"; // ffmpeg.exe的目录

    public boolean processImg(String path) {

        File fi = new File(path);

        List commend = new java.util.ArrayList();
        commend.add(ffmpegpath);
        commend.add("-i");
        commend.add(path);
        commend.add("-y");
        commend.add("-f");
        commend.add("image2");
        commend.add("-ss");
        commend.add("38");
        commend.add("-t");
        commend.add("0.001");
        commend.add("-s");
        commend.add("320x240");
        commend.add(fi.getPath() + ".jpg");

        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
