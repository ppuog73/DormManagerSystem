package com.dingli.controller;

import com.dingli.utils.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//退出功能
@WebServlet("/loginOut.action")
public class LoginOutServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //清除保存在session会话中的用户信息
        req.getSession().removeAttribute("session_user");
        //清除cookie中的用户信息，一般有多个cookie，所以要根据用户名找到cookie
        Cookie cookie = CookieUtil.getCooKieByName(req,"cookie_name_pass");
        if (null!=cookie){
            //将cookie的有效时间置0
            cookie.setMaxAge(0);
            //设置cookie的作用范围
            cookie.setPath(req.getContextPath());
            //将cookie响应出去
            resp.addCookie(cookie);
        }
        System.out.println("跳转到登录页面~");
        //重新转到登录页面
        resp.sendRedirect("index.jsp");
    }
}
