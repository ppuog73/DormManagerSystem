<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宿舍管理系统</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css">
    <%--日期控件所需样式表--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" media="screen">
    <%--日期控件所需js--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    <%--如需中文显示，加载中文的资源文件--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <style type="text/css">
        .bs-docs-sidenav {
            background-color: #fff;
            border-radius: 6px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.067);
            padding: 0;
            width: 228px;
        }

        .bs-docs-sidenav > li > a {
            border: 1px solid #e5e5e5;
            display: block;
            padding: 8px 14px;
            margin: 0 0 -1px;
        }
        .bs-docs-sidenav > li:first-child > a {
            border-radius: 6px 6px 0 0;
        }
        .bs-docs-sidenav > li:last-child > a {
            border-radius: 0 0 6px 6px;
        }
        .bs-docs-sidenav > .active > a {
            border: 0 none;
            box-shadow: 1px 0 0 rgba(0, 0, 0, 0.1) inset, -1px 0 0 rgba(0, 0, 0, 0.1) inset;
            padding: 9px 15px;
            position: relative;
            text-shadow: 0 1px 0 rgba(0, 0, 0, 0.15);
            z-index: 2;
        }
        .bs-docs-sidenav .icon-chevron-right {
            float: right;
            margin-right: -6px;
            margin-top: 2px;
            opacity: 0.25;
        }
        .bs-docs-sidenav > li > a:hover {
            background-color: #f5f5f5;
        }
        .bs-docs-sidenav a:hover .icon-chevron-right {
            opacity: 0.5;
        }
        .bs-docs-sidenav .active .icon-chevron-right, .bs-docs-sidenav .active a:hover .icon-chevron-right {
            background-image: url("${pageContext.request.contextPath}/bootstrap/img/glyphicons-halflings-white.png");
            opacity: 1;
        }
    </style>
</head>
<!--首页-->
<body>
<!--上边部分-->
<div class="container-fluid">
    <div style="height: 100px;background: url('${pageContext.request.contextPath}/images/bg.jpg')no-repeat;background-size: 100%">
        <div align="left" style="width: 80%;height: 100px;float: left;padding-top: 25px;padding-left: 30px">
            <font color="white" size="6" >宿舍管理系统</font>
        </div>
        <div style="padding-top: 70px;padding-right: 20px;font-size: 16px">
            <!--登录成功后，把当前用户的信息存入session中-->
            当前用户：&nbsp;&nbsp;<font color="red">${session_user.name}</font>
        </div>
    </div>
</div>
<!--左边部分-->
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2 bs-docs-sidebar">
            <ul class="nav nav-list bs-docs-sidenav">
                <%--超级管理员--%>
                <c:if test="${session_user.roleId == 0}">
                <li><a href="index.action"><i class="icon-chevron-right"></i>首页</a></li>
                <li id="dormManager"><a href="dormManager.action?action=list"><i class="icon-chevron-right"></i>宿舍管理员管理</a></li>
                <li id="student"><a href="student.action?action=list"><i class="icon-chevron-right"></i>学生管理</a></li>
                <li id="dormBuild"><a href="dormBuild.action?action=list"><i class="icon-chevron-right"></i>宿舍楼管理</a></li>
                <li id="record"><a href="record.action?action=list"><i class="icon-chevron-right"></i>缺勤记录</a></li>
                <li id="password"><a href="password.action?action=preChange"><i class="icon-chevron-right"></i>修改密码</a></li>
                <li id="loginOut"><a href="loginOut.action"><i class="icon-chevron-right"></i>退出系统</a></li>
                    <%--cif 选择标签，根据设定条件的不同显示不同的页面功能--%>
                </c:if>
                    <!--宿舍管理员  -->
                    <c:if test="${session_user.roleId == 1}">
                        <li><a href="index.action"><i class="icon-chevron-right"></i>首页</a></li>
                        <li id="student"><a href="student.action?action=list"><i class="icon-chevron-right"></i>学生管理</a></li>
                        <li id="record"><a href="record.action?action=list"><i class="icon-chevron-right"></i>缺勤记录</a></li>
                        <li id="password"><a href="password.action?action=preChange"><i class="icon-chevron-right"></i>修改密码</a></li>
                        <li id="loginOut"><a href="loginOut.action"><i class="icon-chevron-right"></i>退出系统</a></li>
                    </c:if>
                    <!-- 学生 -->
                    <c:if test="${session_user.roleId == 2}">
                        <li><a href="index.action"><i class="icon-chevron-right"></i>首页</a></li>
                        <li id="record"><a href="record.action?action=list"><i class="icon-chevron-right"></i>缺勤记录</a></li>
                        <li id="password"><a href="password.action?action=preChange"><i class="icon-chevron-right"></i>修改密码</a></li>
                        <li id="loginOut"><a href="loginOut.action"><i class="icon-chevron-right"></i>退出系统</a></li>
                    </c:if>


            </ul>
        </div>
        <div class="span10">
            <jsp:include page="${mainRight==null?'blank.jsp':mainRight}"></jsp:include>
        </div>
    </div>
</div>
</body>
</html>
