/*
 * 作者：刘时明
 * 时间：2020/1/9-23:42
 * 作用：
 */
package com.novel.common.domain.im;

import com.google.gson.annotations.Expose;
import com.novel.common.domain.BaseEntity;
import lombok.Data;

@Data
public class Message extends BaseEntity
{
    // 消息命令，0ACK确认，1握手，3私聊，5群发，7系统消息，9系统广播，11心跳，13下线，15文件传输头，17文件内容切片
    // 2握手回复，4私聊回复，12心跳回复，16文件传输头回复
    @Expose private int cmd;
    // 消息长度
    @Expose private int len;
    // 发送者ID
    @Expose private int formId;
    // 接收者ID
    private int toId;
    // 消息内容
    private String content;
}
