package com.caicongyang.stock.controllers;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.caicongyang.core.basic.Result;
import com.caicongyang.stock.domain.TLimitUpConceptStats;
import com.caicongyang.stock.mapper.CommonMapper;
import com.caicongyang.stock.service.ITLimitUpConceptStatsService;
import com.caicongyang.stock.utils.TomDateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/limit-up-concept-stats")
@Api(tags = "涨停概念统计接口")
public class TLimitUpConceptStatsController {

    @Autowired
    private ITLimitUpConceptStatsService limitUpConceptStatsService;
    
    @Autowired
    private CommonMapper commonMapper;

    @GetMapping("/list")
    @ApiOperation("获取涨停概念统计列表")
    public Result<List<TLimitUpConceptStats>> list(
            @ApiParam("交易日期，格式：yyyy-MM-dd，不传则获取最近一个交易日数据") 
            @RequestParam(required = false) String tradeDate) throws ParseException {
        
        // 如果没有传入日期，获取最近一个交易日
        if (tradeDate == null || tradeDate.isEmpty()) {
            tradeDate = commonMapper.queryLastTradingDate();
        }
        
        // 构建查询条件
        LambdaQueryWrapper<TLimitUpConceptStats> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TLimitUpConceptStats::getTradeDate, TomDateUtil.formateDayPattern2Date(tradeDate));
        
        // 按涨停股票数量降序排序
        queryWrapper.orderByDesc(TLimitUpConceptStats::getStockCount);
        
        return Result.ok(limitUpConceptStatsService.list(queryWrapper));
    }

    @GetMapping("/{conceptName}/{tradeDate}")
    @ApiOperation("根据概念名称和交易日期获取统计信息")
    public Result<TLimitUpConceptStats> getById(
            @PathVariable String conceptName, 
            @PathVariable String tradeDate) throws ParseException {
        return Result.ok(limitUpConceptStatsService.lambdaQuery()
                .eq(TLimitUpConceptStats::getConceptName, conceptName)
                .eq(TLimitUpConceptStats::getTradeDate, TomDateUtil.formateDayPattern2Date(tradeDate))
                .one());
    }

    @PostMapping
    @ApiOperation("添加涨停概念统计")
    public Result<Boolean> add(@RequestBody TLimitUpConceptStats limitUpConceptStats) {
        return Result.ok(limitUpConceptStatsService.save(limitUpConceptStats));
    }

    @PutMapping
    @ApiOperation("更新涨停概念统计")
    public Result<Boolean> update(@RequestBody TLimitUpConceptStats limitUpConceptStats) {
        return Result.ok(limitUpConceptStatsService.updateById(limitUpConceptStats));
    }

    @DeleteMapping("/{conceptName}/{tradeDate}")
    @ApiOperation("删除涨停概念统计")
    public Result<Boolean> delete(
            @PathVariable String conceptName, 
            @PathVariable String tradeDate) throws ParseException {
        return Result.ok(limitUpConceptStatsService.lambdaUpdate()
                .eq(TLimitUpConceptStats::getConceptName, conceptName)
                .eq(TLimitUpConceptStats::getTradeDate, TomDateUtil.formateDayPattern2Date(tradeDate))
                .remove());
    }
} 