package com.petadoption.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petadoption.model.entity.FollowUpRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 回访记录Mapper接口
 */
@Mapper
public interface FollowUpRecordMapper extends BaseMapper<FollowUpRecord> {
}