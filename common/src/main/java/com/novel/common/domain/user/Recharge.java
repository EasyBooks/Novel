/*
 * 作者：刘时明
 * 时间：2020/1/19-21:58
 * 作用：
 */
package com.novel.common.domain.user;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

@Data
public class Recharge extends BaseEntity
{
    private Long id;
    private Integer uid;
    private Long amount;
    private Integer type;
}
