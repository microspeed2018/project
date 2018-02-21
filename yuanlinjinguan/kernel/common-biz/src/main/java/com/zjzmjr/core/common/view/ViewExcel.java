package com.zjzmjr.core.common.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.zjzmjr.common.util.RandomCodeUtil;

/**
 * 读取源excel文件，下载到本地的excel视图
 * 
 * @author lenovo
 * @version $Id: ViewExcel.java, v 0.1 2017-1-13 上午10:52:00 lenovo Exp $
 */
public class ViewExcel extends AbstractExcelView{

    private static final Logger logger = LoggerFactory.getLogger(ViewExcel.class);

	/**
	 * 源excel文件
	 */
	private String srcFilePath;
	
	/**
	 * 读取源excel文件，下载到本地的excel视图
	 * 
	 * @see org.springframework.web.servlet.view.document.AbstractExcelView#buildExcelDocument(java.util.Map, org.apache.poi.hssf.usermodel.HSSFWorkbook, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(org.apache.commons.lang.StringUtils.isBlank(getSrcFilePath())){
            return;
        }
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    String type =  srcFilePath.substring(srcFilePath.lastIndexOf("."));
        String finalFileName = "print" + sdf.format(new Date()) + RandomCodeUtil.getRandomCode(RandomCodeUtil.MODE_NUMBER, 3) + type;
        resp.setContentType("application/vnd.ms-excel;charset=UTF-8");
        // 文件名
        resp.addHeader("Content-Disposition", "attachment;filename=" + StringUtils.newStringIso8859_1(finalFileName.getBytes()));
        // resp.setContentLength((int) file.length());
        byte[] buffer = new byte[4096];// 缓冲区
        OutputStream output = null;
        BufferedInputStream input = null;
        InputStream fis = null;
        File file = null;
        try {
            file = new File(getSrcFilePath());
            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(file));
            input = new BufferedInputStream(fis);

            output = new BufferedOutputStream(resp.getOutputStream());
            int n = -1;
            // 遍历，开始下载
            while ((n = input.read(buffer, 0, 4096)) > -1) {
                output.write(buffer, 0, n);
            }
            output.flush(); // 不可少
            resp.flushBuffer();// 不可少
        } catch (Exception e) {
            // 异常自己捕捉
            logger.error("excel文件下载错误", e);
        } finally {
            // 关闭流，不可少
            if (fis != null)
                fis.close();
            if (input != null)
                input.close();
            if (output != null)
                output.close();
            if (file != null) {
                if (file.exists()) {
                    file.delete();
                }
                file = null;
            }
        }
    }

    public String getSrcFilePath() {
        return srcFilePath;
    }

    public void setSrcFilePath(String srcFilePath) {
        this.srcFilePath = srcFilePath;
    }
    
}
