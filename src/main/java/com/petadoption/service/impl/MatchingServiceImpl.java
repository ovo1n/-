package com.petadoption.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petadoption.mapper.MatchingRecordMapper;
import com.petadoption.mapper.PetMapper;
import com.petadoption.model.entity.MatchingRecord;
import com.petadoption.model.entity.Pet;
import com.petadoption.model.entity.User;
import com.petadoption.service.MatchingService;
import com.petadoption.service.PetService;
import com.petadoption.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 智能匹配服务实现类
 */
@Service
public class MatchingServiceImpl extends ServiceImpl<MatchingRecordMapper, MatchingRecord>
        implements MatchingService {

    @Autowired
    private MatchingRecordMapper matchingRecordMapper;

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    /**
     * 智能匹配算法核心
     * 匹配维度权重：住所(30%) + 经验(25%) + 人口(20%) + 健康(15%) + 加分(10%)
     */
    @Override
    public Double calculateMatchingScore(User user, Pet pet) {
        double totalScore = 0.0;

        // 1. 住所匹配度 (30%)
        double residenceScore = calculateResidenceScore(user, pet);
        totalScore += residenceScore * 0.30;

        // 2. 养宠经验匹配度 (25%)
        double experienceScore = calculateExperienceScore(user, pet);
        totalScore += experienceScore * 0.25;

        // 3. 家庭人口匹配度 (20%)
        double familyScore = calculateFamilyScore(user, pet);
        totalScore += familyScore * 0.20;

        // 4. 健康状况匹配度 (15%)
        double healthScore = calculateHealthScore(user, pet);
        totalScore += healthScore * 0.15;

        // 5. 额外加分 (10%)
        double bonusScore = calculateBonusScore(user, pet);
        totalScore += bonusScore * 0.10;

        return Math.min(100.0, totalScore);
    }

    /**
     * 住所匹配度计算
     */
    private double calculateResidenceScore(User user, Pet pet) {
        if (user == null || pet == null) return 50.0;
        if (user.getResidenceType() == null || pet.getSize() == null) {
            return 50.0;
        }

        // 逻辑：大型犬适合平房和别墅，小型犬适合楼房
        if (pet.getSize() == 3) { // 大型犬
            if (user.getResidenceType() == 2 || user.getResidenceType() == 3) {
                return 100.0;
            } else {
                return 50.0;
            }
        } else if (pet.getSize() == 1) { // 小型犬
            return 100.0; // 任何住所都适合
        } else { // 中型犬
            return 80.0;
        }
    }

    /**
     * 养宠经验匹配度计算
     */
    private double calculateExperienceScore(User user, Pet pet) {
        if (user == null || pet == null) return 50.0;
        if (user.getPetExperience() == null || pet.getBreedingDifficulty() == null) {
            return 50.0;
        }

        int experience = user.getPetExperience(); // 1-无 2-1-3年 3-3-5年 4-5年以上
        int difficulty = pet.getBreedingDifficulty(); // 1-容易 2-中等 3-困难

        if (difficulty == 1) { // 容易饲养
            return 100.0;
        } else if (difficulty == 2) { // 中等难度
            if (experience >= 2) {
                return 100.0;
            } else {
                return 60.0;
            }
        } else { // 困难
            if (experience >= 3) {
                return 100.0;
            } else if (experience >= 2) {
                return 70.0;
            } else {
                return 40.0;
            }
        }
    }

    /**
     * 家庭人口匹配度计算
     */
    private double calculateFamilyScore(User user, Pet pet) {
        if (user == null) return 50.0;
        if (user.getFamilySize() == null) {
            return 50.0;
        }

        // 宠物性格中包含"温和"、"友好"等字样时，适合大家庭
        String personality = pet != null && pet.getPersonality() != null ? pet.getPersonality().toLowerCase() : "";

        if (user.getFamilySize() >= 3) {
            if (personality.contains("温和") || personality.contains("友好")) {
                return 100.0;
            } else {
                return 70.0;
            }
        } else if (user.getFamilySize() == 2) {
            return 90.0;
        } else {
            return 80.0;
        }
    }

    /**
     * ���康状况匹配度计算
     */
    private double calculateHealthScore(User user, Pet pet) {
        if (pet == null) return 50.0;
        if (pet.getHealthStatus() == null) {
            return 50.0;
        }

        // 1-健康 2-有小病 3-有大病
        if (pet.getHealthStatus() == 1) {
            return 100.0; // 健康宠物
        } else if (pet.getHealthStatus() == 2) {
            return 75.0; // 有小病但可接受
        } else {
            return 50.0; // 有大病，需要更多关注
        }
    }

    /**
     * 额外加分计算
     */
    private double calculateBonusScore(User user, Pet pet) {
        double bonusScore = 0.0;

        if (user != null && user.getAgreeScientificCare() != null && user.getAgreeScientificCare() == 1) {
            bonusScore += 30.0;
        }

        if (user != null && user.getHasBalcony() != null && user.getHasBalcony() == 1) {
            bonusScore += 20.0;
        }

        if (pet != null && pet.getIsVaccinated() != null && pet.getIsVaccinated() == 1) {
            bonusScore += 25.0;
        }

        if (pet != null && pet.getIsNeutered() != null && pet.getIsNeutered() == 1) {
            bonusScore += 25.0;
        }

        return Math.min(100.0, bonusScore);
    }

    @Override
    public List<MatchingRecord> getRecommendedPets(Long userId, Integer limit) {
        QueryWrapper<MatchingRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .orderByDesc("matching_score")
               .last("LIMIT " + limit);
        return list(wrapper);
    }

    @Override
    @Transactional
    public void generateRecommendations(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId 不能为空");
        }

        // 1. 获取用户信息
        User user = userService.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在: " + userId);
        }

        // 2. 清理旧的匹配记录（先删除，避免重复）
        clearOldMatchings(userId);

        // 3. 查询所有待领养宠物（根据业务字段筛选，例：status == 0 表示可领养）
        QueryWrapper<Pet> petWrapper = new QueryWrapper<>();
        petWrapper.eq("status", 0);
        List<Pet> pets = petMapper.selectList(petWrapper);

        if (pets == null || pets.isEmpty()) {
            return; // 没有待领养宠物，直接返回
        }

        // 4. 计算匹配分并构建 MatchingRecord 列表
        List<MatchingRecord> records = new ArrayList<>(pets.size());
        LocalDateTime now = LocalDateTime.now();

        for (Pet pet : pets) {
            double score = calculateMatchingScore(user, pet);

            Map<String, Object> details = new HashMap<>();
            details.put("residenceScore", calculateResidenceScore(user, pet));
            details.put("experienceScore", calculateExperienceScore(user, pet));
            details.put("familyScore", calculateFamilyScore(user, pet));
            details.put("healthScore", calculateHealthScore(user, pet));
            details.put("bonusScore", calculateBonusScore(user, pet));

            MatchingRecord record = MatchingRecord.builder()
                    .userId(userId)
                    .petId(pet.getId())
                    .matchingScore(Math.min(100.0, score))
                    .matchingDetails(JSON.toJSONString(details))
                    .isPushed(0)
                    .isClicked(0)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            records.add(record);
        }

        // 5. 批量保存匹配记录（ServiceImpl 提供 saveBatch 方法）
        if (!records.isEmpty()) {
            this.saveBatch(records);
        }
    }

    @Override
    public boolean recordUserClick(Long matchingRecordId) {
        MatchingRecord record = getById(matchingRecordId);
        if (record == null) {
            throw new RuntimeException("匹配记录不存在");
        }

        record.setIsClicked(1);
        record.setUpdatedAt(LocalDateTime.now());

        return updateById(record);
    }

    @Override
    public List<MatchingRecord> getMatchedUsers(Long petId, Integer limit) {
        QueryWrapper<MatchingRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("pet_id", petId)
               .orderByDesc("matching_score")
               .last("LIMIT " + limit);
        return list(wrapper);
    }

    @Override
    public void clearOldMatchings(Long userId) {
        QueryWrapper<MatchingRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        remove(wrapper);
    }
}
