/*
 * 作者：刘时明
 * 时间：2019/11/26-21:50
 * 作用：
 */
package com.novel.common.bean.define;

@FunctionalInterface
public interface ResultTask<E>
{
    E taskWork(Object obj);
}
