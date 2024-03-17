package com.dingli.service;

import com.dingli.domain.Record;
import com.dingli.domain.User;
import com.dingli.utils.PageModel;

import java.util.List;

/**
 * @Author HuWei
 * @Date 2022/2/11 16:14
 * @Version 1.0
 */
public interface RecordService {
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
    Integer getToTalNum(User user, String startDate, String endDate, String dormBuildId, String searchType, String keyword, PageModel pageModel);

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
    List<Record> findRecords(User user, String startDate, String endDate, String dormBuildId, String searchType, String keyword, PageModel pageModel);

    /**
     * 根据id查询出缺勤记录
     * @param id
     * @return record
     */
    Record findRecordById(int id);

    /**
     * 添加一条新缺勤记录
     * @param record 新的缺勤记录
     */
    void saveRecord(Record record);

    /**
     * 更新缺勤记录
     * @param record 缺勤记录
     */
    void update(Record record);
}
