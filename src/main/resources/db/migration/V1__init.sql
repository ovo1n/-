-- V1__init.sql (Flyway migration)
-- 初始表结构：users, pets, matching_record, adoption_application, follow_up_record
-- 请在 src/main/resources/db/migration/ 下保存该文件，启动应用时 Flyway 会执行（prod profile）

-- users 表
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  display_name VARCHAR(200),
  email VARCHAR(200),
  phone VARCHAR(50),
  residence_type TINYINT,       -- 1=apartment,2=house,3=villa (示例)
  pet_experience TINYINT,      -- 1=none,2=1-3yr,3=3-5yr,4=>5yr
  family_size TINYINT,
  agree_scientific_care TINYINT DEFAULT 0, -- 1=yes
  has_balcony TINYINT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- pets 表
CREATE TABLE IF NOT EXISTS pets (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200),
  breed VARCHAR(200),
  size TINYINT,               -- 1=small,2=medium,3=large
  age INT,
  personality VARCHAR(500),
  breeding_difficulty TINYINT, -- 1=easy,2=medium,3=hard
  health_status TINYINT,      -- 1=healthy,2=minor,3=major
  is_vaccinated TINYINT DEFAULT 0,
  is_neutered TINYINT DEFAULT 0,
  image_url VARCHAR(1000),
  status TINYINT DEFAULT 0,   -- 0=pending adoption,1=adopted,2=removed
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- matching_record 表
CREATE TABLE IF NOT EXISTS matching_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  pet_id BIGINT NOT NULL,
  matching_score DOUBLE,
  matching_details JSON,
  is_pushed TINYINT DEFAULT 0,
  is_clicked TINYINT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_user (user_id),
  INDEX idx_pet (pet_id),
  CONSTRAINT fk_matching_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_matching_pet FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- adoption_application 表
CREATE TABLE IF NOT EXISTS adoption_application (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  pet_id BIGINT NOT NULL,
  status TINYINT DEFAULT 0, -- 0=submitted,1=approved,2=rejected
  comment VARCHAR(1000),
  reviewer_id BIGINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_app_user (user_id),
  INDEX idx_app_pet (pet_id),
  CONSTRAINT fk_app_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_app_pet FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- follow_up_record 表
CREATE TABLE IF NOT EXISTS follow_up_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  application_id BIGINT,
  user_id BIGINT,
  pet_id BIGINT,
  content VARCHAR(2000),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_follow_app (application_id),
  CONSTRAINT fk_follow_application FOREIGN KEY (application_id) REFERENCES adoption_application(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 可按需添加更多索引与外键（生产请调整约束策略）
