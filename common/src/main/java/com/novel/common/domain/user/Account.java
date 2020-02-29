/*
 * 作者：刘时明
 * 时间：2020/1/19-21:56
 * 作用：
 */
package com.novel.common.domain.user;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

@Data
public class Account extends BaseEntity
{
    private Long id;
    private Integer uid;
    private Long accountBalance;
    private Long monthlyBalance;
    private Long recommendBalance;
}
