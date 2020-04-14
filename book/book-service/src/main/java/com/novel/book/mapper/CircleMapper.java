/*
 * 作者：刘时明
 * 时间：2020/4/4-15:22
 * 作用：
 */
package com.novel.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.book.Circle;
import com.novel.common.dto.user.CircleDto;
import com.novel.common.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

public interface CircleMapper extends BaseMapper<Circle>
{
    @Select("SELECT circle.id,info.nickname,details.head_img as headImg,circle.uid,book.id as bookId,book.title as bookTitle,circle.content\n" +
            "FROM t_circle circle\n" +
            "LEFT JOIN t_user_info info ON info.uid=circle.uid\n" +
            "LEFT JOIN t_book book ON book.id=circle.book_id\n" +
            "LEFT JOIN t_user_details details ON info.uid=details.uid")
    List<CircleDto> findCircleByBook(Long bookId);

    @SelectProvider(type = CircleMapperProvider.class,method = "findList")
    List<CircleDto> findList(@Param("condition") Map<String, Object> conditionMap);

    @Select("SELECT FOUND_ROWS()")
    long total();

    class CircleMapperProvider
    {
        public String findList(Map<String, Object> paramMap)
        {
            Map<String, Object> conditionMap= (Map<String, Object>) paramMap.get("condition");
            StringBuilder sql=new StringBuilder();
            sql.append("SELECT SQL_CALC_FOUND_ROWS circle.id,circle.uid,info.nickname,circle.content,detail.head_img as headImg,FROM_UNIXTIME(circle.create_time) as createTime,");
            sql.append("(SELECT count(*) FROM t_circle_like `like` WHERE `like`.circle_id=circle.id) as likeNum");
            sql.append(" FROM t_circle circle");
            sql.append(" LEFT JOIN t_user_info info ON circle.uid=info.uid");
            sql.append(" LEFT JOIN t_user_details detail ON detail.uid=circle.uid");
            conditionAppend(sql,conditionMap);
            sortAppend(sql,conditionMap);
            SQLUtil.limitAppend(sql,conditionMap);
            return sql.toString();
        }

        private void sortAppend(StringBuilder sql, Map<String, Object> conditionMap)
        {

        }


        private void conditionAppend(StringBuilder sql,Map<String, Object> conditionMap)
        {
            sql.append(" WHERE circle.status=1");

        }
    }
}
