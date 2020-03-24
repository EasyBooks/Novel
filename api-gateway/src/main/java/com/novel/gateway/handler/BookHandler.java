/*
 * 作者：刘时明
 * 时间：2020/1/19-20:00
 * 作用：
 */
package com.novel.gateway.handler;

import com.novel.common.bean.PageList;
import com.novel.common.domain.book.Book;
import com.novel.common.dto.book.BookDto;
import com.novel.user.service.PRCTypeService;
import com.novel.common.domain.book.Type;
import com.novel.common.utils.ResultUtil;
import com.novel.gateway.utils.DtoUtil;
import com.novel.user.service.RPCBookService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/book")
public class BookHandler
{
    @Reference
    private RPCBookService bookService;

    @GetMapping("list")
    public Object list(int page,int size)
    {
        PageList<Book> bookList = bookService.bookList(null, page, size);
        List<BookDto> bookDtoList=new ArrayList<>(bookList.getData().size());
        return ResultUtil.success(PageList.of(bookDtoList,bookList.getTotal()));
    }
}