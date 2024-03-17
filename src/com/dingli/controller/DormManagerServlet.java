package com.dingli.controller;

import com.dingli.domain.DormBuild;
import com.dingli.domain.User;
import com.dingli.service.DormBuildService;
import com.dingli.service.UserService;
import com.dingli.service.impl.DormBuildServiceImpl;
import com.dingli.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dormManager.action")
public class DormManagerServlet extends HttpServlet {
    public DormManagerServlet() {
        super();
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决post请求乱码问题
        request.setCharacterEncoding("utf-8");
        //获取前端action参数
        String action = request.getParameter("action");
        //获取 用户id
        String id = request.getParameter("id");

        //获取用户基本信息
        UserService userService = new UserServiceImpl();
        //获取宿舍楼信息
        DormBuildService dormBuildService = new DormBuildServiceImpl();
        List<DormBuild> builds = dormBuildService.find();
        request.setAttribute("builds",builds);
        //获取到action参数以后对其值进行判读
        if (null != action && action.equals("list")){
            //接收传来的搜索参数
            String searchType = request.getParameter("searchType");
            String keyword = request.getParameter("keyword");
            //查询宿舍管理员信息（包括基本信息和宿舍楼信息）
            List<User> users = userService.findManager(searchType,keyword);

            request.setAttribute("searchType",searchType);
            request.setAttribute("keyword",keyword);
            request.setAttribute("users",users);
            request.setAttribute("mainRight","dormManagerList.jsp");
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }else if (null !=action && action.equals("preAdd")){
            //调到宿舍管理员添加页面
            request.setAttribute("mainRight","dormManagerAddOrUpdate.jsp");
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }else if (null != action && action.equals("save")){
            String name = request.getParameter("name");
            String passWord = request.getParameter("passWord");
            String sex = request.getParameter("sex");
            String tel = request.getParameter("tel");
            //获取复选框中用户选中的宿舍楼
            String[] dormBuildId = request.getParameterValues("dormBuildId");

            if (null == id || id.equals("")) {//新增


                //保存用户信息
                User user = new User(name, passWord, sex, tel, null, 1);
                user.setDisabled(0);
                //获取当前登录的用户
                User user2 = (User) request.getSession().getAttribute("session_user");
                user.setCreateUserId(user2.getId());
                //保存用户管理员信息
                userService.saveManager(user, dormBuildId);
            }else {//修改
                //根据宿舍管理id，获取宿舍管理员信息
                User user = userService.findById(Integer.parseInt(id));
                user.setName(name);
                user.setPassWord(passWord);
                user.setSex(sex);
                user.setTel(tel);

                userService.updateManager(user);//更新用户基本信息

                //删除当前宿舍管理员管理的楼栋信息
                dormBuildService.deleteByUserId(user.getId());

                //新增当前宿舍管理员管理的楼栋信息
                dormBuildService.saveManagerAndBuild(user.getId(),dormBuildId);
            }
            //跳转到宿舍管理员列表，查看所有的宿舍管理员信息
            //重定向：请求连接断开时，不能再下一个servlet或jsp中获取request参数
            response.sendRedirect(getServletContext().getContextPath()+"/dormManager.action?action=list");
        }else if(null != action && action.equals("preUpdate")){

            //根据宿舍管理id，获取宿舍管理员信息
            User user = userService.findById(Integer.parseInt(id));
            //根据宿舍管理员id获取宿舍管理员管理的楼栋
            List<DormBuild> dormBuilds = dormBuildService.findByUserId(user.getId());
            user.setDormBuilds(dormBuilds);

            List<Integer> dormBuildIds = new ArrayList<>();
            for (DormBuild build : dormBuilds){
                dormBuildIds.add(build.getId());
            }

            request.setAttribute("userBuildids",dormBuildIds);
            request.setAttribute("user",user);
            request.setAttribute("mainRight","dormManagerAddOrUpdate.jsp");
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }else if(null != action && action.equals("deleteOrActive")){
            //获取删除或激活标志
            String disabled = request.getParameter("disabled");

            //根据宿舍管理id，获取宿舍管理员信息
            User user = userService.findById(Integer.parseInt(id));
            user.setDisabled(Integer.parseInt(disabled));

            //更新用户信息
            userService.updateManager(user);
            //userService.updateDisabledById(Integer.parseInt(id),Integer.parseInt(disabled));

            //跳转到宿舍管理员列表页面。查询所有的宿舍管理员
            response.sendRedirect(getServletContext().getContextPath()+"/dormManager.action?action=list");
        }
    }
}

