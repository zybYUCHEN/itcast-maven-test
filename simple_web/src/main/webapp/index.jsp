<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>首页</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</head>
<body>
<c:if test="${sessionScope.user!=null}">
    <div>${sessionScope.user.username}<c:if test="${sessionScope.user.gender=='男'}">先生,欢迎您</c:if><c:if test="${sessionScope.user.gender=='女'}">女士,欢迎您</c:if></div>
</c:if>
<div align="center">
    <a
            href="${pageContext.request.contextPath}/user/find/1/5" style="text-decoration:none;font-size:33px">查询所有用户信息
    </a>
</div>
<c:if test="${sessionScope.user!=null}">
    <div align="right">
        <a
                href="${pageContext.request.contextPath}/user/exit" >退出登录
        </a>
    </div></c:if>
<c:if test="${sessionScope.user ==null}">
    <div align="right">
        <a
                href="${pageContext.request.contextPath}/user/login" >请登录
        </a>
    </div></c:if>

</body>
</html>