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
        queryWrapper.eq(TConceptVolumeStats::getTradeDate, TomDateUtil.formateDayPattern2Date(tradeDate))
                // 先按涉及股票数量降序
                .orderByDesc(TConceptVolumeStats::getStockCount)
                // 再按平均增幅降序
                .orderByDesc(TConceptVolumeStats::getAvgIncrease);
        
        return Result.ok(conceptVolumeStatsService.list(queryWrapper));
    }

} 