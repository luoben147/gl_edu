package com.luoben.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoben.commonutils.R;
import com.luoben.eduservice.client.VodClient;
import com.luoben.eduservice.entity.EduVideo;
import com.luoben.eduservice.mapper.EduVideoMapper;
import com.luoben.eduservice.service.EduVideoService;
import com.luoben.servicebase.exceptionhandler.MyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private VodClient vodClient;

    /**
     * 删除小节，同时删除视频资源
     * @param id
     * @return
     */
    @Override
    public Boolean removeVideoById(String id) {
        //查询云端视频id
        EduVideo video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        //删除视频资源
        if(!StringUtils.isEmpty(videoSourceId)){
            R r = vodClient.removeAliyVideo(videoSourceId);
            if(r.getCode()== 20001){
                throw new MyException(20001,"删除视频失败，熔断器..");
            }
        }
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    /**
     * 根据课程id 删除小节
     *   删除小节和视频资源
     * @param id
     */
    @Override
    public Boolean removeVideoByCourseId(String id) {

        //根据课程id 查询课程里的所有视频id
        QueryWrapper<EduVideo> wrapperVideo=new QueryWrapper<>();
        wrapperVideo.eq("course_id",id);
        wrapperVideo.select("video_source_id"); //只查视频id字段
        List<EduVideo> videoList = baseMapper.selectList(wrapperVideo);

        //得到所有视频列表的云端原始视频id
        List<String> videoSourceIdList = new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            EduVideo video = videoList.get(i);
            String videoSourceId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoSourceIdList.add(videoSourceId);
            }
        }
        //调用vod服务删除远程视频
        if(videoSourceIdList.size() > 0){
            vodClient.deleteBatch(videoSourceIdList);
        }

        //删除video表示的记录
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",id);
        Integer  count = baseMapper.delete(wrapper);
        return null != count && count > 0;
    }
}
