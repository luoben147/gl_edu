package com.luoben.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.luoben.commonutils.R;
import com.luoben.servicebase.exceptionhandler.MyException;
import com.luoben.vod.service.VodService;
import com.luoben.vod.utils.InitVodClient;
import com.luoben.vod.utils.VodPropertiesUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Api(description="阿里云视频点播微服务")
@CrossOrigin //跨域
@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     */
    @PostMapping("uploadALiyVideo")
    public R uploadALiyVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file){
        String videoId = vodService.uploadALiyVideo(file);
        return R.ok().message("视频上传成功").data("videoId", videoId);
    }


    /**
     * 删除阿里云视频
     */
    @ApiOperation(value = "根据ID删除阿里云视频")
    @DeleteMapping("{videoId}")
    public R removeAliyVideo(
            @ApiParam(name = "videoId", value = "云端视频id", required = true)
            @PathVariable String videoId){
        vodService.removeVideo(videoId);
        return R.ok().message("视频删除成功");
    }

    /**
     * 删除多个阿里云视频
     */
    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("delete-batch")
    public R deleteBatch(
            @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
            @RequestParam("videoIdList") List<String> videoIdList){

        vodService.removeVideoList(videoIdList);
        return R.ok().message("视频删除成功");
    }

    /**
     * 根据视频id获取视频凭证
     */
    @ApiOperation(value = "根据视频id获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){

        try {
            //初始化
            DefaultAcsClient client = InitVodClient.initVodClient(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET);

            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            request.setVideoId(id);
            response = client.getAcsResponse(request);
            //播放凭证
            String playAuth=response.getPlayAuth();
            //返回结果
            return R.ok().message("获取凭证成功").data("playAuth", playAuth);
        } catch (Exception e) {
           throw new MyException(20001,"获取凭证失败");
        }

    }

}
