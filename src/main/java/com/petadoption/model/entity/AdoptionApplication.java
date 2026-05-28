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
 * 领养申请实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("adoption_application")
public class AdoptionApplication {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 宠物ID */
    private Long petId;

    /** 申请状态: 0-待审核 1-已同意 2-已驳回 3-已完成 */
    private Integer status;

    /** 申请原因 */
    private String reason;

    /** 养宠承诺 */
    private String commitment;

    /** 居住证明附件URL */
    private String residenceAttachment;

    /** 养宠承诺附件URL */
    private String commitmentAttachment;

    /** 其他附件URL */
    private String otherAttachment;

    /** 审核意见 */
    private String reviewComment;

    /** 审核人ID */
    private Long reviewerId;

    /** 审核时间 */
    private LocalDateTime reviewedAt;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;

    /** 完成时间 */
    private LocalDateTime completedAt;
}