/*
 * 作者：刘时明
 * 时间：2020/1/20-12:49
 * 作用：
 */
package com.novel.im.mapper;

import com.novel.im.domain.TFriendsExample;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider
{
    public String countByExample(TFriendsExample example)
    {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_friends");
        return sql.toString();
    }
}
