/*
 * 作者：刘时明
 * 时间：2020/4/15-21:10
 * 作用：
 */
package com.novel.gateway.handler.app;

import com.novel.common.utils.ResultUtil;
import com.novel.user.service.RPCCircleService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/app/circle")
public class CircleController
{
    @Reference(version = "1.0.0",check = false)
    private RPCCircleService circleService;

    @GetMapping("list")
    public Object list(@RequestParam int page,@RequestParam int size)
    {
        return ResultUtil.success(circleService.findList(Map.of("page",(page-1)*size,"size",size)));
    }
}
