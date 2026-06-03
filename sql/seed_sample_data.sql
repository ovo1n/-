-- seed_sample_data.sql
-- （用于本地快速验收）向数据库插入少量测试用户和宠物

USE petadopt;

-- 示例用户（密码请按实际加密策略替换，示例明文仅作测试）
INSERT INTO users (username, password, display_name, email, residence_type, pet_experience, family_size, agree_scientific_care, has_balcony)
VALUES
('alice', 'password123', 'Alice', 'alice@example.com', 2, 4, 3, 1, 1),
('bob', 'password123', 'Bob', 'bob@example.com', 1, 1, 1, 0, 0);

-- 示例宠物
INSERT INTO pets (name, breed, size, age, personality, breeding_difficulty, health_status, is_vaccinated, is_neutered, image_url, status)
VALUES
('小白', '比熊犬', 1, 2, '温和 友好', 1, 1, 1, 1, 'https://images.unsplash.com/photo-1587300411107-ec48192ad386?w=400', 0),
('小黑', '泰迪犬', 1, 1, '活泼 好动', 2, 1, 1, 0, 'https://images.unsplash.com/photo-1574158622682-e40e69881006?w=400', 0),
('咪咪', '英短猫', 1, 3, '温顺 乖巧', 1, 1, 0, 1, 'https://images.unsplash.com/photo-1519052537078-e6302a4968d4?w=400', 0);
