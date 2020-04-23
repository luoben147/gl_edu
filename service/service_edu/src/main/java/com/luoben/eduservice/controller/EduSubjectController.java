package com.luoben.eduservice.controller;


import com.luoben.commonutils.R;
import com.luoben.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    /**
     * 添加课程分类
     * 获取上传过来的Excel文件，把文件内容读出来
     */
    @ApiOperation(value = "excel导入课程", notes = "excel导入课程(一级课程，二级课程)")
    @PostMapping("addSubject")
    public R addSubject(
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

}

