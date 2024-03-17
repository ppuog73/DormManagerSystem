package com.dingli.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//首页功能
@WebServlet("/index.action")//注解不写找不到资源
public class IndexServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //直接跳转
        req.getRequestDispatcher("/jsp/main.jsp").forward(req,resp);
    }
}
