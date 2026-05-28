package com.petadoption.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petadoption.mapper.UserMapper;
import com.petadoption.model.entity.User;
import com.petadoption.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.time.LocalDateTime;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User register(User user) {
        // 检查用户名是否存在
        User existingUser = userMapper.selectByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查手机号是否存在
        User existingPhoneUser = userMapper.selectByPhoneNumber(user.getPhoneNumber());
        if (existingPhoneUser != null) {
            throw new RuntimeException("手机号已被注册");
        }

        // 加密密码
        String encryptedPassword = encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);

        // 设置默认值
        user.setRole(1); // 默认普通用户
        user.setStatus(1); // 启用
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 保存用户
        save(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (user.getStatus() == 0) {
            throw new RuntimeException("账户已禁用");
        }

        String encryptedPassword = encryptPassword(password);
        if (!user.getPassword().equals(encryptedPassword)) {
            throw new RuntimeException("密码错误");
        }

        // 更新最后登录时间
        user.setLastLoginAt(LocalDateTime.now());
        updateById(user);

        return user;
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean updateUserInfo(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return updateById(user);
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        String encryptedOldPassword = encryptPassword(oldPassword);
        if (!user.getPassword().equals(encryptedOldPassword)) {
            throw new RuntimeException("旧密码错误");
        }

        String encryptedNewPassword = encryptPassword(newPassword);
        user.setPassword(encryptedNewPassword);
        user.setUpdatedAt(LocalDateTime.now());

        return updateById(user);
    }

    /**
     * MD5密码加密
     */
    private String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}