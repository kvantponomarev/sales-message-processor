package com.jp.message;

public class BulkSaleMessage extends SaleMessage {

    private long numberOfSales;

    public long getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(long numberOfSales) {
        this.numberOfSales = numberOfSales;
    }
}
