/*
 * 作者：刘时明
 * 时间：2020/4/6-11:00
 * 作用：
 */
package com.novel.im.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper
{
    @Update("UPDATE t_user_info SET public_key=#{publicKey} WHERE uid=#{uid}")
    int updatePublicKey(int uid,String publicKey);
}
