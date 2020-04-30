package com.luoben.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luoben.commonutils.R;
import com.luoben.eduservice.entity.EduTeacher;
import com.luoben.eduservice.service.EduTeacherService;
import com.luoben.eduservice.vo.TeacherVo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author luoben
 * @since 2020-04-21
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询讲师列表
     *
     * @return
     */
    @ApiOperation(value = "查询讲师列表，返回所有讲师信息", notes = "查询所有讲师")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * 逻辑删除讲师
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据讲师编号逻辑删除讲师", notes = "删除讲师")
    @ApiImplicitParam(name = "id", required = true, value = "讲师ID", type = "String")
    public R removeTeacher(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

    /**
     * 分页查询讲师
     */
    @ApiOperation(value = "分页查询讲师列表", notes = "分页查询讲师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", type = "Long", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页大小", defaultValue = "5", type = "Long", paramType = "query")

    })
    @GetMapping("pageTeacher")
    public R pageListTeacher(@RequestParam(value = "current") Long current,
                             @RequestParam(value = "limit") Long limit) {

        Page<EduTeacher> page = new Page<>(current, limit);
        teacherService.page(page, null);

        long total = page.getTotal();//总记录数
        List<EduTeacher> records = page.getRecords(); //数据list

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);

        return R.ok().data(map);
    }


    /**
     * 条件查询带分页
     */
    @ApiOperation(value = "条件查询讲师列表", notes = "条件查询讲师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页大小", defaultValue = "5", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "teacherQuery", value = "查询条件", dataType = "TeacherVo", paramType = "body")

    })
    @PostMapping("pageTeacherCondition")
    public R pageTeacherCondition(@RequestParam("page") Long pageNum,
                                  @RequestParam("limit") Long limit,
                                  @RequestBody() TeacherVo teacherVo) {

        Page<EduTeacher> page = new Page<>(pageNum, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        // 多条件组合查询
        // mybatis学过 动态sql
        String name = teacherVo.getName();

        Integer level = teacherVo.getLevel();
        String begin = teacherVo.getBegin();
        String end = teacherVo.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (null != level) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        teacherService.page(page, wrapper);

        long total = page.getTotal();//总记录数
        List<EduTeacher> records = page.getRecords(); //数据list集合
        return R.ok().data("total", total).data("rows", records);
    }


    /**
     * 添加讲师
     *
     * @return
     */
    @ApiOperation(value = "添加讲师", notes = "添加讲师")
    @ApiResponses({
            @ApiResponse(code = 20000, message = "添加成功"),
            @ApiResponse(code = 20001, message = "添加失败")
    })
    @PostMapping()
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.save(eduTeacher);
        return flag ? R.ok() : R.error();
    }


    /**
     * 根据讲师id查询
     */
    @ApiOperation(value = "根据ID查询讲师", notes = "根据ID查询讲师")
    @ApiImplicitParam(name = "id", required = true, value = "讲师ID",paramType = "path",type = "String")
    @GetMapping("{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    /**
     * 修改讲师
     */
    @ApiOperation(value = "根据ID修改讲师", notes = "根据ID修改讲师")
    @PutMapping("{id}")
    public R updateTeacher(
            @ApiParam(name = "id",value = "讲师ID",required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher",value = "讲师对象",required = true)
            @RequestBody EduTeacher eduTeacher){
        eduTeacher.setId(id);
        boolean flag = teacherService.updateById(eduTeacher);
        return flag? R.ok():R.error();
    }
}

