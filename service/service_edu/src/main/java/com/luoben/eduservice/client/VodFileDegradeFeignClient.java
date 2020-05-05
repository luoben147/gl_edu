package com.luoben.eduservice.client;

import com.luoben.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * vod 熔断器的实现类
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {

    //出错之后会执行
    @Override
    public R removeAliyVideo(String videoId) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
