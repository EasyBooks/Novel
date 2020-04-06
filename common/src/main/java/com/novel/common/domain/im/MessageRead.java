/*
 * 作者：刘时明
 * 时间：2020/1/19-22:12
 * 作用：
 */
package com.novel.common.domain.im;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

@Data
public class MessageRead extends BaseEntity
{
    private Integer uid;
    private Long msgId;
    private Integer type;
}
