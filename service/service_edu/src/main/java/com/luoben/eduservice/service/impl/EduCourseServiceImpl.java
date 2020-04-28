package com.luoben.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoben.eduservice.entity.EduCourse;
import com.luoben.eduservice.entity.EduCourseDescription;
import com.luoben.eduservice.mapper.EduCourseMapper;
import com.luoben.eduservice.service.EduCourseService;
import com.luoben.eduservice.vo.CourseInfoVo;
import com.luoben.eduservice.vo.CoursePublishVo;
import com.luoben.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author luoben
 * @since 2020-04-27
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述
    @Autowired
    EduCourseDescriptionServiceImpl courseDescriptionService;


    /**
     * 添加课程基本信息
     * @param courseInfoVo
     */
    @Override
    public String  saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        boolean resultCourseInfo = this.save(eduCourse);
        if(!resultCourseInfo){
            throw new MyException(20001,"添加课程失败");
        }

        //获取添加之后课程id
        String cid = eduCourse.getId();

        //2.课程简介表添加课程简介
        EduCourseDescription courseDescription=new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        boolean resultDescription = courseDescriptionService.save(courseDescription);
        if(!resultDescription){
            throw new MyException(20001, "课程详情信息保存失败");
        }
        return cid;
    }

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        //课程表
        CourseInfoVo result=new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse,result);
        //课程简介表
        EduCourseDescription description = courseDescriptionService.getById(courseId);
        result.setDescription(description.getDescription());

        return result;
    }

    /**
     * 修改课程
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfoById(CourseInfoVo courseInfoVo) {
        //保存课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        Integer count = baseMapper.updateById(course);
        if(count==0){
            throw new MyException(20001, "课程信息修改失败");
        }

        //保存课程详情信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(course.getId());
        boolean resultDescription = courseDescriptionService.updateById(courseDescription);
        if(!resultDescription){
            throw new MyException(20001, "课程详情信息修改失败");
        }
    }

    /**
     * 根据ID获取课程发布信息
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    /**
     * 根据id发布课程
     * @param id
     */
    @Override
    public Boolean publishCourseById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus(EduCourse.COURSE_NORMAL);
        Integer count = baseMapper.updateById(course);
        return null != count && count > 0;
    }
}
