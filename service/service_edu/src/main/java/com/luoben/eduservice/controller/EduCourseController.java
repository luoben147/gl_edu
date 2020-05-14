package com.luoben.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luoben.commonutils.R;
import com.luoben.eduservice.entity.EduCourse;
import com.luoben.eduservice.service.EduCourseService;
import com.luoben.eduservice.vo.CourseInfoVo;
import com.luoben.eduservice.vo.CoursePublishVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author luoben
 * @since 2020-04-27
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 课程列表
     */
    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
              EduCourse courseQuery) {

        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }


    /**
     * 添加课程基本信息
     */
    @ApiOperation(value = "新增课程")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo) {
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        if (!StringUtils.isEmpty(courseId)) {
            return R.ok().data("courseId", courseId);
        } else {
            return R.error().message("保存失败");
        }
    }

    /**
     * 根据课程id查询课程基本信息
     */
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String courseId) {

        CourseInfoVo courseInfoVo = courseService.getCourseInfoById(courseId);
        return R.ok().data("item", courseInfoVo);
    }

    /**
     * 课程修改
     *
     * @param courseInfoVo
     * @param id
     * @return
     */
    @ApiOperation(value = "更新课程")
    @PutMapping("updateCourseInfo/{id}")
    public R upDateCourseInfo(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo,
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        courseInfoVo.setId(id);
        courseService.updateCourseInfoById(courseInfoVo);
        return R.ok();
    }

    /**
     * 获取发布课程信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("coursePublishInfo/{id}")
    public R getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", coursePublishVo);
    }

    /**
     * 发布课程
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publishCourse/{id}")
    public R publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {

        courseService.publishCourseById(id);
        return R.ok();
    }

    /**
     * 删除课程
     */
    @ApiOperation(value = "根据id删除课程")
    @DeleteMapping("{id}")
    public R removeCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        boolean flag= courseService.removeCourseById(id);
        return flag ? R.ok() : R.error();
    }

}

