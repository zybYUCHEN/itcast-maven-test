package cn.itcast.util.Component.exResolver.resolver;

import cn.itcast.exception.BeanException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther : 32725
 * @Date: 2019/2/14 11:27
 * @Description: 自定义异常处理器
 */
public class BeanExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //1.打印异常到控制台
        ex.printStackTrace();
        BeanException exception = null;
        //2.捕获异常并判断抛出的异常是否是自定义类型的
        if (ex instanceof BeanException) {
            //是,进行类型转换
            exception = (BeanException) ex;
        }else {
            //否，创建新的自定义异常类对象
            exception = new BeanException("请联系管理员");
        }
        //3.存储异常信息
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", exception);
        mv.setViewName("error");
        return mv;
    }
}
