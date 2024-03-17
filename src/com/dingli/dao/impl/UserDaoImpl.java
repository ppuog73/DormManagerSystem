package com.dingli.dao.impl;

import com.dingli.dao.UserDao;
import com.dingli.domain.DormBuild;
import com.dingli.domain.User;
import com.dingli.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class  UserDaoImpl implements UserDao {
    /*根据学号和密码查询数据库，返回用户信息*/
    @Override
    public User findUserByStuCodeAndPass(String stuCode, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            //获取连接
            conn = DBUtils.getConnection();
            //获取数据库操作对象
            //加个disabled = 0 表示未删除的
            String sql = "select * from tb_user where stu_code = ? and password = ?and disabled = 0";
            ps = conn.prepareStatement(sql);//预编译SQL语句

            //给SQL语句中的占位符设值
            //接收传过来的账号号和密码
            ps.setString(1,stuCode);
            ps.setString(2,password);

            //执行SQL语句
            resultSet = ps.executeQuery();//查询结果集合

            //查询结果集的处理
            while (resultSet.next()){
                //返回的是User对象，把查询结果封装到user对象里去
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassWord(resultSet.getString("passWord"));
                user.setStuCode(resultSet.getString("stu_code"));
                user.setDormCode(resultSet.getString("dorm_Code"));
                user.setSex(resultSet.getString("sex"));
                user.setTel(resultSet.getString("tel"));
                user.setDormBuildId(resultSet.getInt("dormBuildId"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setCreateUserId(resultSet.getInt("create_user_id"));
                user.setDisabled(resultSet.getInt("disabled"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭数据资源
            DBUtils.close(resultSet,ps,conn);
        }

        return null;
    }








    @Override
    public List<User> findManager(String searchType, String keyword) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            //获取数据库连接
            conn = DBUtils.getConnection();

            String sqlTemp = "";
            if (null != keyword && !keyword.equals("")){
                //说明用户点击了搜索按钮进行查询
                if ("name".equals(searchType)){
                    sqlTemp = " and name like '%" + keyword +"%'";
                }else if ("sex".equals(searchType)){
                    sqlTemp = " and sex = '"+keyword+"'";
                }else if ("tel".equals(searchType)){
                    sqlTemp = " and tel = "+keyword;
                }
            }
            String sql = "select * from tb_user where role_id= 1"+sqlTemp;
            ps = conn.prepareStatement(sql);
            resultSet =ps.executeQuery();
            while (resultSet.next()){
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassWord(resultSet.getString("passWord"));
                user.setStuCode(resultSet.getString("stu_code"));
                user.setDormCode(resultSet.getString("dorm_Code"));
                user.setSex(resultSet.getString("sex"));
                user.setTel(resultSet.getString("tel"));
                user.setDormBuildId(resultSet.getInt("dormBuildId"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setCreateUserId(resultSet.getInt("create_user_id"));
                user.setDisabled(resultSet.getInt("disabled"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭数据资源
            DBUtils.close(resultSet,ps,conn);
        }
        return users;
    }

    @Override
    public String findManagerStuCodeMax() {//获取用户表中最大学号
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        String stu_code = "";

        try {
            conn = DBUtils.getConnection();
            String sql = "select MAX(stu_code) as maxNo from tb_user";
            ps = conn.prepareStatement(sql);
            resultSet=ps.executeQuery();
            while (resultSet.next()){
                String maxNo =resultSet.getString("maxNo");
                stu_code = "00"+(Integer.parseInt(maxNo)+1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(resultSet,ps,conn);
        }
        return stu_code;
    }
    //保存用户信息

    /**
     * 保存管理员用户基本信息
     * @param user 用户基本信息
     * @return 用户id
     */
    @Override
    public Integer saveManager(User user){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            conn = DBUtils.getConnection();
            String sql = "insert into tb_user(name,passWord,stu_code,sex,tel,role_id,create_user_id,disabled) value(?,?,?,?,?,?,?,?)";
            //Statement.RETURN_GENERATED_KEYS指定返回生成的是注解
            ps =conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,user.getName());
            ps.setString(2,user.getPassWord());
            ps.setString(3,user.getStuCode());
            ps.setString(4,user.getSex());
            ps.setString(5,user.getTel());
            ps.setInt(6,user.getRoleId());
            ps.setInt(7,user.getCreateUserId());
            ps.setInt(8,user.getDisabled());

            ps.executeUpdate();

            resultSet = ps.getGeneratedKeys();//获取执行sql后的主键值

            resultSet.next();
            return  resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(resultSet,ps,conn);
        }
        return null;
    }

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @Override
    public User findById(Integer id) {//根据用户id获取用户信息
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = DBUtils.getConnection();

            String sql = "select * from tb_user where id= ? ";

            ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            resultSet = ps.executeQuery();
            while (resultSet.next()){
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassWord(resultSet.getString("passWord"));
                user.setStuCode(resultSet.getString("stu_code"));
                user.setDormCode(resultSet.getString("dorm_Code"));
                user.setSex(resultSet.getString("sex"));
                user.setTel(resultSet.getString("tel"));
                user.setDormBuildId(resultSet.getInt("dormBuildId"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setCreateUserId(resultSet.getInt("create_user_id"));
                user.setDisabled(resultSet.getInt("disabled"));

                return user;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(resultSet,ps,conn);
        }
        return null;
    }

    @Override
    public void updateManager(User user) {//更新用户基本信息
        Connection conn = null;
        PreparedStatement ps = null;


        try {
            conn = DBUtils.getConnection();

            String sql = "update tb_user set name = ?, passWord = ?,sex = ?,tel = ? ,disabled = ? where id =?";

            ps=conn.prepareStatement(sql);
            ps.setString(1,user.getName());
            ps.setString(2,user.getPassWord());
            ps.setString(3,user.getSex());
            ps.setString(4,user.getTel());
            ps.setInt(5,user.getDisabled());
            ps.setInt(6,user.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(ps,conn);
        }
    }

    @Override
    public void updateDisabledById(int id, int disabled) {
        //激活或删除管理员用户
    }

    @Override
    public User findUserByStuCode(String stuCode) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            //获取连接
            conn = DBUtils.getConnection();
            //获取数据库操作对象
            String sql = "select * from tb_user where stu_code = ? ";
            ps = conn.prepareStatement(sql);//预编译SQL语句

            //给SQL语句中的占位符设值
            ps.setString(1,stuCode);


            //执行SQL语句
            resultSet = ps.executeQuery();//查询结果集合

            //查询结果集的处理
            while (resultSet.next()){
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassWord(resultSet.getString("passWord"));
                user.setStuCode(resultSet.getString("stu_code"));
                user.setDormCode(resultSet.getString("dorm_Code"));
                user.setSex(resultSet.getString("sex"));
                user.setTel(resultSet.getString("tel"));
                user.setDormBuildId(resultSet.getInt("dormBuildId"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setCreateUserId(resultSet.getInt("create_user_id"));
                user.setDisabled(resultSet.getInt("disabled"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭数据资源
            DBUtils.close(resultSet,ps,conn);
        }

        return null;
    }

    @Override
    public void saveStudent(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            conn = DBUtils.getConnection();
            String sql = "insert into tb_user(name,passWord,stu_code, dorm_Code,sex,tel,dormBuildId,role_id,create_user_id,disabled) value(?,?,?,?,?,?,?,?,?,1)";
            //Statement.RETURN_GENERATED_KEYS指定返回生成的是注解
            ps =conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,user.getName());//name
            ps.setString(2,user.getPassWord());//passWord
            ps.setString(3,user.getStuCode());//stu_code
            ps.setString(4,user.getDormCode());//dorm_Code
            ps.setString(5,user.getSex());//sex
            ps.setString(6,user.getTel());//tel
            ps.setInt(7,user.getDormBuildId());//dormBuildId
            ps.setInt(8,user.getRoleId());//role_id
            ps.setInt(9,user.getCreateUserId());//create_user_id
            //ps.setInt(10,user.getDisabled());//disabled


            ps.executeUpdate();

           /* 只是学生可以不用返回主键，需要返回主键是因为要保存中间表
           resultSet = ps.getGeneratedKeys();//获取执行sql后的主键值

            resultSet.next();

            Integer id = resultSet.getInt(1);*/
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(resultSet,ps,conn);

        }
    }

    @Override
    public List<User> findStudent(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            //获取连接
            conn = DBUtils.getConnection();
            //获取数据库操作对象
            String SQL = "select user.*,build.name buildName,build.* from tb_user user "+
                    " left join tb_dormbuild build on build.id = user.dormBuildId "+ "" +
                     " where user.role_id = 2";
            ps = conn.prepareStatement(sql);//预编译SQL语句
            //执行SQL语句
            resultSet = ps.executeQuery();//查询结果集合

            //查询结果集的处理
            List<User> users = new ArrayList<>();
            while (resultSet.next()){
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassWord(resultSet.getString("passWord"));
                user.setStuCode(resultSet.getString("stu_code"));
                user.setDormCode(resultSet.getString("dorm_Code"));
                user.setSex(resultSet.getString("sex"));
                user.setTel(resultSet.getString("tel"));
                user.setDormBuildId(resultSet.getInt("dormBuildId"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setCreateUserId(resultSet.getInt("create_user_id"));
                user.setDisabled(resultSet.getInt("disabled"));

                DormBuild build = new DormBuild();
                build.setId(resultSet.getInt("dormBuildId"));
                build.setName(resultSet.getString("buildName"));
                build.setRemark(resultSet.getString("remark"));
                user.setDormBuild(build);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭数据资源
            DBUtils.close(resultSet,ps,conn);
        }
        return null;
    }

    @Override
    public Integer findTotalNum(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            //获取连接
            conn = DBUtils.getConnection();
            //获取数据库操作对象
            String SQL = "select count(*) from tb_user user "+
                    " left join tb_dormbuild build on build.id = user.dormBuildId " + ""
                    +" where user.role_id = 2";
            ps = conn.prepareStatement(sql);//预编译SQL语句
            //执行SQL语句
            resultSet = ps.executeQuery();//查询结果集合
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭数据资源
            DBUtils.close(resultSet,ps,conn);
        }
        return null;
    }

    /**
     * 修改学生信息
     * @param user 学生信息
     */
    @Override
    public void updateStudent(User user) {
        Connection conn = null;
        PreparedStatement ps = null;


        try {
            conn = DBUtils.getConnection();

            String sql = "update tb_user set name = ?, passWord = ?,sex = ?,tel = ? ,disabled = ?,stu_code = ?,dorm_Code = ?,dormBuildId = ?  where id =?";

            ps=conn.prepareStatement(sql);
            ps.setString(1,user.getName());
            ps.setString(2,user.getPassWord());
            ps.setString(3,user.getSex());
            ps.setString(4,user.getTel());
            ps.setInt(5,user.getDisabled());
            ps.setString(6,user.getStuCode());
            ps.setString(7,user.getDormCode());
            ps.setInt(8,user.getDormBuildId());
            ps.setInt(9,user.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(ps,conn);
        }
    }

    @Override
    public User findByUserIdAndManager(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            //获取连接
            conn = DBUtils.getConnection();
            //获取数据库操作对象
            String SQL = "select * from tb_user user where user_id = ?";
            ps = conn.prepareStatement(sql);//预编译SQL语句

            //执行SQL语句
            resultSet = ps.executeQuery();//查询结果集合

            //查询结果集的处理
            while (resultSet.next()){
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassWord(resultSet.getString("passWord"));
                user.setStuCode(resultSet.getString("stu_code"));
                user.setDormCode(resultSet.getString("dorm_Code"));
                user.setSex(resultSet.getString("sex"));
                user.setTel(resultSet.getString("tel"));
                user.setDormBuildId(resultSet.getInt("dormBuildId"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setCreateUserId(resultSet.getInt("create_user_id"));
                user.setDisabled(resultSet.getInt("disabled"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭数据资源
            DBUtils.close(resultSet,ps,conn);
        }
        return null;
    }

    /**
     * 判断新输入的学号的学生是否在管理员管辖的楼栋内
     *
     * @param sql
     * @return user 在当前登录的管理员管辖的楼栋下的学生
     */
    @Override
    public User findStuCodeAndManager(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            //获取连接
            conn = DBUtils.getConnection();
            //获取数据库操作对象
            String SQL = "select * from tb_user user where user_id = ?";
            System.out.println("sql语句的stuCode = "+sql);
            ps = conn.prepareStatement(sql);//预编译SQL语句

            //执行SQL语句
            resultSet = ps.executeQuery();//查询结果集合

            //查询结果集的处理
            while (resultSet.next()){
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassWord(resultSet.getString("passWord"));
                user.setStuCode(resultSet.getString("stu_code"));
                user.setDormCode(resultSet.getString("dorm_Code"));
                user.setSex(resultSet.getString("sex"));
                user.setTel(resultSet.getString("tel"));
                user.setDormBuildId(resultSet.getInt("dormBuildId"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setCreateUserId(resultSet.getInt("create_user_id"));
                user.setDisabled(resultSet.getInt("disabled"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭数据资源
            DBUtils.close(resultSet,ps,conn);
        }
        return null;
    }

    /**
     * 修改用户密码
     *
     * @param currentUser 当前登录用户
     */
    @Override
    public void updatePassword(User currentUser) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "update tb_user set passWord = ? where id =?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,currentUser.getPassWord());
            ps.setInt(2,currentUser.getId());
            int row = ps.executeUpdate();
            if (row == 1){
                System.out.println("密码修改成功~");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(ps,conn);
        }
    }
}


























































