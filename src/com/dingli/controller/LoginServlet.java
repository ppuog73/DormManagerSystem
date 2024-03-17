package com.dingli.controller;

import com.dingli.domain.User;
import com.dingli.service.UserService;
import com.dingli.service.impl.UserServiceImpl;
import com.dingli.utils.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
/**接收前端传来的数据*/
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public LoginServlet() {
        super();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        /*
        * 前端数据到req，将数据通过数据库查，先调用service层查数据库，service调用dao层
        * controller->service->dao->database然后逐一返回
        * */
        //tomcat处理post请求乱码问题
        req.setCharacterEncoding("utf-8");
        //获取前端传送过来的用户数据
        String stuCode = req.getParameter("stuCode");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        //根据用户在前端输入的学号和密码查询数据库
        UserService userService = new UserServiceImpl();
        User user = userService.findUserByStuCodeAndPass(stuCode,password);

        if (null == user){
            req.setAttribute("error","您输入的学号或密码错误");
            req.getRequestDispatcher("index.jsp").forward(req, res);
        }else {
            //用户登录成功的情况下，将用户数据存入session中，保存时间默认为30分钟
            req.getSession().setAttribute("session_user",user);
            //用户选择“记住我”后，将会保存信息到cookie中，为期七天
            if (null != remember && remember.equals("remember-mc")){
                CookieUtil.addCookie("cookie_name_pass",7*24*60*60,req,res,
                        //保存的时候先进行编码
                        URLEncoder.encode(stuCode,"utf-8"),
                        URLEncoder.encode(password,"utf-8"));
            }
            //跳转到主页界面
            req.getRequestDispatcher("/jsp/main.jsp").forward(req,res);
        }
    }
}
