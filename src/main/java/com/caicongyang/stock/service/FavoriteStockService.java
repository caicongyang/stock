package com.caicongyang.stock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caicongyang.stock.domain.FavoriteStock;
import java.time.LocalDateTime;

public interface FavoriteStockService {
    IPage<FavoriteStock> getPage(String symbol, LocalDateTime date, int pageNum, int pageSize);
    boolean addFavorite(FavoriteStock stock);
    boolean removeFavorite(Long id);
    void updateStockInfo(FavoriteStock stock);
} 