var log = console.log.bind(console)

var e = function (selector) {
    return document.querySelector(selector)
}

var ajax = function (method, url, data, callback) {
    var r = new XMLHttpRequest()
    // 设置请求方法和请求地址
    r.open(method, url, true)
    r.setRequestHeader("Content-Type", "application/json")

    // 注册响应函数
    // 注册响应函数
    r.onreadystatechange = function() {
        if (r.readyState === 4) {
            callback(r.response)
        }
    }

    // 发送请求
    data = JSON.stringify(data)
    r.send(data)
}


var todoTemplate = function (todo) {

    var t = `
        <div class="todo-cell">
            <span>${todo.id}: ${todo.content}</span>
        </div>
    `
    return t
}

var insertTodo = function (todoCell) {
    var todoList = e("#id-todo-list")
    todoList.insertAdjacentHTML("beforeend", todoCell)
}

var loadTodos = function () {
    var path = "/ajax/todo/all"
    var method = "POST"
    var data = ""

    ajax(method, path, data, function (response) {
        log("response: ", response)
        var todoList = JSON.parse(response)
        log("todo all:", todoList)

        for (let i = 0; i < todoList.length; i++) {
            var todo = todoList[i]
            var html = todoTemplate(todo)
            insertTodo(html)
        }
    })
}


var bindButtonClick = function () {
    var button = e("#id-button-add")
    button.addEventListener("click", function () {
        log("click")
        var input = e("#id-input-todo")

        var path = "/ajax/todo/add"
        var method = "POST"
        var data = {
            content: input.value
        }

        ajax(method, path, data, function (response) {
            log("response: ", response)
            var todo = JSON.parse(response)
            var html = todoTemplate(todo)
            insertTodo(html)
        })
    })
}


var _main = function () {
    bindButtonClick()
    loadTodos()
}

log("todo.js")
_main()