package com.dingli.filter;

import com.dingli.domain.User;
import com.dingli.service.UserService;
import com.dingli.service.impl.UserServiceImpl;
import com.dingli.utils.CookieUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

//登录过滤器
@WebFilter("*.action")
//页面提交的action都以.action结尾，拦截这些请求，对其进行选择性放行
public class LoginFilter implements Filter {
    public LoginFilter() {
    }

    /**
     * 初始化拦截器
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //强转ServletRequest、ServletResponse为HttpServletRequest、HttpServletResponse
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        //HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //①判断用户是否登录，登录就放行，没登录就跳转到登录页面
        //登录用户信息保存在session中，从中获取当前登录的用户
        HttpSession session = httpServletRequest.getSession();

        User currentUser = (User) session.getAttribute("session_user");

        UserService userService = new UserServiceImpl();
        //不为null，表示用户已经登录
        if (null != currentUser){
            //判断登录的用户是否有对某些请求的访问权限
            roleJudgment(currentUser,httpServletRequest,servletResponse,filterChain);

        }else {
            //②判断cookie中是否有用户信息（学号和密码）
            //便利所有的cookie，查询是否有保存了当前登录用户的cookie
            Cookie cookie = CookieUtil.getCooKieByName(httpServletRequest,"cookie_name_pass");
            if (null!=cookie){
                //不为null，说明有保存当前用户的cookie
                String stuCodePass = URLDecoder.decode(cookie.getValue(),"utf-8");
                //stucode#password，所以用split分隔开，分别拿到学号和密码
                String[] stu_pass = stuCodePass.split("#");
                //判断获得的学号和密码是否有效，避免该用户已经被管理员注销，被注销就无法登陆。
                User user1 = userService.findUserByStuCodeAndPass(stu_pass[0],stu_pass[1]);
                if (null!=user1){
                    //有效就可以放行
                    //记住保存在浏览器cookie的学号密码登陆成功了也要讲它存进session里
                    roleJudgment(user1,httpServletRequest,servletResponse,filterChain);
                }else {
                    //存的用户信息失效了
                    httpServletRequest.setAttribute("error","验证已失效，请重新登录！");
                    httpServletRequest.getRequestDispatcher("/index.jsp").forward(servletRequest,servletResponse);
                }
            }else {
                //cookie里没有存储用户信息
                //给一个提示信息，提醒先登录
                httpServletRequest.setAttribute("error","请先登录！");
                httpServletRequest.getRequestDispatcher("/index.jsp").forward(servletRequest,servletResponse);
            }
        }
    }
    //角色判断放法，根据用户角色判断能使用的功能
    /**
     * 功能根据前端页面改动
     * */
    private void roleJudgment(User user, HttpServletRequest httpServletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取用户角色id
        Integer roleId = user.getRoleId();
        //获取用户发送的url（请求地址）
        String requestServletPath = httpServletRequest.getRequestURI();
        System.out.println("请求地址："+requestServletPath);
        //动态获取项目名
        String contextPath = httpServletRequest.getContextPath();
        System.out.println("项目名："+contextPath);
        System.out.println(requestServletPath.startsWith(contextPath+"/dormBuild.action") || requestServletPath.startsWith(contextPath+"/dormManager.action"));
        System.out.println(requestServletPath.startsWith(contextPath+"/student.action") && !roleId.equals(2));
        if ((requestServletPath.startsWith(contextPath+"/dormBuild.action") || requestServletPath.startsWith(contextPath+"/dormManager.action")) && roleId.equals(0)){
            //当用户发送的宿舍楼管理模块或者宿舍管理员管理模块的请求时，只有当前用户是超管的时候才放行
            System.out.println("放行~");
            httpServletRequest.getSession().setAttribute("session_user",user);
            filterChain.doFilter(httpServletRequest,servletResponse);
        }else if (requestServletPath.startsWith(contextPath+"/student.action") && !roleId.equals(2)){
            //当用户发送的请求是学生模块时，只有当前用户不是学生就放行
            System.out.println("放行~");
            httpServletRequest.getSession().setAttribute("session_user",user);
            filterChain.doFilter(httpServletRequest,servletResponse);
        }else if(requestServletPath.startsWith(contextPath+"/record.action") ||
                requestServletPath.startsWith(contextPath+"/password.action") ||
                requestServletPath.startsWith(contextPath+"/loginOut.action") ||
                requestServletPath.startsWith(contextPath+"/index.action")){
                //当用户发送的请求是考勤、修改密码、退出系统时，不管用户是什么角色都放行
            httpServletRequest.getSession().setAttribute("session_user",user);
            System.out.println("放行~");
            filterChain.doFilter(httpServletRequest,servletResponse);
        }else {
            System.out.println("非法访问，请正确登录");
            httpServletRequest.getRequestDispatcher("/index.jsp").forward(httpServletRequest,servletResponse);
        }
    }

    /**
     * 销毁方法
     */
    @Override
    public void destroy() {

    }
}
