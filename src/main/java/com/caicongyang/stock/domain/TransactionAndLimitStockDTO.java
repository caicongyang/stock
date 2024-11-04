package com.caicongyang.stock.domain;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@ApiModel(value = "异动&30日内涨停对象", description = "")
public class TransactionAndLimitStockDTO implements Serializable {

    private String stockCode;

    private Date tradeDate;

    private Double volumeIncreaseRatio;


    private String stockName;


    private Integer counter;

    private  String industryName;


    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Double getVolumeIncreaseRatio() {
        return volumeIncreaseRatio;
    }

    public void setVolumeIncreaseRatio(Double volumeIncreaseRatio) {
        this.volumeIncreaseRatio = volumeIncreaseRatio;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }


    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}
