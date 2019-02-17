package cn.itcast.util.Component.interceptor;

import cn.itcast.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Auther : 32725
 * @Date: 2019/2/14 13:18
 * @Description: 访问资源拦截器，拦截那些请求必须要登陆之后才能访问的controller方法的请求
 */
public class AccessInterceptor implements HandlerInterceptor {

    /**
     * @Author: 32725
     * @Param: [request, response, handler]
     * @Return: boolean
     * @Description: 在访问controller方法之前执行的方法，最常用
     **/
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.判断用户是否登陆
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            //用户尚未登陆，跳转进登陆页面
            request.getRequestDispatcher("/WEB-INF/page/login.jsp").forward(request, response);
            return false;//不放行
        } else {
            return true;//放行
        }
    }

}
