package com.caicongyang.stock.controllers;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.caicongyang.core.basic.Result;
import com.caicongyang.stock.domain.TLimitUpConceptDetails;
import com.caicongyang.stock.mapper.CommonMapper;
import com.caicongyang.stock.service.ITLimitUpConceptDetailsService;
import com.caicongyang.stock.utils.TomDateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/limit-up-concept-details")
@Api(tags = "涨停概念详情接口")
public class TLimitUpConceptDetailsController {

    @Autowired
    private ITLimitUpConceptDetailsService limitUpConceptDetailsService;
    
    @Autowired
    private CommonMapper commonMapper;

    @GetMapping("/list")
    @ApiOperation("获取涨停概念详情列表")
    public Result<List<TLimitUpConceptDetails>> list(
            @ApiParam("交易日期，格式：yyyy-MM-dd，不传则获取最近一个交易日数据") 
            @RequestParam(required = false) String tradeDate,
            @ApiParam("概念名称") 
            @RequestParam(required = false) String conceptName) throws ParseException {
        
        // 如果没有传入日期，获取最近一个交易日
        if (tradeDate == null || tradeDate.isEmpty()) {
            tradeDate = commonMapper.queryLastTradingDate();
        }
        
        // 构建查询条件
        LambdaQueryWrapper<TLimitUpConceptDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TLimitUpConceptDetails::getTradeDate, TomDateUtil.formateDayPattern2Date(tradeDate));
        
        // 如果传入了概念名称，则按概念名称筛选
        if (conceptName != null && !conceptName.isEmpty()) {
            queryWrapper.eq(TLimitUpConceptDetails::getConceptName, conceptName);
        }
        
        // 按涨跌幅降序排序
        queryWrapper.orderByDesc(TLimitUpConceptDetails::getPctChg);
        
        return Result.ok(limitUpConceptDetailsService.list(queryWrapper));
    }
} 