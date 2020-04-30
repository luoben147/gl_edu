package com.luoben.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luoben.eduservice.entity.EduVideo;
import com.luoben.eduservice.mapper.EduVideoMapper;
import com.luoben.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author luoben
 * @since 2020-04-27
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    /**
     * 根据课程id 删除小节
     * @param id
     */
    @Override
    public void removeVideoByCourseId(String id) {
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
