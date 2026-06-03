package com.petadoption.controller;

import com.petadoption.model.dto.ApiResponse;
import com.petadoption.model.entity.MatchingRecord;
import com.petadoption.model.entity.Pet;
import com.petadoption.model.entity.User;
import com.petadoption.service.MatchingService;
import com.petadoption.service.PetService;
import com.petadoption.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 智能匹配控制层 (REST API)
 */
@RestController
@RequestMapping("/api/matching")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    /**
     * 获取用户的推荐宠物
     */
    @GetMapping("/recommendations/{userId}")
    public ApiResponse<List<MatchingRecord>> getRecommendedPets(@PathVariable Long userId,
                                                                @RequestParam(defaultValue = "10") Integer limit) {
        try {
            List<MatchingRecord> records = matchingService.getRecommendedPets(userId, limit);
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    @PostMapping("/generate/{userId}")
    public ApiResponse<Void> generateRecommendations(@PathVariable Long userId) {
        try {
            matchingService.generateRecommendations(userId);
            return ApiResponse.success(null, "生成完成");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    @PostMapping("/click/{matchingRecordId}")
    public ApiResponse<Void> recordUserClick(@PathVariable Long matchingRecordId) {
        try {
            boolean ok = matchingService.recordUserClick(matchingRecordId);
            if (ok) return ApiResponse.success(null);
            return ApiResponse.badRequest("更新失败");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    @GetMapping("/matched-users/{petId}")
    public ApiResponse<List<MatchingRecord>> getMatchedUsers(@PathVariable Long petId,
                                                             @RequestParam(defaultValue = "10") Integer limit) {
        try {
            List<MatchingRecord> records = matchingService.getMatchedUsers(petId, limit);
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }
}
