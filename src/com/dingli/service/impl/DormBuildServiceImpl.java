package com.dingli.service.impl;

import com.dingli.dao.DormBuildDao;
import com.dingli.dao.impl.DormBuildDaoImpl;
import com.dingli.domain.DormBuild;
import com.dingli.service.DormBuildService;

import java.util.List;

public class DormBuildServiceImpl implements DormBuildService {
    private DormBuildDao dormBuildDao = new DormBuildDaoImpl();
    /*
    * 查找所有的楼栋信息
    *return所有宿舍楼信息
    * */
    @Override
    public List<DormBuild> find() {
        return dormBuildDao.find();
    }
    /**
     * 根据宿管id查找宿舍管理员的管理的楼栋信息
     * @param id
     * @return
     */
    @Override
    public List<DormBuild> findByUserId(Integer id) {//根据宿舍管理员id查找宿舍管理员管理的楼栋信息
        return dormBuildDao.findByUserId(id);
    }

    @Override
    public void deleteByUserId(Integer id) {//根据宿舍管理员id删除宿舍管理员管理的楼栋信息
        dormBuildDao.deleteByUserId(id);
    }

    @Override
    public void saveManagerAndBuild(Integer id, String[] dormBuildId) {//根据宿舍管理员id保存宿舍管理员管理的楼栋信息
        dormBuildDao.saveManagerAndBuild(id,dormBuildId);
    }

    @Override
    public List<DormBuild> findALL() {
        return dormBuildDao.findALL();
    }
    /**
     * 根据宿舍名找宿舍楼
     * @param id
     * @return DormBuild
     */
    @Override
    public DormBuild findById(int id) {
        return dormBuildDao.findById(id);
    }

    /**
     * 新增宿舍楼
     * @param  name , remark
     */
    @Override
    public void saveBuild(String name , String remark) {
        dormBuildDao.saveBuild(name,remark);
    }





}
