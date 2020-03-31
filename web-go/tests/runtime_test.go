package tests

import (
	"fmt"
	"runtime"
	"testing"
	"time"
)

func TestCPU(t *testing.T) {
	// CPU核心数量
	println(runtime.NumCPU())
	a := 0
	go func(a int) {
		a++
		// 当前协程让出CPU时间片
		runtime.Gosched()
		// 退出协程
		runtime.Goexit()
		a++
	}(a)
	time.Sleep(1 * time.Second)
	fmt.Println(a)
}
