package cn.itcast.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/7 13:08
 * @Description: 封装页面展示元素
 */
public class PageBean<T> implements Serializable {

    private Integer currentPage;  //当前的页码
    private Integer pageSize;     //每页展示的数据量
    private Integer totalPage;    //总页数
    private Integer totalCount;   //总记录数
    private List<T> list;         //每页展示的数据

    @Override
    public String toString() {
        return "PageBean{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", totalCount=" + totalCount +
                ", list=" + list +
                '}';
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
