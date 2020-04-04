package com.novel.user.service.dubbo;

import com.novel.common.domain.user.Banner;
import com.novel.common.dto.user.BannerDto;
import com.novel.user.service.BannerService;
import com.novel.user.service.RPCBannerService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 19:45
 **/
@Service(version = "2.0.0",timeout = 5000)
public class RPCBannerServiceImpl implements RPCBannerService
{
    @Autowired
    private BannerService bannerService;

    @Override
    public List<BannerDto> list() {
        return bannerService.list();
    }

    @Override
    public int saveBanner(Banner banner) {
        return bannerService.saveBanner(banner);
    }

    @Override
    public int updateBanner(Banner banner) {
        return bannerService.updateBanner(banner);
    }

    @Override
    public int deleteBanner(Long id) {
        return bannerService.deleteBanner(id);
    }

    @Override
    public Banner find(Long id) {
        return bannerService.find(id);
    }
}
