package com.luoben.eduservice.client;

import com.luoben.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-oss")
@Component //注解防止，在其他位置注入CodClient时idea报错
public interface OssClient {

    @ApiOperation(value = "删除Oss文件", notes = "删除Oss文件")
    @DeleteMapping("/eduoss/fileoss")
    public R deleteOssFile(@RequestParam("fileUrl") String fileUrl);
}
