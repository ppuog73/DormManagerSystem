package com.dingli.utils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PageTag extends TagSupport {
    private Integer totalNum;//总数据量

    public PageTag() {
    }

    public PageTag(Integer totalNum, Integer pageSize, Integer pageIndex, String submitUrl) {
        this.totalNum = totalNum;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.submitUrl = submitUrl;
    }

    private Integer pageSize;//每一页显示的行数
    private Integer pageIndex;//表示当前页数
    private String submitUrl;//表示点击上一页下一页尾页是发送的请求
    @Override
    public int doEndTag() throws JspException {//遇见开始标签时执行
        return super.doEndTag();
    }

    @Override
    public int doStartTag() throws JspException {//遇见结束标签后执行

        JspWriter writer = pageContext.getOut();

        try {
            StringBuffer page = new StringBuffer();
            int totalPage = (totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize+1);
            if (totalNum > pageSize){
                //只有当查询处理的数据>每一页展示的数据时进行分页
                if (pageIndex == 1){
                    //当前页面是首页
                    page.append("<a href='#'>首页</a>&nbsp;");
                    page.append("<a href='#'>上一页</a>&nbsp;");
                    page.append("<a href='"+submitUrl+"&pageIndex="+(pageIndex+1)+"'>下一页</a>&nbsp;");
                    page.append("<a href='"+submitUrl+"&pageIndex="+totalPage+"'>尾页</a>&nbsp;");
                }else if (pageIndex == totalPage){
                    //当前页是尾页
                    page.append("<a href='"+submitUrl+"&pageIndex="+1+"'>首页</a>&nbsp;");
                    page.append("<a href='"+submitUrl+"&pageIndex="+(pageIndex-1)+"'>上一页</a>&nbsp;");
                    page.append("<a href='#'>下一页</a>&nbsp;");
                    page.append("<a href='#'>尾页</a>&nbsp;");
                }else {
                    //当前页属于中间页
                    page.append("<a href='"+submitUrl+"&pageIndex="+1+"'>首页</a>&nbsp;");
                    page.append("<a href='"+submitUrl+"&pageIndex="+(pageIndex-1)+"'>上一页</a>&nbsp;");
                    page.append("<a href='"+submitUrl+"&pageIndex="+(pageIndex+1)+"'>下一页</a>&nbsp;");
                    page.append("<a href='"+submitUrl+"&pageIndex="+totalPage+"'>尾页</a>&nbsp;");
                }
                page.append("<br> 当前是第"+pageIndex+"&nbsp;/共"+totalPage+"页 &nbsp;/共"+totalNum+"条数据");

            }
            writer.print(page.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return super.doStartTag();
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getSubmitUrl() {
        return submitUrl;
    }

    public void setSubmitUrl(String submitUrl) {
        this.submitUrl = submitUrl;
    }
}
