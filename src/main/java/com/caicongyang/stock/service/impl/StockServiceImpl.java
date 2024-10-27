package com.caicongyang.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.caicongyang.httper.HttpClientProvider;
import com.caicongyang.stock.domain.*;
import com.caicongyang.stock.mail.MailService;
import com.caicongyang.stock.mapper.CommonMapper;
import com.caicongyang.stock.mapper.TStockMapper;
import com.caicongyang.stock.mapper.TTransactionStockMapper;
import com.caicongyang.stock.service.ITStockMainService;
import com.caicongyang.stock.service.StockService;
import com.caicongyang.stock.service.ITIndustryStockService;
import com.caicongyang.stock.utils.TomDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 *
 */
@Service
public class StockServiceImpl implements StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);


    private static String apiUrl = "https://dataapi.joinquant.com/apis";


    private static List<String> senderList = Arrays.asList("1491318829@qq.com", "lmxiels@163.com", "wy545777485@163.com");

    @Autowired
    HttpClientProvider provider;
    @Resource
    protected CommonMapper commonMapper;


    @Resource
    protected TStockMapper tStockMapper;

    @Resource
    protected TTransactionStockMapper tTransactionStockMapper;


    @Autowired
    private ITStockMainService itStockMainService;


    @Autowired
    private ITVolumeIncreaseService volumeIncreaseService;


    @Autowired
    private MailService mailService;


    @Autowired
    private ITIndustryStockService industryStockService;


    @Override
    public Boolean TradeFlag() {
        TStock stock = new TStock();
        stock.setTradeDate(new Date());
        Wrapper<TStock> wrapper = new QueryWrapper<>(stock);
        return CollectionUtils.isEmpty(tStockMapper.selectList(wrapper)) ? false : true;
    }

    @Override
    public List<Map<String, Object>> catchTransactionStockData(String currentDate) throws Exception {

        currentDate = commonMapper.queryTradingDate(currentDate);

        String preTradingDate = commonMapper.queryPreTradingDate(currentDate);

        HashMap map = new HashMap();

        map.put("currentDate", currentDate);
        map.put("preDate", preTradingDate);
        List<Map<String, Object>> maps = commonMapper.queryTransactionStock(map);

        for (Map<String, Object> item : maps) {
            String stockCode = (String) item.get("stock_code");
            TTransactionStock stock = new TTransactionStock();
            stock.setStockCode(stockCode.substring(0,6));
            stock.setLastDayCompare(((BigDecimal) item.get("last_day_compare")).doubleValue());
            stock.setMeanRatio(((BigDecimal) item.get("mean_ratio")).doubleValue());
            stock.setTradingDay(currentDate);
            stock.setGain(((Double) item.get("pct_chg")).doubleValue());
//            TStockMain main = itStockMainService
//                    .getIndustryByStockCode(stockCode);
//            if (null != main) {
//                stock.setJqL2(main.getJqL2());
//                stock.setSwL3(main.getSwL3());
//                stock.setZjw(main.getZjw());
//            }
//
//            double v = getCurrentGain(currentDate, preTradingDate, stockCode);
//            stock.setGain(v);
            try {
                tTransactionStockMapper.insert(stock);
            } catch (Exception e) {
                logger.error("插入异常股票错误", e);
            }

        }


//        for (String mail : senderList) {
//            mailService.sendSimpleMail(mail, currentDate + "异动股票", JsonUtils.jsonFromObject(maps));
//        }

        return maps;
    }

    @Deprecated
    public double getCurrentGain(String currentDate, String preTradingDate, String stockCode) throws ParseException {
        // 查询今日股价信息
        TStock currentStock = new TStock();
        currentStock.setTradeDate(TomDateUtil.formateDayPattern2Date(currentDate));
        currentStock.setStockCode(stockCode);
        Wrapper<TStock> currentWrapper = new QueryWrapper<>(currentStock);
        currentStock = tStockMapper.selectOne(currentWrapper);


        // 查询上一个交易日股价信息
        TStock preDayStock = new TStock();
        preDayStock.setTradeDate(TomDateUtil.formateDayPattern2Date(preTradingDate));
        preDayStock.setStockCode(stockCode);
        Wrapper<TStock> wrapper = new QueryWrapper<>(preDayStock);
        preDayStock = tStockMapper.selectOne(wrapper);


        if (Objects.nonNull(currentStock)  &&  Objects.nonNull(preDayStock) &&  Objects.nonNull(preDayStock.getClose()) && Objects.nonNull(currentStock.getClose())) {
            return (currentStock.getClose() - preDayStock.getClose()) / preDayStock.getClose();
        } else {
            return 0.0d;
        }
    }

    @Override
    public List<TVolumeIncreaseDTO> getTransactionStockData(String currentDate) throws Exception {
        List<TVolumeIncreaseDTO> result = commonMapper.getTransactionStockDataWithIndustry(currentDate);

        if (CollectionUtils.isEmpty(result)) {
            String lastTradingDate = commonMapper.queryLastTradingDate();
            result = commonMapper.getTransactionStockDataWithIndustry(lastTradingDate);
        }

        // 处理可能的空值
        for (TVolumeIncreaseDTO dto : result) {
            if (dto.getIndustryName() == null) {
                dto.setIndustryName("未分类");
            }
        }

        return result;
    }


    @Override
    public List<Map<String, Object>> getIntervalTransactionStockData(String startDate, String endDate) {
        HashMap<String, String> param = new HashMap<>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return commonMapper.getIntervalTransactionStockData(param);
    }

    @Override
    public List<TVolumeIncreaseDTO> getTransactionAndClose2TenDayAvgStockData(String currentDate) {
        List<TVolumeIncreaseDTO> result = commonMapper.getTransactionAndClose2TenDayAvgStockData(currentDate);

        if (CollectionUtils.isEmpty(result)) {
            String lastTradingDate = commonMapper.queryLastTradingDate();
            result = commonMapper.getTransactionAndClose2TenDayAvgStockData(lastTradingDate);
        }
        return  result;
    }


}
