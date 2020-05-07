package com.luoben.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoben.cms.entity.CrmBanner;
import com.luoben.cms.mapper.CrmBannerMapper;
import com.luoben.cms.service.CrmBannerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author luoben
 * @since 2020-05-06
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 查询所有bannner
     * @return
     */
    //缓存banner数据到redis
    @Cacheable(value = "banner",key = "'selectBannerList'")
    @Override
    public List<CrmBanner> selectBannerList() {
        //根据id降序，显示排序之后的前2条
        QueryWrapper<CrmBanner> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法，拼接sql
        wrapper.last("limit 2");

        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }

    //@CachePut使用该注解标志的方法，每次都会执行，并将结果存入指定的缓存中。
    // 其他方法可以直接从响应的缓存中读取缓存数据，而不需要再去查询数据库。一般用在新增方法上。
    @CachePut(value = "banner", key="'saveBanner'")
    @Override
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    //@CacheEvict使用该注解标志的方法，会清空指定的缓存。一般用在更新或者删除方法上
    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void updateBannerById(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }
}
