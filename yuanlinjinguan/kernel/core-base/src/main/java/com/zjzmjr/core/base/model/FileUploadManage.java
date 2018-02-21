package com.zjzmjr.core.base.model;

import java.io.File;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 文件上传
 * 
 * @author oms
 * @version $Id: FileUploadManage.java, v 0.1 2017-6-14 下午2:09:28 oms Exp $
 */
@WebService
public interface FileUploadManage {

    /**
     * ftp上传文件
     * 
     * @param multipartRequest
     * @param uploadType
     * @return
     */
    boolean ftpUpload(@WebParam(name = "file") File srcfile, @WebParam(name = "path") String path, @WebParam(name = "name") String fileName);
    
}
