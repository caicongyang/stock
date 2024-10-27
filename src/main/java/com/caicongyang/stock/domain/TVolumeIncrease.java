package com.caicongyang.stock.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_volume_increase")
@ApiModel(value="TVolumeIncrease对象", description="成交量显著增加的股票表")
public class TVolumeIncrease implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "股票代码")
    private String stockCode;

    @ApiModelProperty(value = "交易日期")
    private Date tradeDate;

    @ApiModelProperty(value = "成交量增加比率")
    private Double volumeIncreaseRatio;

    @ApiModelProperty(value = "收盘价")
    private Double close;

    @ApiModelProperty(value = "开盘价")
    private Double open;

    @ApiModelProperty(value = "最高价")
    private Double high;

    @ApiModelProperty(value = "最低价")
    private Double low;
}
