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
 * 宠物实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pet")
public class Pet {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 宠物名称 */
    private String name;

    /** 宠物品种: 1-犬 2-猫 3-其他 */
    private Integer species;

    /** 具体品种 */
    private String breed;

    /** 性别: 1-公 2-母 */
    private Integer gender;

    /** 年龄（月） */
    private Integer ageInMonths;

    /** 体型: 1-小型 2-中型 3-大型 */
    private Integer size;

    /** 性格特征（多个用逗号分隔） */
    private String personality;

    /** 健康状况: 1-健康 2-有小病 3-有大病 */
    private Integer healthStatus;

    /** 饲养难度: 1-容易 2-中等 3-困难 */
    private Integer breedingDifficulty;

    /** 宠物照片URL */
    private String photoUrl;

    /** 宠物描述 */
    private String description;

    /** 救助地点 */
    private String rescueLocation;

    /** 救助人ID */
    private Long rescuerId;

    /** 宠物状态: 0-待审核 1-待领养 2-已领养 3-已下架 */
    private Integer status;

    /** 是否已疫苗接种: 0-否 1-是 */
    private Integer isVaccinated;

    /** 是否已绝育: 0-否 1-是 */
    private Integer isNeutered;

    /** 宠物二维码 */
    private String qrCode;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;

    /** 发布时间 */
    private LocalDateTime publishedAt;
}