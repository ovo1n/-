package com.petadoption.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petadoption.model.entity.FollowUpRecord;
import java.util.List;

/**
 * 领养回访记录服务接口
 */
public interface FollowUpRecordService extends IService<FollowUpRecord> {

    /**
     * 创建回访记录
     */
    FollowUpRecord createFollowUpRecord(FollowUpRecord record);

    /**
     * 获取用户的回访记录
     */
    List<FollowUpRecord> getUserFollowUpRecords(Long userId);

    /**
     * 获取宠物的回访记录
     */
    List<FollowUpRecord> getPetFollowUpRecords(Long petId);

    /**
     * 获取需要回访的宠物列表（领养完成后30天未回访）
     */
    List<FollowUpRecord> getPendingFollowUpPets();

    /**
     * 统计平均满意度评分
     */
    Double getAverageSatisfactionScore();
}
