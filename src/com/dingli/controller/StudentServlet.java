package com.dingli.controller;

import com.dingli.domain.DormBuild;
import com.dingli.domain.User;
import com.dingli.service.DormBuildService;
import com.dingli.service.UserService;
import com.dingli.service.impl.DormBuildServiceImpl;
import com.dingli.service.impl.UserServiceImpl;
import com.dingli.utils.PageModel;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/student.action")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID =1L;
    public StudentServlet(){
        super();
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //解决传递中文乱码问题
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        //获取学生id
        String id = request.getParameter("id");
        //获取disabled值
        String disabled = request.getParameter("disabled");
        //获取当前登录的用户
        User user = (User) request.getSession().getAttribute("session_user");

        Integer roleId = user.getRoleId();

        DormBuildService buildService = new DormBuildServiceImpl();

        UserService userService = new UserServiceImpl();

        List<DormBuild> builds = new ArrayList<>();
        if (roleId.equals(0)){
            //超级管理员能将学生添加到其管理的所有的宿舍楼，查询所有宿舍楼
            builds = buildService.findALL();

        }else if(roleId.equals(1)){
            //宿舍管理员只能添加学生到其管理的宿舍楼
            builds = buildService.findByUserId(user.getId());
        }
        request.setAttribute("builds",builds);

        if (action != null && action.equals("list")){
            //右侧展示查询学生信息
            String dormBuildId = request.getParameter("dormBuildId");
            //接收传来的搜索参数
            String searchType = request.getParameter("searchType");
            String keyword = request.getParameter("keyword");
            //当前要查询的页面
            String pageIndex = request.getParameter("pageIndex");
            //默认查询第一页，需要两个参数，当前页码pageIndex，每页展示的条数
            PageModel pageModel = new PageModel();
            if (pageIndex != null && !pageIndex.equals("")){
                pageModel.setPageIndex(Integer.parseInt(pageIndex));
            }
            List<User> students = userService.findStudent(dormBuildId,searchType,keyword,user,pageModel);

            //获取查询处理的总数量
            Integer totalNum = userService.findTotalNum(dormBuildId,searchType,keyword,user);
            request.setAttribute("totalNum",totalNum);
            request.setAttribute("pageIndex",pageModel.getPageIndex());
            request.setAttribute("dormBuildId",dormBuildId);
            request.setAttribute("searchType",searchType);
            request.setAttribute("keyword",keyword);
            request.setAttribute("students",students);
            request.setAttribute("mainRight","/jsp/studentList.jsp");
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }else if (action != null && action.equals("preAdd")){
            //根据用户角色查询宿舍楼进行添加学生
            //如果当前用户是宿舍管理员，只能添加学生到其管理的宿舍楼
            //跳转到学生的添加页面
            request.setAttribute("mainRight","/jsp/studentAddOrUpdate.jsp");
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }else if (action != null && action.equals("save")){
            //保存学生
            String stuCode = request.getParameter("stuCode");
            String name = request.getParameter("name");
            String sex = request.getParameter("sex");
            String tel = request.getParameter("tel");
            String passWord = request.getParameter("passWord");
            String dormBuildId = request.getParameter("dormBuildId");
            String dormCode = request.getParameter("dormCode");
            System.out.println("准备要修改的信息："+stuCode+" "+name+" "+sex+" "+tel+" "+passWord+" "+dormBuildId+" "+dormCode);

            //如果未存在则保存
            User student = userService.findUserByStuCode(stuCode);
            System.out.println("根据学号查询出来的学生 "+student);
            //根据id来判断是否是修改用户还是添加新用户
            if (id != null && !id.equals("")){
                //更新之前查询数据是否已经存在当前学号的学生，如果存在则跳转到修改页面
                //通过学号找到的学生不等于当前要修改的学生
                if (student != null && student.getId().equals(Integer.parseInt(id))){
                    //当前学号的学生已经存在请重新修改，跳到修改页面
                    request.getRequestDispatcher("/student.action?action=preUpdate&id="+id).forward(request,response);
                }else {
                    // 不存在才更新
                    System.out.println("开始更新信息~");
                    User studentUpdate = userService.findById(Integer.parseInt(id));
                    studentUpdate.setStuCode(stuCode);//stu_code
                    studentUpdate.setName(name);//name
                    studentUpdate.setSex(sex);//sex
                    studentUpdate.setTel(tel);//tel
                    studentUpdate.setPassWord(passWord);//passWord
                    studentUpdate.setDormBuildId(Integer.parseInt(dormBuildId));//dormBuildId
                    studentUpdate.setDormCode(dormCode);//dorm_Code
                    userService.updateStudent(studentUpdate);
                    response.sendRedirect(getServletContext().getContextPath() + "/student.action?action=list");
                }
            }else {
                //保存
                if (student != null){
                    //在保存之前查询数据是否已经存在当前学号的学生，若果存在则跳转到添加页面
                    response.sendRedirect(getServletContext().getContextPath()+"/student.action?action=preAdd");
                }else {
                    User user2 = new User();
                    user2.setStuCode(stuCode);
                    user2.setName(name);
                    user2.setSex(sex);
                    user2.setTel(tel);
                    user2.setPassWord(passWord);
                    user2.setDormBuildId(Integer.parseInt(dormBuildId));
                    user2.setDormCode(dormCode);
                    user2.setRoleId(2);
                    user2.setCreateUserId(user.getId());
                    userService.saveStudent(user2);
                    response.sendRedirect(getServletContext().getContextPath() + "/student.action?action=list");
            }
            }
        }else if (action != null && action.equals("preUpdate")){
            //通过学生id查找学生并保存，以便在页面展示学号
            User userUpdate = userService.findById(Integer.parseInt(id));
            //判断当前登录的用户是否有修改该学生的权利，如没有就跳转到学生管理的列表页，如有则跳转到修改页面
            User user2 = userService.findByUserIdAndManager(userUpdate.getId(),user);
            if(user2 != null){
                //表示有修改权限
                request.setAttribute("userUpdate",userUpdate);
                //跳转到学会管理的修改页面
                request.setAttribute("mainRight","studentAddOrUpdate.jsp");
                request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
            }else {
                //无修改权限，跳转到学生管理列表页面
                response.sendRedirect(getServletContext().getContextPath() + "/student.action?action=list");
            }

        }else if (action != null & action.equals("deleteOrActive") ){
            //删除或激活
            User studentUpdate = userService.findById(Integer.parseInt(id));
            studentUpdate.setDisabled(Integer.parseInt(disabled));

            //判断当前登录的用户是否有修改该学生的权利，如没有调整到学生管理的列表页，如有则跳转到修改页面
            User user2 = userService.findByUserIdAndManager(studentUpdate.getId(),user);
            if(user2 != null){
                //表示有修改权限

                userService.updateStudent(studentUpdate);
                response.sendRedirect(getServletContext().getContextPath() + "/student.action?action=list");
            }else {
                //没有修改权限
                response.sendRedirect(getServletContext().getContextPath() + "/student.action?action=list");
            }

        }
    }


}

