package com.petadoption.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petadoption.model.entity.Pet;
import java.util.List;

/**
 * 宠物服务接口
 */
public interface PetService extends IService<Pet> {

    /**
     * 分页查询待领养的宠物
     */
    List<Pet> getPendingAdoptionPets(Integer pageNum, Integer pageSize);

    /**
     * 按品种查询宠物
     */
    List<Pet> getPetsBySpecies(Integer species);

    /**
     * 按体型查询宠物
     */
    List<Pet> getPetsBySize(Integer size);

    /**
     * 搜索宠物（按名称或品种）
     */
    List<Pet> searchPets(String keyword);

    /**
     * 创建宠物并生成二维码
     */
    Pet createPetWithQRCode(Pet pet);

    /**
     * 发布宠物（审核通过后发布）
     */
    boolean publishPet(Long petId);

    /**
     * 下架宠物
     */
    boolean removePet(Long petId);

    /**
     * 获取热门宠物（收藏最多）
     */
    List<Pet> getPopularPets(Integer limit);
}
