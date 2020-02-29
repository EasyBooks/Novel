/*
 * 作者：刘时明
 * 时间：2020/1/9-0:51
 * 作用：
 */
package com.novel.user.service;

import com.novel.common.domain.book.Book;

import java.util.List;
import java.util.Map;

public interface RPCBookService
{
    /**
     * 获取书籍列表
     *
     * @return
     */
    List<Book> bookList(Map<String,Object> conditionMap);

    /**
     * 修改书籍
     *
     * @param book
     * @return
     */
    boolean updateBook(Book book);

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    boolean deleteBook(long id);

    /**
     * 查询小说
     * @param id
     * @return
     */
    Book findBook(long id);
}
