/*
 * 作者：刘时明
 * 时间：2020/4/4-15:22
 * 作用：
 */
package com.novel.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.user.Circle;
import com.novel.common.dto.book.CircleDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CircleMapper extends BaseMapper<Circle>
{
    @Select("SELECT circle.id,info.nickname,details.head_img as headImg,circle.uid,book.id as bookId,book.title as bookTitle,circle.content\n" +
            "FROM t_circle circle\n" +
            "LEFT JOIN t_user_info info ON info.uid=circle.uid\n" +
            "LEFT JOIN t_book book ON book.id=circle.book_id\n" +
            "LEFT JOIN t_user_details details ON info.uid=details.uid")
    List<CircleDto> findCircleByBook(Long bookId);
}
