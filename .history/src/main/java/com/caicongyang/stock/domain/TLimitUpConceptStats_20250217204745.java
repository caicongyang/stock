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
@TableName("t_limit_up_concept_stats")
@ApiModel(value="TLimitUpConceptStats对象", description="涨停概念统计表")
public class TLimitUpConceptStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "概念名称")
    private String conceptName;

    @ApiModelProperty(value = "分析日期")
    private Date tradeDate;

    @ApiModelProperty(value = "涨停股票数量")
    private Integer stockCount;

    @ApiModelProperty(value = "平均涨幅")
    private BigDecimal avgIncrease;

    @ApiModelProperty(value = "最大涨幅")
    private BigDecimal maxIncrease;
    
    @ApiModelProperty(value = "总成交量")
    private BigDecimal totalVolume;
} 