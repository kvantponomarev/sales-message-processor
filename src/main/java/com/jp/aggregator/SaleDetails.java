package com.jp.aggregator;

public class SaleDetails {

    private long numberOfSales;
    private double totalValue;

    public SaleDetails(long numberOfSales, double totalValue) {
        this.numberOfSales = numberOfSales;
        this.totalValue = totalValue;
    }

    public long getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(long numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public void addSalesInformation(long numberOfSales, double price) {
        this.numberOfSales += numberOfSales;
        this.totalValue += (numberOfSales * price);
    }
}
