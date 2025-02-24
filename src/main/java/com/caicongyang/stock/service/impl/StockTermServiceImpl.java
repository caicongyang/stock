package com.caicongyang.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.caicongyang.stock.domain.StockTerm;
import com.caicongyang.stock.mapper.StockTermMapper;
import com.caicongyang.stock.service.StockTermService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockTermServiceImpl implements StockTermService {
    @Autowired
    private StockTermMapper stockTermMapper;

    @Override
    public List<StockTerm> listAllTerms() {
        return stockTermMapper.selectList(
            new LambdaQueryWrapper<StockTerm>()
                .orderByDesc(StockTerm::getWeight)
        );
    }

    @Override
    public boolean addTerm(StockTerm term) {
        // 检查词条是否已存在
        if (isTermExists(term.getText())) {
            throw new RuntimeException("该词条已存在");
        }
        // 验证权重范围
        if (term.getWeight() < 1 || term.getWeight() > 100) {
            throw new RuntimeException("权重必须在1-100之间");
        }
        
        // 设置时间
        LocalDateTime now = LocalDateTime.now();
        term.setCreateTime(now);
        term.setUpdateTime(now);
        
        return stockTermMapper.insert(term) > 0;
    }

    @Override
    public boolean deleteTerm(Long id) {
        return stockTermMapper.deleteById(id) > 0;
    }

    @Override
    public List<StockTerm> getWordCloudData() {
        return listAllTerms();
    }

    private boolean isTermExists(String text) {
        return stockTermMapper.selectCount(
            new LambdaQueryWrapper<StockTerm>()
                .eq(StockTerm::getText, text)
        ) > 0;
    }
} 