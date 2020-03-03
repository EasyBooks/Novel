/*
 * 作者：刘时明
 * 时间：2020/3/4-0:44
 * 作用：
 */
package com.novel.admin.utils;

import com.novel.common.domain.book.Book;
import com.novel.common.dto.book.BookDto;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class DtoUtil
{
    public static List<BookDto> convertBook(List<Book> books)
    {
        List<BookDto> dtoList = new ArrayList<>(books.size());
        for (Book b : books)
        {
            BookDto temp = new BookDto();
            BeanUtils.copyProperties(b, temp);
            dtoList.add(temp);
        }
        return dtoList;
    }
}
