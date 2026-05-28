package com.petadoption.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 领养回访记录实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("follow_up_record")
public class FollowUpRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 领养申请ID */
    private Long adoptionApplicationId;

    /** 用户ID */
    private Long userId;

    /** 宠物ID */
    private Long petId;

    /** 回访时间 */
    private LocalDateTime followUpDate;

    /** 回访人（管理员）ID */
    private Long followUpById;

    /** 宠物当前状态描述 */
    private String petStatus;

    /** 用户养宠情况 */
    private String adoptionStatus;

    /** 宠物健康状况评分（1-10） */
    private Integer healthScore;

    /** 用户满意度评分（1-10） */
    private Integer satisfactionScore;

    /** 回访备注 */
    private String remark;

    /** 照片/视频URL */
    private String attachmentUrl;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;
}