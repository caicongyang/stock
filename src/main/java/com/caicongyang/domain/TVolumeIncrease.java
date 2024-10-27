package com.caicongyang.domain;

import java.time.LocalDate;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 成交量显著增加的股票表
 * </p>
 *
 * @author caicongyang
 * @since 2024-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TVolumeIncrease对象", description="成交量显著增加的股票表")
public class TVolumeIncrease implements Serializable {

    private static final long serialVersionUID = 1L;

    private String stockCode;

    private LocalDate tradeDate;

    private Double volumeIncreaseRatio;

    private Double close;

    private Double open;

    private Double high;

    private Double low;


}
