package com.dingli.controller;

import com.dingli.domain.DormBuild;
import com.dingli.service.DormBuildService;
import com.dingli.service.impl.DormBuildServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dormBuild.action")
public class DormBuildServlet extends HttpServlet {
    private static final long serialVersionUID =1L;
    public DormBuildServlet(){
        super();
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        DormBuildService dormBuildService = new DormBuildServiceImpl();

        if (action != null && action.equals("list")){
            List<DormBuild> builds = new ArrayList<>();
            if (id == null || id.equals("")){
                //点击查询，查询所有宿舍楼信息，跳转到宿舍楼列表页面
                builds = dormBuildService.find();
            }else if (id != null && !id.equals("")){
                //点击搜索搜索宿舍楼，根据宿舍楼id查询宿舍楼信息
                DormBuild build = dormBuildService.findById(Integer.parseInt(id));
                builds.add(build);

            }

            //查询所有宿舍楼，在select中遍历
            List<DormBuild> buildSelects = dormBuildService.find();
            request.setAttribute("buildSelects",buildSelects);
            request.setAttribute("id",id);
            request.setAttribute("builds",builds);
            request.setAttribute("mainRight","/jsp/dormBuildList.jsp");
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }else if (action != null && action.equals("preAdd")){

            request.setAttribute("mainRight","/jsp/dormBuildAddOrUpdate.jsp");
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);

        }else if (action != null && action.equals("save")){
            //保存宿舍楼信息，名字和备注
            String name = request.getParameter("name");
            String remark = request.getParameter("remark");
            //要保存先判断该宿舍楼是否已经存在
            DormBuild dormBuild = new DormBuild();
            if (name != null && name.equals("")){
                //宿舍楼存在
                request.setAttribute("error","该宿舍楼已存在");
            }else{//宿舍楼不存在，就保存宿舍楼
                dormBuildService.saveBuild(name,remark);
                //保存成功，跳转到宿舍楼列表页面重新查询所有宿舍楼
                response.sendRedirect(getServletContext().getContextPath()+"/dormBuild.action?action=list");
            }
        }/*else if ( action != null && action.equals("deletedOrActive" )){
            //宿舍楼激活和删除
            //对其disabled进行修改，1是删除状态等待激活 0是激活状态等待删除
            //页面请求修改状态
            String disabled = request.getParameter("disabled");
            if( Integer.parseInt(disabled) == 0 ){

                request.setAttribute("disabled",disabled);
            }else if (Integer.parseInt(disabled) == 1){
                request.setAttribute("disabled",disabled);
            }
            request.setAttribute("mainRight","/jsp/dormManagerList.jsp");
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }*/

    }
}
