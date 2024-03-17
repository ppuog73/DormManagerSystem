package com.dingli.utils;

public class PageModel {
    private int pageIndex =1;//当前页码
    private int pageSize =3;//每页显示记录数
    private int totalNum;//总记录数

    public PageModel() {
    }

    public PageModel(int pageIndex, int pageSize, int totalNum) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
    }

    /**
     * 获取
     * @return pageIndex
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置
     * @param pageIndex
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 获取
     * @return pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取
     * @return totalNum
     */
    public int getTotalNum() {
        return totalNum;
    }

    /**
     * 设置
     * @param totalNum
     */
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getStarNum(){//计算开始 行号
        return (this.getPageIndex()-1)*this.getPageSize();
    }
    @Override
    public String toString() {
        return "PageModel{pageIndex = " + pageIndex + ", pageSize = " + pageSize + ", totalNum = " + totalNum + "}";
    }
}
