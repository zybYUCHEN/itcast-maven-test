package cn.itcast.controller;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.exception.BeanException;
import cn.itcast.service.UserService;
import com.sun.security.ntlm.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.tools.tree.NewArrayExpression;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.UUID;


/**
 * @Auther : 32725
 * @Date: 2019/2/7 13:08
 * @Description: 处理用户操作的控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @Author: 32725
     * @Param: [_currentPage, _pageSize, model]
     * @Return: java.lang.String
     * @Description: 跳转到分页展示页面的方法，默认当前页为第一页，每页展示数据为5条
     **/
    @RequestMapping(value = "/find/{_currentPage}/{_pageSize}", method = RequestMethod.GET)
    public String findAllByPage(@PathVariable String _currentPage, @PathVariable String _pageSize, Model model) {
        int currentPage = 1;
        int pageSize = 5;

        //1. 处理异常情况
        if (_currentPage != null || !"".equals(_currentPage)) {
            try {
                currentPage = Integer.parseInt(_currentPage);
                if (currentPage < 1) {
                    currentPage = 1;
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }
        if (_pageSize != null || "".equals(_pageSize)) {
            try {
                pageSize = Integer.parseInt(_pageSize);
                if (pageSize < 0) {
                    pageSize = 5;
                }
            } catch (NumberFormatException e) {
                pageSize = 5;
            }
        }
        PageBean<User> pb = userService.findAllByPage(currentPage, pageSize);
        model.addAttribute("pb", pb);
        return "list";
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findComplex(User user, Model model, String _currentPage, String _pageSize) {
        int currentPage = 1;
        int pageSize = 5;

        //1. 处理异常情况
        if (_currentPage != null || !"".equals(_currentPage)) {
            try {
                currentPage = Integer.parseInt(_currentPage);
                if (currentPage < 1) {
                    currentPage = 1;
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }
        if (_pageSize != null || "".equals(_pageSize)) {
            try {
                pageSize = Integer.parseInt(_pageSize);
                if (pageSize < 0) {
                    pageSize = 5;
                }
            } catch (NumberFormatException e) {
                pageSize = 5;
            }
        }
        PageBean<User> pb = userService.findComplex(user, currentPage, pageSize);
        model.addAttribute("pb", pb);
        return "list";
    }


    /**
     * @Author: 32725
     * @Param: [user]
     * @Return: java.lang.String
     * @Description: 添加用户,新增保存头像功能（上传功能）
     **/
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(User user, HttpSession session, MultipartFile upload) throws Exception {
        //0.判断文件类型是否符合要求
        String contentType = upload.getContentType();
        System.out.println(contentType);
        if (!contentType.contains("image/")){
            throw new RuntimeException("文件格式不正确");
        }
        //1.获取存储路径
        String path = session.getServletContext().getRealPath("/uploads/");

        //2.创建文件对象
        File file = new File(path);
        //3..判断文件是否存在，如果不存在，则创建
        if (!file.exists()){
            file.mkdir();
        }
        //4.获取上传文件名称
        String filename = upload.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //5.获取唯一文件名
        filename=uuid+"_"+filename;
        user.setPhoto(filename);
        //6.上传文件
        upload.transferTo(new File(file,filename));
        userService.addUser(user);
        return "/login.jsp";
    }

    /**
    * @Author: 32725
    * @Param: [id, currentPage, model]
    * @Return: java.lang.String
    * @Description: 更新用户数据页面数据回显
    **/
    @RequestMapping(value = "/update/{id}/{currentPage}", method = RequestMethod.GET)
    public String update(@PathVariable int id, @PathVariable int currentPage, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("currentPage", currentPage);
        return "update";
    }

    /**
    * @Author: 32725
    * @Param: [user]
    * @Return: java.lang.String
    * @Description: 更新用户数据
    **/
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String updateUser(User user,MultipartFile upload) throws Exception {
        //1.判断上传文件格式是否正确
        String contentType = upload.getContentType();
        if (!contentType.contains("image/")){
            throw new BeanException("文件格式不正确，请传入图片");
        }
        //2.获取存储服务器路径
        String path = "http://localhost:8081/simple_web_war_exploded/uploads/";
        //3.获取文件名称
        String filename = upload.getOriginalFilename();
        filename=UUID.randomUUID().toString().replace("-", "").toUpperCase()+"_"+filename;
        //3.创建客户端对象
        Client client = Client.create();
        //4.连接存储服务器
        WebResource webResource = client.resource(path+filename);
        //5.上传文件
        webResource.put(upload.getBytes());
        userService.updateUser(user);
        return "forward:/WEB-INF/page/login.jsp";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable int id, @RequestHeader("Referer") String referer) {
        userService.deleteUser(id);
        return "redirect:" + referer;

    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteSelect(HttpServletRequest request, @RequestHeader("Referer") String referer) {
        String[] ids = request.getParameterValues("choice");
        for (String id : ids) {
            userService.deleteUser(Integer.parseInt(id));
        }
        return "redirect:" + referer;
    }

    /**
     * @Author: 32725
     * @Param: [username, password, checkCode, model, modelMap, request, response]
     * @Return: java.lang.String
     * @Description: 登陆方法
     **/
    @RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
    public String login(String username, String password, String checkCode,
                        Integer ckecked, HttpSession session, HttpServletResponse response) {
        if (username==null||password==null||checkCode==null){
            return "forward:/WEB-INF/page/login.jsp";
        }
        //1.获取程序生成的验证码
        String checkcodeServer = (String) session.getAttribute("CHECKCODE_SERVER");
        //2.移除session中的验证码，确保验证码的一次性
        session.removeAttribute("CHECKCODE_SERVER");
        //3..添加记住状态功能
            //3.1创建cookie对象用于存储状态
        Cookie cookie1 = new Cookie("username", username);
        Cookie cookie2 = new Cookie("password", password);

        System.out.println(ckecked);
            //3.2判断cheaked是否有值，并且为指定的值1
        if(ckecked!=null&&ckecked==1){
            //3.2.1勾选了记住状态，存储cookie
            cookie1.setMaxAge(176160768);
            cookie2.setMaxAge(176160768);
        }else {
            //3.2.2没有勾选记住状态,删除cookie
            cookie1.setMaxAge(0);
            cookie2.setMaxAge(0);
        }
            //3.3设置cookie存储路径
            cookie1.setPath("/");
            cookie2.setPath("/");
            //3.4发送cookie
            response.addCookie(cookie1);
            response.addCookie(cookie2);

        //4.先判断验证码是否正确，如果勾选记住状态则不用检测验证码
        if (!checkCode.equalsIgnoreCase(checkcodeServer)){
            //验证码不正确，直接返回登陆页面，并且打印出错原因
            session.setAttribute("login_msg", "验证码不正确");
            return "forward:/WEB-INF/page/login.jsp";
        }


        //5.验证码正确，进行账号密码判断
        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);

        User user = userService.findUserByUsernameAndPassWord(loginUser);

        if (user == null) {
            //用户名或密码错误
            session.setAttribute("login_msg", "用户名或密码错误");
            return "forward:/WEB-INF/page/login.jsp";
        }
        //6..用户和密码正确
        session.setAttribute("user",user);
        return "redirect:/index.jsp";
    }
    /**
    * @Author: 32725
    * @Param: []
    * @Return: void
    * @Description: 异步请求，请求验证码,辅助记住状态，实现验证码回显
    **/
    @RequestMapping(value = "/login/{id}",method = RequestMethod.POST)
    public @ResponseBody String loginCode(@PathVariable Integer id,HttpSession session,
                                          @CookieValue("username") String cookie1,@CookieValue("password") String cookie2){
        String code = "";
        //1.判断是否有cookie带过来
                code = (String) session.getAttribute("CHECKCODE_SERVER");
        return code;
    }
    /**
    * @Author: 32725
    * @Param: []
    * @Return: java.lang.String
    * @Description: 退出登陆
    **/
    @RequestMapping(value = "/exit",method = RequestMethod.GET)
    public String exit(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/user/login";
    }
}
