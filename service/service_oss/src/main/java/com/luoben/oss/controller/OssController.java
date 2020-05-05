package com.luoben.oss.controller;

import com.luoben.commonutils.R;
import com.luoben.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "文件上传服务")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像
     */
    @ApiOperation(value = "头像上传", notes = "头像上传")
    @PostMapping()
    public R uploadOssFile(MultipartFile file){

       String url= ossService.uploadFileAvatar(file);

        return R.ok().data("url",url);
    }

    /**
     * 删除OSS文件
     */
    @ApiOperation(value = "删除OSS文件", notes = "删除OSS文件")
    @DeleteMapping()
    public R deleteOssFile(
            @ApiParam(name = "fileUrl", value = "oss文件url", required = true)
            @RequestParam("fileUrl") String fileUrl){
        boolean flag = ossService.deleteOssFile(fileUrl);
        return flag ? R.ok() : R.error().message("删除OSS文件失败");
    }

}
