package com.novel.user.service;

import com.novel.common.bean.PageList;
import com.novel.common.domain.book.Circle;
import com.novel.common.dto.user.CircleDto;

import java.util.Map;


/**
 * @program: Novel
 * @description:
 * @author: lsm
 * @create: 2020-04-14 17:16
 **/
public interface RPCCircleService
{
    PageList<CircleDto> findList(Map<String,Object> conditionMap);

    PageList<CircleDto> findListByBookId(long bookId,int page,int size);

    int insertCircle(Circle circle);

    int updateCircle(Circle circle);

    int deleteCircle(Long id);
}
