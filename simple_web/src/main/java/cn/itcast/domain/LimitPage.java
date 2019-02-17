package cn.itcast.domain;


import java.io.Serializable;

/**
 * @Auther : 32725
 * @Date: 2019/2/7 16:43
 * @Description: 分页查询Bean对象
 */
public class LimitPage implements Serializable {
    private Integer pageSize;
    private Integer start;
    private User user;

    public LimitPage(Integer start,Integer pageSize ) {
        this.pageSize = pageSize;
        this.start = start;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LimitPage(User user, int start, int pageSize) {
        this.pageSize = pageSize;
        this.start = start;
        this.user=user;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
