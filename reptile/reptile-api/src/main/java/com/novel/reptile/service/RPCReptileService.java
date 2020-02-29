/*
 * 作者：刘时明
 * 时间：
 * 作用：爬虫RPC API
 */
package com.novel.reptile.service;

import java.util.List;

public interface RPCReptileService
{
    List<String> search(String keyword, int size);
}
