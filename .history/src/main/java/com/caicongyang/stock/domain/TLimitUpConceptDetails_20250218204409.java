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
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_limit_up_concept_details")
@ApiModel(value="TLimitUpConceptDetails对象", description="涨停概念详情表")
public class TLimitUpConceptDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "自增主键")
    private Integer id;

    @ApiModelProperty(value = "分析日期")
    private Date tradeDate;

    @ApiModelProperty(value = "概念名称")
    private String conceptName;

    @ApiModelProperty(value = "股票代码")
    private String stockCode;

    @ApiModelProperty(value = "股票名称")
    private String stockName;

    @ApiModelProperty(value = "涨跌幅")
    private BigDecimal pctChg;

    @ApiModelProperty(value = "收盘价")
    private BigDecimal close;
    
    @ApiModelProperty(value = "成交量")
    private BigDecimal volume;
} 