package com.soft.house.databussiness.mapper;

import com.soft.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 新增用户
     * @param user
     * @return
     */
    public int insert(User user);

    /**
     * 根据邮箱删除用户
     * @param email
     * @return
     */
    public int delete(String email);
}
