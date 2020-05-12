package com.luoben.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoben.commonutils.vo.UcenterMemberVo;
import com.luoben.eduservice.client.UcenterClient;
import com.luoben.eduservice.entity.EduComment;
import com.luoben.eduservice.mapper.EduCommentMapper;
import com.luoben.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author luoben
 * @since 2020-05-11
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 根据课程id查询评论
     * @param pageParam
     * @param courseId
     * @return
     */
    @Override
    public Map<String, Object> getCourseComment(Page<EduComment> pageParam, String courseId) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<EduComment>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam,wrapper);

        List<EduComment> records = pageParam.getRecords();
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
    public boolean saveComment(EduComment comment) {

        UcenterMemberVo memberInfo = ucenterClient.getMemberInfo(comment.getMemberId());
        comment.setNickname(memberInfo.getNickname());
        comment.setAvatar(memberInfo.getAvatar());

        Integer result = baseMapper.insert(comment);
        return null != result && result > 0;
    }
}
