package com.jp.aggregator;

import com.jp.message.SaleMessage;

public interface MessageAggregator {
    void aggregateMessage(SaleMessage saleMessage);
    void printReport();
    void printAdjustmentReport();
}
