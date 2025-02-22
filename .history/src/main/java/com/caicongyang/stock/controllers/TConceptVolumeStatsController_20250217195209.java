package com.caicongyang.stock.controllers;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.caicongyang.core.basic.Result;
import com.caicongyang.stock.domain.TConceptVolumeStats;
import com.caicongyang.stock.mapper.CommonMapper;
import com.caicongyang.stock.service.ITConceptVolumeStatsService;
import com.caicongyang.stock.utils.TomDateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/concept-volume-stats")
@Api(tags = "概念成交量统计接口")
public class TConceptVolumeStatsController {

    @Autowired
    private ITConceptVolumeStatsService conceptVolumeStatsService;
    
    @Autowired
    private CommonMapper commonMapper;

    @GetMapping("/list")
    @ApiOperation("获取概念成交量统计列表")
    public Result<List<TConceptVolumeStats>> list(
            @ApiParam("交易日期，格式：yyyy-MM-dd，不传则获取最近一个交易日数据") 
            @RequestParam(required = false) String tradeDate) throws ParseException {
        
        // 如果没有传入日期，获取最近一个交易日
        if (tradeDate == null || tradeDate.isEmpty()) {
            tradeDate = commonMapper.queryLastTradingDate();
        }
        
        // 构建查询条件
        LambdaQueryWrapper<TConceptVolumeStats> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TConceptVolumeStats::getTradeDate, TomDateUtil.formateDayPattern2Date(tradeDate));
        
        // 按平均增幅降序排序
        queryWrapper.orderByDesc(TConceptVolumeStats::getAvgIncrease);
        
        return Result.ok(conceptVolumeStatsService.list(queryWrapper));
    }

    @GetMapping("/{conceptName}/{tradeDate}")
    @ApiOperation("根据概念名称和交易日期获取统计信息")
    public Result<TConceptVolumeStats> getById(@PathVariable String conceptName, @PathVariable String tradeDate) throws ParseException {
        // 构建查询条件
        return Result.ok(conceptVolumeStatsService.lambdaQuery()
                .eq(TConceptVolumeStats::getConceptName, conceptName)
                .eq(TConceptVolumeStats::getTradeDate, TomDateUtil.formateDayPattern2Date(tradeDate))
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
    public Result<Boolean> delete(@PathVariable String conceptName, @PathVariable String tradeDate) throws ParseException {
        return Result.ok(conceptVolumeStatsService.lambdaUpdate()
                .eq(TConceptVolumeStats::getConceptName, conceptName)
                .eq(TConceptVolumeStats::getTradeDate, TomDateUtil.formateDayPattern2Date(tradeDate))
                .remove());
    }
} 