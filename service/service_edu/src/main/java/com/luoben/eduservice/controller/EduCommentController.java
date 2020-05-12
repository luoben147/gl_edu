package com.luoben.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luoben.commonutils.JwtUtils;
import com.luoben.commonutils.R;
import com.luoben.eduservice.entity.EduComment;
import com.luoben.eduservice.service.EduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author luoben
 * @since 2020-05-11
 */
@Api(description = "课程评论前端接口")
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    /**
     * 根据课程id查询评论列表
     */
    @ApiOperation(value = "评论分页列表")
    @GetMapping("{page}/{limit}")
    public R getCourseComment( @ApiParam(name = "page", value = "当前页码", required = true)
                                   @PathVariable Long page,

                               @ApiParam(name = "limit", value = "每页记录数", required = true)
                                   @PathVariable Long limit,

                               @ApiParam(name = "courseId", value = "课程id", required = false)
                                           String courseId){
        Page<EduComment> pageParam = new Page<>(page, limit);
        Map<String, Object> map = commentService.getCourseComment(pageParam,courseId);
        return  R.ok().data(map);
    }


    /**
     * 添加评论
     */
    @ApiOperation(value = "添加评论")
    @PostMapping("auth/save")
    public R save(@RequestBody EduComment comment, HttpServletRequest request){
        //token中解析用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);
        boolean flag = commentService.saveComment(comment);
        if(flag){
            return R.ok();
        }else {
            return R.error().message("添加评论失败");
        }

    }
}

