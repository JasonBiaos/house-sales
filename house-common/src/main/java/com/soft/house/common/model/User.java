package com.soft.house.common.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @ClassName: User
 * @Description: 用户表实体类
 * @Author: Jason Biao
 * @Date: 2019/8/13 15:34
 * @Version: 1.0
 **/
public class User {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String passwd;

    /**
     * 确认密码
     */
    private String confirmPasswd;

    /**
     * 用户类型-1.普通用户 2.经纪人
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否启用-1.启用 0.停用
     */
    private Integer enable;

    /**
     * 头像图片:存的为url地址
     */
    private String avatar;

    /**
     * 上传文件
     */
    private MultipartFile avatarFile;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 激活邮箱的key
     */
    private String key;

    /**
     * 经纪机构id
     */
    private Long agencyId;

    /**
     * 自我介绍
     */
    private String aboutme;

    /**
     * 经纪机构名称
     */
    private String agencyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getConfirmPasswd() {
        return confirmPasswd;
    }

    public void setConfirmPasswd(String confirmPasswd) {
        this.confirmPasswd = confirmPasswd;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public MultipartFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }
}
