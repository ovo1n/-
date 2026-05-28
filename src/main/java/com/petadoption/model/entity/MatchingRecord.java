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
 * 宠物-领养人匹配记录实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("matching_record")
public class MatchingRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 宠物ID */
    private Long petId;

    /** 匹配度分数（0-100） */
    private Double matchingScore;

    /** 详细匹配评分JSON */
    private String matchingDetails;

    /** 是否已推送: 0-否 1-是 */
    private Integer isPushed;

    /** 推送时间 */
    private LocalDateTime pushedAt;

    /** 用户是否点击: 0-未点击 1-已点击 */
    private Integer isClicked;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;
}