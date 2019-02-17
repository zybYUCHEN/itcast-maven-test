package cn.itcast.service.impl;

import cn.itcast.domain.LimitPage;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.mapper.UserMapper;
import cn.itcast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.SchemaOutputResolver;
import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/7 13:17
 * @Description: 操作用户的业务层接口的实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * @Author: 32725
     * @Param: [currentPage, pageSize]
     * @Return: cn.itcast.domain.PageBean<cn.itcast.domain.User>
     * @Description: 分页查询用户数据，并将数据封装成PageBean对象，并返回
     **/
    @Override
    public PageBean<User> findAllByPage(int currentPage, int pageSize) {

        //1.定义一个空的pageBean对象，用于封装数据
        PageBean<User> pb = new PageBean<>();
        //2.封装每页展示数据量
        pb.setPageSize(pageSize);

        //4.查询总记录数，并封装
        int totalCount = userMapper.findTotalCount();
        pb.setTotalCount(totalCount);
        //5.封装总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        //3.封装当前页码
        if (currentPage >= totalPage) {
            currentPage = totalPage;
        }
        pb.setCurrentPage(currentPage);

        //6.分页查询每页展示的数据，并封装
        int start = (currentPage - 1) * pageSize;
        if (start < 0) {
            start = 1;
        }
        LimitPage page = new LimitPage(start, pageSize);
        List<User> list = userMapper.findPage(page);
        pb.setList(list);
        return pb;
    }

    /**
     * @Author: 32725
     * @Param: [user]
     * @Return: void
     * @Description: 添加用户
     **/
    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: cn.itcast.domain.User
     * @Description: 根据用户id查找用户，并返回
     **/
    @Override
    public User findUserById(int id) {
        userMapper.findUserById(id);
        return userMapper.findUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }

    /**
    * @Author: 32725
    * @Param: [user, currentPage, pageSize]
    * @Return: cn.itcast.domain.PageBean<cn.itcast.domain.User>
    * @Description: 条件模糊查询
    **/
    @Override
    public PageBean<User> findComplex(User user, int currentPage, int pageSize) {
        //1.定义一个空的pageBean对象，用于封装数据
        PageBean<User> pb = new PageBean<>();
        //2.封装每页展示数据量
        pb.setPageSize(pageSize);

        //3.更改为模糊查询
        String username = user.getUsername();
        String address = user.getAddress();
        String email = user.getEmail();
        user.setUsername("%"+username+"%");
        user.setAddress("%"+address+"%");
        user.setEmail("%"+email+"%");


        //4.查询总记录数，并封装
        int totalCount = userMapper.findTotalCounts(user);
        pb.setTotalCount(totalCount);
        //5.封装总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        //6.封装当前页码
        if (currentPage >= totalPage) {
            currentPage = totalPage;//处理页面分页条，点击下一页出错
        }
        pb.setCurrentPage(currentPage);

        //6.分页查询每页展示的数据，并封装
        int start = (currentPage - 1) * pageSize;
        if (start < 0) {
            start = 1;//预防总页数为0时，start为负数情况的发生
        }
        LimitPage page = new LimitPage(user,start, pageSize);
        List<User> list = userMapper.findPage(page);
        pb.setList(list);

        System.out.println(pb);
        return pb;

    }

    @Override
    public User findUserByUsernameAndPassWord(User loginUser) {

        return userMapper.findUserByUsernameAndPassWord(loginUser);
    }
}
