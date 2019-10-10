package com.soft.house.databussiness.service;

import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.soft.house.common.model.User;
import com.soft.house.databussiness.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: MailService
 * @Description: 邮件服务
 * @Author: Jason Biao
 * @Date: 2019/8/19 14:21
 * @Version: 1.0
 **/
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserMapper userMapper;

    /** 域名 */
    @Value("${domain.name}")
    private String domainName;

    /**  */
    @Value("${spring.mail.username}")
    private String form;

    /**
     * 基于guava cache 的缓存建立
     * 规定缓存项的数目不超过固定值，只需使用CacheBuilder.maximumSize(long)
     * expireAfterAccess(long,TimeUnit):设置超时时间
     */
    private final Cache<String,String> registerCache =
            CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES)
                    .removalListener(new RemovalListener<String, String>() {//设置监听事件，就是在 删除key的时候触发这个事件
                        /**
                         * 如果超时执行删除用户操作
                         * @param removalNotification
                         */
                        @Override
                        public void onRemoval(RemovalNotification<String, String> removalNotification) {
                            String email = removalNotification.getValue();
                            User user = new User();
                            user.setEmail(email);
                            List<User> targetUser = userMapper.selectUsersByQuery(user);
                            /** 代码优化: 在删除前首先判断用户是否已经被激活，对于未激活的用户进行移除操作*/
                            if (!targetUser.isEmpty() && Objects.equal(targetUser.get(0).getEnable(),0)){
                                    userMapper.delete(email);
                            }

                        }
                    }).build();

    /**
     * 1.缓存key-email的关系(缓存用的guava cache)
     * 2.借助spring mail 发送邮件
     * 3.借助异步框架进行异步操作
     * @param email
     */
    @Async
    public void registerNotify(String email){
        /** 生成10位数随机的key */
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        /** 将(key,email)放入缓存中 */
        registerCache.put(randomKey,email);
        String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
        sendMail("房产平台激活邮件",url,email);
    }

    /**
     * 发送邮件方法(异步方式)
     * @param title
     * @param url
     * @param email
     */
    @Async
    public void sendMail(String title,String url,String email){
        SimpleMailMessage message = new SimpleMailMessage();
        /** 邮件发送人 */
        message.setFrom(form);
        /** 邮件主题 */
        message.setSubject(title);
        /** 邮件收件人 */
        message.setTo(email);
        /** 邮件内容 */
        message.setText(url);
        mailSender.send(message);
    }

    /**
     * 验证邮箱是否激活
     * @param key
     * @return
     */
    public boolean enable(String key){
        String email = registerCache.getIfPresent(key);
        if (StringUtils.isBlank(email)){
            return false;
        }
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setEnable(1);
        /** 根据邮箱更新用户 */
        userMapper.update(updateUser);
        registerCache.invalidate(key);
        return true;
    }
}
