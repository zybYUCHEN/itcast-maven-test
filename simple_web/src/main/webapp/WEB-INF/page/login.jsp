<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>管理员登录</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <%--4. 导入管理cookie的插件--%>
    <script src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>

    <script type="text/javascript">
        //切换验证码
        function refreshCode(){
            //1.获取验证码图片对象
            var vcode = document.getElementById("vcode");

            //2.设置其src属性，加时间戳
            vcode.src = "${pageContext.request.contextPath}/checkCode?time="+new Date().getTime();
        }

      <%
           //编辑脚本，实现浏览器记住状态功能
           String username = "";
           String password = "";
           String checked = "";
           boolean flag=false;

           Cookie[] cookies = request.getCookies();
           if (cookies!=null&&cookies.length>1){
               //遍历数组，实现给表单项赋值
                for (Cookie cookie : cookies) {
               if ("username".equals(cookie.getName())){
                   username = cookie.getValue();
                   flag=true;
               }
               if ("password".equals(cookie.getName())){
                   password = cookie.getValue();
               }
           }
           }
           if (flag){
                request.setAttribute("checked", "checked");
           }else {
               request.setAttribute("checked", "");//有添加就必须有删除
           }
            request.setAttribute("username", username);
           request.setAttribute("password", password);

      %>

        $(function () {
            //页面一加载，如果之前时勾选了记住状态，就发送异步请求，请求验证码信息
            if ($("#code").prop("checked")){
                $.ajax({
                    type:"post",
                    url:"/home_war/user/login/1",
                    data:"application/json",
                    success:function(data){
                            var flag = $("#checkCode");
                            flag.prop("placeholder",data);
                            flag.val(data);
                    },
                    error : function() {
                        // location.href="user/find"
                    }
                });
            }
        })


    </script>
</head>
<body>
<div class="container" style="width: 400px;">
    <h3 style="text-align: center;">管理员登录</h3>
    <form action="${pageContext.request.contextPath}/user/login" method="post">
        <div class="form-group">
            <label for="username">用户名：</label>
            <c:if test="${username==''}">
                <input type="text" name="username" class="form-control" id="username"  placeholder="请输入用户名"/>
            </c:if>
            <c:if test="${username!=''}">
                <input type="text" name="username" class="form-control" id="username" value="${username}" placeholder="${username}"/>
            </c:if>
        </div>

        <div class="form-group">
            <label for="password">密码：</label>
            <c:if test="${username==''}">
                <input type="password" name="password" class="form-control" id="password"  placeholder="请输入密码"/>
            </c:if>
            <c:if test="${username!=''}">
                <input type="password" name="password" class="form-control" id="password" value="${password}" placeholder="${password}"/>
            </c:if>
        </div>
        <div class="form-inline">
            <label for="checkCode">验证码：</label>
            <input type="text" name="checkCode" class="form-control" id="checkCode"  style="width: 120px;"/>
            <a href="javascript:refreshCode();">
                <img src="${pageContext.request.contextPath}/checkCode" title="看不清点击刷新" id="vcode"/>
            </a>
        </div>
        <hr/>
        <div class="form-group" style="text-align: center;">
            <input class="btn btn btn-primary" type="submit" id="login" value="登录">
            <div class="auto_login">
                <input type="checkbox"  id="code" name="ckecked" ${checked} value="1" >
                <span>记住密码</span>
            </div>

        </div>
    </form>

    <!-- 出错显示的信息框 -->
    <div class="alert alert-warning alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" >
            <span>&times;</span>
        </button>
        <strong>${login_msg}</strong>
    </div>
</div>
</body>
</html>