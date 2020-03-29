/*
 * 作者：刘时明
 * 时间：2020/1/19-20:00
 * 作用：
 */
package com.novel.gateway.handler;

import com.novel.common.bean.PageList;
import com.novel.common.domain.book.Book;
import com.novel.common.dto.book.BookDto;
import com.novel.common.dto.user.AuthorDto;
import com.novel.common.utils.ResultUtil;
import com.novel.gateway.aspect.annotation.IdParam;
import com.novel.user.service.RPCBookService;
import com.novel.user.service.RPCCollectionService;
import com.novel.user.service.RPCUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/book")
public class BookHandler
{
    @Reference(version = "1.0.0", check = false)
    private RPCBookService bookService;
    @Reference(version = "1.0.0", check = false)
    private RPCUserService userService;
    @Reference(version = "1.0.0", check = false)
    private RPCCollectionService collectionService;

    @GetMapping("list")
    public Object list(@RequestParam int page, @RequestParam int size, String title,Long typeId,Long id,Integer sort)
    {
        Map<String,Object> conditionMap=new HashMap<>();
        if(id!=null)
        {
            conditionMap.put("id",id);
        }
        if(sort!=null)
        {
            conditionMap.put("sort",sort);
        }
        if(title!=null)
        {
            conditionMap.put("title",title);
        }
        if(typeId!=null)
        {
            conditionMap.put("typeId",typeId);
        }
        PageList<BookDto> bookList = bookService.bookList(conditionMap, page, size);
        makeUpBookDto(bookList);
        return ResultUtil.success(bookList);
    }

    @GetMapping("collection")
    public Object findCollection(@IdParam Integer uid, @RequestParam int page, @RequestParam int size)
    {
        PageList<Long> collectionList = collectionService.collectionBookIds(uid, page, size);
        long total = collectionList.getTotal();
        PageList<BookDto> bookList = bookService.findCollection(collectionList.getData(),page,size);
        makeUpBookDto(bookList);
        bookList.setTotal(total);
        return ResultUtil.success(bookList);
    }

    private void makeUpBookDto(PageList<BookDto> bookList)
    {
        List<Long> ids=new ArrayList<>(bookList.getData().size());
        for (BookDto b:bookList.getData())
        {
            ids.add(b.getId());
        }
        if(ids.size()==0)return;
        List<AuthorDto> authors = userService.findAuthors(ids);
        Map<Long,List<AuthorDto>> authorMap=new HashMap<>(authors.size());
        for (AuthorDto a:authors)
        {
            List<AuthorDto> temp = authorMap.get(a.getBookId());
            if(temp==null)
            {
                temp=new ArrayList<>();
            }
            temp.add(a);
            authorMap.put(a.getBookId(),temp);
        }
        for (BookDto b : bookList.getData())
        {
            b.setAuthors(authorMap.get(b.getId()));
        }
    }
}