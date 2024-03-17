package com.dingli.controller;

import com.dingli.domain.User;
import com.dingli.service.UserService;
import com.dingli.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/password.action")
public class PassWordChangeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        //获取当前的登录的用户
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("session_user");
        System.out.println("当前登录的用户:"+currentUser);
        System.out.println("================修改密码==============");
        String action = request.getParameter("action");
        System.out.println("action: "+action);
        //获取用户输入的原密码
        String oldPassword = request.getParameter("oldPassword");
        System.out.println("用户输入的老密码："+oldPassword);
        //获取用户真正的原密码
        String passWord = currentUser.getPassWord();

        if (action != null && ("preChange").equals(action)){
            //跳转到修改密码页面
            request.setAttribute("mainRight","/jsp/passwordChange.jsp");
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }else if ( action != null && ("ajaxOldPassWord").equals(action) ){
            //检查用户输入的原密码是否正确
            //比较输入的原密码与真正的原密码是否相同
            if ( !oldPassword.equals(passWord) ){
                //密码输入错误，返回提示信息
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer= response.getWriter();
                writer.print("原密码错误！请重新输入");
                writer.flush();
                writer.close();
            }
        }else if ( action != null && ("change").equals(action) ){
            //修改密码
            //修改密码要判断原密码不能为空才能修改
            //获取用户输入的新密码
            String newPassword = request.getParameter("newPassword");
            //把新密码封装到当前用户的密码属性中
            currentUser.setPassWord(newPassword);
            //执行更新操作
            userService.updatePassword(currentUser);
            //执行修改以后跳转到登录页面
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
    }
}
