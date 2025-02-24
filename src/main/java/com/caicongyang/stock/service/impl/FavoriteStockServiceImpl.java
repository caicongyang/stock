package com.caicongyang.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caicongyang.stock.domain.FavoriteStock;
import com.caicongyang.stock.mapper.FavoriteStockMapper;
import com.caicongyang.stock.service.FavoriteStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class FavoriteStockServiceImpl implements FavoriteStockService {
    @Autowired
    private FavoriteStockMapper favoriteStockMapper;

    @Override
    public IPage<FavoriteStock> getPage(String symbol, LocalDateTime date, int pageNum, int pageSize) {
        LambdaQueryWrapper<FavoriteStock> queryWrapper = new LambdaQueryWrapper<>();
        
        // 添加股票代码查询条件
        if (symbol != null && !symbol.isEmpty()) {
            queryWrapper.like(FavoriteStock::getSymbol, symbol);
        }
        
        // 添加日期查询条件
        if (date != null) {
            queryWrapper.ge(FavoriteStock::getCreateTime, date);
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(FavoriteStock::getCreateTime);
        
        return favoriteStockMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @Override
    public boolean addFavorite(FavoriteStock stock) {
        // 检查是否已经存在
        if (isStockExists(stock.getSymbol())) {
            throw new RuntimeException("该股票已在自选列表中");
        }
        
        // 设置时间
        LocalDateTime now = LocalDateTime.now();
        stock.setCreateTime(now);
        stock.setUpdateTime(now);
        
        return favoriteStockMapper.insert(stock) > 0;
    }

    @Override
    public boolean removeFavorite(Long id) {
        return favoriteStockMapper.deleteById(id) > 0;
    }

    @Override
    public void updateStockInfo(FavoriteStock stock) {
        stock.setUpdateTime(LocalDateTime.now());
        favoriteStockMapper.updateById(stock);
    }

    private boolean isStockExists(String symbol) {
        return favoriteStockMapper.selectCount(
            new LambdaQueryWrapper<FavoriteStock>()
                .eq(FavoriteStock::getSymbol, symbol)
        ) > 0;
    }
} 