package com.caicongyang.stock.controllers;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caicongyang.core.basic.Result;
import com.caicongyang.stock.domain.TradingLog;
import com.caicongyang.stock.service.TradingLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trading-log")
@Api(tags = "交易复盘日志接口")
public class TradingLogController {
    @Autowired
    private TradingLogService tradingLogService;

    @GetMapping("/page")
    @ApiOperation("分页获取复盘日志")
    public Result<IPage<TradingLog>> getPage(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.ok(tradingLogService.getPage(pageNum, pageSize));
    }

    @GetMapping("/{id}")
    @ApiOperation("获取复盘日志详情")
    public Result<TradingLog> getById(@ApiParam("日志ID") @PathVariable Long id) {
        return Result.ok(tradingLogService.getById(id));
    }

    @PostMapping
    @ApiOperation("新增复盘日志")
    public Result<Boolean> save(@RequestBody TradingLog log) {
        return Result.ok(tradingLogService.save(log));
    }

    @PutMapping
    @ApiOperation("更新复盘日志")
    public Result<Boolean> update(@RequestBody TradingLog log) {
        return Result.ok(tradingLogService.update(log));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除复盘日志")
    public Result<Boolean> delete(@ApiParam("日志ID") @PathVariable Long id) {
        return Result.ok(tradingLogService.delete(id));
    }
} 