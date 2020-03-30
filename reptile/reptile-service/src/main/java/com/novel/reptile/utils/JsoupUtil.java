/*
 * 作者：刘时明
 * 时间：2020/3/29-21:48
 * 作用：
 */
package com.novel.reptile.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Random;

public class JsoupUtil
{
    private static final int TIME_OUT = 5 * 1000;
    private static final Random RANDOM = new Random();

    /**
     * 根据url获取数据
     *
     * @param url
     * @return
     */
    public static Document getHtmlTextByUrl(String url)
    {
        Document document = null;
        try
        {
            // 防止被拉黑，随机延迟
            Thread.sleep(RANDOM.nextInt(1000));
            document = Jsoup.connect(url)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    // .cookie("auth", "token")
                    .header("Cookie", "ZHID=12841BB20B991EFDEDC8221F12057B6C; v_user=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3Dng8c5G0SIq7Jmz1-4IOKQHG-BVJIPMSaG3HxJ7Lt4MGrBIpUStchWVh99c-bFIxT%26wd%3D%26eqid%3Dc5cec17a000ad753000000065e24461c%7Chttp%3A%2F%2Fwww.zongheng.com%2F%7C21213541; Hm_up_c202865d524849216eea846069349eb9=%7B%22uid_%22%3A%7B%22value%22%3A%2212841BB20B991EFDEDC8221F12057B6C%22%2C%22scope%22%3A1%7D%7D; rSet=1_3_1_14; ver=2018; zh_visitTime=1585464109244; PassportCaptchaId=064a9db1fb5a272756d3189526b633a7; AST=1585488324066067ff7378f; JSESSIONID=abcMwp5FKT9ftdOpjpMex; zhffr=0; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2216fbdb74a6967-08a552fc2c9337-7d1b3a4a-1327104-16fbdb74a6a106%22%2C%22%24device_id%22%3A%2216fbdb74a6967-08a552fc2c9337-7d1b3a4a-1327104-16fbdb74a6a106%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_referrer_host%22%3A%22%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%7D%7D; Hm_lvt_c202865d524849216eea846069349eb9=1585472558,1585480125,1585482085,1585484437; Hm_lpvt_c202865d524849216eea846069349eb9=1585484458")
                    .timeout(TIME_OUT).post();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return document;
    }
}
