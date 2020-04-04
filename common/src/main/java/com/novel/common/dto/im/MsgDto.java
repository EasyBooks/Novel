/*
 * 作者：刘时明
 * 时间：2020/3/31-23:44
 * 作用：
 */
package com.novel.common.dto.im;

import lombok.Data;

@Data
public class MsgDto
{
    private String id;
    private int cmd;
    // 发送者ID
    private int formId;
    // 发送者昵称
    private String formNickname;
    // 发送者头像
    private String formHeadImg;
    // 内容
    private String content;
    // 阅读标识
    private Integer readStatus;
}
