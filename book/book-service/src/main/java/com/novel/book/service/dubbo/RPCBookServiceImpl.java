/*
 * 作者：刘时明
 * 时间：2020/1/19-19:39
 * 作用：
 */
package com.novel.book.service.dubbo;

import com.novel.book.service.BookService;
import com.novel.common.bean.PageList;
import com.novel.common.domain.book.Book;
import com.novel.common.dto.book.BookDetailDto;
import com.novel.common.dto.book.BookDto;
import com.novel.common.dto.user.CircleDto;
import com.novel.user.service.RPCBookService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service(version = "1.0.0", timeout = 6000)
public class RPCBookServiceImpl implements RPCBookService
{
    @Autowired
    private BookService bookService;

    @Override
    public PageList<BookDto> bookList(Map<String, Object> conditionMap, int page, int size)
    {
        return bookService.bookList(conditionMap, page, size);
    }

    @Override
    public boolean updateBook(Book book)
    {
        return bookService.updateBook(book);
    }

    @Override
    public boolean deleteBook(long id)
    {
        return bookService.deleteBook(id);
    }

    @Override
    public Book findBook(long id)
    {
        return bookService.findBook(id);
    }

    @Override
    public PageList<BookDto> findCollection(List<Long> idList, int page, int size)
    {
        return bookService.findCollection(idList, page, size);
    }

    @Override
    public BookDetailDto bookDetail(Long id)
    {
        return bookService.bookDetail(id);
    }

    @Override
    public Map<String, List<BookDto>> boutiqueList()
    {
        return bookService.boutiqueList();
    }
}
