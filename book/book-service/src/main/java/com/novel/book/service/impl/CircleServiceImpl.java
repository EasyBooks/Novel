/*
 * 作者：刘时明
 * 时间：2020/4/14-22:29
 * 作用：
 */
package com.novel.book.service.impl;

import com.novel.book.mapper.CircleMapper;
import com.novel.book.service.CircleService;
import com.novel.common.bean.PageList;
import com.novel.common.domain.book.Circle;
import com.novel.common.dto.user.CircleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CircleServiceImpl implements CircleService
{
    @Autowired
    private CircleMapper circleMapper;

    @Override
    public PageList<CircleDto> findList(Map<String, Object> conditionMap)
    {
        List<CircleDto> circleDtoList= circleMapper.findList(conditionMap);

        return PageList.of(circleDtoList,circleMapper.total());
    }

    @Override
    public int insertCircle(Circle circle)
    {
        return circleMapper.insert(circle);
    }

    @Override
    public int updateCircle(Circle circle)
    {
        return circleMapper.updateById(circle);
    }

    @Override
    public int deleteCircle(Long id)
    {
        return circleMapper.deleteById(id);
    }
}
