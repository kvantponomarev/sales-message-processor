package com.jp.aggregator;

import com.jp.message.SaleMessage;
import com.jp.model.Sale;

import java.util.List;
import java.util.Map;

public interface MessageAggregator {
    void aggregateMessage(SaleMessage saleMessage);
    void printReport();
    void printAdjustmentReport();
    Map<String, List<Sale>> getSales();
}
