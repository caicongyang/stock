package com.caicongyang.stock.controllers;

import com.caicongyang.core.basic.Result;
import com.caicongyang.stock.domain.StockTerm;
import com.caicongyang.stock.service.StockTermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/stock/term")
@CrossOrigin  // 添加跨域支持
@Api(tags = "交易词条管理接口")
public class StockTermController {
    @Autowired
    private StockTermService stockTermService;

    @GetMapping("/list")
    @ApiOperation("获取词条列表")
    public Result<List<StockTerm>> list() {
        return Result.ok(stockTermService.listAllTerms());
    }

    @GetMapping("/wordcloud")
    @ApiOperation("获取词云数据")
    public Result<List<StockTerm>> getWordCloudData() {
        return Result.ok(stockTermService.getWordCloudData());
    }

    @PostMapping
    @ApiOperation("添加词条")
    public Result<Boolean> addTerm(@RequestBody StockTerm term) {
        return Result.ok(stockTermService.addTerm(term));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除词条")
    public Result<Boolean> deleteTerm(@ApiParam("词条ID") @PathVariable Long id) {
        return Result.ok(stockTermService.deleteTerm(id));
    }
} 