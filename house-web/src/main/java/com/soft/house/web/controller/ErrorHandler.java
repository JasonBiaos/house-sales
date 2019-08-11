package com.soft.house.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: ErrorHandler
 * @Description: 自定义异常输出配置类
 * @Author: Jason Biao
 * @Date: 2019/6/16 19:35
 * @Version: 1.0
 *
 * @ControllerAdvice： (controller层)定义统一处理的异常处理类
 **/
@ControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    /**
     * 指定拦截异常的类型
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class,RuntimeException.class})
    public String error500(HttpServletRequest request,Exception e){
        logger.error(e.getMessage(),e);
        logger.error(request.getRequestURI() + "encounter 500");
        return "error/500";
    }
}
