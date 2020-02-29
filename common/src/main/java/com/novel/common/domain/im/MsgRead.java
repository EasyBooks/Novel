/*
 * 作者：刘时明
 * 时间：2020/1/19-22:12
 * 作用：
 */
package com.novel.common.domain.im;

import com.novel.common.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class MsgRead extends BaseEntity
{
    private Long id;
    private Integer uid;
    private Long msgId;
}
