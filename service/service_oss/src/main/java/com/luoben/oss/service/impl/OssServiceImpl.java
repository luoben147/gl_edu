package com.luoben.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.luoben.oss.service.OssService;
import com.luoben.oss.utils.OssPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传文件到阿里云OSS
     *
     * @param file
     * @return
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = OssPropertiesUtils.END_POINT;
        String accessKeyId = OssPropertiesUtils.KEY_ID;
        String accessKeySecret = OssPropertiesUtils.KEY_SECRET;
        String bucketName = OssPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String filename = file.getOriginalFilename();

            //1.uuid 避重
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename= uuid+filename;
            //2. 把文件按日期分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            filename=datePath+"/"+filename;

            //第一个参数 ：Bucker名称
            //第二个参数 ：上传到oss文件路径和文件名称    /aa/bb/1.jpg
            ossClient.putObject(bucketName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //需要把上传到阿里云oss路径手动拼接
            //https://gl-edu.oss-cn-beijing.aliyuncs.com/dog1.jpg
            String url="https://"+bucketName+"."+endpoint+"/"+filename;

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
