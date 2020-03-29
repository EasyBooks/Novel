/*
 * 作者：刘时明
 * 时间：2020/1/19-19:39
 * 作用：
 */
package com.novel.book.service.dubbo;

import com.novel.book.service.BookService;
import com.novel.common.bean.PageList;
import com.novel.common.domain.book.Book;
import com.novel.common.dto.book.BookDto;
import com.novel.user.service.RPCBookService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(version = "1.0.0", timeout = 5000)
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
    public PageList<Book> findCollection(int uid, int page, int size)
    {
        return bookService.findCollection(uid, page, size);
    }
}
