package com.luoben.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luoben.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author luoben
 * @since 2020-05-11
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> getCourseComment(Page<EduComment> pageParam, String courseId);

    boolean saveComment(EduComment comment);
}
