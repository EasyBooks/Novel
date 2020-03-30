/*
 * 作者：刘时明
 * 时间：2020/3/28-20:31
 * 作用：
 */
package com.novel.gateway.handler;

import com.novel.gateway.aspect.annotation.IdParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/test")
public class TestHandler
{
    @GetMapping("hello")
    public Object hello(@IdParam Integer uid)
    {
        return "ok" + uid;
    }
}
