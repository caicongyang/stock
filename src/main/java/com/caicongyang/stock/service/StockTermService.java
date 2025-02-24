package com.caicongyang.stock.service;

import com.caicongyang.stock.domain.StockTerm;
import java.util.List;

public interface StockTermService {
    List<StockTerm> listAllTerms();
    boolean addTerm(StockTerm term);
    boolean deleteTerm(Long id);
    List<StockTerm> getWordCloudData();
} 