package com.novel.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.user.Banner;
import com.novel.common.dto.user.BannerDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-02 19:27
 **/
public interface BannerMapper extends BaseMapper<Banner>
{
    @Select("SELECT img,link,title FROM t_banner WHERE status=1 AND enable=1 LIMIT 4")
    List<BannerDto> list();
}
