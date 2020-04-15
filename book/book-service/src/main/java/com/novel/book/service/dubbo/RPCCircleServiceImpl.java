/*
 * 作者：刘时明
 * 时间：2020/4/14-22:28
 * 作用：
 */
package com.novel.book.service.dubbo;

import com.novel.book.service.CircleService;
import com.novel.common.bean.PageList;
import com.novel.common.domain.book.Circle;
import com.novel.common.dto.user.CircleDto;
import com.novel.user.service.RPCCircleService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(version = "1.0.0",timeout = 5000)
public class RPCCircleServiceImpl implements RPCCircleService
{
    @Autowired
    private CircleService circleService;

    @Override
    public PageList<CircleDto> findList(Map<String, Object> conditionMap)
    {
        return circleService.findList(conditionMap);
    }

    @Override
    public int insertCircle(Circle circle)
    {
        return circleService.insertCircle(circle);
    }

    @Override
    public int updateCircle(Circle circle)
    {
        return circleService.updateCircle(circle);
    }

    @Override
    public int deleteCircle(Long id)
    {
        return circleService.deleteCircle(id);
    }

    @Override
    public PageList<CircleDto> findListByBookId(long bookId, int page, int size)
    {
        return circleService.findListByBookId(bookId, page, size);
    }
}
