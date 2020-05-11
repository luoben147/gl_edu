package com.luoben.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoben.eduservice.client.OssClient;
import com.luoben.eduservice.entity.EduCourse;
import com.luoben.eduservice.entity.EduCourseDescription;
import com.luoben.eduservice.mapper.EduCourseMapper;
import com.luoben.eduservice.service.EduChapterService;
import com.luoben.eduservice.service.EduCourseService;
import com.luoben.eduservice.service.EduVideoService;
import com.luoben.eduservice.vo.CourseApiQueryVo;
import com.luoben.eduservice.vo.CourseInfoVo;
import com.luoben.eduservice.vo.CoursePublishVo;
import com.luoben.eduservice.vo.CourseWebVo;
import com.luoben.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private  EduCourseDescriptionServiceImpl courseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OssClient ossClient;

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

    /**
     * 分页课程列表
     * @param pageParam
     * @param courseQuery
     */
    @Override
    public void pageQuery(Page<EduCourse> pageParam, EduCourse courseQuery) {
        QueryWrapper wrapper=new QueryWrapper();

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, wrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }

        baseMapper.selectPage(pageParam, wrapper);

    }

    /**
     * 删除课程
     *   删除章节，小节，视频
     * @param id
     * @return
     */
    @Transactional
    @Override
    public boolean removeCourseById(String id) {
        //删除小节 和视频
        videoService.removeVideoByCourseId(id);
        //删除章节
        chapterService.removeChapterByCourseId(id);
        //删除描述
        courseDescriptionService.removeById(id);
        //删除封面 oss
        EduCourse eduCourse = baseMapper.selectById(id);
        String cover = eduCourse.getCover();
        ossClient.deleteOssFile(cover);

        //删除课程
        Integer count = baseMapper.deleteById(id);
        return null != count && count > 0;
    }

    /**
     * 根据讲师id查询讲师的课程
     * @param id
     * @return
     */
    @Override
    public List<EduCourse> selectByTeacherId(String id) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<EduCourse>();

        queryWrapper.eq("teacher_id", id);
        //按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");

        List<EduCourse> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }

    /**
     * 条件查询课程
     * @param pageParam
     * @param courseQuery
     * @return
     */
    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseApiQueryVo courseQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<EduCourse>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            wrapper.eq("subject_id", courseQuery.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo selectInfoWebById(String courseId) {

        return baseMapper.selectInfoWebById(courseId);
    }
}
