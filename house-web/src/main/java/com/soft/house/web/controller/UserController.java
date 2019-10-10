package com.soft.house.web.controller;

import com.soft.house.common.constants.CommonConstants;
import com.soft.house.common.model.User;
import com.soft.house.common.result.ResultMsg;
import com.soft.house.databussiness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: UserController
 * @Description: 用户Controller
 * @Author: Jason Biao
 * @Date: 2019/8/13 15:19
 * @Version: 1.0
 **/
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册提交流程：1.注册验证 2.发送邮件 3.验证失败重定向到注册页面
     * 是否为注册页获取：根据account对象为依据判断是否为注册页获取请求
     * @param user
     * @param modelMap
     * @return
     */
    @RequestMapping("accounts/register")
    public String accountsRegister(User user,ModelMap modelMap){

        /** 用户为空跳转到注册页面 */
        if (user == null || user.getName() == null){
            return "/user/accounts/register";
        }

        /** 用户表单验证 */
        ResultMsg resultMsg = UserHelper.validate(user);

        if (resultMsg.isSuccess()&& userService.addUser(user)){
            modelMap.put("email",user.getEmail());
            return "/user/accounts/registerSubmit";
        }else {
            return "redirect:/accounts/register?" + resultMsg.asUrlParams();
        }

    }

    /**
     * 验证邮箱是否激活
     * @param key
     * @return
     */
    @RequestMapping("accounts/verify")
    public String verify(String key){
        boolean result = userService.enable(key);
        if (result){
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        }else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期");
        }
    }

    /********************************************************登录流程*****************************************************/

    /**
     * 用户登录接口
     * @param request
     * @return
     */
    @RequestMapping("/accounts/signin")
    public String signin(HttpServletRequest request){
        /**获取用户名、密码、目标页*/
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String target = request.getParameter("target");
        /**用户名和密码为空跳转到登录页*/
        if (username == null || password == null){
            request.setAttribute("target",target);
            return "/user/accounts/signin";
        }
        /**用户名密码验证*/
        User user = userService.auth(username,password);
        if (user == null){
            return "redirect:/accounts/signin?" + "target=" + target + "&username=" + username + "&"
                    + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        }else {
            HttpSession session = request.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE,user);
            session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE,user);
            return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
        }
    }

    /**
     * 注销用户
     * @param request
     * @return
     */
    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        /**注销session*/
        session.invalidate();
        return "redirect:/index";
    }
}
