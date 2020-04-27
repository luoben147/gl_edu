package com.luoben.eduservice.service;

import com.luoben.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luoben.eduservice.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author luoben
 * @since 2020-04-27
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseInfoVo
     */
    String  saveCourseInfo(CourseInfoVo courseInfoVo);
}
