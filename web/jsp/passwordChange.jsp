<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript">
    //检测密码是否输入完整
    function checkForm(){
        let oldPassword=document.getElementById("oldPassword").value;
        let newPassword=document.getElementById("newPassword").value;
        let rPassword=document.getElementById("rPassword").value;

        $("#error").html("");
        if(oldPassword===""||newPassword===""||rPassword===""){
            document.getElementById("error").innerHTML="信息填写不完整！";
            return false;
        } else if(newPassword === oldPassword){
            document.getElementById("error").innerHTML="修改前后密码不能一致！";
            return false;
        }else if(newPassword!==rPassword){
            document.getElementById("error").innerHTML="新密码两次填写不一致！";
            return false;
        }
        //提交的时候验证原密码是否正确
        if(onblurFn(oldPassword)){
            return false;
        }

        return true;
    }

    //判断原密码是否正确
    function onblurFn(oldPassword){
        //每一次检查前都要把提示信息设置为空，避免误导
        $("#error").html("");
        if(oldPassword != null && oldPassword !== ""){
            //校验用户输入的原密码正确
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/password.action?action=ajaxOldPassWord",
                data: "oldPassword="+oldPassword,
                async:false,//同步请求
                success: function(msg){
                    if(msg){
                        //用户输入的原密码不正确
                        $("#error").html(msg);
                        return true;
                    }else{
                        return false;
                    }
                }
            });
        }
    }


    $(document).ready(function(){
        $("ul li:eq(5)").addClass("active");
    });
</script>
<div class="data_list">
    <div class="data_list_title">
        修改密码
    </div>
    <form action="password.action?action=change" method="post" onsubmit="return checkForm()">
        <div class="data_form" >
            <table align="center">
                <tr>
                    <td><font color="red">*</font>原密码：</td>
                    <td><input type="password"  id="oldPassword" onblur="onblurFn(this.value)" name="oldPassword" value=""  style="margin-top:5px;height:30px;" /></td>
                </tr>
                <tr>
                    <td><font color="red">*</font>新密码：</td>
                    <td><input type="password" id="newPassword"  name="newPassword" value="" style="margin-top:5px;height:30px;" /></td>
                </tr>
                <tr>
                    <td><font color="red">*</font>重复密码：</td>
                    <td><input type="password" id="rPassword"  name="rPassword" value="" style="margin-top:5px;height:30px;" /></td>
                </tr>
            </table>
            <div align="center">
                <input type="submit" class="btn btn-primary" value="提交"/>
            </div>
            <div align="center">
                <font id="error" color="red"></font>
            </div>
        </div>
    </form>
</div>
