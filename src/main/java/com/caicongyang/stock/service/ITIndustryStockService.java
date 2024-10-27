package com.caicongyang.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.caicongyang.stock.domain.TIndustryStock;

/**
 * <p>
 * 行业股票关联表 服务类
 * </p>
 *
 * @author caicongyang
 * @since 2024-10-27
 */
public interface ITIndustryStockService extends IService<TIndustryStock> {
    TIndustryStock getByStockCode(String stockCode);
}
