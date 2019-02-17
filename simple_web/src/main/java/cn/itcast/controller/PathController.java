package cn.itcast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther : 32725
 * @Date: 2019/2/12 18:17
 * @Description: 负责转发到 WEB-INF下page包内的jsp页面
 */
@Controller
@RequestMapping("/path")
public class PathController {
    @RequestMapping("/pageName/{pageName}")
    public String gotoPage(@PathVariable String pageName){
        return pageName;
    }
}
