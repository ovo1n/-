package com.petadoption.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petadoption.mapper.FollowUpRecordMapper;
import com.petadoption.model.entity.FollowUpRecord;
import com.petadoption.service.FollowUpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 领养回访记录服务实现类
 */
@Service
public class FollowUpRecordServiceImpl extends ServiceImpl<FollowUpRecordMapper, FollowUpRecord>
        implements FollowUpRecordService {

    @Autowired
    private FollowUpRecordMapper followUpRecordMapper;

    @Override
    public FollowUpRecord createFollowUpRecord(FollowUpRecord record) {
        record.setCreatedAt(LocalDateTime.now());
        record.setUpdatedAt(LocalDateTime.now());
        if (record.getFollowUpDate() == null) {
            record.setFollowUpDate(LocalDateTime.now());
        }
        save(record);
        return record;
    }

    @Override
    public List<FollowUpRecord> getUserFollowUpRecords(Long userId) {
        QueryWrapper<FollowUpRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .orderByDesc("follow_up_date");
        return list(wrapper);
    }

    @Override
    public List<FollowUpRecord> getPetFollowUpRecords(Long petId) {
        QueryWrapper<FollowUpRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("pet_id", petId)
               .orderByDesc("follow_up_date");
        return list(wrapper);
    }

    @Override
    public List<FollowUpRecord> getPendingFollowUpPets() {
        // 实现需要回访的宠物列表
        QueryWrapper<FollowUpRecord> wrapper = new QueryWrapper<>();
        wrapper.isNull("follow_up_date")
               .orderByAsc("created_at");
        return list(wrapper);
    }

    @Override
    public Double getAverageSatisfactionScore() {
        QueryWrapper<FollowUpRecord> wrapper = new QueryWrapper<>();
        wrapper.select("AVG(satisfaction_score) as avg_score");
        
        List<FollowUpRecord> records = list(wrapper);
        if (records != null && !records.isEmpty()) {
            FollowUpRecord record = records.get(0);
            if (record.getSatisfactionScore() != null) {
                return record.getSatisfactionScore().doubleValue();
            }
        }
        return 0.0;
    }
}
