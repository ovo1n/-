package com.petadoption.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petadoption.model.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

/**
 * 宠物Mapper接口
 */
@Mapper
public interface PetMapper extends BaseMapper<Pet> {
}