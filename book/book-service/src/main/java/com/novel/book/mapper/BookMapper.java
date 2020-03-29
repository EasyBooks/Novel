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
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public interface BookMapper extends BaseMapper<Book>
{
    @Update("update t_book set id=#{newId} where id=#{oldId}")
    int updateId(@Param("oldId") long oldId, @Param("newId") long newId);

    @SelectProvider(type = BookMapperProvider.class, method = "queryBookDto")
    List<BookDto> queryBookDto(@Param("condition") Map<String, Object> conditionMap);

    @SelectProvider(type = BookMapperProvider.class, method = "countBookDto")
    int countBookDto(@Param("condition") Map<String, Object> conditionMap);

    @SelectProvider(type = BookMapperProvider.class, method = "queryBookByIds")
    List<BookDto> queryBookByIds(@Param("condition") Map<String, Object> conditionMap);

    class BookMapperProvider
    {
        private static String COUNT_SQL = "select count(*)";
        private static String SELECT_SQL = "select book.id,book.type_id as typeId,title,book.synopsis,cover,recommend,click,collection,word_num,book.create_time as createTime,type.`name` as typeName";

        private void appendSql(Map<String, Object> conditionMap, StringBuilder sql)
        {
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
            if (conditionMap.containsKey("ids"))
            {
                List<Long> idList = (List<Long>) conditionMap.get("ids");
                if (idList.size() > 0)
                {
                    sql.append(" AND book.id in(");
                    for (Long id : idList)
                    {
                        sql.append(id).append(",");
                    }
                    sql.delete(sql.length() - 1, sql.length());
                    sql.append(conditionMap.get(")"));
                }
            }
        }

        private void appendSqlLimit(Map<String, Object> conditionMap, StringBuilder sql)
        {
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
        }

        public String countBookDto(Map<String, Object> paramMap)
        {
            StringBuilder sql = new StringBuilder();
            Map<String, Object> conditionMap = (Map<String, Object>) paramMap.get("condition");
            sql.append(COUNT_SQL);
            sql.append(" FROM t_book as book ");
            sql.append("LEFT JOIN t_type type ON book.type_id=type.id ");
            sql.append("WHERE 1=1 ");
            appendSql(conditionMap, sql);
            return sql.toString();
        }

        public String queryBookDto(Map<String, Object> paramMap)
        {
            StringBuilder sql = new StringBuilder();
            Map<String, Object> conditionMap = (Map<String, Object>) paramMap.get("condition");
            sql.append(SELECT_SQL);
            sql.append(" FROM t_book as book ");
            sql.append("LEFT JOIN t_type type ON book.type_id=type.id ");
            sql.append("WHERE 1=1 ");
            appendSql(conditionMap, sql);
            appendSqlLimit(conditionMap, sql);
            return sql.toString();
        }

        public String queryBookByIds(Map<String, Object> paramMap)
        {
            StringBuilder sql = new StringBuilder();
            Map<String, Object> conditionMap = (Map<String, Object>) paramMap.get("condition");
            sql.append(SELECT_SQL);
            sql.append(" FROM t_book as book ");
            sql.append("LEFT JOIN t_type type ON book.type_id=type.id ");
            appendSql(conditionMap, sql);
            appendSqlLimit(conditionMap, sql);
            return sql.toString();
        }
    }
}
