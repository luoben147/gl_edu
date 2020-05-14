package com.luoben.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luoben.commonutils.JwtUtils;
import com.luoben.commonutils.R;
import com.luoben.commonutils.vo.CourseWebVoOrder;
import com.luoben.eduservice.client.OrderClient;
import com.luoben.eduservice.entity.EduCourse;
import com.luoben.eduservice.service.EduChapterService;
import com.luoben.eduservice.service.EduCourseService;
import com.luoben.eduservice.vo.ChapterVo;
import com.luoben.eduservice.vo.CourseApiQueryVo;
import com.luoben.eduservice.vo.CourseWebVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(description = "前端课程接口")
@RestController
@RequestMapping("/eduservice/courseApi")
public class CourseApiController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseApiQueryVo courseQuery){
        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam, courseQuery);
        return  R.ok().data(map);
    }


    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId,
            HttpServletRequest request){

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节和小节信息
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);

        //远程调用，判断课程是否被购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return R.error().code(28004).message("请登录");
        }
        boolean buyCourse = orderClient.isBuyCourse(memberId, courseId);

        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList).data("isbuy",buyCourse);
    }



    /**
     * 根据课程id查询课程基本信息
     */
    @GetMapping("getCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getCourseInfoOrder(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String courseId) {

        CourseWebVo courseInfoVo = courseService.selectInfoWebById(courseId);
        CourseWebVoOrder courseWebVoOrder=new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfoVo,courseWebVoOrder);
        return courseWebVoOrder;
    }


}
