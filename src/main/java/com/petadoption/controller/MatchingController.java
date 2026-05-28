package com.petadoption.controller;

import com.petadoption.model.dto.ApiResponse;
import com.petadoption.model.entity.MatchingRecord;
import com.petadoption.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 智能匹配控制层
 */
@RestController
@RequestMapping("/matching")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    /**
     * 获取用户的推荐宠物列表
     */
    @GetMapping("/recommendations/{userId}")
    public ApiResponse<List<MatchingRecord>> getRecommendedPets(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") Integer limit) {
        try {
            List<MatchingRecord> records = matchingService.getRecommendedPets(userId, limit);
            return ApiResponse.success(records, "推荐查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 为用户生成推荐（触发匹配算法）
     */
    @PostMapping("/generate/{userId}")
    public ApiResponse<Void> generateRecommendations(@PathVariable Long userId) {
        try {
            matchingService.generateRecommendations(userId);
            return ApiResponse.success(null, "推荐生成成功，请稍候...");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 记录用户点击推荐宠物
     */
    @PostMapping("/{matchingRecordId}/click")
    public ApiResponse<Void> recordUserClick(@PathVariable Long matchingRecordId) {
        try {
            boolean success = matchingService.recordUserClick(matchingRecordId);
            if (success) {
                return ApiResponse.success(null, "点击已记录");
            } else {
                return ApiResponse.badRequest("记录失败");
            }
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 获取宠物的匹配用户列表
     */
    @GetMapping("/pet/{petId}")
    public ApiResponse<List<MatchingRecord>> getMatchedUsers(
            @PathVariable Long petId,
            @RequestParam(defaultValue = "10") Integer limit) {
        try {
            List<MatchingRecord> records = matchingService.getMatchedUsers(petId, limit);
            return ApiResponse.success(records, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }
}
