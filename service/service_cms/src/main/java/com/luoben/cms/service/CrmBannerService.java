package com.luoben.cms.service;

import com.luoben.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author luoben
 * @since 2020-05-06
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 查询所有bannner
     * @return
     */
    List<CrmBanner> selectBannerList();


    void saveBanner(CrmBanner banner);

    void updateBannerById(CrmBanner banner);

    void removeBannerById(String id);
}
