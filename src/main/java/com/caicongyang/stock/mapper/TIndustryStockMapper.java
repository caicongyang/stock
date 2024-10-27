package com.caicongyang.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caicongyang.stock.domain.TIndustryStock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TIndustryStockMapper extends BaseMapper<TIndustryStock> {
    // 可以添加自定义的SQL方法
}
