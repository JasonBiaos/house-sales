package com.soft.house.web.controller;

import com.soft.house.common.model.User;
import com.soft.house.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: UserHelper
 * @Description: 用户表单字段验证类
 * @Author: Jason Biao
 * @Date: 2019/8/13 16:08
 * @Version: 1.0
 **/
public class UserHelper {

    public static ResultMsg validate(User user){
        /**
         * 验证email
         */
        if (StringUtils.isBlank(user.getEmail())){
            return ResultMsg.errorMsg("Email不能为空，请重新输入！");
        }

        /**
         * 验证用户名
         */
        if (StringUtils.isBlank(user.getName())){
            return ResultMsg.errorMsg("用户名不能为空！");
        }

        /**
         * 验证密码
         */
        if (StringUtils.isBlank(user.getConfirmPasswd()) || StringUtils.isBlank(user.getPasswd())
                || !user.getPasswd().equals(user.getConfirmPasswd())){
            return ResultMsg.errorMsg("您所输入的密码为空或者有误，请重新输入！");
        }

        /**
         * 验证密码长度
         */
        if (user.getPasswd().length() < 6){
            return ResultMsg.errorMsg("密码长度不得小于6位，请重新输入！");
        }

        return ResultMsg.successMsg("");
    }
}
