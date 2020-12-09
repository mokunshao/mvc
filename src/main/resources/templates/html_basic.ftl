<!DOCTYPE html>
<!-- c + / -->
 <!--注释是这样的, 不会被显示出来 -->
<!--
    html 格式是浏览器使用的标准网页格式
    简而言之就是 标签套标签
    css 控制显示
-->
<!-- html 中是所有的内容 -->
<html>
    <!-- head 中是放一些控制信息, 不会被显示 -->
    <head>
        <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
        <meta charset="utf-8">
        <!-- title 是浏览器显示的页面标题 -->
        <title>例子111eeeee1</title>
    </head>
    <!-- body 中是浏览器要显示的内容 -->
    <body>
        <!-- html 中的空格是会被转义的, 所以显示的和写的是不一样的 -->
        <!-- 代码写了很多空格, 显示的时候就只有一个 -->
        很         好普通版
        <h1>很好 h1 版</h1>
        <h2>很好 h2 版</h2>
        <h3>很好 h3 版</h3>

        <!-- form 是用来给服务器传递数据的 tag -->
        <!-- action 属性是 path -->
        <!-- method 属性是 HTTP方法 一般是 get 或者 post -->
        <!-- get post 的区别上课会讲 -->
        <!--/a/b/c-->
<!--        一般情况下我们会用 /message/add, 而不会用 /addmessage   -->
        <form action="/message" method="get">
            <!-- textarea 是一个文本域 -->
            <!-- name rows cols 都是属性, 用处上课讲 -->
            <input name="author"/>
            <br/>
            <textarea name="message" rows="8" cols="40"></textarea>
            <br/>
            <!-- button type=submit 才可以提交表单 -->
            <!--http://localhost:4000/message?author=gua&message=hello
Request Method: GET-->
            <!--GET /message?message=hello HTTP/1.1-->
            <!--GET /message?message=hello&author=gua HTTP/1.1-->
            <!--GET /message?message=&author=gua HTTP/1.1-->
            <!--
            GET 是 form 的 method 属性指定
            PATH 是 form 中的 action
            问号后面的叫 query
            问号 ? 后面的参数是 k=v 这样的格式
            并且用 & 符号隔开
            k 由 name 属性指定
            v 由 input/textarea 中内容指定
            -->
            <button type="submit">GET 提交</button>
        </form>
        <form action="/message" method="post">
            <input name="author" />
            <!--<input name="name">-->
            <br>
            <textarea name="message" rows="8" cols="40"></textarea>
            <br>
            <button type="submit">POST 提交</button>
        </form>

        <#list messgeList as m>
            <#assign t = 1596442719 * 1000>
            ${t?number_to_datetime?string("yyyy-MM-dd")}
            <div> ${m.author}: ${m.message}</div>
        </#list>
    </body>
</html>
