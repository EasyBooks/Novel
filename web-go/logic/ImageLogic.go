package logic

import (
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/gocolly/colly"
	"github.com/gocolly/colly/extensions"
	"io"
	"net/url"
	"os"
	"strings"
	"time"
)

var Urls = []string{"http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1585661685339_R&pv=&ic=&nc=1&z=&hd=&latest=&copyright=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&hs=2&sid=&word="}

/**
 * 根据关键字和指定的图片资源网站
 */
func ImageReptile(keyword string) {
	t := time.Now()
	c := colly.NewCollector(func(collector *colly.Collector) {
		collector.Async = true
		extensions.RandomUserAgent(collector)
	})
	imageC := c.Clone()
	// 请求头
	c.OnRequest(func(r *colly.Request) {

	})
	//构造图片url，让图片imageC收集器来下载图片
	c.OnResponse(func(r *colly.Response) {
		var f interface{}
		if err := json.Unmarshal(r.Body[13:len(r.Body)-1], &f); err != nil {
			panic(err)
		}
		imgList := f.(map[string]interface{})["imglist"]
		for k, img := range imgList.([]interface{}) {
			url := img.(map[string]interface{})["imgurl"].(string)
			url = url + "#" + img.(map[string]interface{})["caption"].(string)
			fmt.Printf("find -->%d:%s\n", k, url)
			imageC.Visit(url)
		}
	})
	c.OnError(func(response *colly.Response, err error) {
		fmt.Println(err)
	})
	// 根据图片url来下载图片
	imageC.OnResponse(func(r *colly.Response) {
		fileName := ""
		caption := strings.Split(r.Request.URL.String(), "#")
		if len(caption) >= 2 {
			fileName = caption[1] + ".jpg"
		} else {
			fileName = "未知"
		}
		// 对url格式进行转换
		res, err := url.QueryUnescape(fileName)
		// 把信息中的逗号全部换成下换线，逗号文件命名会报错
		fileName = strings.Replace(res, ",", "_", -1)
		fmt.Printf("下载 -->%s \n", fileName)
		f, err := os.Create("./download/" + fileName)
		if err != nil {
			panic(err)
		}
		io.Copy(f, bytes.NewReader(r.Body))
	})
	//需要下载图片的数量
	pageSize := 200
	pageNum := 10
	for i := 0; i < pageNum; i++ {
		url := fmt.Sprintf("https://www.quanjing.com/Handler/SearchUrl.ashx?t=1952&callback=searchresult&q=%s&stype=1&pagesize=%d&pagenum=%d&imageType=2&imageColor=&brand=&imageSType=&fr=1&sortFlag=1&imageUType=&btype=&authid=&_=1577435470818", keyword, pageSize, i)
		_ = c.Visit(url)
	}
	c.Wait()
	imageC.Wait()
	fmt.Printf("done,cost:%s\n", time.Since(t))
}
