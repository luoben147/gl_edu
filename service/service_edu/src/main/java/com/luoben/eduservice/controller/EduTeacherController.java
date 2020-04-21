package com.luoben.eduservice.controller;


import com.luoben.eduservice.entity.EduTeacher;
import com.luoben.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询讲师列表
     * @return
     */
    @ApiOperation(value = "查询讲师列表，返回所有讲师信息", notes = "查询所有讲师")
    @GetMapping("findAll")
    public List<EduTeacher> findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        return list;
    }

    /**
     * 逻辑删除讲师
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据讲师编号逻辑删除讲师", notes = "删除讲师")
    @ApiImplicitParam(name = "id", required = true, value = "讲师ID",type = "String")
    public boolean removeTeacher(@PathVariable String id){
        boolean flag = teacherService.removeById(id);
        return flag;
    }

}

