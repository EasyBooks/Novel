package com.novel.user.service.impl;

import com.novel.common.domain.user.Banner;
import com.novel.common.dto.user.BannerDto;
import com.novel.user.mapper.BannerMapper;
import com.novel.user.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 19:34
 **/
@Service
@Transactional
public class BannerServiceImpl implements BannerService
{
    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<BannerDto> list()
    {
        return bannerMapper.list();
    }

    @Override
    public int saveBanner(Banner banner) {
        return bannerMapper.insert(banner);
    }

    @Override
    public int updateBanner(Banner banner) {
        return bannerMapper.updateById(banner);
    }

    @Override
    public int deleteBanner(Long id) {
        final Banner banner = this.find(id);
        if(banner==null)return 0;
        banner.setStatus(0);
        return bannerMapper.updateById(banner);
    }

    @Override
    public Banner find(Long id) {
        return bannerMapper.selectById(id);
    }
}
