// AJAX 和使用方法
// ajax 就是浏览器提供的用 js 获取链接内容的 API
// 你可以理解为发送网络请求的标准库

// 获取登录页面
// 创建 AJAX 对象
let r = new XMLHttpRequest()
// 设置请求方法和请求地址
r.open('GET', 'https://www.kuaibiancheng.com/sandbox/todo/3587405093/all', true)
// 注册响应函数
// 注册响应函数
r.onreadystatechange = function() {
    if (r.readyState === 4) {
        console.log('state change', r, r.status, r.response)
        let response = JSON.parse(r.response)
        console.log('response', response)
    } else {
        console.log('change')
    }
}

// 发送请求
let account = {}
let data = JSON.stringify(account)
r.send(data)


var ajax = function (method, path, data, callback) {
    let r = new XMLHttpRequest()
    // 设置请求方法和请求地址
    r.open(method, path, true)
    // 注册响应函数
    // 注册响应函数
    r.onreadystatechange = function() {
        if (r.readyState === 4) {
            callback(r.response)
        }
    }

// 发送请求
    let d = JSON.stringify(data)
    r.send(d)
}

var url = 'https://www.kuaibiancheng.com/sandbox/todo/3587405093/all'
var method = "GET"
let data = ""

ajax(method, url, data, function (response) {
    console.log("get response: ", response)
})


console.log("ajax 发送完毕")