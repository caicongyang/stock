package com.caicongyang.stock.controllers;

import com.caicongyang.stock.domain.TVolumeIncrease;
import com.caicongyang.stock.service.impl.ITVolumeIncreaseService;
import com.caicongyang.core.basic.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController
@RequestMapping("/volume-increase")
@Api(tags = "成交量显著增加的股票接口")
public class TVolumeIncreaseController {

    @Autowired
    private ITVolumeIncreaseService volumeIncreaseService;

    @GetMapping("/list")
    @ApiOperation("获取成交量显著增加的股票列表")
    public Result<List<TVolumeIncrease>> list() {
        return Result.ok(volumeIncreaseService.list());
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID获取成交量显著增加的股票")
    public Result<TVolumeIncrease> getById(@PathVariable Integer id) {
        return Result.ok(volumeIncreaseService.getById(id));
    }

    @PostMapping
    @ApiOperation("添加成交量显著增加的股票")
    public Result<Boolean> add(@RequestBody TVolumeIncrease volumeIncrease) {
        return Result.ok(volumeIncreaseService.save(volumeIncrease));
    }

    @PutMapping
    @ApiOperation("更新成交量显著增加的股票")
    public Result<Boolean> update(@RequestBody TVolumeIncrease volumeIncrease) {
        return Result.ok(volumeIncreaseService.updateById(volumeIncrease));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除成交量显著增加的股票")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.ok(volumeIncreaseService.removeById(id));
    }
}
