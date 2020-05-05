package com.luoben.vod.controller;

import com.luoben.commonutils.R;
import com.luoben.vod.service.VodService;
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
}
