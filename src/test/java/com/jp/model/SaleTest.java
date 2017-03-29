package com.jp.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SaleTest {

    @Test
    public void testAddAdjustment() {
        Sale sale = new Sale("apple", 3, 10);
        Adjustment adjustment = new Adjustment(AdjustmentOperation.ADD, 3);
        sale.addAdjustment(adjustment);
        assertEquals(sale.getAdjustedPrice(), 13, 0.01);
        assertEquals(sale.getTotalAdjustedValue(), 39, 0.01);
        assertNotNull(sale.getAdjustments());
        assertEquals(sale.getAdjustments().size(), 1);
        Adjustment recordedAdjustment = sale.getAdjustments().get(0);

        assertEquals(adjustment.getOperation(), recordedAdjustment.getOperation());
        assertEquals(adjustment.getValue(), recordedAdjustment.getValue(), 0.01);
    }

    @Test
    public void addSeveralAdjustments() {
        Sale sale = new Sale("apple", 20, 10);

        Adjustment adjustmentAdd = new Adjustment(AdjustmentOperation.ADD, 3);
        sale.addAdjustment(adjustmentAdd);
        Adjustment adjustmentMultiply = new Adjustment(AdjustmentOperation.MULTIPLY, 3);
        sale.addAdjustment(adjustmentMultiply);
        Adjustment adjustmentSubtract = new Adjustment(AdjustmentOperation.SUBTRACT, 3);
        sale.addAdjustment(adjustmentSubtract);

        assertEquals(sale.getAdjustedPrice(), 36, 0.01);
        assertEquals(sale.getTotalAdjustedValue(), 720, 0.01);
        assertNotNull(sale.getAdjustments());
        assertEquals(sale.getAdjustments().size(), 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullAdjustment() {
        Sale sale = new Sale("apple", 20, 10);
        sale.addAdjustment(null);
    }
}