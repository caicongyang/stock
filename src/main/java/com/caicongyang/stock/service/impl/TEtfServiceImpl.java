package com.caicongyang.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caicongyang.stock.domain.HighestInPeriodResult;
import com.caicongyang.stock.domain.TEtf;
import com.caicongyang.stock.domain.TEtfHigher;
import com.caicongyang.stock.domain.TEtfHigherDTO;
import com.caicongyang.stock.domain.TTransactionEtf;
import com.caicongyang.stock.domain.TTransactionEtfDTO;
import com.caicongyang.stock.mapper.CommonMapper;
import com.caicongyang.stock.mapper.TEtfHigherMapper;
import com.caicongyang.stock.mapper.TEtfMapper;
import com.caicongyang.stock.mapper.TTransactionEtfMapper;
import com.caicongyang.stock.service.ITEtfHigherService;
import com.caicongyang.stock.service.ITStockMainService;
import com.caicongyang.stock.service.ITEtfService;
import com.caicongyang.stock.utils.TomDateUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caicongyang
 * @since 2020-07-08
 */
@Service
public class TEtfServiceImpl extends ServiceImpl<TEtfMapper, TEtf> implements ITEtfService {

    @Resource
    protected CommonMapper mapper;


    @Resource
    protected TEtfMapper tEtfMapper;

    @Resource
    protected TTransactionEtfMapper tTransactionEtfMapper;


    @Resource
    private ITStockMainService itStockMainService;


    @Resource
    private ITEtfHigherService itEtfHigherService;

    @Resource
    private TEtfHigherMapper etfHigherMapper;


    @Override
    public List<TTransactionEtfDTO> querySortEtfStockData(String currentDate) throws IOException {

        String preTradingDate = mapper.queryPreTradingDate(currentDate);
        List<TTransactionEtfDTO> resultList = new ArrayList<>();
        HashMap queryMap = new HashMap();
        queryMap.put("currentDate", currentDate);
        queryMap.put("preDate", preTradingDate);
        List<Map<String, Object>> maps = tEtfMapper.querySortEtfStockData(queryMap);
        if (CollectionUtils.isEmpty(maps)) {
            //如果当天没有，则获取最近一个交易日
            String lastTradingDate = mapper.queryLastTradingDate();
            preTradingDate = mapper.queryPreTradingDate(lastTradingDate);

            queryMap.put("currentDate", lastTradingDate);
            queryMap.put("preDate", preTradingDate);

            maps = tEtfMapper.querySortEtfStockData(queryMap);
        }

        for (Map map : maps) {
            TTransactionEtfDTO item = new TTransactionEtfDTO();
            item.setStockCode((String) map.getOrDefault("stock_code", ""));
            item.setStockName((String) map.getOrDefault("stock_name", ""));

            item.setLastDayCompare(((BigDecimal) map.getOrDefault("last_day_compare", "")).doubleValue());
            item.setTradingDay(currentDate);
            //item.setStockName(itStockMainService.getStockNameByStockCode(item.getStockCode()));
            resultList.add(item);
        }
        return resultList;
    }

    @Override
    public List<TTransactionEtf> catchTransactionStockData(String currentDate) {
        String preTradingDate = mapper.queryPreTradingDate(currentDate);
        List<TTransactionEtf> resultList = new ArrayList<>();
        HashMap queryMap = new HashMap();
        queryMap.put("currentDate", currentDate);
        queryMap.put("preDate", preTradingDate);
        List<Map<String, Object>> maps = tEtfMapper.catchTransactionEtf(queryMap);
        if (CollectionUtils.isNotEmpty(maps)) {
            for (Map map : maps) {
                TTransactionEtf item = new TTransactionEtf();
                item.setStockCode((String) map.getOrDefault("stock_code", ""));
                item.setLastDayCompare(((BigDecimal) map.getOrDefault("last_day_compare", "")).doubleValue());
                item.setMeanRatio(((BigDecimal) map.get("mean_ratio")).doubleValue());
                item.setTradingDay(currentDate);
                resultList.add(item);
                tTransactionEtfMapper.insert(item);
            }
        }
        return resultList;
    }

    @Override
    public List<TTransactionEtfDTO> getTransactionEtfData(String currentDate) throws IOException {
        TTransactionEtf queryItem = new TTransactionEtf();
        queryItem.setTradingDay(currentDate);

        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper<TTransactionEtf>().eq(true, TTransactionEtf::getTradingDay, currentDate).orderByDesc(true, TTransactionEtf::getLastDayCompare);
        List<TTransactionEtf> result = tTransactionEtfMapper.selectList(lambdaQueryWrapper);
        List<TTransactionEtfDTO> returnList = new ArrayList<>();

        if (CollectionUtils.isEmpty(result)) {
            //如果当天没有，则获取最近一个交易日
            String lastTradingDate = mapper.queryLastTradingDate();
            lambdaQueryWrapper = new LambdaQueryWrapper<TTransactionEtf>().eq(true, TTransactionEtf::getTradingDay, lastTradingDate).orderByDesc(true, TTransactionEtf::getLastDayCompare);
            result = tTransactionEtfMapper.selectList(lambdaQueryWrapper);
        }

        if (CollectionUtils.isNotEmpty(result)) {
            for (TTransactionEtf etf : result) {
                TTransactionEtfDTO dto = new TTransactionEtfDTO();
                BeanUtils.copyProperties(etf, dto);
//                dto.setStockName(itStockMainService.getStockNameByStockCode(etf.getStockCode()));
                returnList.add(dto);
            }
        }
        return returnList;
    }

    @Override
    public void calculateHigherStock(String tradingDay) throws ParseException {
        TEtf etf = new TEtf();
        Date date = TomDateUtil.formateDayPattern2Date(tradingDay);
        etf.setTradeDate(TomDateUtil.date2LocalDate(date));
        QueryWrapper<TEtf> groupByWrapper = new QueryWrapper<>();
        groupByWrapper.setEntity(etf);
        groupByWrapper.groupBy("stock_code");
        groupByWrapper.select("stock_code");
        List<TEtf> tEtfList = tEtfMapper.selectList(groupByWrapper);

        for (TEtf item : tEtfList) {
            String stockCode = item.getStockCode();
            TEtf queryItem = new TEtf();
            QueryWrapper<TEtf> queryByWrapper = new QueryWrapper<>();
            queryItem.setStockCode(stockCode);
            queryByWrapper.setEntity(queryItem);
            queryByWrapper.orderByDesc("trade_date");
            List<TEtf> itemList = tEtfMapper.selectList(queryByWrapper);

            HighestInPeriodResult result = getHighestInPeriodResult(itemList);
            if (null != result && result.getIntervalDays() > 30 && result.getVolume() > 10000000) {
                TEtfHigher entity = new TEtfHigher();
                entity.setIntervalDays(result.getIntervalDays());
                entity.setPreviousHighsDate(
                        TomDateUtil.date2LocalDate(result.getPreviousHighsDate()));
                entity.setStockCode(result.getStockCode());
                entity.setTradingDay(etf.getTradeDate());
                itEtfHigherService.save(entity);
            }

        }

    }

    @Override
    public List<TEtfHigherDTO> getHigherEtf(String currentDate) throws ParseException, IOException {
        TEtfHigher queryItem = new TEtfHigher();
        queryItem.setTradingDay(
                TomDateUtil.date2LocalDate(TomDateUtil.formateDayPattern2Date(currentDate)));
        Wrapper<TEtfHigher> wrapper = new QueryWrapper<>(queryItem).orderByAsc("interval_days");
        List<TEtfHigher> result = etfHigherMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(result)) {
            //如果当天没有，则获取最近一个交易日
            String lastTradingDate = mapper.queryLastTradingDate();
            queryItem.setTradingDay(
                    TomDateUtil.date2LocalDate(TomDateUtil.formateDayPattern2Date(lastTradingDate)));
            ((QueryWrapper<TEtfHigher>) wrapper).setEntity(queryItem);
            result = etfHigherMapper.selectList(wrapper);
        }

        List<TEtfHigherDTO> returnList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result)) {
            for (TEtfHigher item : result) {
                TEtfHigherDTO dto = new TEtfHigherDTO();
                BeanUtils.copyProperties(item, dto);
                dto.setStockName(itStockMainService.getStockNameByStockCode(item.getStockCode()));
                returnList.add(dto);
            }
        }

        return returnList;
    }

    @Override
    public List<TTransactionEtf> getTransactionAndClose2TenDayAvgEtfData(String currentDate) {
        List<TTransactionEtf> result = tEtfMapper.getTransactionAndClose2TenDayAvgEtfData(currentDate);
        if (CollectionUtils.isEmpty(result)) {
            //如果当天没有，则获取最近一个交易日
            String lastTradingDate = mapper.queryLastTradingDate();
            result = tEtfMapper.getTransactionAndClose2TenDayAvgEtfData(lastTradingDate);

        }
        return  result;
    }


    //当前天数，多少天内最高， 当一个比当前高的天数；
    private HighestInPeriodResult getHighestInPeriodResult(List<TEtf> list) {
        int intervalDays = 0;
        Date previousHighsDate = null;
        TEtf currentStockData = list.get(0);
        if (null == currentStockData.getHigh()) {
            return null;
        }

        for (int i = 1; i < list.size(); i++) {
            //验证数据的完整性
            if (null == list.get(i).getHigh()) {
                break;
            }
            if (currentStockData.getHigh() >= list.get(i).getHigh()) {
                intervalDays++;
            } else {
                previousHighsDate = TomDateUtil.LocalDate2date(list.get(i).getTradeDate());
                //找到大于当前股权的日期跳出循环
                break;
            }

        }
        HighestInPeriodResult result = new HighestInPeriodResult();
        result.setIntervalDays(intervalDays);
        result.setPreviousHighsDate(previousHighsDate);
        result.setStockCode(list.get(0).getStockCode());
        result.setVolume(currentStockData.getVolume());

        intervalDays = 0;
        previousHighsDate = null;
        return result;
    }

}
