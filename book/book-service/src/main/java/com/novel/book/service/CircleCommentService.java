/*
 * 作者：刘时明
 * 时间：2020/4/14-23:09
 * 作用：
 */
package com.novel.book.service;

import com.novel.common.dto.user.CircleCommentDto;

import java.util.List;

public interface CircleCommentService
{
    List<CircleCommentDto> circleCommentLit(List<Long> circleIds);
}
