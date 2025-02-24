package com.caicongyang.stock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caicongyang.stock.domain.TradingLog;

public interface TradingLogService {
    IPage<TradingLog> getPage(int pageNum, int pageSize);
    TradingLog getById(Long id);
    boolean save(TradingLog log);
    boolean update(TradingLog log);
    boolean delete(Long id);
} 