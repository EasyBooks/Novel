/*
 * 作者：刘时明
 * 时间：2020/3/29-13:06
 * 作用：
 */
package com.novel.user.service.dubbo;

import com.novel.common.bean.PageList;
import com.novel.user.service.CollectionService;
import com.novel.user.service.RPCCollectionService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "2.0.0", timeout = 5000)
public class RPCCollectionServiceImpl implements RPCCollectionService
{
    @Autowired
    private CollectionService collectionService;

    @Override
    public PageList<Long> collectionBookIds(Integer uid, int page, int size)
    {
        return collectionService.collectionBookIds(uid, page, size);
    }

    @Override
    public int saveCollection(Integer uid, Integer type, Long bookId)
    {
        return collectionService.saveCollection(uid, type, bookId);
    }

    @Override
    public int deleteCollection(Long id)
    {
        return 0;
    }
}
