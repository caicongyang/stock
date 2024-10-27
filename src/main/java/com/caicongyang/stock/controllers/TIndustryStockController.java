package com.caicongyang.stock.controllers;

import com.caicongyang.core.basic.Result;
import com.caicongyang.stock.domain.TIndustryStock;
import com.caicongyang.stock.service.ITIndustryStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/industry-stock")
@Api(tags = "行业股票关联接口")
public class TIndustryStockController {

    @Autowired
    private ITIndustryStockService industryStockService;

    @GetMapping("/list")
    @ApiOperation("获取行业股票关联列表")
    public Result<List<TIndustryStock>> list() {
        return Result.ok(industryStockService.list());
    }

    @GetMapping("/{stockCode}/{industryCode}")
    @ApiOperation("根据股票代码和行业代码获取关联信息")
    public Result<TIndustryStock> getByStockAndIndustry(@PathVariable String stockCode, @PathVariable String industryCode) {
        return Result.ok(industryStockService.getById(stockCode + "," + industryCode));
    }

    @PostMapping
    @ApiOperation("添加行业股票关联")
    public Result<Boolean> add(@RequestBody TIndustryStock industryStock) {
        return Result.ok(industryStockService.save(industryStock));
    }

    @PutMapping
    @ApiOperation("更新行业股票关联")
    public Result<Boolean> update(@RequestBody TIndustryStock industryStock) {
        return Result.ok(industryStockService.updateById(industryStock));
    }

    @DeleteMapping("/{stockCode}/{industryCode}")
    @ApiOperation("删除行业股票关联")
    public Result<Boolean> delete(@PathVariable String stockCode, @PathVariable String industryCode) {
        return Result.ok(industryStockService.removeById(stockCode + "," + industryCode));
    }
}
