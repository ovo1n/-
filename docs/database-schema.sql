-- 流浪宠物救助与领养智能管理平台 - 数据库设计脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS pet_adoption DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE pet_adoption;

-- 1. 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL UNIQUE COMMENT '用户名',
  `phone_number` varchar(20) NOT NULL UNIQUE COMMENT '手机号',
  `password` varchar(255) NOT NULL COMMENT '密码（加密存储）',
  `real_name` varchar(50) COMMENT '用户姓名',
  `avatar` varchar(255) COMMENT '用户头像URL',
  `email` varchar(100) COMMENT '邮箱',
  `role` int DEFAULT 1 COMMENT '用户角色: 1-普通用户 2-救助人 3-管理员',
  `status` int DEFAULT 1 COMMENT '账户状态: 0-禁用 1-启用',
  `residence_type` int COMMENT '住所类型: 1-楼房 2-平房 3-别墅',
  `has_balcony` int COMMENT '是否有阳台: 0-无 1-有',
  `pet_experience` int COMMENT '养宠经验: 1-无 2-1-3年 3-3-5年 4-5年以上',
  `family_size` int COMMENT '家庭人口数',
  `agree_scientific_care` int COMMENT '是否同意科学养宠: 0-否 1-是',
  `id_number` varchar(18) COMMENT '身份证号',
  `residence_proof` varchar(255) COMMENT '居住证明URL',
  `bio` text COMMENT '用户简介',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_login_at` datetime COMMENT '最后登录时间',
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`),
  KEY `idx_phone_number` (`phone_number`),
  KEY `idx_role` (`role`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 2. 宠物表
CREATE TABLE IF NOT EXISTS `pet` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '宠物ID',
  `name` varchar(50) NOT NULL COMMENT '宠物名称',
  `species` int NOT NULL COMMENT '宠物品种: 1-犬 2-猫 3-其他',
  `breed` varchar(100) COMMENT '具体品种',
  `gender` int COMMENT '性别: 1-公 2-母',
  `age_in_months` int COMMENT '年龄（月）',
  `size` int COMMENT '体型: 1-小型 2-中型 3-大型',
  `personality` varchar(255) COMMENT '性格特征（多个用逗号分隔）',
  `health_status` int DEFAULT 1 COMMENT '健康状况: 1-健康 2-有小病 3-有大病',
  `breeding_difficulty` int DEFAULT 1 COMMENT '饲养难度: 1-容易 2-中等 3-困难',
  `photo_url` varchar(255) COMMENT '宠物照片URL',
  `description` text COMMENT '宠物描述',
  `rescue_location` varchar(255) COMMENT '救助地点',
  `rescuer_id` bigint COMMENT '救助人ID',
  `status` int DEFAULT 0 COMMENT '宠物状态: 0-待审核 1-待领养 2-已领养 3-已下架',
  `is_vaccinated` int DEFAULT 0 COMMENT '是否已疫苗接种: 0-否 1-是',
  `is_neutered` int DEFAULT 0 COMMENT '是否已绝育: 0-否 1-是',
  `qr_code` varchar(255) COMMENT '宠物二维码',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `published_at` datetime COMMENT '发布时间',
  PRIMARY KEY (`id`),
  KEY `idx_species` (`species`),
  KEY `idx_status` (`status`),
  KEY `idx_rescuer_id` (`rescuer_id`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='宠物表';

-- 3. 领养申请表
CREATE TABLE IF NOT EXISTS `adoption_application` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '领养申请ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `pet_id` bigint NOT NULL COMMENT '宠物ID',
  `status` int DEFAULT 0 COMMENT '申请状态: 0-待审核 1-已同意 2-已驳回 3-已完成',
  `reason` text COMMENT '申请原因',
  `commitment` text COMMENT '养宠承诺',
  `residence_attachment` varchar(255) COMMENT '居住证明附件URL',
  `commitment_attachment` varchar(255) COMMENT '养宠承诺附件URL',
  `other_attachment` varchar(255) COMMENT '其他附件URL',
  `review_comment` text COMMENT '审核意见',
  `reviewer_id` bigint COMMENT '审核人ID',
  `reviewed_at` datetime COMMENT '审核时间',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `completed_at` datetime COMMENT '完成时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_pet_id` (`pet_id`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  FOREIGN KEY (`pet_id`) REFERENCES `pet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='领养申请表';

-- 4. 匹配记录表
CREATE TABLE IF NOT EXISTS `matching_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '匹配记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `pet_id` bigint NOT NULL COMMENT '宠物ID',
  `matching_score` double DEFAULT 0 COMMENT '匹配度分数（0-100）',
  `matching_details` json COMMENT '详细匹配评分JSON',
  `is_pushed` int DEFAULT 0 COMMENT '是否已推送: 0-否 1-是',
  `pushed_at` datetime COMMENT '推送时间',
  `is_clicked` int DEFAULT 0 COMMENT '用户是否点击: 0-未点击 1-已点击',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_pet_id` (`pet_id`),
  KEY `idx_matching_score` (`matching_score`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  FOREIGN KEY (`pet_id`) REFERENCES `pet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='匹配记录表';

-- 5. 领养回访记录表
CREATE TABLE IF NOT EXISTS `follow_up_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '回访记录ID',
  `adoption_application_id` bigint NOT NULL COMMENT '领养申请ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `pet_id` bigint NOT NULL COMMENT '宠物ID',
  `follow_up_date` datetime NOT NULL COMMENT '回访时间',
  `follow_up_by_id` bigint COMMENT '回访人（管理员）ID',
  `pet_status` text COMMENT '宠物当前状态描述',
  `adoption_status` text COMMENT '用户养宠情况',
  `health_score` int COMMENT '宠物健康状况评分（1-10）',
  `satisfaction_score` int COMMENT '用户满意度评分（1-10）',
  `remark` text COMMENT '回访备注',
  `attachment_url` varchar(255) COMMENT '照片/视频URL',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_adoption_application_id` (`adoption_application_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_follow_up_date` (`follow_up_date`),
  FOREIGN KEY (`adoption_application_id`) REFERENCES `adoption_application` (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  FOREIGN KEY (`pet_id`) REFERENCES `pet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='领养回访记录表';

-- 创建索引以优化查询性能
CREATE INDEX idx_pet_rescuer_created ON pet(rescuer_id, created_at);
CREATE INDEX idx_adoption_user_status ON adoption_application(user_id, status);
CREATE INDEX idx_matching_user_score ON matching_record(user_id, matching_score DESC);