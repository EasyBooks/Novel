package com.novel.reptile.service.dubbo;

import com.novel.reptile.service.NovelService;
import com.novel.reptile.service.RPCReptileService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "1.0.0", timeout = 5000)
public class RPCNovelServiceImpl implements RPCReptileService
{
    @Autowired
    private NovelService novelService;

    @Override
    public List<String> search(String keyword, int size)
    {
        return novelService.search(keyword, size);
    }
}
