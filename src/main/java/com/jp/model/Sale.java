package com.jp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one Sale operation
 */
public class Sale {

    //Product Type
    private final String product;
    //Number of sales
    private final long numberOfSales;
    //Initial price before adjustments
    private final double initialPrice;

    //Total value after all adjustments
    private double totalAdjustedValue;
    //price after all adjustments
    private double adjustedPrice;
    //Lost of applied adjustments
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

    /**
     * Saves adjustment, recalculates adjustedPrice and totalAdjustedValue based on the adjustment specified
     * @param adjustment
     */
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
