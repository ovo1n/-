package com.petadoption.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petadoption.model.entity.User;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     */
    User register(User user);

    /**
     * 用户登录
     */
    User login(String username, String password);

    /**
     * 根据手机号查询用户
     */
    User getUserByPhoneNumber(String phoneNumber);

    /**
     * 更新用户信息
     */
    boolean updateUserInfo(User user);

    /**
     * 修改密码
     */
    boolean changePassword(Long userId, String oldPassword, String newPassword);
}