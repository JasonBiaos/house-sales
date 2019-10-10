package com.soft.house.web.interceptor;

import com.google.common.base.Joiner;
import com.soft.house.common.constants.CommonConstants;
import com.soft.house.common.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName: AuthInterceptor(自定义拦截器)
 * @Description: AuthInteceptor是为了每一次的请求的时候都先去session中取user对象，如果session中有，
 *                就放user对象到threadlocal中。这是为了业务处理的时候能直接获取用户对象。
 * @Author: Jason Biao
 * @Date: 2019/10/10 16:37
 * @Version: 1.0
 **/
@Component
public class AuthInterceptor implements HandlerInterceptor {

    /**
     * 请求前(controller方法执行前)
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,String[]> map = request.getParameterMap();
        map.forEach((k,v)->{
            if (k.equals("errorMsg") || k.equals("successMsg") || k.equals("target")){
                request.setAttribute(k,Joiner.on(",").join(v));
            }
        });

        /**获取请求的url，如果为静态资源或者错误页面就返回true*/
        String requestUrl = request.getRequestURI();
        if (requestUrl.startsWith("/static") || requestUrl.startsWith("/error")){
            return true;
        }
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        if (user != null){
            UserContext.getUser();
        }
        return true;
    }

    /**
     * 请求中(controller方法执行后)
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求后(页面渲染后)
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
}
