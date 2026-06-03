package com.petadoption.controller;

import com.petadoption.model.dto.ApiResponse;
import com.petadoption.model.entity.FollowUpRecord;
import com.petadoption.service.FollowUpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 回访记录控制层 (REST API)
 */
@RestController
@RequestMapping("/api/follow-ups")
public class FollowUpRecordController {

    @Autowired
    private FollowUpRecordService followUpRecordService;

    @PostMapping
    public ApiResponse<FollowUpRecord> createFollowUp(@RequestBody FollowUpRecord record) {
        try {
            FollowUpRecord created = followUpRecordService.create(record);
            return ApiResponse.success(created);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<FollowUpRecord>> getUserFollowUps(@PathVariable Long userId) {
        try {
            List<FollowUpRecord> records = followUpRecordService.getByUserId(userId);
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    @GetMapping("/pet/{petId}")
    public ApiResponse<List<FollowUpRecord>> getPetFollowUps(@PathVariable Long petId) {
        try {
            List<FollowUpRecord> records = followUpRecordService.getByPetId(petId);
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    @GetMapping("/pet/{petId}/recent")
    public ApiResponse<List<FollowUpRecord>> getRecentPetFollowUps(@PathVariable Long petId) {
        try {
            List<FollowUpRecord> records = followUpRecordService.getRecentByPetId(petId);
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    @GetMapping("/pet/{petId}/count")
    public ApiResponse<Integer> countPetFollowUps(@PathVariable Long petId) {
        try {
            Integer count = followUpRecordService.countByPetId(petId);
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }
}
