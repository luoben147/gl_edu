package com.luoben.eduservice.service;

import com.luoben.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author luoben
 * @since 2020-04-27
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id 删除小节
     * @param id
     */
    void removeVideoByCourseId(String id);
}
