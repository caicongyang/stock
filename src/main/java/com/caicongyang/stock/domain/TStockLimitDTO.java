package com.caicongyang.stock.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author caicongyang
 * @since 2022-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "TStockLimit DTO", description = "")
public class TStockLimitDTO extends TStockLimit implements Serializable {

    private String stockName;


    private Integer counter;

    private String industryName;

    //main_net_inflow_3d
    private Double mainNetInflow3d;

    private  String fundFlow;

    private String turnoverRate;

}
