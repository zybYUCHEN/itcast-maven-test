package cn.itcast.mapper;

import cn.itcast.domain.LimitPage;
import cn.itcast.domain.User;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/7 13:19
 * @Description: 处理用户操作的持节层接口
 */
public interface UserMapper {


    /**
    * @Author: 32725
    * @Param: []
    * @Return: int
    * @Description: 查询总记录数
    **/
    int findTotalCount();

    /**
    * @Author: 32725
    * @Param: [currentPage, pageSize, start]
    * @Return: java.util.List<cn.itcast.domain.User>
    * @Description: 查询每页展示的数据
    **/
    List<User> findPage(LimitPage page);

    /**
     * @Author: 32725
     * @Param: [user]
     * @Return: void
     * @Description: 添加用户
     **/
    void addUser(User user);

    /**
    * @Author: 32725
    * @Param: [id]
    * @Return: cn.itcast.domain.User
    * @Description: 根据用户id查找用户
    **/
    User findUserById(int id);

    void updateUser(User user);

    void deleteUser(int id);

    int findTotalCounts(User user);

    User findUserByUsernameAndPassWord(User loginUser);
}
