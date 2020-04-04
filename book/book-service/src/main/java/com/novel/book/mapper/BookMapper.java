/*
 * 作者：刘时明
 * 时间：2020/1/19-19:34
 * 作用：
 */
package com.novel.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.book.Book;
import com.novel.common.dto.book.BookDetailDto;
import com.novel.common.dto.book.BookDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

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

    @SelectProvider(type = BookMapperProvider.class, method = "boutiqueList")
    List<BookDto> boutiqueList();

    @Select("SELECT book.id,book.cover,book.title,book.synopsis,click,collection,instalments,\n" +
            "(SELECT top FROM (\n" +
            "SELECT\n" +
            "t1.*,@rank :=@rank + 1 AS top\n" +
            "FROM(\n" +
            "SELECT id,instalments FROM t_book \n" +
            "ORDER BY instalments DESC\n" +
            ") as t1,\n" +
            "(SELECT @rank := 0) AS t2\n" +
            ") as m\n" +
            "WHERE m.id= book.id) as top\n" +
            "FROM t_book book\n" +
            "WHERE book.id=#{id}")
    BookDetailDto findBookDetail(Long id);

    @Select("SELECT boo2.id,boo2.title,boo2.cover FROM t_book boot1\n" +
            "INNER JOIN t_book boo2 ON boot1.type_id=boo2.type_id\n" +
            "WHERE boot1.id<>boo2.id AND boot1.id=#{id} LIMIT 4")
    List<BookDto> findRelatedList(Long id);

    class BookMapperProvider
    {
        // 男生必读
        private static Long MALE_TYPE= 536159252016926720L;
        // 女生必读
        private static Long FEMALE=536159685947035648L;

        private static String COUNT_SQL = "select count(*)";
        private static String SELECT_SQL = "select book.id,book.type_id as typeId,title,book.synopsis,cover,recommend,click,collection,word_num,book.create_time as createTime,type.`name` as typeName";

        private StringBuilder sql;

        public BookMapperProvider()
        {
            sql=new StringBuilder();
        }

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
            Map<String, Object> conditionMap = (Map<String, Object>) paramMap.get("condition");
            sql.append(SELECT_SQL);
            sql.append(" FROM t_book as book ");
            sql.append("LEFT JOIN t_type type ON book.type_id=type.id ");
            appendSql(conditionMap, sql);
            appendSqlLimit(conditionMap, sql);
            return sql.toString();
        }

        public String boutiqueList()
        {
            sql.append("(select 'male' as remake,book.id,book.type_id as typeId,title,book.synopsis,cover,recommend,click,collection,word_num,book.create_time as createTime,type.`name` as typeName");
            sql.append(" FROM t_book as book");
            sql.append(" LEFT JOIN t_type type ON book.type_id=type.id");
            sql.append(" WHERE type.id=536159252016926720 LIMIT 3)");
            sql.append(" UNION ALL");
            sql.append(" (select 'female' as remake,book.id,book.type_id as typeId,title,book.synopsis,cover,recommend,click,collection,word_num,book.create_time as createTime,type.`name` as typeName");
            sql.append(" FROM t_book as book");
            sql.append(" LEFT JOIN t_type type ON book.type_id=type.id");
            sql.append(" WHERE type.id=536159685947035648 LIMIT 3)");
            sql.append(" UNION ALL");
            sql.append(" (select 'newBook' as remake,book.id,book.type_id as typeId,title,book.synopsis,cover,recommend,click,collection,word_num,book.create_time as createTime,type.`name` as typeName");
            sql.append(" FROM t_book as book");
            sql.append(" LEFT JOIN t_type type ON book.type_id=type.id");
            sql.append(" ORDER BY book.create_time DESC LIMIT 8)");
            sql.append(" UNION ALL");
            sql.append(" (select 'recommend' as remake,book.id,book.type_id as typeId,title,book.synopsis,cover,recommend,click,collection,word_num,book.create_time as createTime,type.`name` as typeName");
            sql.append(" FROM t_book as book");
            sql.append(" LEFT JOIN t_type type ON book.type_id=type.id");
            sql.append("  ORDER BY book.click DESC LIMIT 3)");
            return sql.toString();
        }
    }
}
