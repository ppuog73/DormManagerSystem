package com.dingli.domain;

import java.util.List;

public class User {
    private Integer id;//用户id
    private String name;//用户名
    private String passWord;//用户密码
    private String stuCode;//用户学号
    private String sex;//用户性别
    private String tel;//用户电话
    private Integer dormBuildId;//学生所住宿舍楼id
    private String dormCode;//宿舍号码
    private Integer roleId;//角色id  权限 0超管 1宿管 2学生
    private Integer disabled;//用户是否激活 0 已激活 1还未激活
    private Integer createUserId;//创建用户的id，创建用户的人对应的用户id（管理员的id）

    private DormBuild dormBuild;//学生住的宿舍楼
    private List<DormBuild> dormBuilds;//宿管住的宿舍楼

    public User() {
        super();
    }

    public User(String name, String passWord, String sex, String tel, Integer dormBuildId, Integer roleId) {
        super();
        this.name = name;
        this.passWord = passWord;
        this.sex = sex;
        this.tel = tel;
        this.dormBuildId = dormBuildId;
        this.roleId = roleId;
    }

    public User(Integer id, String name, String passWord, String sex, String tel, Integer dormBuildId, Integer roleId) {
        super();
        this.id = id;
        this.name = name;
        this.passWord = passWord;
        this.sex = sex;
        this.tel = tel;
        this.dormBuildId = dormBuildId;
        this.roleId = roleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getStuCode() {
        return stuCode;
    }

    public void setStuCode(String stuCode) {
        this.stuCode = stuCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getDormBuildId() {
        return dormBuildId;
    }

    public void setDormBuildId(Integer dormBuildId) {
        this.dormBuildId = dormBuildId;
    }

    public String getDormCode() {
        return dormCode;
    }

    public void setDormCode(String dormCode) {
        this.dormCode = dormCode;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public DormBuild getDormBuild() {
        return dormBuild;
    }

    public void setDormBuild(DormBuild dormBuild) {
        this.dormBuild = dormBuild;
    }

    public List<DormBuild> getDormBuilds() {
        return dormBuilds;
    }

    public void setDormBuilds(List<DormBuild> dormBuilds) {
        this.dormBuilds = dormBuilds;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passWord='" + passWord + '\'' +
                ", stuCode='" + stuCode + '\'' +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                ", dormBuildId=" + dormBuildId +
                ", dormCode='" + dormCode + '\'' +
                ", roleId=" + roleId +
                ", disabled=" + disabled +
                ", createUserId=" + createUserId +
                ", dormBuild=" + dormBuild +
                ", dormBuilds=" + dormBuilds +
                '}';
    }
}
