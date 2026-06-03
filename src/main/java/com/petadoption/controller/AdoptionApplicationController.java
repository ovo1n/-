package com.petadoption.controller;

import com.petadoption.model.dto.ApiResponse;
import com.petadoption.model.entity.AdoptionApplication;
import com.petadoption.service.AdoptionApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 领养申请控制层 (REST API)
 */
@RestController
@RequestMapping("/api/adoptions")
public class AdoptionApplicationController {

    @Autowired
    private AdoptionApplicationService adoptionApplicationService;

    @PostMapping
    public ApiResponse<AdoptionApplication> submitApplication(@RequestBody AdoptionApplication application) {
        try {
            AdoptionApplication submittedApp = adoptionApplicationService.submitApplication(application);
            return ApiResponse.success(submittedApp, "申请已提交");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<AdoptionApplication>> getUserApplications(@PathVariable Long userId) {
        try {
            List<AdoptionApplication> apps = adoptionApplicationService.getUserApplications(userId);
            return ApiResponse.success(apps);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    @PostMapping("/generate/{userId}")
    public ApiResponse<Void> generateRecommendations(@PathVariable Long userId) {
        try {
            adoptionApplicationService.generateRecommendations(userId);
            return ApiResponse.success(null, "已触发推荐生成");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    @PostMapping("/{applicationId}/approve")
    public ApiResponse<Void> approveApplication(@PathVariable Long applicationId, @RequestParam Long reviewerId, @RequestParam(required = false) String comment) {
        try {
            boolean ok = adoptionApplicationService.approveApplication(applicationId, reviewerId, comment);
            if (ok) return ApiResponse.success(null, "已同意");
            return ApiResponse.badRequest("操作失败");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    @PostMapping("/{applicationId}/reject")
    public ApiResponse<Void> rejectApplication(@PathVariable Long applicationId, @RequestParam Long reviewerId, @RequestParam(required = false) String comment) {
        try {
            boolean ok = adoptionApplicationService.rejectApplication(applicationId, reviewerId, comment);
            if (ok) return ApiResponse.success(null, "已拒绝");
            return ApiResponse.badRequest("操作失败");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    @GetMapping("/pet/{petId}")
    public ApiResponse<List<AdoptionApplication>> getPetApplications(@PathVariable Long petId) {
        try {
            List<AdoptionApplication> apps = adoptionApplicationService.getPetApplications(petId);
            return ApiResponse.success(apps);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    @GetMapping("/pending")
    public ApiResponse<List<AdoptionApplication>> getPendingApplications() {
        try {
            List<AdoptionApplication> apps = adoptionApplicationService.getPendingApplications();
            return ApiResponse.success(apps);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    @GetMapping("/count/user/{userId}")
    public ApiResponse<Integer> countUserAdoptions(@PathVariable Long userId) {
        try {
            Integer count = adoptionApplicationService.countUserAdoptions(userId);
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }
}
