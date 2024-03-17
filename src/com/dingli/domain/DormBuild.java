package com.dingli.domain;

public class DormBuild {
    private Integer id;//宿舍楼id
    private String name;//宿舍楼名字
    private String remark;//宿舍楼的备注
    private Integer disabled;//宿舍楼是否被激活（代表宿舍楼是否可以使用）

    public DormBuild() {
        super();
    }

    public DormBuild(Integer id, String name, String remark, Integer disabled) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.disabled = disabled;
    }

    /**
     * 获取
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取
     * @return disabled
     */
    public Integer getDisabled() {
        return disabled;
    }

    /**
     * 设置
     * @param disabled
     */
    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }
    @Override
    public String toString() {
        return "DormBuild{id = " + id + ", name = " + name + ", remark = " + remark + ", disabled = " + disabled + "}";
    }
}
