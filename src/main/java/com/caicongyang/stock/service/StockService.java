package com.caicongyang.stock.service;

import com.caicongyang.stock.domain.TTransactionStockDTO;
import com.caicongyang.stock.domain.TVolumeIncrease;
import com.caicongyang.stock.domain.TVolumeIncreaseDTO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface StockService {

    /**
     * a.当天是否交易
     *
     * @return
     */
    Boolean TradeFlag();



    public double getCurrentGain(String currentDate, String preTradingDate, String stockCode) throws ParseException;


    /**
     * b. 2：15根据当天成交量  捕捉异常股票数据
     * *
     */
    List<Map<String, Object>> catchTransactionStockData(String currentDate) throws Exception;


    /**
     * 获取当天的异动股票数据
     * *
     */
    List<TVolumeIncreaseDTO> getTransactionStockData(String currentDate) throws Exception;

    /**
     * 查询时间间隔的股票异动数据
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> getIntervalTransactionStockData(String startDate, String endDate);


    List<TVolumeIncreaseDTO> getTransactionAndClose2TenDayAvgStockData(String currentDate);


    /**
     * c.抓取异常股票的版块分布和概念分布
     */


    /**
     * d.抓取当天的交易数据
     */

    /**
     *  d.19：00 根据当天成交量，捕捉异常股票数据
     */


    /**
     * f.发送版本或者概念版块具体的股票代码到邮箱
     *
     */


}
