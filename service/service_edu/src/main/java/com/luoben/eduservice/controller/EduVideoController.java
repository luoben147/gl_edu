package com.luoben.eduservice.controller;


import com.luoben.commonutils.R;
import com.luoben.eduservice.entity.EduVideo;
import com.luoben.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author luoben
 * @since 2020-04-27
 */
@Api(description = "课时管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;


    @ApiOperation(value = "新增课时")
    @PostMapping("saveVideoInfo")
    public R save(
            @ApiParam(name = "videoForm", value = "课时对象", required = true)
            @RequestBody EduVideo videoInfo) {
        videoService.save(videoInfo);
        return R.ok();
    }


    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("videoInfo/{id}")
    public R getVideInfoById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id) {
        EduVideo videoInfo = videoService.getById(id);
        return R.ok().data("item", videoInfo);
    }


    @ApiOperation(value = "更新课时")
    @PutMapping("updateVideoInfo/{id}")
    public R updateVideoInfoById(
            @ApiParam(name = "videoInfo", value = "课时基本信息", required = true)
            @RequestBody EduVideo videoInfo,
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id) {
        videoService.updateById(videoInfo);
        return R.ok();
    }

    /**
     * 删除小节
     * 同时删除小节内的视频
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除课时")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id) {
        boolean result = videoService.removeVideoById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除课时失败");
        }
    }

}

