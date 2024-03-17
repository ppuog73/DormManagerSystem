package com.dingli.dao.impl;

import com.dingli.dao.RecordDao;
import com.dingli.domain.DormBuild;
import com.dingli.domain.Record;
import com.dingli.domain.User;
import com.dingli.utils.DBUtils;
import org.springframework.jdbc.core.JdbcTemplate;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author HuWei
 * @Date 2022/2/11 16:28
 * @Version 1.0
 */
public class RecordDaoImpl implements RecordDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(DBUtils.getDataSource());
    /**
     *
     * @param sql 拼接好的sql语句
     * @return
     */
    @Override
    public Integer getToTalNum(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            //执行批处理
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            Integer count = resultSet.getInt(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(ps,conn);
        }
        return null;
    }

    @Override
    public List<Record> findRecords(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            //执行批处理
            ResultSet resultSet = ps.executeQuery();
            List<Record> records = new ArrayList<>();
            while (resultSet.next()){
                Record record = new Record();
                record.setId(resultSet.getInt("recordId"));
                record.setDate(resultSet.getDate("date"));
                record.setStudentId(resultSet.getInt("student_id"));
                record.setRemark(resultSet.getString("remark"));
                record.setDisabled(resultSet.getInt("recordDisabled"));

                User user = new User();
                user.setId(resultSet.getInt("student_id"));
                user.setName(resultSet.getString("name"));
                user.setPassWord(resultSet.getString("passWord"));
                user.setStuCode(resultSet.getString("stu_code"));
                user.setDormCode(resultSet.getString("dorm_Code"));
                user.setSex(resultSet.getString("sex"));
                user.setTel(resultSet.getString("tel"));
                user.setDormBuildId(resultSet.getInt("dormBuildId"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setCreateUserId(resultSet.getInt("create_user_id"));

                DormBuild dormBuild = new DormBuild();
                dormBuild.setName(resultSet.getString("buildName"));

                user.setDormBuild(dormBuild);
                record.setUser(user);
                records.add(record);

            }
            System.out.println("查询出来的所有缺勤记录"+records);
            return records;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(ps,conn);
        }
        return null;
    }

    /**
     * 根据id查询缺勤记录
     *
     * @param id
     * @return 一条缺勤记录
     */
    @Override
    public Record findRecordById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtils.getConnection();
            StringBuilder sql = new StringBuilder("select record.*,user.*,record.id recordId,record.disabled recordDisabled from tb_record record left join tb_user user on user.id = record.student_id where record.id = ? ");
            preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Record record = new Record();
                record.setId(resultSet.getInt("recordId"));
                record.setStudentId(resultSet.getInt("student_id"));
                record.setDate(resultSet.getDate("date"));
                record.setRemark(resultSet.getString("remark"));
                record.setDisabled(resultSet.getInt("recordDisabled"));

                User user = new User();
                user.setId(resultSet.getInt("student_id"));
                user.setName(resultSet.getString("name"));
                user.setPassWord(resultSet.getString("passWord"));
                user.setStuCode(resultSet.getString("stu_code"));
                user.setDormCode(resultSet.getString("dorm_Code"));
                user.setSex(resultSet.getString("sex"));
                user.setTel(resultSet.getString("tel"));
                user.setDormBuildId(resultSet.getInt("dormBuildId"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setCreateUserId(resultSet.getInt("create_user_id"));

                record.setUser(user);
                return record;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtils.close(resultSet,preparedStatement,connection);
        }
        return null;
    }

    /**
     * 添加一条新的缺勤记录
     *
     * @param record 新的缺勤记录
     */
    @Override
    public void saveRecord(Record record) {
        String sql = "insert into tb_record(student_id,DATE,remark,disabled) values(?,?,?,?)";
        System.out.println("添加的缺勤记录："+record);
        int row = jdbcTemplate.update(sql, record.getStudentId(), record.getDate(), record.getRemark(), record.getDisabled());
        System.out.println("修改成功："+row);
/*        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection=DBUtils.getConnection();
            String sql = "insert into tb_record(student_id,DATE,remark,disabled) values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,record.getStudentId());
            preparedStatement.setDate(2,new Date(record.getDate().getTime()));
            preparedStatement.setString(3,record.getRemark());
            preparedStatement.setInt(4,record.getDisabled());
            //执行sql
            preparedStatement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(preparedStatement,connection);
        }*/
    }

    /**
     * 更新缺勤记录
     *
     * @param record 缺勤记录
     */
    @Override
    public void update(Record record) {
        System.out.println("更改的缺勤记录："+record);
        String sql = "update tb_record set student_id = ? , DATE = ? ,remark = ? , disabled = ? where id = ?";
        int row = jdbcTemplate.update(sql, record.getStudentId(), record.getDate(), record.getRemark(), record.getDisabled(), record.getId());
        System.out.println("修改成功："+row);
        /*Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection=DBUtils.getConnection();
            String sql = "update tb_record set student_id = ? , DATE = ? ,remark = ? , disabled = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,record.getStudentId());
            System.out.println(record.getStudentId());
            preparedStatement.setDate(2, new Date(record.getDate().getTime()));
            //原生的jdbc有bug
            //You have an error in your SQL syntax; check the manual that corresponds to
            //your MySQL server version for the right syntax to use
            //near '? , date = ? ,remark = ? , disabled = ? where id = ?' at line 1
            System.out.println(new Date(record.getDate().getTime()));
            preparedStatement.setString(3,record.getRemark());
            System.out.println(record.getRemark());
            preparedStatement.setInt(4,record.getDisabled());
            System.out.println(record.getDisabled());
            preparedStatement.setInt(5,record.getId());
            System.out.println(record.getId());
            //执行sql
            preparedStatement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(preparedStatement,connection);
        }*/
    }
}
