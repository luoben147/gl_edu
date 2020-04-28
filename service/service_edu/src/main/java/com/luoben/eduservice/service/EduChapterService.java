package com.luoben.eduservice.service;

import com.luoben.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luoben.eduservice.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author luoben
 * @since 2020-04-27
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程Id获取课程大纲列表
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 删除章节
     * @param id
     * @return
     */
    boolean removeChapterById(String id);
}
