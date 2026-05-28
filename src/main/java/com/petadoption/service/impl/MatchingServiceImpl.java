package com.petadoption.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petadoption.mapper.MatchingRecordMapper;
import com.petadoption.mapper.PetMapper;
import com.petadoption.model.entity.MatchingRecord;
import com.petadoption.model.entity.Pet;
import com.petadoption.model.entity.User;
import com.petadoption.service.MatchingService;
import com.petadoption.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
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
        if (user.getFamilySize() == null) {
            return 50.0;
        }

        // 宠物性格中包含"温和"、"友好"等字样时，适合大家庭
        String personality = pet.getPersonality() != null ? pet.getPersonality().toLowerCase() : "";

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
     * 健康状况匹配度计算
     */
    private double calculateHealthScore(User user, Pet pet) {
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

        // 加分项1：用户同意科学养宠
        if (user.getAgreeScientificCare() != null && user.getAgreeScientificCare() == 1) {
            bonusScore += 30.0;
        }

        // 加分项2：用户有阳台
        if (user.getHasBalcony() != null && user.getHasBalcony() == 1) {
            bonusScore += 20.0;
        }

        // 加分项3：宠物已疫苗接种
        if (pet.getIsVaccinated() != null && pet.getIsVaccinated() == 1) {
            bonusScore += 25.0;
        }

        // 加分项4：宠物已绝育
        if (pet.getIsNeutered() != null && pet.getIsNeutered() == 1) {
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
    public void generateRecommendations(Long userId) {
        // 清空旧的匹配记录
        clearOldMatchings(userId);

        // 这里应该调用UserService和PetService获取用户和宠物信息
        // 为简化，这里仅作为接口框架
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
