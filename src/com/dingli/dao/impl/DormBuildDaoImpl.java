package com.dingli.dao.impl;

import com.dingli.dao.DormBuildDao;
import com.dingli.domain.DormBuild;
import com.dingli.utils.DBUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DormBuildDaoImpl implements DormBuildDao {
    private static JdbcTemplate jdbcTemplate = new JdbcTemplate(DBUtils.getDataSource());
    /**
     * 根据宿管id查找宿舍管理员的管理的楼栋信息
     * @param id
     * @return
     */
    @Override
    public List<DormBuild> findByUserId(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        List<DormBuild> dormBuilds = new ArrayList<>();

        try {
            conn=DBUtils.getConnection();
            String sql = "select tb_dormbuild.* from tb_manage_dormbuild left join tb_dormbuild on " +
                    "tb_dormbuild.id = tb_manage_dormbuild.dormBuild_id where user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            resultSet = ps.executeQuery();

            while (resultSet.next()){
                DormBuild dormBuild =new DormBuild();

                dormBuild.setId(resultSet.getInt("id"));
                dormBuild.setName(resultSet.getString("name"));
                dormBuild.setDisabled(resultSet.getInt("disabled"));
                dormBuild.setRemark(resultSet.getString("remark"));

                dormBuilds.add(dormBuild);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(resultSet,ps,conn);
        }
        return dormBuilds;
    }

    /**
     * 查找所有楼栋信息
     * @return
     */
    @Override
    public List<DormBuild> find() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<DormBuild> dormBuilds = new ArrayList<>();

        try {
            conn=DBUtils.getConnection();

            String sql = "select * from tb_dormbuild";
            ps = conn.prepareStatement(sql);


            resultSet = ps.executeQuery();

            while (resultSet.next()){
                DormBuild dormBuild =new DormBuild();

                dormBuild.setId(resultSet.getInt("id"));
                dormBuild.setName(resultSet.getString("name"));
                dormBuild.setDisabled(resultSet.getInt("disabled"));
                dormBuild.setRemark(resultSet.getString("remark"));

                dormBuilds.add(dormBuild);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(resultSet,ps,conn);
        }
        return dormBuilds;
    }

    /**
     * //保存宿舍楼管理员和宿舍楼的中间表
     * @param id 管理员用户id
     * @param dormBuildId 宿舍楼id
     */
    @Override

    public void saveManagerAndBuild(Integer id, String[] dormBuildId) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBUtils.getConnection();

            String sql = "insert into tb_manage_dormbuild(user_id,dormBuild_id) value(?,?)";

            ps = conn.prepareStatement(sql);

            for (String s: dormBuildId){
                ps.setInt(1,id);/*有空指针异常*/
                ps.setInt(2,Integer.parseInt(s));
                ps.addBatch();//将sql语句添加到批处理
            }

            //执行批处理
            ps.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(ps,conn);
        }
    }

    @Override
    public void deleteByUserId(Integer id) {//根据用户id删除中间表
        String sql = "delete from tb_manage_dormbuild where user_id = ?";
        try{
            jdbcTemplate.update(sql,id);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * 根据宿舍楼名找宿舍楼
     * @param id
     * @return DormBuild
     */
    @Override
    public DormBuild findById(int id) {
        String sql = "select * from tb_dormbuild where id= ?";
        try{
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<DormBuild>(DormBuild.class),id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DormBuild> findALL() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        List<DormBuild> dormBuilds = new ArrayList<>();

        try {
            conn=DBUtils.getConnection();
            String sql = "select * from tb_dormbuild ";
            ps = conn.prepareStatement(sql);



            resultSet = ps.executeQuery();

            while (resultSet.next()){
                DormBuild dormBuild =new DormBuild();

                dormBuild.setId(resultSet.getInt("id"));
                dormBuild.setName(resultSet.getString("name"));
                dormBuild.setDisabled(resultSet.getInt("disabled"));
                dormBuild.setRemark(resultSet.getString("Remark"));

                dormBuilds.add(dormBuild);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(resultSet,ps,conn);
        }
        return dormBuilds;
    }

    /**
     * 新增宿舍楼
     * @param name , remark
     */
    @Override
    public void saveBuild(String name , String remark) {
        //插入一个新的宿舍楼
        String sql = "insert into tb_dormbuild (name,remark,disabled) values (?,?,0)";
        int rows = jdbcTemplate.update(sql, name, remark);
        System.out.println("插入新宿舍楼成功！受影响行"+ rows +"rows");
    }

    /**
     * 根据宿舍楼名找宿舍楼
     * @param id
     * @return
     */
/*    @Override
    public DormBuild findDormBuildById(int id) {
        String sql = "select * from tb_dormbuild where id= ?";
        System.out.println(sql);
        try{
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<DormBuild>(DormBuild.class),id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }*/
}

