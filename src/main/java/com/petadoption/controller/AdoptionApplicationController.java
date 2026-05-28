package com.petadoption.controller;

import com.petadoption.model.dto.ApiResponse;
import com.petadoption.model.entity.AdoptionApplication;
import com.petadoption.service.AdoptionApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 领养申请控制层
 */
@RestController
@RequestMapping("/adoptions")
public class AdoptionApplicationController {

    @Autowired
    private AdoptionApplicationService adoptionApplicationService;

    /**
     * 提交领养申请
     */
    @PostMapping
    public ApiResponse<AdoptionApplication> submitApplication(@RequestBody AdoptionApplication application) {
        try {
            AdoptionApplication submittedApp = adoptionApplicationService.submitApplication(application);
            return ApiResponse.success(submittedApp, "申请已提交，请等待审核");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 获取用户的领养申请
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<List<AdoptionApplication>> getUserApplications(@PathVariable Long userId) {
        try {
            List<AdoptionApplication> applications = adoptionApplicationService.getUserApplications(userId);
            return ApiResponse.success(applications, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 获取宠物的领养申请
     */
    @GetMapping("/pet/{petId}")
    public ApiResponse<List<AdoptionApplication>> getPetApplications(@PathVariable Long petId) {
        try {
            List<AdoptionApplication> applications = adoptionApplicationService.getPetApplications(petId);
            return ApiResponse.success(applications, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 获取申请详情
     */
    @GetMapping("/{applicationId}")
    public ApiResponse<AdoptionApplication> getApplicationInfo(@PathVariable Long applicationId) {
        try {
            AdoptionApplication application = adoptionApplicationService.getById(applicationId);
            if (application == null) {
                return ApiResponse.badRequest("申请不存在");
            }
            return ApiResponse.success(application);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 审核通过申请
     */
    @PostMapping("/{applicationId}/approve")
    public ApiResponse<Void> approveApplication(
            @PathVariable Long applicationId,
            @RequestParam Long reviewerId,
            @RequestParam(required = false) String comment) {
        try {
            boolean success = adoptionApplicationService.approveApplication(applicationId, reviewerId, comment);
            if (success) {
                return ApiResponse.success(null, "申请已通过");
            } else {
                return ApiResponse.badRequest("审核失败");
            }
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 拒绝申请
     */
    @PostMapping("/{applicationId}/reject")
    public ApiResponse<Void> rejectApplication(
            @PathVariable Long applicationId,
            @RequestParam Long reviewerId,
            @RequestParam(required = false) String comment) {
        try {
            boolean success = adoptionApplicationService.rejectApplication(applicationId, reviewerId, comment);
            if (success) {
                return ApiResponse.success(null, "申请已拒绝");
            } else {
                return ApiResponse.badRequest("拒绝失败");
            }
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 完成领养
     */
    @PostMapping("/{applicationId}/complete")
    public ApiResponse<Void> completeAdoption(@PathVariable Long applicationId) {
        try {
            boolean success = adoptionApplicationService.completeAdoption(applicationId);
            if (success) {
                return ApiResponse.success(null, "领养已完成");
            } else {
                return ApiResponse.badRequest("操作失败");
            }
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 获取待审核的申请
     */
    @GetMapping("/pending")
    public ApiResponse<List<AdoptionApplication>> getPendingApplications() {
        try {
            List<AdoptionApplication> applications = adoptionApplicationService.getPendingApplications();
            return ApiResponse.success(applications, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 统计用户的领养数量
     */
    @GetMapping("/user/{userId}/count")
    public ApiResponse<Integer> countUserAdoptions(@PathVariable Long userId) {
        try {
            Integer count = adoptionApplicationService.countUserAdoptions(userId);
            return ApiResponse.success(count, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }
}
