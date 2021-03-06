/*
 * 作者：刘时明
 * 时间：2020/1/9-0:51
 * 作用：
 */
package com.novel.user.service;

import com.novel.common.bean.PageList;
import com.novel.common.domain.book.Book;
import com.novel.common.dto.book.BookDetailDto;
import com.novel.common.dto.book.BookDto;
import com.novel.common.dto.user.CircleDto;

import java.util.List;
import java.util.Map;

public interface RPCBookService
{
    /**
     * 获取书籍列表
     *
     * @return
     */
    PageList<BookDto> bookList(Map<String,Object> conditionMap, int page, int size);

    /**
     * 修改小说
     *
     * @param book
     * @return
     */
    boolean updateBook(Book book);

    /**
     * 删除小说
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

    /**
     * 查询收藏小说
     * @param idList
     * @param page
     * @param size
     * @return
     */
    PageList<BookDto> findCollection(List<Long> idList, int page, int size);

    /**
     * 精选书籍
     *
     * @return
     */
    Map<String,List<BookDto>> boutiqueList();

    /**
     * 书籍详情
     * @param id
     * @return
     */
    BookDetailDto bookDetail(Long id);
}
