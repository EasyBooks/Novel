/*
 * 作者：刘时明
 * 时间：2020/4/6-10:57
 * 作用：
 */
package com.novel.im.service;

/**
 * 严格来说，IM服务应该调用User服务
 * 这样做的目的是便于IM服务独立测试
 */
public interface UserService
{
    int setPublicKey(Integer uid,String publicKey);
}
