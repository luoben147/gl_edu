package com.luoben.eduservice.controller;


import com.luoben.commonutils.R;
import com.luoben.eduservice.entity.EduChapter;
import com.luoben.eduservice.service.EduChapterService;
import com.luoben.eduservice.vo.ChapterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(description = "课程章节管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 根据课程Id获取课程大纲列表
     *
     * @return
     */
    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {

        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("items", chapterVoList);
    }

    /**
     * 添加章节
     */
    @ApiOperation(value = "新增章节")
    @PostMapping()
    public R addChapter(
            @ApiParam(name = "EduChapter", value = "章节对象", required = true)
            @RequestBody EduChapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    /**
     * 根据Id查询章节
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询章节")
    @GetMapping("{id}")
    public R getChapterById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id) {
        EduChapter chapter = chapterService.getById(id);
        return R.ok().data("item", chapter);
    }

    /**
     * 修改章节
     */
    @ApiOperation(value = "根据ID修改章节")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody EduChapter chapter) {
        chapter.setId(id);
        chapterService.updateById(chapter);
        return R.ok();
    }

    /**
     * 删除章节
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id) {
        boolean result = chapterService.removeChapterById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除章节失败");
        }
    }

}

