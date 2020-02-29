/*
 * 作者：刘时明
 * 时间：2020/1/19-19:39
 * 作用：
 */
package com.novel.book.service.dubbo;

import com.novel.user.service.PRCTypeService;
import com.novel.book.service.TypeService;
import com.novel.common.domain.book.Type;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(version = "1.0.0", timeout = 5000)
public class RPCTypeServiceImpl implements PRCTypeService
{
    @Autowired
    private TypeService typeService;

    @Override
    public List<Type> typeList(Map<String, Object> conditionMap)
    {
        return typeService.typeList(conditionMap);
    }

    @Override
    public boolean updateType(Type type)
    {
        return typeService.updateType(type);
    }

    @Override
    public boolean deleteType(long id)
    {
        return typeService.deleteType(id);
    }

    @Override
    public Type findType(long id)
    {
        return typeService.findType(id);
    }
}
