package com.soft.house.common.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * @ClassName: HashUtils
 * @Description: MD5加盐工具类
 * @Author: Jason Biao
 * @Date: 2019/8/13 19:59
 * @Version: 1.0
 **/
public class HashUtils {

    /** guava的md5 */
    private static final HashFunction FUNCTION = Hashing.md5();

    /** 定义加盐的常量 */
    private static final String SALT = "soft.com";

    /**
     * 密码加盐方法
     * @param password
     * @return
     */
    public static String encryPassword(String password){
        HashCode hashCode = FUNCTION.hashString(password + SALT, Charset.forName("UTF-8"));
        return hashCode.toString();
    }
}
