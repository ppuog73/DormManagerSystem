package com.dingli.domain;

/**
 * @Author HuWei
 * @Date 2022/2/11 14:49
 * @Version 1.0
 */

import java.util.Date;

/**
 * 缺勤记录
 */
public class Record {
    private int id;
    private Integer studentId;
    private Date date;
    private String remark;
    private Integer disabled;

    private String beginDate;
    private String endDate;
    private User user;



    public Record() {
    }

    public Record(int id, Integer studentId, Date date, String remark, Integer disabled, String beginDate, String endDate, User user) {
        this.id = id;
        this.studentId = studentId;
        this.date = date;
        this.remark = remark;
        this.disabled = disabled;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", date=" + date +
                ", remark='" + remark + '\'' +
                ", disabled=" + disabled +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", user=" + user +
                '}';
    }
}
