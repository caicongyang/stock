package com.caicongyang.stock.controllers;

import com.caicongyang.core.basic.Result;
import com.caicongyang.stock.domain.TConceptVolumeStats;
import com.caicongyang.stock.service.ITConceptVolumeStatsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concept-volume-stats")
@Api(tags = "概念成交量统计接口")
public class TConceptVolumeStatsController {

    @Autowired
    private ITConceptVolumeStatsService conceptVolumeStatsService;

    @GetMapping("/list")
    @ApiOperation("获取概念成交量统计列表")
    public Result<List<TConceptVolumeStats>> list() {
        return Result.ok(conceptVolumeStatsService.list());
    }

    @GetMapping("/{conceptName}/{tradeDate}")
    @ApiOperation("根据概念名称和交易日期获取统计信息")
    public Result<TConceptVolumeStats> getById(@PathVariable String conceptName, @PathVariable String tradeDate) {
        // 构建查询条件
        return Result.ok(conceptVolumeStatsService.lambdaQuery()
                .eq(TConceptVolumeStats::getConceptName, conceptName)
                .eq(TConceptVolumeStats::getTradeDate, tradeDate)
                .one());
    }

    @PostMapping
    @ApiOperation("添加概念成交量统计")
    public Result<Boolean> add(@RequestBody TConceptVolumeStats conceptVolumeStats) {
        return Result.ok(conceptVolumeStatsService.save(conceptVolumeStats));
    }

    @PutMapping
    @ApiOperation("更新概念成交量统计")
    public Result<Boolean> update(@RequestBody TConceptVolumeStats conceptVolumeStats) {
        return Result.ok(conceptVolumeStatsService.updateById(conceptVolumeStats));
    }

    @DeleteMapping("/{conceptName}/{tradeDate}")
    @ApiOperation("删除概念成交量统计")
    public Result<Boolean> delete(@PathVariable String conceptName, @PathVariable String tradeDate) {
        return Result.ok(conceptVolumeStatsService.lambdaUpdate()
                .eq(TConceptVolumeStats::getConceptName, conceptName)
                .eq(TConceptVolumeStats::getTradeDate, tradeDate)
                .remove());
    }
} 