package main

import (
	"fmt"
	"github.com/PuerkitoBio/goquery"
	"net/http"
)

func main() {
	url := "http://book.zongheng.com/book/189169.html"
	resp, err := http.Get(url)
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()
	if resp.StatusCode != 200 {
		fmt.Println("err")
	}
	doc, err := goquery.NewDocumentFromReader(resp.Body)
	if err != nil {
		panic(err)
	}
	selection := doc.Find(".wrap")
	for _, v := range selection.Nodes {
		fmt.Println(v.Namespace)
	}
}
