package com.petadoption.controller;

import com.petadoption.model.dto.ApiResponse;
import com.petadoption.model.entity.FollowUpRecord;
import com.petadoption.service.FollowUpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 领养回访记录控制层
 */
@RestController
@RequestMapping("/follow-ups")
public class FollowUpRecordController {

    @Autowired
    private FollowUpRecordService followUpRecordService;

    /**
     * 创建回访记录
     */
    @PostMapping
    public ApiResponse<FollowUpRecord> createFollowUpRecord(@RequestBody FollowUpRecord record) {
        try {
            FollowUpRecord createdRecord = followUpRecordService.createFollowUpRecord(record);
            return ApiResponse.success(createdRecord, "回访记录已创建");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 获取用户的回访记录
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<List<FollowUpRecord>> getUserFollowUpRecords(@PathVariable Long userId) {
        try {
            List<FollowUpRecord> records = followUpRecordService.getUserFollowUpRecords(userId);
            return ApiResponse.success(records, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 获取宠物的回访记录
     */
    @GetMapping("/pet/{petId}")
    public ApiResponse<List<FollowUpRecord>> getPetFollowUpRecords(@PathVariable Long petId) {
        try {
            List<FollowUpRecord> records = followUpRecordService.getPetFollowUpRecords(petId);
            return ApiResponse.success(records, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 获取回访记录详情
     */
    @GetMapping("/{recordId}")
    public ApiResponse<FollowUpRecord> getFollowUpRecordInfo(@PathVariable Long recordId) {
        try {
            FollowUpRecord record = followUpRecordService.getById(recordId);
            if (record == null) {
                return ApiResponse.badRequest("回访记录不存在");
            }
            return ApiResponse.success(record);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 更新回访记录
     */
    @PutMapping("/{recordId}")
    public ApiResponse<Void> updateFollowUpRecord(@PathVariable Long recordId, @RequestBody FollowUpRecord record) {
        try {
            record.setId(recordId);
            boolean success = followUpRecordService.updateById(record);
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
     * 获取平均满意度评分
     */
    @GetMapping("/statistics/average-satisfaction")
    public ApiResponse<Double> getAverageSatisfactionScore() {
        try {
            Double score = followUpRecordService.getAverageSatisfactionScore();
            return ApiResponse.success(score, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }
}
