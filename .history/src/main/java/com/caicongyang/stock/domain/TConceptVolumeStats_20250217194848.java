package com.caicongyang.stock.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_concept_volume_stats")
@ApiModel(value="TConceptVolumeStats对象", description="概念成交量统计表")
public class TConceptVolumeStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "概念名称")
    private String conceptName;

    @ApiModelProperty(value = "分析日期")
    private Date tradeDate;

    @ApiModelProperty(value = "涉及股票数量")
    private Integer stockCount;

    @ApiModelProperty(value = "平均成交量增幅(倍)")
    private BigDecimal avgIncrease;

    @ApiModelProperty(value = "最大成交量增幅(倍)")
    private BigDecimal maxIncrease;
} 