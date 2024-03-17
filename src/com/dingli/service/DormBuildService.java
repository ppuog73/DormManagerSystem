package com.dingli.service;

import com.dingli.domain.DormBuild;

import java.util.List;

public interface DormBuildService {
//查找所有楼栋信息
    List<DormBuild> find();

    /**
     * 根据宿管id查找宿舍管理员的管理的楼栋信息
     * @param id
     * @return
     */
    List<DormBuild> findByUserId(Integer id);
//根据用户id删除中间表信息
    void deleteByUserId(Integer id);
//根据宿舍管理员id保存宿舍管理员管理楼栋的信息
    void saveManagerAndBuild(Integer id, String[] dormBuildId);

    List<DormBuild> findALL();

    /**
     * 根据宿舍名找宿舍楼
     * @param id
     * @return DormBuild
     */
    DormBuild findById(int id);

    /**
     * 新增宿舍楼
     * @param  name , remark
     */
    void saveBuild(String name , String remark);

}
