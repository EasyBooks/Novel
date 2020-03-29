/*
 * 作者：刘时明
 * 时间：2020/3/1-8:12
 * 作用：
 */
package com.novel.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.user.User;
import com.novel.common.dto.user.AuthorDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User>
{
    @Select("SELECT salt FROM user WHERE username=#{username} AND status=1")
    String selectSalt(String username);

    @Insert("insert into t_book_author values(#{user.id},#{userId},#{bookId},#{user.status},#{user.createTime},#{user.updateTime})")
    int insertAuthor(@Param("user") User user, @Param("userId") Long userId, @Param("bookId") Long bookId);

    @SelectProvider(type = UserMapperProvider.class,method = "findAuthors")
    List<AuthorDto> findAuthors(@Param("bookIds") List<Long> bookIds);

    class UserMapperProvider
    {
        public String findAuthors(Map<String,Object> paramMap)
        {
            List<Long> bookIds=(List<Long>)paramMap.get("bookIds");
            StringBuilder sql=new StringBuilder();
            sql.append("select author.book_id,user.uid,nickname,details.head_img from user `user`");
            sql.append(" inner join t_book_author author on user.id=author.user_id");
            sql.append(" left join t_user_details details on details.uid=`user`.uid");
            sql.append(" where author.book_id in(");
            for (Long id:bookIds)
            {
                sql.append(id);
                sql.append(",");
            }
            if(bookIds.size()>0)
            {
                sql.delete(sql.length()-1,sql.length());
            }
            sql.append(")");
            return sql.toString();
        }
    }
}
