package com.jp.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdjustmentOperationTest {

    @Test
    public void testAddOperation() {
        assertEquals(AdjustmentOperation.ADD.adjust(10, 3), 13, 0.01);
        assertEquals(AdjustmentOperation.ADD.adjust(0, 0), 0, 0.01);
    }

    @Test
    public void testSubtractOperation() {
        assertEquals(AdjustmentOperation.SUBTRACT.adjust(10, 3), 7, 0.01);
        assertEquals(AdjustmentOperation.SUBTRACT.adjust(0, 0), 0, 0.01);
    }

    @Test(expected = RuntimeException.class)
    public void testIncorrectSubtract() {
        AdjustmentOperation.SUBTRACT.adjust(10, 15);
    }

    @Test
    public void testMultiplyOperation() {
        assertEquals(AdjustmentOperation.MULTIPLY.adjust(10, 3), 30, 0.01);
        assertEquals(AdjustmentOperation.MULTIPLY.adjust(10, 0), 0, 0.01);
    }
}