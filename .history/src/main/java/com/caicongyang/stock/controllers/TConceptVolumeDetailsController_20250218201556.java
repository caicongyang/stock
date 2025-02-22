package com.caicongyang.stock.controllers;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.caicongyang.core.basic.Result;
import com.caicongyang.stock.domain.TConceptVolumeDetails;
import com.caicongyang.stock.mapper.CommonMapper;
import com.caicongyang.stock.service.ITConceptVolumeDetailsService;
import com.caicongyang.stock.utils.TomDateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/concept-volume-details")
@Api(tags = "概念成交量详情接口")
public class TConceptVolumeDetailsController {

    @Autowired
    private ITConceptVolumeDetailsService conceptVolumeDetailsService;
    
    @Autowired
    private CommonMapper commonMapper;

    @GetMapping("/list")
    @ApiOperation("获取概念成交量详情列表")
    public Result<List<TConceptVolumeDetails>> list(
            @ApiParam("交易日期，格式：yyyy-MM-dd，不传则获取最近一个交易日数据") 
            @RequestParam(required = false) String tradeDate,
            @ApiParam("概念名称") 
            @RequestParam(required = false) String conceptName) throws ParseException {
        
        // 如果没有传入日期，获取最近一个交易日
        if (tradeDate == null || tradeDate.isEmpty()) {
            tradeDate = commonMapper.queryLastTradingDate();
        }
        
        // 构建查询条件
        LambdaQueryWrapper<TConceptVolumeDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TConceptVolumeDetails::getTradeDate, TomDateUtil.formateDayPattern2Date(tradeDate));
        
        // 如果传入了概念名称，则按概念名称筛选
        if (conceptName != null && !conceptName.isEmpty()) {
            queryWrapper.eq(TConceptVolumeDetails::getConceptName, conceptName);
        }
        
        // 按成交量增幅降序排序
        queryWrapper.orderByDesc(TConceptVolumeDetails::getVolumeIncreaseRatio);
        
        return Result.ok(conceptVolumeDetailsService.list(queryWrapper));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID获取详情")
    public Result<TConceptVolumeDetails> getById(@PathVariable Integer id) {
        return Result.ok(conceptVolumeDetailsService.getById(id));
    }

    @GetMapping("/stock/{stockCode}")
    @ApiOperation("获取指定股票的概念成交量详情")
    public Result<List<TConceptVolumeDetails>> getByStockCode(
            @PathVariable String stockCode,
            @ApiParam("交易日期，格式：yyyy-MM-dd，不传则获取最近一个交易日数据") 
            @RequestParam(required = false) String tradeDate) throws ParseException {
        
        if (tradeDate == null || tradeDate.isEmpty()) {
            tradeDate = commonMapper.queryLastTradingDate();
        }
        
        return Result.ok(conceptVolumeDetailsService.lambdaQuery()
                .eq(TConceptVolumeDetails::getStockCode, stockCode)
                .eq(TConceptVolumeDetails::getTradeDate, TomDateUtil.formateDayPattern2Date(tradeDate))
                .list());
    }

    @PostMapping
    @ApiOperation("添加概念成交量详情")
    public Result<Boolean> add(@RequestBody TConceptVolumeDetails conceptVolumeDetails) {
        return Result.ok(conceptVolumeDetailsService.save(conceptVolumeDetails));
    }

    @PutMapping
    @ApiOperation("更新概念成交量详情")
    public Result<Boolean> update(@RequestBody TConceptVolumeDetails conceptVolumeDetails) {
        return Result.ok(conceptVolumeDetailsService.updateById(conceptVolumeDetails));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除概念成交量详情")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.ok(conceptVolumeDetailsService.removeById(id));
    }
} 