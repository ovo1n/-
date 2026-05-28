package com.petadoption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 流浪宠物救助与领养智能管理平台 - 主启动类
 */
@SpringBootApplication
@MapperScan("com.petadoption.mapper")
public class PetAdoptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetAdoptionApplication.class, args);
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║    流浪宠物救助与领养智能管理平台启动成功！                  ║");
        System.out.println("║    Pet Adoption Platform started successfully!                ║");
        System.out.println("║    http://localhost:8080/api                                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}