package cn.itcast.service;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;

/**
 * @Auther : 32725
 * @Date: 2019/2/7 13:16
 * @Description: 处理用户操作的业务层接口
 */
public interface UserService {

    /**
    * @Author: 32725
    * @Param: [currentPage, pageSize]
    * @Return: cn.itcast.domain.PageBean<cn.itcast.domain.User>
    * @Description:  分页查询用户数据，并将数据封装成PageBean对象，并返回
    **/
    PageBean<User> findAllByPage(int currentPage, int pageSize);

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
    * @Description: 根据用户id查找用户，并返回
    **/
    User findUserById(int id);

    /**
    * @Author: 32725
    * @Param: [user]
    * @Return: void
    * @Description: 修改用户数据
    **/
    void updateUser(User user);

    void deleteUser( int id);

    PageBean<User> findComplex(User user, int currentPage, int pageSize);

    User findUserByUsernameAndPassWord(User loginUser);
}
