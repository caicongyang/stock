package com.caicongyang.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caicongyang.stock.domain.TVolumeIncrease;

import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 成交量显著增加的股票表 Mapper 接口
 * </p>
 *
 * @author caicongyang
 * @since 2024-10-27
 */
@Mapper
public interface TVolumeIncreaseMapper extends BaseMapper<TVolumeIncrease> {
    // 可以添加自定义的SQL方法
}
