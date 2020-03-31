package tests

import (
	"fmt"
	"io/ioutil"
	"net/http"
	"testing"
	"time"
)

func TestGet(t *testing.T) {
	client := http.Client{
		Timeout: 5 * time.Second,
	}
	response, err := client.Get("http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1585661685339_R&pv=&ic=&nc=1&z=&hd=&latest=&copyright=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&hs=2&sid=&word=新垣结衣")
	if err != nil {
		panic(err)
	}
	if response.StatusCode == 200 {
		defer response.Body.Close()
	}
	b, err := ioutil.ReadAll(response.Body)
	if err != nil {
		panic(err)
	}
	fmt.Println(string(b))
}
