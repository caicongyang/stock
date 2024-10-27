package com.caicongyang.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caicongyang.stock.domain.TIndustryStock;
import com.caicongyang.stock.mapper.TIndustryStockMapper;
import com.caicongyang.stock.service.ITIndustryStockService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;


/**
 * <p>
 * 行业股票关联表 服务实现类
 * </p>
 *
 * @author caicongyang
 * @since 2024-10-27
 */
@Service
public class TIndustryStockServiceImpl extends ServiceImpl<TIndustryStockMapper, TIndustryStock> implements ITIndustryStockService {
    @Override
    public TIndustryStock getByStockCode(String stockCode) {
        return this.getOne(new QueryWrapper<TIndustryStock>().eq("stock_code", stockCode));
    }
}
