package com.petadoption.controller;

import com.petadoption.model.entity.MatchingRecord;
import com.petadoption.model.entity.Pet;
import com.petadoption.service.MatchingService;
import com.petadoption.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 简单的 MVC 控制器，把后端数据填充到 Thymeleaf 模板中
 */
@Controller
public class WebController {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private PetService petService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) Long userId) {
        List<Map<String, Object>> recommendations = new ArrayList<>();

        if (userId != null) {
            try {
                matchingService.generateRecommendations(userId);
            } catch (Exception ignored) {
            }

            List<MatchingRecord> recs = matchingService.getRecommendedPets(userId, 9);
            recommendations = recs.stream().map(r -> {
                Map<String, Object> m = new HashMap<>();
                Pet p = petService.getById(r.getPetId());
                m.put("pet", p);
                m.put("matchingScore", r.getMatchingScore());
                return m;
            }).collect(Collectors.toList());
        } else {
            // guest: show popular pets
            try {
                List<Pet> pets = petService.getPopularPets(9);
                recommendations = pets.stream().map(p -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("pet", p);
                    m.put("matchingScore", null);
                    return m;
                }).collect(Collectors.toList());
            } catch (Exception ignored) {
            }
        }

        // features fallback
        List<Map<String, String>> features = Arrays.asList(
                mapOf("智能匹配", "基于科学算法，为您推荐最适合的宠物伙伴"),
                mapOf("简化申请", "一键提交领养申请，实时查看审核进度"),
                mapOf("贴心回访", "定期回访跟踪，确保您和宠物的生活质量")
        );

        model.addAttribute("recommendations", recommendations);
        model.addAttribute("features", features);
        return "index";
    }

    @GetMapping("/pets/{petId}")
    public String petDetail(@PathVariable Long petId, Model model) {
        Pet pet = petService.getById(petId);
        model.addAttribute("pet", pet);
        return "pet_detail";
    }

    @GetMapping("/apply/{petId}")
    public String applyForm(@PathVariable Long petId, Model model) {
        Pet pet = petService.getById(petId);
        model.addAttribute("pet", pet);
        return "application_form";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/user/profile")
    public String userProfile(@RequestParam(required = false) Long userId, Model model) {
        if (userId != null) {
            model.addAttribute("user", /* lazy: userService.getById(userId) if available */ null);
        }
        return "user_profile";
    }

    private Map<String, String> mapOf(String title, String desc) {
        Map<String, String> m = new HashMap<>();
        m.put("title", title);
        m.put("desc", desc);
        return m;
    }
}
