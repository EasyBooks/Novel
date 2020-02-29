/**
 * 作者：刘时明
 * 时间：2019/11/7-16:56
 * 作用：
 */
package com.novel.common.bean.define;

@FunctionalInterface
public interface SubscribeOperation
{
    void onMessage(String channel, String message);
}
