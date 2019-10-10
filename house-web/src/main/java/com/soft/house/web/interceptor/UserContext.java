package com.soft.house.web.interceptor;

import com.soft.house.common.model.User;

/**
 * @ClassName: UserContext
 * @Description: ThreadLocal操作用户session类
 * @Author: Jason Biao
 * @Date: 2019/10/10 16:59
 * @Version: 1.0
 **/
public class UserContext {

    private static final ThreadLocal<User> USER_HODLER = new ThreadLocal<>();

    /**
     * 设置用户
     * @param user
     */
    public static void setUser(User user){
        USER_HODLER.set(user);
    }

    /**
     * 删除用户
     */
    public static void remove(){
        USER_HODLER.remove();
    }

    /**
     * 获取用户
     * @return
     */
    public static User getUser(){
        return USER_HODLER.get();
    }
}
