/*
 * 作者：刘时明
 * 时间：2020/1/9-12:33
 * 作用：
 */
package com.novel.im.netty;

import io.netty.channel.Channel;
import lombok.Data;

@Data
public class UserBean
{
    // AES公钥
    private String aesKey;
    // 通道
    private Channel channel;
}
