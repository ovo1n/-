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
 * 用户实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户名 */
    private String username;

    /** 手机号 */
    private String phoneNumber;

    /** 密码（加密存储） */
    private String password;

    /** 用户姓名 */
    private String realName;

    /** 用户头像 */
    private String avatar;

    /** 邮箱 */
    private String email;

    /** 用户角色: 1-普通用户 2-救助人 3-管理员 */
    private Integer role;

    /** 账户状态: 0-禁用 1-启用 */
    private Integer status;

    /** 住所类型: 1-楼房 2-平房 3-别墅 */
    private Integer residenceType;

    /** 是否有阳台: 0-无 1-有 */
    private Integer hasBalcony;

    /** 养宠经验: 1-无 2-1-3年 3-3-5年 4-5年以上 */
    private Integer petExperience;

    /** 家庭人口数 */
    private Integer familySize;

    /** 是否同意科学养宠: 0-否 1-是 */
    private Integer agreeScientificCare;

    /** 身份证号 */
    private String idNumber;

    /** 居住证明URL */
    private String residenceProof;

    /** 用户简介 */
    private String bio;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;

    /** 最后登录时间 */
    private LocalDateTime lastLoginAt;
}