/*
 * 作者：刘时明
 * 时间：2020/3/29-13:01
 * 作用：
 */
package com.novel.user.service;

import com.novel.common.bean.PageList;

public interface RPCCollectionService
{
    PageList<Long> collectionBookIds(Integer uid, int page, int size);

    int saveCollection(Integer uid,Integer type,Long bookId);

    int deleteCollection(Long id);
}
