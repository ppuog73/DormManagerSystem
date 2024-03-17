package com.dingli.service.impl;

import com.dingli.dao.DormBuildDao;
import com.dingli.dao.RecordDao;
import com.dingli.dao.impl.DormBuildDaoImpl;
import com.dingli.dao.impl.RecordDaoImpl;
import com.dingli.domain.DormBuild;
import com.dingli.domain.Record;
import com.dingli.domain.User;
import com.dingli.service.RecordService;
import com.dingli.utils.PageModel;

import java.util.List;

/**
 * @Author HuWei
 * @Date 2022/2/11 16:14
 * @Version 1.0
 */
public class RecordServiceImpl implements RecordService {
    private DormBuildDao dormBuildDao = new DormBuildDaoImpl();
    private RecordDao recordDao = new RecordDaoImpl();
    /**
     * 根据条件查询出缺勤记录的总记录数
     * @param user 当前登录用户
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param dormBuildId 宿舍楼id
     * @param searchType 下拉框查询
     * @param keyword 用户输入查询
     * @param pageModel 分页插件
     * @return totalNum
     */
    @Override
    public Integer getToTalNum(User user, String startDate, String endDate, String dormBuildId, String searchType, String keyword, PageModel pageModel) {
        //根据条件拼接sql语句
        StringBuilder sql = new StringBuilder("select count(*) from tb_record record left join tb_user user on user.id = record.student_id where 1 = 1 ");
        if (keyword!=null && "name".equals(searchType) && !keyword.equals("")){
            //根据名字搜索
            sql.append(" and user.name like '%" +keyword+ "%'");
        }else if (keyword!=null && "stuCode".equals(searchType) && !keyword.equals("")){
            //根据学号搜索
            sql.append(" and user.stu_code = '"+keyword.trim()+"'");
        }else if (keyword!=null && "dormCode".equals(searchType) && !keyword.equals("")){
            //根据宿舍编号搜索
            sql.append(" and user.dorm_Code = '"+keyword.trim()+"'");
        }else if (keyword!=null && "sex".equals(searchType) && !keyword.equals("")){
            //根据性别搜索
            sql.append(" and user.sex = '"+keyword.trim()+"'");
        }
        if (dormBuildId != null && !"".equals(dormBuildId)){
            //根据宿舍楼搜索
            sql.append(" and user.dormBuildId = "+dormBuildId);
        }
        if (startDate != null && !"".equals(startDate)){
            //查询出来的考勤记录时间要大于等于查询开始的时间
            sql.append(" and record.date >= '"+startDate + "'");
        }
        if (endDate != null && !"".equals(endDate)){
            //查询出来的考勤记录时间要小于等于查询结束的时间
            sql.append(" and record.date <= '"+endDate + "'");
        }
        //还要考虑当前用户的角色：超级管理员  普通管理员  普通学生
        if ( user.getRoleId()!=null && user.getRoleId().equals(1) ){
            //表示当前登录用户是管理员，那么查询出来的考勤记录所属于的学生必须在他的管理范围内
            //所以要查询出当前宿舍管理员管理的宿舍楼
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
        if (user.getRoleId().equals(2)){
            //当前登录用户是学生
            sql.append(" and user.id = "+user.getId());
        }
        return recordDao.getToTalNum(sql.toString());
    }

    /**
     * 根据条件查询出缺勤记录的详情
     * @param user 当前的登录的用户
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param dormBuildId 宿舍楼id
     * @param searchType 下拉框查询
     * @param keyword 用户输入查询
     * @param pageModel 分页插件
     * @return 缺勤记录详情
     */
    @Override
    public List<Record> findRecords(User user, String startDate, String endDate, String dormBuildId, String searchType, String keyword, PageModel pageModel) {
        //根据条件拼接sql语句
        StringBuilder sql = new StringBuilder("select record.*,user.*,record.id recordId,record.disabled recordDisabled,build.name buildName from tb_record record left join tb_user user on user.id = record.student_id left join tb_dormbuild build on build.id = user.dormBuildId where 1 = 1 ");
        if (keyword!=null && "name".equals(searchType) && !keyword.equals("")){
            //根据名字搜索
            sql.append(" and user.name like '%" +keyword+ "%'");
        }else if (keyword!=null && "stuCode".equals(searchType) && !keyword.equals("")){
            //根据学号搜索
            sql.append(" and user.stu_code = '"+keyword.trim()+"'");
        }else if (keyword!=null && "dormCode".equals(searchType) && !keyword.equals("")){
            //根据宿舍编号搜索
            sql.append(" and user.dorm_Code = '"+keyword.trim()+"'");
        }else if (keyword!=null && "sex".equals(searchType) && !keyword.equals("")){
            //根据性别搜索
            sql.append(" and user.sex = '"+keyword.trim()+"'");
        }
        if (dormBuildId != null && !"".equals(dormBuildId)){
            //根据宿舍楼搜索
            sql.append(" and user.dormBuildId = "+dormBuildId);
        }
        if (startDate != null && !"".equals(startDate)){
            //查询出来的考勤记录时间要大于等于查询开始的时间
            sql.append(" and record.date >= '"+ startDate+ "'" );
        }
        if (endDate != null && !"".equals(endDate)){
            //查询出来的考勤记录时间要小于等于查询结束的时间
            sql.append(" and record.date <= '"+endDate + "'");
        }
        //还要考虑当前用户的角色：超级管理员  普通管理员  普通学生
        if ( user.getRoleId()!=null && user.getRoleId().equals(1) ){
            //表示当前登录用户是管理员，那么查询出来的考勤记录所属于的学生必须在他的管理范围内
            //所以要查询出当前宿舍管理员管理的宿舍楼
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
        if (user.getRoleId().equals(2)){
            //当前登录用户是学生
            sql.append(" and user.id = "+user.getId());
        }
        //日期条件加上
        sql.append(" order by record.date desc ");

        //分页查询条件加上
        sql.append(" limit "+pageModel.getStarNum()+" , "+pageModel.getPageSize());
        System.out.println("查询所有缺勤记录语句："+sql);
        return recordDao.findRecords(sql.toString());
    }

    /**
     * 根据id查询出缺勤记录
     *
     * @param id
     * @return record
     */
    @Override
    public Record findRecordById(int id) {
        return recordDao.findRecordById(id);
    }

    /**
     * 添加一条新缺勤记录
     *
     * @param record 新的缺勤记录
     */
    @Override
    public void saveRecord(Record record) {
        recordDao.saveRecord(record);
    }

    /**
     * 更新缺勤记录
     *
     * @param record 缺勤记录
     */
    @Override
    public void update(Record record) {
        recordDao.update(record);
    }
}
