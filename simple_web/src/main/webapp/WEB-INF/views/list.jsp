<%@ page import="java.awt.datatransfer.Clipboard" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="http://localhost:8080/home_war/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
    <script>
        function deleteUser(id){
            if(confirm("确认删除吗？")){
                location.href="/home_war/user/delete/"+id;
            }
        }
       $(function () {
           $("#allCheck").click(function () {
               //$("input[type=checkbox]").prop("checked", $("#allCheck").prop("checked"));
                $(".checkNum").each(function () {
                    $(this).prop("checked",$("#allCheck").prop("checked"))
                })
           })
       });
        function deleteSelect() {
                var flag = false;
                var cbs = $(".checkNum");
                cbs.each(function () {
                    if ($(this).prop("checked")==true){
                        flag=true;
                    }
                });
                if (flag == false) {
                    alert("请勾选删除项.")
                    return;
                }
                if (confirm("确定要删除吗?")) {
                   $("#ds").submit();
                }

        }

    </script>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>

    <div style="float: left;">

        <form class="form-inline" action="${pageContext.request.contextPath}/user/find" method="get">
            <div class="form-group">
                <label for="exampleInputName2">姓名</label>
                <input type="text" name="username" class="form-control" id="exampleInputName2">
            </div>
            <div class="form-group">
                <label for="exampleInputName3">籍贯</label>
                <input type="text" name="address" class="form-control" id="exampleInputName3">
            </div>

            <div class="form-group">
                <label for="exampleInputEmail2">邮箱</label>
                <input type="email" class="form-control" name="email" id="exampleInputEmail2">
            </div>
            <input type="submit" class="btn btn-default" value="查询"/>
        </form>

    </div>


    <div style="float: right;margin: 5px;">

        <a class="btn btn-primary" href="${pageContext.request.contextPath}/path/pageName/add">添加联系人</a>
        <a class="btn btn-primary" href="javascript:deleteSelect()">删除选中</a>

    </div>

        <input type="hidden" name="_method" value="DELETE">
    <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th><input type="checkbox" id="allCheck"></th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>籍贯</th>
            <th>QQ</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>
        <form id="ds" action="${pageContext.request.contextPath}/user/delete" method="post">
        <tr>
            <c:forEach items="${pb.list}" var="user" varStatus="s">
            <td><input type="checkbox"  name="choice"  value="${user.id}" class="checkNum"></td>
                <input type="hidden" name="_method" value="DELETE">
            <td>${s.count}</td>
            <td>${user.username}</td>
            <td>${user.gender}</td>
            <td>${user.age}</td>
            <td>${user.address}</td>
            <td>${user.qq}</td>
            <td>${user.email}</td>
            <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/user/update/${user.id}/${pb.currentPage}">修改</a>&nbsp;<a class="btn btn-default btn-sm"
                                                                                    href="javascript:deleteUser(${user.id})">删除</a></td>
        </tr>
        </c:forEach>
    </form>

    </table>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li>
                    <c:if test="${pb.currentPage-1<=1}">
                    <a href="${pageContext.request.contextPath}/user/find/1/5" aria-label="Previous">
                        </c:if>

                        <c:if test="${pb.currentPage-1>1}">
                        <a href="${pageContext.request.contextPath}/user/find/${pb.currentPage-1}/5"
                           aria-label="Previous">
                            </c:if>
                            <span aria-hidden="true">上一页</span>
                        </a>


                        <c:forEach begin="1" end="${pb.totalPage}" varStatus="s">
                        <c:if test="${pb.currentPage==s.count}">
                <li class="active">
                    </c:if>
                    <c:if test="${pb.currentPage!=s.count}">
                <li class="">
                    </c:if>
                    <a href="${pageContext.request.contextPath}/user/find/${s.count}/5">${s.count}</a></li>
                </c:forEach>


                <li>
                    <c:if test="${pb.currentPage+1>=pb.totalPage}">
                    <a href="${pageContext.request.contextPath}/user/find/${pb.totalPage}/5" aria-label="Next">
                        </c:if>

                        <c:if test="${pb.currentPage+1<pb.totalPage}">
                        <a href="${pageContext.request.contextPath}/user/find/${pb.currentPage+1}/5" aria-label="Next">
                            </c:if>
                            <span aria-hidden="true">下一页</span>
                        </a>
                </li>
                <span style="font-size: 25px;margin-left: 5px;">
                    共${pb.totalCount}条记录，共${pb.totalPage}页
                </span>

            </ul>
        </nav>


    </div>
</div>
</body>
</html>
