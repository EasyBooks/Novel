/*
 * 作者：刘时明
 * 时间：2020/3/28-19:55
 * 作用：
 */
package com.novel.gateway.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdParam
{
}
