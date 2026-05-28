package com.petadoption.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petadoption.model.entity.MatchingRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 匹配记录Mapper接口
 */
@Mapper
public interface MatchingRecordMapper extends BaseMapper<MatchingRecord> {
}