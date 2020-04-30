package com.luoben.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.luoben.servicebase.exceptionhandler.MyException;
import com.luoben.vod.service.VodService;
import com.luoben.vod.utils.InitVodClient;
import com.luoben.vod.utils.VodPropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@Slf4j
public class VodServiceImpl implements VodService {

    /**
     * 上传视频到阿里云
     *
     * @param file
     * @return
     */
    @Override
    public String uploadALiyVideo(MultipartFile file) {

        try {
            //fileName : 上传文件原始名称
            String filename = file.getOriginalFilename();
            //title : 上传到阿里云后显示的文件名称
            String title = filename.substring(0, filename.lastIndexOf("."));

            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET, title, filename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.warn(errorMessage);
                if (StringUtils.isEmpty(videoId)) {
                    throw new MyException(20001, errorMessage);
                }
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "service vod 服务上传失败");
        }

    }

    /**
     * 删除阿里云视频
     *
     * @param videoId
     */
    @Override
    public void removeVideo(String videoId) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);

            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException(20001, "云端视频删除失败");
        }
    }
}
