package com.dingli.controller;

import com.dingli.domain.DormBuild;
import com.dingli.domain.Record;
import com.dingli.domain.User;
import com.dingli.service.DormBuildService;
import com.dingli.service.RecordService;
import com.dingli.service.UserService;
import com.dingli.service.impl.DormBuildServiceImpl;
import com.dingli.service.impl.RecordServiceImpl;
import com.dingli.service.impl.UserServiceImpl;
import com.dingli.utils.PageModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/record.action")
public class RecordServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决post请求乱码问题
        request.setCharacterEncoding("utf-8");
        //获取前端action参数
        String action = request.getParameter("action");
        //获取 用户id
        String id = request.getParameter("id");
        //获取角色id,角色id在用户登录时就已经跟随用户信息一起存储到了session中
        //所以获取session然后获取用户信息再取role_id
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("session_user");
        DormBuildService buildService = new DormBuildServiceImpl();
        UserService userService = new UserServiceImpl();
        RecordService recordService = new RecordServiceImpl();
        if (null != action && action.equals("list")){
            Integer roleId = currentUser.getRoleId();
            List<DormBuild> builds = new ArrayList<>();
            if (roleId.equals(0)){
                //超级管理员能将学生添加到其管理的所有的宿舍楼，查询所有宿舍楼
                builds = buildService.findALL();
            }else if(roleId.equals(1)){
                //宿舍管理员只能添加学生到其管理的宿舍楼
                builds = buildService.findByUserId(currentUser.getId());
            }
            request.setAttribute("builds",builds);
            //展示缺勤记录
            //接收搜索参数
            //起始时间
            String startDate = request.getParameter("startDate");
            //终止时间
            String endDate = request.getParameter("endDate");
            //用户管理楼栋id
            String dormBuildId = request.getParameter("dormBuildId");
            //搜索类型
            String searchType = request.getParameter("searchType");
            //搜索输入框输入内容
            String keyword = request.getParameter("keyword");
            //当前要查询的页面
            String pageIndex = request.getParameter("pageIndex");
            //默认查询第一页，需要两个参数，当前页码pageIndex，每页展示的条数
            PageModel pageModel = new PageModel();
            if (pageIndex != null && !pageIndex.equals("")){
                pageModel.setPageIndex(Integer.parseInt(pageIndex));
            }
            //根据这些参数搜索出记录
            List<Record> records = recordService.findRecords(currentUser,startDate,endDate,dormBuildId,searchType,keyword,pageModel);
            System.out.println(records);
            //获取查询总数
            Integer totalNum = recordService.getToTalNum(currentUser,startDate,endDate,dormBuildId,searchType,keyword,pageModel);
            request.setAttribute("totalNum",totalNum);
            request.setAttribute("pageIndex",pageModel.getPageIndex());
            request.setAttribute("startDate",startDate);
            request.setAttribute("endDate",endDate);
            request.setAttribute("dormBuildId",dormBuildId);
            request.setAttribute("searchType",searchType);
            request.setAttribute("keyword",keyword);
            request.setAttribute("records",records);
            request.setAttribute("mainRight","recordList.jsp");
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }else if(null != action && action.equals("preAdd")){
            //添加和修改在一起，通过id是否为空来判断是添加还是修改
            //如果id有数那就是修改，为空就是新增
            request.setAttribute("mainRight","recordAddOrUpdate.jsp");
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }/*else if (null != action && action.equals("ajaxStuCode")){
            //判断当前用户是否有添加该学号缺勤记录的权限
            String stuCode = request.getParameter("stuCode");
            System.out.println("输入的新增记录的学号："+stuCode);
            User user = userService.findStuCodeAndManager(stuCode,currentUser);
            System.out.println("是否有添加权限，失去焦点判断user："+user);
            if (user == null){
                //表示没有添加该学号学生缺勤记录的权限
                response.setCharacterEncoding("UTF-8");
                PrintWriter printWriter = response.getWriter();
                printWriter.print("你输入的学号的学生不在你管理的楼栋之内，无修改权限，请重新输入！");
                //刷新
                printWriter.flush();
                //关闭资源
                printWriter.close();
            }
        }*/else if (null != action && action.equals("save")){
            //保存缺勤记录
            String stuCode = request.getParameter("stuCode");
            String date = request.getParameter("date");
            String remark = request.getParameter("remark");
            //保存和更新前，判断用户是否有添加或修改该学号学生缺勤记录的权限
            User user = userService.findStuCodeAndManager(stuCode,currentUser);
            System.out.println("查询用户是否有添加权限"+user);
            //点击保存，根据传来的缺勤记录id判断是否为null，为null则保存新的记录，不为null则修改
            if (id !=null && !id.equals("")){
                //更新记录
                if (user != null){
                    //有修改权限，要执行修改
                    //先通过要修改缺勤记录的id，去找到这条缺勤记录
                    Record record = recordService.findRecordById(Integer.parseInt(id));
                    try {
                        //然后修改
                        record.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                        record.setRemark(remark);
                        record.setStudentId(user.getId());
                        //数据封装好后执行更新update
                        recordService.update(record);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //更新完成以后跳转到列表页面查询
                    response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
                }else {
                    //无修改权限，返回列表页面
                    response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
                }
            }else {
                //保存新记录
                if ( user == null ){
                    //表示没有添加该学号学生缺勤记录的权限
                    //跳转到添加页面
                    request.setAttribute("error","您没有权限添加记录");
                    request.setAttribute("mainRight","recordAddOrUpdate.jsp");
                    request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
                }else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date2 = null;
                    try {
                        date2 = dateFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //有添加的权限
                    Record record = new Record();
                    record.setStudentId(user.getId());
                    record.setDate(date2);
                    record.setDisabled(0);
                    record.setRemark(remark);
                    //保存到数据库
                    recordService.saveRecord(record);
                    //保存后跳转到缺勤记录列表页面
                    response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
                }
            }
        }else if (null != action && ("preUpdate").equals(action)){
            //跳转到修改页面
            //要修改哪一条记录，接收该条考勤的id
            Record record = recordService.findRecordById(Integer.parseInt(id));
            System.out.println("想要修改的缺勤记录："+record);
            //查看用户是否 有修改考勤记录的权限
            User user = userService.findStuCodeAndManager(record.getUser().getStuCode(),currentUser);
            System.out.println("有无权限修改该学生："+user);
            if (user == null ){
                //user查询为null，则没有权限修改考勤记录，跳转到考勤记录列表页面
                response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
            }else {
                //不为null则有修改权限就展示信息
                request.setAttribute("record",record);
                request.setAttribute("mainRight","recordAddOrUpdate.jsp");
                request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
            }
        }else if (null != action && ("deleteOrActive").equals(action)){
            //删除或激活
            String disabled = request.getParameter("disabled");
            //找到要删除或激活的缺勤记录
            Record record = recordService.findRecordById(Integer.parseInt(id));
            //然后更改
            record.setDisabled(Integer.parseInt(disabled));
            //查看用户是否有删除考勤记录的权限
            User user = userService.findStuCodeAndManager(record.getUser().getStuCode(),currentUser);
            if (user == null ){
                //user查询为null，则没有权限删除考勤记录，跳转到考勤记录列表页面
                response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
            }else {
                recordService.update(record);
                //删除或更新完跳转到缺勤记录列表页面
                response.sendRedirect(getServletContext().getContextPath() + "/record.action?action=list");
            }
        }
        /*else if (null != action && action.equals("save")){
            String stuCode = request.getParameter("stuCode");
            String date = request.getParameter("date");
            String remark = request.getParameter("remark");
            //保存和更新前，判断用户是否有添加或修改该学号学生缺勤记录的权限
            User user = userService.findStuCodeAndManager(stuCode,currentUser);
            System.out.println("查询添加缺勤权限user："+user);
            if (id != null && !id.equals("")){
                //表示是更新记录
                if ( user!=null ){
                    //有修改权限，执行修改
                    Record record = recordService.findRecordById(Integer.parseInt(id));
                    try {
                        record.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                        record.setRemark(remark);
                        record.setStudentId(user.getId());
                        //数据封装好后执行更新update
                        recordService.update(record);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
                }else {
                    //无修改权限
                    response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
                }
            }else {
                //否则是添加新记录
                if ( user!=null ){
                    //说明当前登录的用户有添加该学号学生的缺勤记录的权限
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = null;
                    try {
                        date1 = dateFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Record record = new Record();
                    record.setStudentId(user.getId());
                    record.setDate(date1);
                    record.setDisabled(0);
                    record.setRemark(remark);
                    //保存到数据库
                    recordService.saveRecord(record);
                    response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
                }else {
                    //没有添加的权限
                    request.setAttribute("error","您没有权限添加记录");
                    request.setAttribute("mainRight","recordAddOrUpdate.jsp");
                    request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
                }
            }
        }else if (null != action && action.equals("preUpdate")){
            //跳转到修改页面
            //要知道要修改哪些信息，把这些信息显示在修改页面
            Record record = recordService.findRecordById(Integer.parseInt(id));
            System.out.println("想要修改的缺勤记录："+record);
            //查看用户是否 有修改考勤记录的权限
            User user = userService.findStuCodeAndManager(record.getUser().getStuCode(),currentUser);
            System.out.println("有无权限修改该学生："+user);
            //user查询为null，则没有权限修改考勤记录，跳转到考勤记录列表页面
            if (user == null ){
                response.sendRedirect(getServletContext().getContextPath()+"/record.action?action=list");
            }else {
                //不为null就跳转到修改页面，展示修改信息
                request.setAttribute("record",record);
                request.setAttribute("mainRight","recordAddOrUpdate.jsp");
                request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
            }
        }*/
    }
}
