/*
 * 作者：刘时明
 * 时间：2020/1/19-21:55
 * 作用：
 */
package com.novel.common.domain.user;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

@Data
public class Follow extends BaseEntity
{
    private Long id;
    private Integer followId;
    private Integer fansId;
}
