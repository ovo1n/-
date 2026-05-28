package com.petadoption.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petadoption.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据手机号查询用户
     */
    User selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * 根据用户名查询用户
     */
    User selectByUsername(@Param("username") String username);
}