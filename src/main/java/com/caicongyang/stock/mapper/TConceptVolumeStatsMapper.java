package com.caicongyang.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caicongyang.stock.domain.TConceptVolumeStats;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TConceptVolumeStatsMapper extends BaseMapper<TConceptVolumeStats> {
    // 可以添加自定义的SQL方法
} 