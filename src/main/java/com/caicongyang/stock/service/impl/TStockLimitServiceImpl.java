package com.caicongyang.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caicongyang.stock.domain.*;
import com.caicongyang.stock.mapper.CommonMapper;
import com.caicongyang.stock.mapper.TStockLimitMapper;
import com.caicongyang.stock.service.ITStockLimitService;
import com.caicongyang.stock.service.ITStockMainService;
import com.caicongyang.stock.service.ITStockService;
import com.caicongyang.stock.service.StockService;
import com.caicongyang.stock.utils.TomDateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caicongyang
 * @since 2022-06-03
 */
@Service
public class TStockLimitServiceImpl extends ServiceImpl<TStockLimitMapper, TStockLimit> implements ITStockLimitService {

    private static final Logger logger = LoggerFactory.getLogger(TStockLimitServiceImpl.class);


    @Resource
    private ITStockService tStockService;

    @Resource
    StockService stockService;

    @Resource
    CommonMapper commonMapper;

    @Resource
    ITStockMainService itStockMainService;

    @Resource
    TStockLimitMapper limitMapper;


    public void catchAllDaliyLimitStock(String tradeDay) throws Exception {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doCtchAllDaliyLimitStock(tradeDay);
                } catch (Exception e) {
                    logger.error("执行失败", e);
                }
            }
        }).start();


    }

    private void doCtchAllDaliyLimitStock(String tradeDay) throws ParseException {
        Date date = TomDateUtil.formateDayPattern2Date(tradeDay);

        List<TStock> list = tStockService.list(new LambdaQueryWrapper<TStock>().eq(TStock::getTradeDate, date));
        List<TStockLimit> insertList = new ArrayList<>();
        for (TStock stock : list) {
            double currentGain = stock.getPctChg();
            if (currentGain >= 9.5) {
                TStockLimit limit = new TStockLimit();
                limit.setStockCode(stock.getStockCode());
                limit.setGain(currentGain);
                limit.setTradeDate(date);
                insertList.add(limit);
            }
        }
        this.saveBatch(insertList);
    }


    @Override
    public List<TStockLimitDTO> getIntervalLimitStockData(String startDate, String endDate) throws Exception {


        Date startLocalDate = TomDateUtil.formateDayPattern2Date(startDate);
        Date endLocalDate = TomDateUtil.formateDayPattern2Date(endDate);

//        List<TStockLimit> limitList = this.list(new LambdaQueryWrapper<TStockLimit>().between(true, TStockLimit::getTradingDay, startLocalDate, endLocalDate));

        HashMap map = new HashMap();
        map.put("startDate", startLocalDate);
        map.put("endDate", endLocalDate);
        List<TStockLimitDTO> limitList = limitMapper.getIntervalLimitStockData(map);


        if (CollectionUtils.isEmpty(limitList)) {
            return null;
        }

        List<TStockLimitDTO> reList = new LinkedList<>();


        for (TStockLimitDTO item : limitList) {
            TStockLimitDTO dto = new TStockLimitDTO();

//            TStockMain tStockMain = itStockMainService.getIndustryByStockCode(item.getStockCode());
//
//            BeanUtils.copyProperties(tStockMain, dto, getNullPropertyNames(tStockMain));
            BeanUtils.copyProperties(item, dto, getNullPropertyNames(item));
            reList.add(dto);
        }


        //java8 联合排序


        Comparator<TStockLimitDTO> counter = Comparator.nullsLast(Comparator
                .comparing(TStockLimitDTO::getCounter, Comparator.nullsLast(Comparator.reverseOrder())));

        Comparator<TStockLimitDTO> tradingDay = Comparator.nullsLast(Comparator
                .comparing(TStockLimitDTO::getTradeDate, Comparator.nullsLast(Comparator.reverseOrder())));


        // 联合排序
        Comparator<TStockLimitDTO> finalComparator = Comparator
                .nullsLast(tradingDay.thenComparing(counter));

        return reList.stream().sorted(finalComparator)
                .collect(Collectors.toList());
    }

    @Override
    public List<TStockLimitDTO> getLimitAndTransactionStockStock(String currentDate) throws Exception {

        String lastTradingDate = commonMapper.queryTradingDate(currentDate);

        Date tradingDay = TomDateUtil.formateDayPattern2Date(lastTradingDate);


        LocalDate startLocalDate = TomDateUtil.date2LocalDate(TomDateUtil.addDays(tradingDay, -30));
        LocalDate endLocalDate = TomDateUtil.date2LocalDate(TomDateUtil.addDays(tradingDay, -1));


        HashMap map = new HashMap();
        map.put("startDate", startLocalDate);
        map.put("endDate", endLocalDate);
        map.put("currentDate", lastTradingDate);

        List<TStockLimitDTO> limitList = limitMapper.getLimitAndTransactionStock(map);

        return limitList;


    }

    @Override
    public List<TransactionAndLimitStockDTO> getTransactionStockStockAndLimit(String currentDate) throws Exception {
        String lastTradingDate = commonMapper.queryTradingDate(currentDate);


        Date tradingDay = TomDateUtil.formateDayPattern2Date(lastTradingDate);


        LocalDate startLocalDate = TomDateUtil.date2LocalDate(TomDateUtil.addDays(tradingDay, -30));
        LocalDate endLocalDate = TomDateUtil.date2LocalDate(TomDateUtil.addDays(tradingDay, -1));

        HashMap map = new HashMap();
        map.put("startDate", startLocalDate);
        map.put("endDate", endLocalDate);
        map.put("currentDate", lastTradingDate);

        List<TransactionAndLimitStockDTO> limitList = limitMapper.getTransactionAndLimitStock(map);

        return limitList;
    }


    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set emptyNames = new HashSet();
        for (java.beans.PropertyDescriptor pd : pds) {
            //check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }


}
