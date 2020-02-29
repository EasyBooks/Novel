/*
 * 作者：刘时明
 * 时间：2020/1/20-12:49
 * 作用：
 */
package com.novel.im.mapper;

import com.novel.im.domain.TFriendsExample;
import org.apache.ibatis.annotations.SelectProvider;

public interface UserMapper
{
    @SelectProvider(type=UserSqlProvider.class, method="countByExample")
    long countByExample(TFriendsExample example);
}
