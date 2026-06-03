-- db_setup.sql
-- (仅用于本地或运维执行) 创建数据库与用户，并授权
-- 生产环境请不要直接使用示例密码，务必更换并限制访问来源

CREATE DATABASE IF NOT EXISTS `petadopt` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'pet_user'@'%' IDENTIFIED BY 'pet_password';
GRANT ALL PRIVILEGES ON `petadopt`.* TO 'pet_user'@'%';
FLUSH PRIVILEGES;

-- 如果需要 root 登录验证：
-- mysql -uroot -p < db_setup.sql
