package com.petadoption.controller;

import com.petadoption.model.dto.ApiResponse;
import com.petadoption.model.entity.AdoptionApplication;
import com.petadoption.model.entity.Pet;
import com.petadoption.model.entity.User;
import com.petadoption.service.AdoptionApplicationService;
import com.petadoption.service.PetService;
import com.petadoption.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计数据控制层（用于数据可视化）
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private PetService petService;

    @Autowired
    private AdoptionApplicationService adoptionApplicationService;

    @Autowired
    private UserService userService;

    /**
     * 获取仪表板统计数据
     */
    @GetMapping("/dashboard")
    public ApiResponse<Map<String, Object>> getDashboardStatistics() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // 待领养宠物总数
            long pendingPetsCount = petService.count();
            stats.put("pendingPetsCount", pendingPetsCount);
            
            // 待审核的领养申请数
            List<AdoptionApplication> pendingApplications = adoptionApplicationService.getPendingApplications();
            stats.put("pendingApplicationsCount", pendingApplications.size());
            
            // 注册用户数
            long userCount = userService.count();
            stats.put("userCount", userCount);
            
            // 已完成的领养数
            long completedAdoptionsCount = adoptionApplicationService.count();
            stats.put("completedAdoptionsCount", completedAdoptionsCount);
            
            return ApiResponse.success(stats, "数据获取成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }
}
