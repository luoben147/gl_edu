package com.luoben.eduservice.client;

import com.luoben.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component //注解防止，在其他位置注入CodClient时idea报错
public interface VodClient {

    //定义调用的接口 完全路径
    //@PathVariable 等参数注解一定要指定参数名称，否则出错
    //根据视频id删除阿里云视频
    @DeleteMapping("/eduvod/video/{videoId}")
    public R removeAliyVideo(@PathVariable("videoId") String videoId);

    //批量删除视频
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);

}
