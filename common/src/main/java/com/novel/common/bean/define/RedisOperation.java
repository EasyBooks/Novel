/**
 * 作者：刘时明
 * 时间：2019/11/7-16:22
 * 作用：
 */
package com.novel.common.bean.define;

@FunctionalInterface
public interface RedisOperation<E>
{
    Object operation(E e);
}
