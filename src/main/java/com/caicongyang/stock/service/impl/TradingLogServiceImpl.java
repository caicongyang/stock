package com.caicongyang.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caicongyang.stock.domain.TradingLog;
import com.caicongyang.stock.mapper.TradingLogMapper;
import com.caicongyang.stock.service.TradingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TradingLogServiceImpl implements TradingLogService {
    @Autowired
    private TradingLogMapper tradingLogMapper;

    @Override
    public IPage<TradingLog> getPage(int pageNum, int pageSize) {
        return tradingLogMapper.selectPage(
            new Page<>(pageNum, pageSize),
            new LambdaQueryWrapper<TradingLog>()
                .orderByDesc(TradingLog::getDate)
        );
    }

    @Override
    public TradingLog getById(Long id) {
        return tradingLogMapper.selectById(id);
    }

    @Override
    public boolean save(TradingLog log) {
        LocalDateTime now = LocalDateTime.now();
        log.setCreateTime(now);
        log.setUpdateTime(now);
        return tradingLogMapper.insert(log) > 0;
    }

    @Override
    public boolean update(TradingLog log) {
        log.setUpdateTime(LocalDateTime.now());
        return tradingLogMapper.updateById(log) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return tradingLogMapper.deleteById(id) > 0;
    }
} 