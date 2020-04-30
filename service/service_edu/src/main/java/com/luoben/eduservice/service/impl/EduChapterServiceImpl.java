package com.luoben.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoben.eduservice.entity.EduChapter;
import com.luoben.eduservice.entity.EduVideo;
import com.luoben.eduservice.mapper.EduChapterMapper;
import com.luoben.eduservice.service.EduChapterService;
import com.luoben.eduservice.service.EduVideoService;
import com.luoben.eduservice.vo.ChapterVo;
import com.luoben.eduservice.vo.VideoVo;
import com.luoben.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author luoben
 * @since 2020-04-27
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    /**
     * 根据课程Id获取课程大纲列表
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //最终数据
        ArrayList<ChapterVo> result = new ArrayList<>();

        //1.根据课程id查询课程里的所有章节
        QueryWrapper<EduChapter> chapterWrapper=new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterWrapper);
        //2.根据课程id查询课程里的所有小节
        QueryWrapper<EduVideo> videoWrapper=new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> videoList = videoService.list(videoWrapper);
        //封装数据
        for (int i = 0; i <chapterList.size() ; i++) {
            //章节
            EduChapter chapter=chapterList.get(i);
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);

            //小节数据
            ArrayList<VideoVo> videoVoArrayList = new ArrayList<>();
            for (int j = 0; j <videoList.size() ; j++) {
                EduVideo video=videoList.get(j);
                if(chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoArrayList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoArrayList);
            result.add(chapterVo);
        }
        return result;
    }

    @Override
    public boolean removeChapterById(String id) {
        //根据id查询是否存在视频，如果有则提示用户尚有子节点
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",id);
        int count = videoService.count(wrapper);
        if(count>0){//有小节
            throw new MyException(20001,"该分章节下存在视频课程，请先删除视频课程");
        }else { //没有小节
            Integer result = baseMapper.deleteById(id);
            return null != result && result > 0;
        }
    }

    /**
     * 根据课程Id删除章节
     * @param id
     */
    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
