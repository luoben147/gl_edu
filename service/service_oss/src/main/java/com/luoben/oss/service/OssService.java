package com.luoben.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    /**
     * 上传文件到阿里云OSS
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file);

    /**
     * 删除文件
     * @param objectName
     */
    boolean deleteOssFile(String objectName);
}
