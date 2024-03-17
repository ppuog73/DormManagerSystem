package com.dingli.service.impl;

import com.dingli.dao.DormBuildDao;
import com.dingli.dao.UserDao;
import com.dingli.dao.impl.DormBuildDaoImpl;
import com.dingli.dao.impl.UserDaoImpl;
import com.dingli.domain.DormBuild;
import com.dingli.domain.Record;
import com.dingli.domain.User;
import com.dingli.service.UserService;
import com.dingli.utils.PageModel;

import java.util.List;

public class UserServiceImpl implements UserService {
    //根据学号和密码查询数据库
    private UserDao userDao = new UserDaoImpl();
    private DormBuildDao dormBuildDao = new DormBuildDaoImpl();

    @Override
    public User findUserByStuCodeAndPass(String stuCode, String password) {
        return userDao.findUserByStuCodeAndPass(stuCode,password);
    }

    @Override
    public List<User> findManager(String searchType, String keyword) {
        //获取宿舍管理员基本信息
        List<User> users = userDao.findManager(searchType,keyword);

        //获取宿舍管理员的楼栋信息
        for (User user : users){
            List<DormBuild> dormBuilds =dormBuildDao.findByUserId(user.getId());
            user.setDormBuilds(dormBuilds);
        }
        return users;
    }

    @Override
    public void saveManager(User user, String[] dormBuildId) {
        //获取最大学号
        String managerStuCodeMax = userDao.findManagerStuCodeMax();
        user.setStuCode(managerStuCodeMax);

        //保存用户管理员基本信息
        Integer userId = userDao.saveManager(user);

        //保存宿舍管理员和宿舍楼的中间表
        dormBuildDao.saveManagerAndBuild(userId,dormBuildId);
    }

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public void updateManager(User user) {//更新用户基本信息
        userDao.updateManager(user);
    }

    @Override
    public void updateDisabledById(int id, int disabled) {
        userDao.updateDisabledById(id,disabled);
    }

    @Override
    public User findUserByStuCode(String stuCode) {
        return userDao.findUserByStuCode(stuCode);
    }

    @Override
    public void saveStudent(User user) {
        userDao.saveStudent(user);
    }

    /**
     * 通过参数查询学生信息
     * @param dormBuildId
     * @param searchType
     * @param keyword
     * @param user
     * @param pageModel
     * @return
     */
    @Override
    public List<User> findStudent(String dormBuildId, String searchType, String keyword, User user, PageModel pageModel) {
        StringBuffer sql = new StringBuffer();
        //不管当前用户角色是哪个，查询的都是学生，所有角色id=2
        sql.append("select user.*,build.name buildName,build.* from tb_user user "+
                " left join tb_dormbuild build on build.id = user.dormBuildId " + "" +
                " where user.role_id = 2");//sql语句要注意空格
        if (keyword != null && !keyword.equals("") && "name".equals(searchType)) {
            //根据名字查询
            sql.append(" and  user.name like '%"+keyword.trim()+"%'");

        }else if (keyword != null && !keyword.equals("") && "stuCode".equals(searchType)){
            //根据学号查询
            sql.append(" and  user.stu_code = '"+keyword.trim()+"'");

        }else if (keyword != null && !keyword.equals("") && "dormCode".equals(searchType)){
            //根据宿舍编号查询
            sql.append(" and  user.dorm_code = '"+keyword.trim()+"'");
        }else if (keyword != null && !keyword.equals("") && "sex".equals(searchType)){
            //根据性别查询
            sql.append(" and  user.sex = '"+keyword.trim()+"'");
        }else if (keyword != null && !keyword.equals("") && "tel".equals(searchType)){
            //根据电话号码查询
            sql.append(" and  user.tel = '"+keyword.trim()+"'");
        }
        if (dormBuildId != null && !dormBuildId.equals("")){
            //根据宿舍楼id查询
            sql.append(" and  user.dormBuildId = '"+dormBuildId+"'");
        }
        /*sql.append(" and  user.dormBuildId in()");*/
        //判断当前用户是否为宿舍管理员，如果是则虚查询其管理的所有宿舍楼id
        if (user.getRoleId().equals(1)) {
            //获取当前宿舍管理员管理的所有宿舍楼
            List<DormBuild> builds = dormBuildDao.findByUserId(user.getId());
            sql.append(" and  user.dormBuildId in(");
            for (int i = 0; i < builds.size(); i++) {
                sql.append(builds.get(i).getId()+",");
            }
            //删除最后一个
            sql.deleteCharAt(sql.length()-1);
            sql.append(")");

        }

        sql.append(" limit "+pageModel.getStarNum()+" , "+pageModel.getPageSize());
        List<User> students = userDao.findStudent(sql.toString());
        return students;
    }

    //获取查询处理的总数量
    @Override
    public Integer findTotalNum(String dormBuildId, String searchType, String keyword, User user) {
        StringBuffer sql = new StringBuffer();
        //不管当前用户角色是哪个，查询的都是学生，所有角色id=2
        sql.append("select count(*) from tb_user user "+
                " left join tb_dormbuild build on build.id = user.dormBuildId " + "" +
                " where user.role_id = 2");//sql语句要注意空格
        if (keyword != null && !keyword.equals("") && "name".equals(searchType)) {
            //根据名字查询
            sql.append(" and  user.name like '%"+keyword.trim()+"%'");

        }else if (keyword != null && !keyword.equals("") && "stu_Code".equals(searchType)){
            //根据学号查询
            sql.append(" and  user.stu_code = '"+keyword.trim()+"'");

        }else if (keyword != null && !keyword.equals("") && "dormCode".equals(searchType)){
            //根据宿舍编号查询
            sql.append(" and  user.dorm_code = '"+keyword.trim()+"'");
        }else if (keyword != null && !keyword.equals("") && "sex".equals(searchType)){
            //根据性别查询
            sql.append(" and  user.sex = '"+keyword.trim()+"'");
        }else if (keyword != null && !keyword.equals("") && "tel".equals(searchType)){
            //根据电话号码查询
            sql.append(" and  user.tel = '"+keyword.trim()+"'");
        }
        if (dormBuildId != null && !dormBuildId.equals("")){
            //根据宿舍楼id查询
            sql.append(" and  user.dormBuildId = '"+dormBuildId+"'");
        }
        /*sql.append(" and  user.dormBuildId in()");*/
        //判断当前用户是否为宿舍管理员，如果是则虚查询其管理的所有宿舍楼id
        if (user.getRoleId().equals(1)) {
            //获取当前宿舍管理员管理的所有宿舍楼
            List<DormBuild> builds = dormBuildDao.findByUserId(user.getId());
            sql.append(" and  user.dormBuildId in(");
            for (int i = 0; i < builds.size(); i++) {
                sql.append(builds.get(i).getId()+",");
            }
            //删除最后一个
            sql.deleteCharAt(sql.length()-1);
            sql.append(")");

        }
        return userDao.findTotalNum(sql.toString());
    }

    @Override
    public void updateStudent(User studentUpdate) {
        userDao.updateStudent(studentUpdate);
    }

    //判断用户是否为管理员
    @Override
    public User findByUserIdAndManager(Integer id, User user) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from tb_user user where user.id = "+id);

        if (user.getRoleId() != null &&user.getRoleId().equals(1)){
            //表示当前登录的用户角色是宿舍管理员，则要求要修改的学生必须居住在其所管理的宿舍中
            List<DormBuild> builds = dormBuildDao.findByUserId(user.getId());
            sql.append(" and  user.dormBuildId in(");
            for (int i = 0; i < builds.size(); i++) {
                sql.append(builds.get(i).getId()+",");
            }
            //删除最后一个
            sql.deleteCharAt(sql.length()-1);
            sql.append(")");
        }
        return userDao.findByUserIdAndManager(sql.toString());
    }

    /**
     * 判断新输入的学号的学生是否在管理员管辖的楼栋内
     *
     * @param stuCode     新输入的学号
     * @param user 当前登录的用户
     * @return user 在当前登录的管理员管辖的楼栋下的学生
     */
    @Override
    public User findStuCodeAndManager(String stuCode, User user) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from tb_user user where user.stu_code ="+stuCode);

        if (user.getRoleId() != null &&user.getRoleId().equals(1)){
            //表示当前登录的用户角色是宿舍管理员，则要求要修改的学生必须居住在其所管理的宿舍中
            List<DormBuild> builds = dormBuildDao.findByUserId(user.getId());
            sql.append(" and  user.dormBuildId in(");
            for (int i = 0; i < builds.size(); i++) {
                sql.append(builds.get(i).getId()+",");
            }
            //删除最后一个
            sql.deleteCharAt(sql.length()-1);
            sql.append(")");
        }
        return userDao.findStuCodeAndManager(sql.toString());
    }

    /**
     * 修改用户密码
     *
     * @param currentUser 当前登录用户
     */
    @Override
    public void updatePassword(User currentUser) {
        userDao.updatePassword(currentUser);
    }


}
