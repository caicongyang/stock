package com.caicongyang.stock.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_industry_stock")
@ApiModel(value="TIndustryStock对象", description="行业股票关联表")
public class TIndustryStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行业代码")
    private String industryCode;

    @ApiModelProperty(value = "行业名称")
    private String industryName;

    @ApiModelProperty(value = "股票代码")
    private String stockCode;

    @ApiModelProperty(value = "股票名称")
    private String stockName;
}
