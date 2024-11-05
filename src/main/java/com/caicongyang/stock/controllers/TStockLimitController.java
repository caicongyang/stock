package com.caicongyang.stock.controllers;


import com.caicongyang.core.basic.Result;
import com.caicongyang.stock.domain.TStockLimitDTO;
import com.caicongyang.stock.domain.TransactionAndLimitStockDTO;
import com.caicongyang.stock.service.ITStockLimitService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caicongyang
 * @since 2022-06-03
 */
@RestController
@RequestMapping("/t-stock-limit")
public class TStockLimitController {


    @Resource
    ITStockLimitService limitService;

    @GetMapping("/catchLimitStock")
    @ApiOperation(value = "获取当天的涨停股票数据", notes = "获取当天的涨停股票数据")
    public @ResponseBody
    Result<Object> getTransactionStockData(@RequestParam(required = false, value = "currentDate") String currentDate)
            throws Exception {
        limitService.catchAllDaliyLimitStock(currentDate);
        return Result.ok();
    }


    @GetMapping("/getIntervalLimitStockData")
    @ApiOperation(value = "获取当天的涨停股票数据", notes = "获取当天的涨停股票数据")
    public @ResponseBody
    Result<List<TStockLimitDTO>> getIntervalLimitStockData(@RequestParam(required = false, value = "startDate") String startDate,
                                                           @RequestParam(required = false, value = "endDate") String endDate)
            throws Exception {
        List<TStockLimitDTO> intervalLimitStockData = limitService.getIntervalLimitStockData(startDate, endDate);
        List<TStockLimitDTO> collect = intervalLimitStockData.stream().filter(a -> !a.getStockCode().startsWith("8")).collect(Collectors.toList());
        return Result.ok(collect);
    }


    @GetMapping("/getLimitAndTransactionStock")
    @ApiOperation(value = "获取当天的涨停&异动的股票数据", notes = "获取当天的涨停&异动的股票数据")
    public @ResponseBody
    Result<List<TStockLimitDTO>> getLimitAndTransactionStockStock(@RequestParam(value = "currentDate") String currentDate)
            throws Exception {
        List<TStockLimitDTO> limitAndTransactionStockStock = limitService.getLimitAndTransactionStockStock(currentDate);
        List<TStockLimitDTO> collect = limitAndTransactionStockStock.stream().filter(a -> !a.getStockCode().startsWith("8")).collect(Collectors.toList());
        return Result.ok(collect);
    }


    @GetMapping("/getTransactionStockStockAndLimit")
    @ApiOperation(value = "获取当天的异动股票&30日内涨停的股票数据", notes = "获取当天的异动股票&30日内涨停的股票数据")
    public @ResponseBody
    Result<List<TransactionAndLimitStockDTO>> getTransactionStockStockAndLimit(@RequestParam(value = "currentDate") String currentDate)
            throws Exception {
        List<TransactionAndLimitStockDTO> transactionStockStockAndLimit = limitService.getTransactionStockStockAndLimit(currentDate);
        List<TransactionAndLimitStockDTO> collect = transactionStockStockAndLimit.stream().filter(a -> !a.getStockCode().startsWith("8")).collect(Collectors.toList());
        return Result.ok(collect);

    }



}
