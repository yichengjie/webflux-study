package com.yicj.hello.service;

import com.yicj.hello.model.Stock;

import java.util.List;
import java.util.concurrent.Future;

public interface RStockService {

    List<Stock> getAllStock() ;

    Future<List<Stock>> getAllStockAsync() ;

    Future<List<Stock>> getAllStockAsync2() ;
}
