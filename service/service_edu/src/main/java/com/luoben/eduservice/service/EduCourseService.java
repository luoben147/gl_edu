package com.luoben.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luoben.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luoben.eduservice.vo.CourseApiQueryVo;
import com.luoben.eduservice.vo.CourseInfoVo;
import com.luoben.eduservice.vo.CoursePublishVo;
import com.luoben.eduservice.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfoById(String courseId);

    /**
     * 课程修改
     * @param courseInfoVo
     */
    void updateCourseInfoById(CourseInfoVo courseInfoVo);

    /**
     * 根据ID获取课程发布信息
     * @param id
     * @return
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 根据id发布课程
     * @param id
     */
    Boolean publishCourseById(String id);

    /**
     * 分页课程列表
     * @param pageParam
     * @param courseQuery
     */
    void pageQuery(Page<EduCourse> pageParam, EduCourse courseQuery);

    /**
     * 删除课程
     * @param id
     * @return
     */
    boolean removeCourseById(String id);

    /**
     * 根据讲师id查询课程
     * @param id
     * @return
     */
    List<EduCourse> selectByTeacherId(String id);

    /**
     * 条件查询课程
     * @param pageParam
     * @param courseQuery
     * @return
     */
    Map<String,Object> pageListWeb(Page<EduCourse> pageParam, CourseApiQueryVo courseQuery);

    /**
     * 根据课程id查询 课程信息
     * @param courseId
     * @return
     */
    CourseWebVo selectInfoWebById(String courseId);
}
