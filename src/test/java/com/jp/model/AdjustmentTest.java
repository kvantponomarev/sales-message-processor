package com.jp.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AdjustmentTest {
    @Test
    public void testAddOperation() {
        Adjustment adjustment = new Adjustment(AdjustmentOperation.ADD, 10);
        assertEquals(adjustment.adjustPrice(4), 14, 0.01);
    }

    @Test
    public void testSubtractOperation() {
        Adjustment adjustment = new Adjustment(AdjustmentOperation.SUBTRACT, 10);
        assertEquals(adjustment.adjustPrice(14), 4, 0.01);
    }

    @Test(expected = RuntimeException.class)
    public void testIncorrectSubtract() {
        Adjustment adjustment = new Adjustment(AdjustmentOperation.SUBTRACT, 10);
        adjustment.adjustPrice(4);
    }

    @Test
    public void testMultiplyOperation() {
        Adjustment adjustment = new Adjustment(AdjustmentOperation.MULTIPLY, 10);
        assertEquals(adjustment.adjustPrice(2), 20, 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullOperation() {
        Adjustment adjustment = new Adjustment(null, 10);
    }
}