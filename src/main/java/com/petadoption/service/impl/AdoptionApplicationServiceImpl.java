package com.petadoption.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petadoption.mapper.AdoptionApplicationMapper;
import com.petadoption.model.entity.AdoptionApplication;
import com.petadoption.service.AdoptionApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 领养申请服务实现类
 */
@Service
public class AdoptionApplicationServiceImpl extends ServiceImpl<AdoptionApplicationMapper, AdoptionApplication>
        implements AdoptionApplicationService {

    @Autowired
    private AdoptionApplicationMapper adoptionApplicationMapper;

    @Override
    public AdoptionApplication submitApplication(AdoptionApplication application) {
        application.setStatus(0); // 待审核状态
        application.setCreatedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());
        save(application);
        return application;
    }

    @Override
    public List<AdoptionApplication> getUserApplications(Long userId) {
        QueryWrapper<AdoptionApplication> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .orderByDesc("created_at");
        return list(wrapper);
    }

    @Override
    public List<AdoptionApplication> getPetApplications(Long petId) {
        QueryWrapper<AdoptionApplication> wrapper = new QueryWrapper<>();
        wrapper.eq("pet_id", petId)
               .orderByDesc("created_at");
        return list(wrapper);
    }

    @Override
    public boolean approveApplication(Long applicationId, Long reviewerId, String comment) {
        AdoptionApplication application = getById(applicationId);
        if (application == null) {
            throw new RuntimeException("申请不存在");
        }

        application.setStatus(1); // 已同意
        application.setReviewerId(reviewerId);
        application.setReviewComment(comment);
        application.setReviewedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());

        return updateById(application);
    }

    @Override
    public boolean rejectApplication(Long applicationId, Long reviewerId, String comment) {
        AdoptionApplication application = getById(applicationId);
        if (application == null) {
            throw new RuntimeException("申请不存在");
        }

        application.setStatus(2); // 已拒绝
        application.setReviewerId(reviewerId);
        application.setReviewComment(comment);
        application.setReviewedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());

        return updateById(application);
    }

    @Override
    public boolean completeAdoption(Long applicationId) {
        AdoptionApplication application = getById(applicationId);
        if (application == null) {
            throw new RuntimeException("申请不存在");
        }

        application.setStatus(3); // 已完成
        application.setCompletedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());

        return updateById(application);
    }

    @Override
    public List<AdoptionApplication> getPendingApplications() {
        QueryWrapper<AdoptionApplication> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0) // 待审核状态
               .orderByAsc("created_at");
        return list(wrapper);
    }

    @Override
    public Integer countUserAdoptions(Long userId) {
        QueryWrapper<AdoptionApplication> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .eq("status", 3); // 已完成的领养
        return (int) count(wrapper);
    }
}
