/*
 * 作者：刘时明
 * 时间：2020/2/28-12:43
 * 作用：
 */
package com.novel.common.dto.nsq;

import lombok.Data;

import java.io.Serializable;

@Data
public class NsqBookDto implements Serializable
{
    private Integer code;
    private byte[] data;
}
