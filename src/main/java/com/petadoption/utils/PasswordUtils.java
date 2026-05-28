package com.petadoption.utils;

import org.springframework.util.DigestUtils;

/**
 * 密码工具类
 */
public class PasswordUtils {

    /**
     * MD5加密密码
     */
    public static String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 验证密码
     */
    public static boolean verifyPassword(String rawPassword, String encryptedPassword) {
        String encrypted = encryptPassword(rawPassword);
        return encrypted.equals(encryptedPassword);
    }

    /**
     * 生成随机密码
     */
    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return password.toString();
    }
}
