package com.dingli.dao;

import com.dingli.domain.DormBuild;

import java.util.List;

public interface DormBuildDao {
    /**
     * 根据宿管id查找宿舍管理员的管理的楼栋信息
     * @param id
     * @return
     */
    List<DormBuild> findByUserId(Integer id);

    //查询所有的宿舍楼
    List<DormBuild> find();
    //保存宿舍管理员和宿舍楼的中间表
    void saveManagerAndBuild(Integer id, String[] dormBuildId);
    //根据用户id删除中间表
    void deleteByUserId(Integer id);

    /**
     * 根据宿舍名找宿舍楼
     * @param id
     * @return DormBuild
     */
    DormBuild findById(int id);

    List<DormBuild> findALL();

    /**
     * 新增宿舍楼
     * @param name , remark
     */
    void saveBuild(String name , String remark);

    /**
     * 根据宿舍楼名字查询宿舍楼
     * @param id
     * @return
     */
    //DormBuild findDormBuildById(int id);

}
