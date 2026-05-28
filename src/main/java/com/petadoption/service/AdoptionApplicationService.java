package com.petadoption.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petadoption.model.entity.AdoptionApplication;
import java.util.List;

/**
 * 领养申请服务接口
 */
public interface AdoptionApplicationService extends IService<AdoptionApplication> {

    /**
     * 提交领养申请
     */
    AdoptionApplication submitApplication(AdoptionApplication application);

    /**
     * 查询用户的领养申请
     */
    List<AdoptionApplication> getUserApplications(Long userId);

    /**
     * 查询宠物的领养申请
     */
    List<AdoptionApplication> getPetApplications(Long petId);

    /**
     * 审核通过申请
     */
    boolean approveApplication(Long applicationId, Long reviewerId, String comment);

    /**
     * 拒绝申请
     */
    boolean rejectApplication(Long applicationId, Long reviewerId, String comment);

    /**
     * 完成领养（生成回访任务）
     */
    boolean completeAdoption(Long applicationId);

    /**
     * 查询待审核的申请
     */
    List<AdoptionApplication> getPendingApplications();

    /**
     * 统计用户的领养数量
     */
    Integer countUserAdoptions(Long userId);
}
