<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/1/8
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>宿舍管理系统</title>
      <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
      <script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
      <script type="text/javascript">
          function checkForm() {
              //获取用户名和密码
              let stuCode=document.getElementById('stuCode').value;
              let passWord=document.getElementById('password').value;
              if (stuCode == null || stuCode == ""){
                  document.getElementById("error").innerText="学号不能为空"
                  return false;
              }
              if (passWord == null || passWord == ""){
                  document.getElementById("error").innerText="密码不能为空"
                  return false;
              }
              return false;
          }
          $(document).ready(function () {
              $("#login").addClass("active")
          });
      </script>
      <style type="text/css">
          body{
              padding-top: 200px;
              padding-bottom: 40px;
              background: url("${pageContext.request.contextPath}/images/bg.jpg") no-repeat center fixed;
              background-size: 100% 100%;
          }

          .form-signin{
              max-width: 300px;
              padding: 19px 29px 0px;
              margin: 0 auto 20px;
              background-color: white;
              border: 1px solid grey;
              -webkit-border-radius: 5px;
              -moz-border-radius: 5px;
              border-radius: 5px;
              -webkit-box-shadow: 0 1px rgba(0,0,0,0.05);
              -moz-box-shadow: 0 1px 2px rgba(0,0,0,0.05);
              box-shadow: 0 1px 2px rgba(0,0,0,0.05);
          }

          .form-signin .form-signin-heading,.form-signin .checkbox{
              margin-bottom: 10px;
          }
          .form-signinn input[type="text"],.form-signin input[type="password"]{
              font-size: 16px;
              height: auto;
              margin-bottom: 15px;
              padding: 7px 9px;
          }
      </style>

  </head>
  <body>
  <div class="container">
      <!--servlet：/login   提交方式：post-->
      <form name="myForm" class="form-signin" action="${pageContext.request.contextPath}/login" method="post">
          <h2 class="form-signin-heading" style="text-align: center"><font color="gray" size="6">宿舍管理系统</font></h2>
          <input type="hidden" value="submit" name="action">
          <!--input-block-lecel类-->
          <input type="text" name="stuCode" id="stuCode" value="0001" class="input-block-level">
          <input type="password" name="password" id="password" value="123456" class="input-block-level">
          <label class="checkbox">
              <div id="remember" name="remember" typeof="checkbox" value="remember-me" style="margin-right: 10px">
                  <input type="checkbox" style="margin-right: 10px">记住我&nbsp;&nbsp;&nbsp;&nbsp;
              </div>
              <font id="error" color="red">${error}</font>
          </label>
          <div style="text-align: center">
              <button type="submit" class="btn btn-large btn-primary">登录</button>&nbsp;&nbsp;&nbsp;&nbsp;
          </div>
          <p align="center" style="padding-top: 15px">版权所有 2020 ddd</p>
      </form>
      </div>
  </body>
</html>
