/*
 * 作者：刘时明
 * 时间：2020/1/19-19:34
 * 作用：
 */
package com.novel.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.book.Book;
import com.novel.common.dto.book.BookDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public interface BookMapper extends BaseMapper<Book>
{
    @SelectProvider(type = BookMapperProvider.class, method = "queryBookDto")
    List<BookDto> queryBookDto(@Param("condition") Map<String, Object> conditionMap);

    class BookMapperProvider
    {
        private static String COUNT_SQL = "count(*)";
        private static String SELECT_SQL = "select book.id,book.type_id as typeId,title,book.synopsis,cover,recommend,click,collection,word_num,book.create_time as createTime,type.`name` as typeName";

        public String queryBookDto(Map<String, Object> paramMap)
        {
            StringBuilder sql = new StringBuilder();
            Map<String, Object> conditionMap = (Map<String, Object>) paramMap.get("condition");
            sql.append(SELECT_SQL);
            sql.append(" FROM t_book as book ");
            sql.append("LEFT JOIN t_type type ON book.type_id=type.id ");
            sql.append("WHERE 1=1 ");
            if (conditionMap.containsKey("title"))
            {
                sql.append(" AND book.title like '%");
                sql.append(conditionMap.get("title"));
                sql.append("%'");
            }
            if (conditionMap.containsKey("typeId"))
            {
                sql.append(" AND book.type_id =");
                sql.append(conditionMap.get("typeId"));
            }
            if (conditionMap.containsKey("id"))
            {
                sql.append(" AND book.id =");
                sql.append(conditionMap.get("id"));
            }

            if (conditionMap.containsKey("sort"))
            {
                switch ((Integer) conditionMap.get("sort"))
                {
                    case 1:
                        sql.append(" ORDER BY book.click ");
                        break;
                }
            }
            if (conditionMap.containsKey("page") && conditionMap.containsKey("size"))
            {
                sql.append(" LIMIT ");
                sql.append(conditionMap.get("page"));
                sql.append(",");
                sql.append(conditionMap.get("size"));
            }
            return sql.toString();
        }
    }
}
