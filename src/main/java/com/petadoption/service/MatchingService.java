package com.petadoption.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petadoption.model.entity.MatchingRecord;
import com.petadoption.model.entity.Pet;
import com.petadoption.model.entity.User;
import java.util.List;

/**
 * 智能匹配服务接口
 */
public interface MatchingService extends IService<MatchingRecord> {

    /**
     * 计算用户与宠物的匹配度
     */
    Double calculateMatchingScore(User user, Pet pet);

    /**
     * 获取用户的推荐宠物列表（按匹配度排序）
     */
    List<MatchingRecord> getRecommendedPets(Long userId, Integer limit);

    /**
     * 为用户执行匹配推荐（批量计算所有待领养宠物的匹配度）
     */
    void generateRecommendations(Long userId);

    /**
     * 记录用户点击推荐宠物的行为
     */
    boolean recordUserClick(Long matchingRecordId);

    /**
     * 获取宠物的匹配用户列表
     */
    List<MatchingRecord> getMatchedUsers(Long petId, Integer limit);

    /**
     * 删除用户的旧匹配记录
     */
    void clearOldMatchings(Long userId);
}
