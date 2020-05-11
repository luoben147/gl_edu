package com.luoben.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luoben.eduservice.entity.EduCourse;
import com.luoben.eduservice.vo.CoursePublishVo;
import com.luoben.eduservice.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author luoben
 * @since 2020-04-27
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    public CourseWebVo selectInfoWebById(String courseId);
}
