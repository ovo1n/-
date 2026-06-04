package com.petadoption.controller;

import com.petadoption.model.dto.ApiResponse;
import com.petadoption.model.entity.User;
import com.petadoption.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 管理员控制层
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有救助人
     */
    @GetMapping("/rescuers")
    public ApiResponse<List<User>> getAllRescuers() {
        try {
            List<User> rescuers = userService.lambdaQuery()
                    .eq(User::getRole, 2)
                    .list();
            return ApiResponse.success(rescuers, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 审核救助人资质
     */
    @PostMapping("/rescuers/{rescuerId}/approve")
    public ApiResponse<Void> approveRescuer(@PathVariable Long rescuerId) {
        try {
            User rescuer = userService.getById(rescuerId);
            if (rescuer == null) {
                return ApiResponse.badRequest("救助人不存在");
            }
            rescuer.setStatus(1); // 启用
            userService.updateUserInfo(rescuer);
            return ApiResponse.success(null, "已批准");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 禁用救助人
     */
    @PostMapping("/rescuers/{rescuerId}/disable")
    public ApiResponse<Void> disableRescuer(@PathVariable Long rescuerId) {
        try {
            User rescuer = userService.getById(rescuerId);
            if (rescuer == null) {
                return ApiResponse.badRequest("救助人不存在");
            }
            rescuer.setStatus(0); // 禁用
            userService.updateUserInfo(rescuer);
            return ApiResponse.success(null, "已禁用");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
