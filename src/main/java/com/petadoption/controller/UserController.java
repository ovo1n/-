package com.petadoption.controller;

import com.petadoption.model.dto.ApiResponse;
import com.petadoption.model.entity.User;
import com.petadoption.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制层 (REST API)
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            return ApiResponse.success(registeredUser, "注册成功");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse<User> login(@RequestParam String username, @RequestParam String password) {
        try {
            User user = userService.login(username, password);
            return ApiResponse.success(user, "登录成功");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/{userId}")
    public ApiResponse<User> getUserInfo(@PathVariable Long userId) {
        try {
            User user = userService.getById(userId);
            if (user == null) {
                return ApiResponse.badRequest("用户不存在");
            }
            return ApiResponse.success(user);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/{userId}")
    public ApiResponse<Void> updateUserInfo(@PathVariable Long userId, @RequestBody User user) {
        try {
            user.setId(userId);
            boolean success = userService.updateUserInfo(user);
            if (success) {
                return ApiResponse.success(null, "更新成功");
            } else {
                return ApiResponse.badRequest("更新失败");
            }
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("/{userId}/change-password")
    public ApiResponse<Void> changePassword(@PathVariable Long userId,
                                           @RequestParam String oldPassword,
                                           @RequestParam String newPassword) {
        try {
            boolean success = userService.changePassword(userId, oldPassword, newPassword);
            if (success) {
                return ApiResponse.success(null, "密码修改成功");
            } else {
                return ApiResponse.badRequest("密码修改失败");
            }
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
