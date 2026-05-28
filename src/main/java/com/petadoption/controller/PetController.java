package com.petadoption.controller;

import com.petadoption.model.dto.ApiResponse;
import com.petadoption.model.entity.Pet;
import com.petadoption.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 宠物控制层
 */
@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    /**
     * 获取待领养宠物列表（分页）
     */
    @GetMapping("/pending")
    public ApiResponse<List<Pet>> getPendingAdoptionPets(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            List<Pet> pets = petService.getPendingAdoptionPets(pageNum, pageSize);
            return ApiResponse.success(pets, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 按品种查询宠物
     */
    @GetMapping("/species/{species}")
    public ApiResponse<List<Pet>> getPetsBySpecies(@PathVariable Integer species) {
        try {
            List<Pet> pets = petService.getPetsBySpecies(species);
            return ApiResponse.success(pets, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 按体型查询宠物
     */
    @GetMapping("/size/{size}")
    public ApiResponse<List<Pet>> getPetsBySize(@PathVariable Integer size) {
        try {
            List<Pet> pets = petService.getPetsBySize(size);
            return ApiResponse.success(pets, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 搜索宠物
     */
    @GetMapping("/search")
    public ApiResponse<List<Pet>> searchPets(@RequestParam String keyword) {
        try {
            List<Pet> pets = petService.searchPets(keyword);
            return ApiResponse.success(pets, "搜索成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 获取热门宠物
     */
    @GetMapping("/popular")
    public ApiResponse<List<Pet>> getPopularPets(@RequestParam(defaultValue = "10") Integer limit) {
        try {
            List<Pet> pets = petService.getPopularPets(limit);
            return ApiResponse.success(pets, "查询成功");
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 获取宠物详情
     */
    @GetMapping("/{petId}")
    public ApiResponse<Pet> getPetInfo(@PathVariable Long petId) {
        try {
            Pet pet = petService.getById(petId);
            if (pet == null) {
                return ApiResponse.badRequest("宠物不存在");
            }
            return ApiResponse.success(pet);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 创建宠物信息（救助人）
     */
    @PostMapping
    public ApiResponse<Pet> createPet(@RequestBody Pet pet) {
        try {
            Pet createdPet = petService.createPetWithQRCode(pet);
            return ApiResponse.success(createdPet, "宠物信息已提交审核");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 更新宠物信息
     */
    @PutMapping("/{petId}")
    public ApiResponse<Void> updatePet(@PathVariable Long petId, @RequestBody Pet pet) {
        try {
            pet.setId(petId);
            boolean success = petService.updateById(pet);
            if (success) {
                return ApiResponse.success(null, "更新成功");
            } else {
                return ApiResponse.badRequest("更新失败");
            }
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage());
        }
    }

    /**
     * 发布宠物（审核通过）
     */
    @PostMapping("/{petId}/publish")
    public ApiResponse<Void> publishPet(@PathVariable Long petId) {
        try {
            boolean success = petService.publishPet(petId);
            if (success) {
                return ApiResponse.success(null, "宠物已发布");
            } else {
                return ApiResponse.badRequest("发布失败");
            }
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * 下架宠物
     */
    @PostMapping("/{petId}/remove")
    public ApiResponse<Void> removePet(@PathVariable Long petId) {
        try {
            boolean success = petService.removePet(petId);
            if (success) {
                return ApiResponse.success(null, "宠物已下架");
            } else {
                return ApiResponse.badRequest("下架失败");
            }
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
