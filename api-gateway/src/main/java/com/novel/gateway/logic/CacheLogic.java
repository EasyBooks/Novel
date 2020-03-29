/*
 * 作者：刘时明
 * 时间：2020/3/29-1:41
 * 作用：
 */
package com.novel.gateway.logic;

import com.novel.user.service.RPCUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * Redis缓存统一处理
 */
@Component
@Slf4j
public class CacheLogic
{
    private static final long CACHE_TIME=60;

//    @Autowired
//    private ValueOperations<String,Object> operations;
    @Reference(version = "1.0.0", check = false)
    private RPCUserService userService;
}
