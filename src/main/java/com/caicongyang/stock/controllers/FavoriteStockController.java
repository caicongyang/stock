package com.caicongyang.stock.controllers;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caicongyang.core.basic.Result;
import com.caicongyang.stock.domain.FavoriteStock;
import com.caicongyang.stock.service.FavoriteStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/favorite-stock")
public class FavoriteStockController {
    @Autowired
    private FavoriteStockService favoriteStockService;

    @GetMapping("/page")
    public Result<IPage<FavoriteStock>> getPage(
            @RequestParam(required = false) String symbol,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime date,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        return Result.ok(favoriteStockService.getPage(symbol, date, pageNum, pageSize));
    }

    @PostMapping
    public Result<Boolean> addFavorite(@RequestBody FavoriteStock stock) {
        return Result.ok(favoriteStockService.addFavorite(stock));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> removeFavorite(@PathVariable Long id) {
        return Result.ok(favoriteStockService.removeFavorite(id));
    }
} 