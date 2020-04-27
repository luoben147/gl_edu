package com.luoben.eduservice.service.impl;

import com.luoben.eduservice.entity.EduCourse;
import com.luoben.eduservice.entity.EduCourseDescription;
import com.luoben.eduservice.mapper.EduCourseMapper;
import com.luoben.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoben.eduservice.vo.CourseInfoVo;
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
}
