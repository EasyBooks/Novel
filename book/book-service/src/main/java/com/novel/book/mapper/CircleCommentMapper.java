/*
 * 作者：刘时明
 * 时间：2020/4/14-23:10
 * 作用：
 */
package com.novel.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.common.domain.book.CircleComment;
import com.novel.common.dto.user.CircleCommentDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface CircleCommentMapper extends BaseMapper<CircleComment>
{
    @SelectProvider(type = CircleCommentMapperProvider.class,method = "circleCommentLit")
    List<CircleCommentDto> circleCommentLit(@Param("circleId") Long circleId,@Param("page")int page,@Param("size")int size);

    class CircleCommentMapperProvider
    {
        public String circleCommentLit()
        {
            String sql = "SELECT `comment`.id,info.nickname,`comment`.content,FROM_UNIXTIME(`comment`.create_time) as createTime," +
                    "detail.head_img as headImg,(SELECT count(*) FROM t_circle_like WHERE circle_id=`comment`.id AND type=1) as likeNum" +
                    ",(SELECT count(*) FROM t_circle_comment WHERE circle_id=#{circleId} AND reply_id=`comment`.id) as replyNum" +
                    " FROM t_circle_comment `comment`" +
                    " LEFT JOIN t_user_info info ON info.uid=`comment`.uid" +
                    " LEFT JOIN t_user_details detail ON detail.uid=`comment`.uid" +
                    " WHERE" +
                    " `comment`.`status`=1 AND" +
                    " `comment`.circle_id=#{circleId} AND" +
                    " `comment`.reply_id=0" +
                    " LIMIT #{page},#{size}";
            System.out.println("sql="+sql);
            return sql;
        }
    }
}
