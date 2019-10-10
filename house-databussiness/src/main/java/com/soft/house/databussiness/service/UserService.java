package com.soft.house.databussiness.service;

import com.google.common.collect.Lists;
import com.soft.house.common.model.User;
import com.soft.house.common.utils.BeanHelper;
import com.soft.house.common.utils.HashUtils;
import com.soft.house.databussiness.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: UserService
 * @Description: 用户业务逻辑类
 * @Author: Jason Biao
 * @Date: 2019/8/13 17:21
 * @Version: 1.0
 **/
@Service
public class UserService {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    /**
     * 新增用户
     * 1.插入用户数据到数据库，状态为非激活；密码用MD5加盐；保存头像到本地
     * 2.生成key并绑定email
     * 3.发送邮件到用户邮箱，用户点击激活链接并修改数据库中的状态为激活
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user){
        /** 用户密码加盐 */
        user.setPasswd(HashUtils.encryPassword(user.getPasswd()));
        /** 获取文件路径*/
        List<String> imgPaths = fileService.getImgPaths(Lists.newArrayList(user.getAvatarFile()));
        if (!imgPaths.isEmpty()){
            user.setAvatar(imgPaths.get(0));
        }
        /** 给为null的字段设置默认值*/
        BeanHelper.setDefaultProp(user,User.class);
        BeanHelper.onInsert(user);
        user.setEnable(0);
        userMapper.insert(user);
        /** 给用户发送邮件 */
        mailService.registerNotify(user.getEmail());
        return true;
    }

    /**
     * 验证邮箱是否激活
     * @param key
     * @return
     */
    public boolean enable(String key){
        return mailService.enable(key);
    }
}
