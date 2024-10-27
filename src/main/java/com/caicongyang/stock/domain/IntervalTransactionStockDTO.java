package com.caicongyang.stock.domain;

import lombok.Data;

import java.util.Date;

@Data
public class IntervalTransactionStockDTO {
    private Long counter;
    private String stockCode;
    private String stockName;
    private String industryName;
    private Date tradeDate;
}
