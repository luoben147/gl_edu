package com.luoben.eduservice.controller;


import com.luoben.commonutils.R;
import com.luoben.eduservice.vo.OneSubject;
import com.luoben.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author luoben
 * @since 2020-04-23
 */
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    /**
     * 添加课程分类
     * 获取上传过来的Excel文件，把文件内容读出来
     */
    @ApiOperation(value = "excel导入课程", notes = "excel导入课程(一级课程，二级课程)")
    @PostMapping("import")
    public R importSubject(
            @ApiParam(name = "file",value = "课程分类excel文件",required = true)
            MultipartFile file){
        try {
            subjectService.saveSubject(file,subjectService);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    /**
     * 课程分类列表（树形结构）
     * @return
     */
    @ApiOperation(value = "嵌套课程分类列表")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list= subjectService.getAllOneAndTowSubject();
        return R.ok().data("list",list);
    }


}

