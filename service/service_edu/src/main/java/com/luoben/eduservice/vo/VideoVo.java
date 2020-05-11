package com.luoben.eduservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "课时信息")
@Data
public class VideoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Boolean isFree;

    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;
}
