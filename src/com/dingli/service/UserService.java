package com.dingli.service;

import com.dingli.domain.Record;
import com.dingli.domain.User;
import com.dingli.utils.PageModel;

import java.util.List;

//根据学号和密码查询数据库
//stuCode学号
//password密码
public interface UserService {


    User findUserByStuCodeAndPass(String stuCode, String password);
    //根据搜索条件查询管理员信息 根据搜索类型searchtype和搜索关键字keyword查询  返回宿舍管理员信息
    List<User> findManager(String searchType, String keyword);

    /**
     * 保存用户信息（包括基本信息和宿舍楼信息）
     * @param user 管理员基本信息
     * @param dormBuildId 管理员管理的宿舍楼id
     */
    void saveManager(User user,String[] dormBuildId);

    //根据用户id获取用户信息
    User findById(Integer id);
//更新用户基本信息
    void updateManager(User user);

    //根据id修改disabled
    void updateDisabledById(int parseInt, int disabled);

//根据学号查询
    User findUserByStuCode(String stuCode);

    //保存添加学生信息
    void saveStudent(User user);

    /**
     * 通过参数查询学生信息
     * @param dormBuildId
     * @param searchType
     * @param keyword
     * @param user
     * @param pageModel
     * @return
     */
    List<User> findStudent(String dormBuildId, String searchType, String keyword, User user, PageModel pageModel);
    //获取查询处理的总数量
    Integer findTotalNum(String dormBuildId, String searchType, String keyword, User user);

    /**
     * 更改学生信息
     * @param studentUpdate
     */
    void updateStudent(User studentUpdate);

    User findByUserIdAndManager(Integer id, User user);

    /**
     * 判断新输入的学号的学生是否在管理员管辖的楼栋内
     * @param stuCode 新输入的学号
     * @param currentUser 当前登录的用户
     * @return user 在当前登录的管理员管辖的楼栋下的学生
     */
    User findStuCodeAndManager(String stuCode, User currentUser);

    /**
     * 修改用户密码
     * @param currentUser 当前登录用户
     */
    void updatePassword(User currentUser);
}
