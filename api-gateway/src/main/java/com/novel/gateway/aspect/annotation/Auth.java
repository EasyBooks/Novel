/*
 * 作者：刘时明
 * 时间：2020/4/6-13:10
 * 作用：
 */
package com.novel.gateway.aspect.annotation;

import com.novel.common.define.AuthType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth
{
    AuthType[] value() default {};
}
