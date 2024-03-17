package com.dingli.dao;

import com.dingli.domain.User;

import java.util.List;

public interface UserDao {
    //根据学号密码查询数据库
    User findUserByStuCodeAndPass(String stuCode, String password);
//根据搜索条件查询宿管员基本信息
    List<User> findManager(String searchType, String keyword);
    /**
     * //获取用户表中最大的学号
     * @return
     */
    String findManagerStuCodeMax();
//保存用户的基本信息
    Integer saveManager(User user);
    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    User findById(Integer id);

    void updateManager(User user);

    void updateDisabledById(int id, int disabled);


    User findUserByStuCode(String stuCode);
//保存添加的学生信息
    void saveStudent(User user);

    List<User> findStudent(String sql);

    Integer findTotalNum(String sql);

    /**
     * 修改学生信息
     * @param studentUpdate
     */
    void updateStudent(User studentUpdate);

    /**
     * 根据用户id判断，该用户是否在当前管理员的管辖之下
     * @param sql
     * @return user 用户
     */
    User findByUserIdAndManager(String sql);

    /**
     * 判断新输入的学号的学生是否在管理员管辖的楼栋内
     *
     * @return user 在当前登录的管理员管辖的楼栋下的学生
     */
    User findStuCodeAndManager(String sql);

    /**
     * 修改用户密码
     * @param currentUser 当前登录用户
     */
    void updatePassword(User currentUser);
}
