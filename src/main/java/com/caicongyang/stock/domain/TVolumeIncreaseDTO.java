package com.caicongyang.stock.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TVolumeIncreaseDTO extends TVolumeIncrease {
    private String industryCode;
    private String industryName;
    private String stockName;
    //main_net_inflow_3d
    private Double mainNetInflow3d;

    private  String fundFlow;

    private String turnoverRate;

    // 如果父类 TVolumeIncrease 没有这个方法，请添加它
    public Double getVolumeIncreaseRatio() {
        return super.getVolumeIncreaseRatio();
    }
}
