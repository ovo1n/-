package com.petadoption.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 */
public class DateUtils {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 获取当前时间
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 格式化日期时间
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DEFAULT_FORMATTER);
    }

    /**
     * 格式化日期
     */
    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

    /**
     * 格式化时间
     */
    public static String formatTime(LocalDateTime dateTime) {
        return dateTime.format(TIME_FORMATTER);
    }

    /**
     * 计算两个日期之间的天数
     */
    public static long daysBetween(LocalDateTime start, LocalDateTime end) {
        return java.time.temporal.ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 检查日期是否为今天
     */
    public static boolean isToday(LocalDateTime dateTime) {
        return dateTime.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }
}
