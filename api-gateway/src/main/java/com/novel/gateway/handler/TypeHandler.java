/*
 * 作者：刘时明
 * 时间：2020/3/24-20:47
 * 作用：
 */
package com.novel.gateway.handler;

import com.novel.common.domain.book.Type;
import com.novel.common.utils.ResultUtil;
import com.novel.gateway.utils.DtoUtil;
import com.novel.user.service.PRCTypeService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/type")
public class TypeHandler
{
    @Reference(version = "99.0.0", check = false)
    private PRCTypeService typeService;

//    @GetMapping("typeList")
//    public Object typeList()
//    {
//        List<Type> typeList = typeService.typeList(null);
//        return ResultUtil.success(DtoUtil.typeDto(typeList));
//    }

    @GetMapping("addType")
    public Object addType(Type type)
    {
        return ResultUtil.success(typeService.updateType(type));
    }

    @GetMapping("updateType")
    public Object updateType(Type type)
    {
        return ResultUtil.success(typeService.updateType(type));
    }
}
