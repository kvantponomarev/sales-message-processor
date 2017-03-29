package com.jp.aggregator;

import com.jp.message.SaleMessage;
import com.jp.model.Sale;

import java.util.List;
import java.util.Map;

/**
 * Aggregates incoming sales notifications.
 */
public interface MessageAggregator {
    /*
    Aggregate incoming sale notification.
     */
    void aggregateMessage(SaleMessage saleMessage);

    /**
     * Prints detailed sales report.
     */
    void printReport();

    /**
     * Prints Adjustments report
     */
    void printAdjustmentReport();

    /**
     * @return all recorded sales grouped by product.
     */
    Map<String, List<Sale>> getSales();
}
