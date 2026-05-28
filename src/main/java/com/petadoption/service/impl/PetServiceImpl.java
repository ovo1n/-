package com.petadoption.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petadoption.mapper.PetMapper;
import com.petadoption.model.entity.Pet;
import com.petadoption.service.PetService;
import com.petadoption.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 宠物服务实现类
 */
@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @Override
    public List<Pet> getPendingAdoptionPets(Integer pageNum, Integer pageSize) {
        QueryWrapper<Pet> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1) // 待领养状态
               .orderByDesc("published_at")
               .last("LIMIT " + (pageNum - 1) * pageSize + "," + pageSize);
        return list(wrapper);
    }

    @Override
    public List<Pet> getPetsBySpecies(Integer species) {
        QueryWrapper<Pet> wrapper = new QueryWrapper<>();
        wrapper.eq("species", species)
               .eq("status", 1);
        return list(wrapper);
    }

    @Override
    public List<Pet> getPetsBySize(Integer size) {
        QueryWrapper<Pet> wrapper = new QueryWrapper<>();
        wrapper.eq("size", size)
               .eq("status", 1);
        return list(wrapper);
    }

    @Override
    public List<Pet> searchPets(String keyword) {
        QueryWrapper<Pet> wrapper = new QueryWrapper<>();
        wrapper.and(w -> w.like("name", keyword)
                         .or()
                         .like("breed", keyword))
               .eq("status", 1);
        return list(wrapper);
    }

    @Override
    public Pet createPetWithQRCode(Pet pet) {
        pet.setStatus(0); // 待审核状态
        pet.setCreatedAt(LocalDateTime.now());
        pet.setUpdatedAt(LocalDateTime.now());

        save(pet);

        // 生成二维码
        String qrCodeData = "pet:" + pet.getId();
        String qrCodeUrl = qrCodeGenerator.generateQRCode(qrCodeData);
        pet.setQrCode(qrCodeUrl);
        updateById(pet);

        return pet;
    }

    @Override
    public boolean publishPet(Long petId) {
        Pet pet = getById(petId);
        if (pet == null) {
            throw new RuntimeException("宠物不存在");
        }

        pet.setStatus(1); // 待领养状态
        pet.setPublishedAt(LocalDateTime.now());
        pet.setUpdatedAt(LocalDateTime.now());

        return updateById(pet);
    }

    @Override
    public boolean removePet(Long petId) {
        Pet pet = getById(petId);
        if (pet == null) {
            throw new RuntimeException("宠物不存在");
        }

        pet.setStatus(3); // 已下架状态
        pet.setUpdatedAt(LocalDateTime.now());

        return updateById(pet);
    }

    @Override
    public List<Pet> getPopularPets(Integer limit) {
        QueryWrapper<Pet> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1)
               .orderByDesc("created_at")
               .last("LIMIT " + limit);
        return list(wrapper);
    }
}
