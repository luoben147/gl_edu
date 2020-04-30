package com.luoben.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    String uploadALiyVideo(MultipartFile file);

    /**
     * 删除阿里云视频
     * @param videoId
     */
    void removeVideo(String videoId);
}
