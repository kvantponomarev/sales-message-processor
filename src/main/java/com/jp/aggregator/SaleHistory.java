package com.jp.aggregator;

import com.jp.message.AdjustmentSaleMessage;
import com.jp.message.SaleMessage;

import java.util.ArrayList;
import java.util.List;

public class SaleHistory {

    private final SaleMessage saleMessage;
    private final List<AdjustmentSaleMessage> adjustments;

    public SaleHistory(SaleMessage saleMessage) {
        this.saleMessage = saleMessage;
        this.adjustments = new ArrayList<AdjustmentSaleMessage>();
    }

    public SaleMessage getSaleMessage() {
        return saleMessage;
    }

    public List<AdjustmentSaleMessage> getAdjustments() {
        return adjustments;
    }

    public void addAdjustment(AdjustmentSaleMessage adjustmentSaleMessage) {
        this.adjustments.add(adjustmentSaleMessage);
    }

    @Override
    public String toString() {
        return "SaleHistory{" +
                "sale = " + saleMessage +
                ", adjustments=" + adjustments +
                '}';
    }
}
