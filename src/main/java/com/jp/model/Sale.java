package com.jp.model;

import java.util.ArrayList;
import java.util.List;

public class Sale {

    private final String product;
    private final long numberOfSales;
    private final double initialPrice;

    private double totalAdjustedValue;
    private double adjustedPrice;
    private List<Adjustment> adjustments;

    public Sale(String product, long numberOfSales, double price) {
        this.product = product;
        this.numberOfSales = numberOfSales;
        this.initialPrice = price;
        this.adjustedPrice = price;
        this.totalAdjustedValue = numberOfSales * price;
    }

    public String getProduct() {
        return product;
    }

    public long getNumberOfSales() {
        return numberOfSales;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public double getTotalAdjustedValue() {
        return totalAdjustedValue;
    }

    public double getAdjustedPrice() {
        return adjustedPrice;
    }

    public List<Adjustment> getAdjustments() {
        return adjustments;
    }

    public void addAdjustment(Adjustment adjustment) {

        if (adjustment == null || adjustment.getOperation() == null) {
            throw new IllegalArgumentException("Incorrect Adjustment specified, " + adjustment);
        }

        if (this.adjustments == null) {
           this.adjustments = new ArrayList<>();
        }

        this.adjustedPrice = adjustment.adjustPrice(this.adjustedPrice);
        this.totalAdjustedValue = this.adjustedPrice * this.numberOfSales;
        this.adjustments.add(adjustment);
    }

    @Override
    public String toString() {
        return "Sale{" + "product='" + product + '\'' + ", numberOfSales=" + numberOfSales + ", initialPrice="
               + initialPrice + ", totalAdjustedValue=" + totalAdjustedValue + ", adjustedPrice=" + adjustedPrice
               + ", adjustments=" + adjustments + '}';
    }
}
