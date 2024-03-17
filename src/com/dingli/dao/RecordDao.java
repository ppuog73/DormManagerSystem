package com.dingli.dao;

import com.dingli.domain.Record;

import java.util.List;

/**
 * @Author HuWei
 * @Date 2022/2/11 16:28
 * @Version 1.0
 */
public interface RecordDao {
    /**
     * 根据条件查询出缺勤记录的总记录数
     * @param sql 拼接好的sql语句
     * @return totalNum 总记录数
     */
    Integer getToTalNum(String sql);

    /**
     * 根据条件查询出缺勤记录的记录详情
     * @param sql sql语句
     * @return list 记录列表
     */
    List<Record> findRecords(String sql);

    /**
     * 根据id查询缺勤记录
     * @param id
     * @return 一条缺勤记录
     */
    Record findRecordById(int id);

    /**
     * 添加一条新的缺勤记录
     * @param record 新的缺勤记录
     */
    void saveRecord(Record record);

    /**
     * 更新缺勤记录
     * @param record 缺勤记录
     */
    void update(Record record);
}
