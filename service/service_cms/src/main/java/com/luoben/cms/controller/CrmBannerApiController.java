package com.luoben.cms.controller;


import com.luoben.cms.entity.CrmBanner;
import com.luoben.cms.service.CrmBannerService;
import com.luoben.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author luoben
 * @since 2020-05-06
 */
@Api(description = "网站首页Banner列表")
@RestController
@RequestMapping("/educms/banneraApi")
public class CrmBannerApiController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectBannerList();
        return R.ok().data("bannerList", list);
    }
}

