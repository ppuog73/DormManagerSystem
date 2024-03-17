<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/1/9
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<script type="text/javascript">
    function userDeleteOrActive(id,disabled) {
        if (confirm("您确定要删除或激活这个宿管吗？")){
            window.location="dormManager.action?action=deleteOrActive&id="+id+"&disabled="+disabled;
        }

    }
    //文档加载完后
    window.onload=function () {
        //接收后端传来的查询条件
        var searchType = "${searchType}";
        var searchTypeSelect = document.getElementById("searchType");
        //获取下拉框里的所有条件,option是一个集合
        var options = searchTypeSelect.options;

        //遍历这个集合，把被选中的查询条件换成后端传来的查询条件
        $.each(options,function (i,option) {
            $(option).attr("selected",option.value == searchType)
        });
    }
    $(document).ready(function () {
        $("#dormManager").addClass("active");
    });
</script>
<div class="data_list">
    <div class="data_list_title">
        宿舍管理员管理
    </div>
    <form name="myForm" class="form-search" method="post" action="dormManager.action?action=list">
        <button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="window.location='dormManager.action?action=preAdd'">添加</button>
        <span class="data_search">
            <select id="searchType" name="searchType" style="width: 80px;">
                <option value="name">姓名</option>
                <option value="sex">性别</option>
                <option value="tel">电话号码</option>
            </select>
            &nbsp;<input id="keyword" name="keyword" type="text" value="${keyword}" style="width:120px;height: 30px;" class="input-medium search-query">
            &nbsp;<button type="submit" class="btn btn-info" onkeydown="if (event.keyCode==13) myForm.submit()">搜索</button>
        </span>
    </form>
    <div>
        <table class="table table-hover table-striped table-bordered">
            <tr>
                <th>序号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>电话</th>
                <th>宿舍楼</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${users}" var="user" varStatus="stat">
                <tr>
                    <td>${stat.index+1}</td>
                    <td>${user.name}</td>
                    <td>${user.sex}</td>
                    <td>${user.tel}</td>
                    <td>
                        <c:forEach items="${user.dormBuilds}" var="dormBuild">
                            ${dormBuild.name}&nbsp;
                        </c:forEach>
                    </td>
                    <td>
                        <button class="btn btn-mini btn-info" type="button" onclick="window.location='dormManager.action?action=preUpdate&id=${user.id}'">修改</button>
                        <c:if test="${user.disabled == 0}">
                            <button class="btn btn-mini btn-danger" type="button" onclick="userDeleteOrActive(${user.id},1)">删除</button>
                        </c:if>
                        <c:if test="${user.disabled == 1}">
                            <button class="btn btn-mini btn-danger" type="button" onclick="userDeleteOrActive(${user.id},0)">激活</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div align="center"><font color="red"></font></div>
</div>
