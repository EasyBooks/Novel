package com.novel.user.service;

import com.novel.common.domain.user.Banner;
import com.novel.common.dto.user.BannerDto;

import java.util.List;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 19:32
 **/
public interface RPCBannerService
{
    List<BannerDto> list();

    int saveBanner(Banner banner);

    int updateBanner(Banner banner);

    int deleteBanner(Long id);

    Banner find(Long id);
}
